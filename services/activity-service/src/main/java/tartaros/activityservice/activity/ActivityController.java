package tartaros.activityservice.activity;

import com.google.api.services.forms.v1.model.FormResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import tartaros.activityservice.authentication.Authentication;
import tartaros.activityservice.authentication.JwtClaims;
import tartaros.activityservice.rabbitmq.publisher.RabbitMQProducer;
import tartaros.activityservice.transaction.Transaction;
import tartaros.activityservice.transaction.TransactionType;
import tartaros.activityservice.transaction.TransactionWrapper;


import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@EnableFeignClients
@RequestMapping("/activity")
class ActivityController {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private GoogleClient googleClient;

    @Autowired private RabbitMQProducer producer;

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityController.class);

    public ActivityController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Activity addActivity(@CookieValue(name="jwt", defaultValue = "") String token, @RequestBody Activity activity) {
        if (!Authentication.verifyAuthentication(token, true)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        activity.setCreatedBy(Authentication.getClaims(token).getEmail());
        activity.setExternalId(googleClient.createForm(activity.getCreatedBy()));
        return activityRepository.save(activity);
    }

    @GetMapping
    public Iterable<Activity> getActivities(@CookieValue(name="jwt", defaultValue = "") String token) {
        if (!Authentication.verifyAuthentication(token, false)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        return activityRepository.findAll();
    }

    @GetMapping("/{id}")
    public Activity getActivity(@CookieValue(name="jwt", defaultValue = "") String token, @PathVariable("id") UUID id) {
        if (!Authentication.verifyAuthentication(token, false)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        Optional<Activity> activity = activityRepository.findById(id);
        if (activity.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Activity not found"
            );
        }
        return activity.get();
    }

    @GetMapping("/{activityId}/response/{responseId}")
    public FormResponse getActivityResponse(@CookieValue(name="jwt", defaultValue = "") String token, @PathVariable("activityId") UUID activityId, @PathVariable("responseId") String responseId) {
        if (!Authentication.verifyAuthentication(token, false)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        Optional<Activity> activity = activityRepository.findById(activityId);
        if (activity.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Activity not found"
            );
        }
        FormResponse response = googleClient.getResponse(activity.get().getExternalId(), responseId);
        // If the response is null or the response has been marked as deleted, return a 404
        if (response == null || activity.get().getDeletedResponses().contains(responseId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Response not found"
            );
        }

        // non-admins can only see their own responses
        JwtClaims claims = Authentication.getClaims(token);
        if (!claims.isAdmin() && !claims.getEmail().equals(response.getRespondentEmail())) {
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(403), "Invalid credentials"
            );
        }

        return response;
    }

    @GetMapping("/{id}/response")
    public Iterable<FormResponse> getActivityResponses(@CookieValue(name="jwt", defaultValue = "") String token, @PathVariable("id") UUID id) {
        if (!Authentication.verifyAuthentication(token, false)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        Optional<Activity> activity = activityRepository.findById(id);
        if (activity.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Activity not found"
            );
        }
        List<FormResponse> responses = googleClient.getResponses(activity.get().getExternalId());

        if (responses == null) {
            return new ArrayList<>();
        }

        // Filter out the responses that were marked as deleted
        responses = responses.stream().filter(response -> !activity.get().getDeletedResponses().contains(response.getResponseId())).toList();

        List<String> visibleQuestions = activity.get().getVisibileQuestions();

        JwtClaims claims = Authentication.getClaims(token);
        if (!claims.isAdmin()) {
            responses = responses.stream().map(response -> {
                // Filter out the answers that should not be visible for public view
                response.setAnswers(response.getAnswers().entrySet().stream().filter(
                        answer -> visibleQuestions.contains(answer.getKey())).collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                );
                // Do not return the email of the respondent for public view
                response.setRespondentEmail(null);
                return response;
            }).toList();
        }

        return responses;
    }

    @GetMapping("/{id}/questions")
    public Iterable<FormQuestion> getQuestions(@CookieValue(name="jwt", defaultValue = "") String token, @PathVariable("id") UUID id) {
        if (!Authentication.verifyAuthentication(token, false)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        Optional<Activity> activity = activityRepository.findById(id);
        if (activity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity not found");
        }
        return googleClient.getQuestions(activity.get().getExternalId());
    }

    @PutMapping("/{id}")
    public Activity updateActivity(@CookieValue(name="jwt", defaultValue = "") String token, @PathVariable("id") UUID id, @RequestBody Activity activity) {
        if (!Authentication.verifyAuthentication(token, true)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        Optional<Activity> activityOptional = activityRepository.findById(id);
        if (activityOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity not found");
        }
        Activity updatedActivity = activityOptional.get();
        if (activity.getActivityStartDate() != null) {
            updatedActivity.setActivityStartDate(activity.getActivityStartDate());
        }
        if (activity.getActivityEndDate() != null) {
            updatedActivity.setActivityEndDate(activity.getActivityEndDate());
        }
        if (activity.getCommittee() != null) {
            updatedActivity.setCommittee(activity.getCommittee());
        }
        if (activity.getCreatedBy() != null) {
            updatedActivity.setCreatedBy(activity.getCreatedBy());
        }
        if (activity.getMaxParticipants() != null) {
            updatedActivity.setMaxParticipants(activity.getMaxParticipants());
        }
        if (activity.getPrice() != null) {
            updatedActivity.setPrice(activity.getPrice());
        }
        if (activity.getSignUpDeadline() != null) {
            updatedActivity.setSignUpDeadline(activity.getSignUpDeadline());
        }
        if (activity.getVisibileQuestions() != null) {
            updatedActivity.setVisibileQuestions(activity.getVisibileQuestions());
        }
        activityRepository.save(updatedActivity);
        return updatedActivity;
    }

    @DeleteMapping("/{id}")
    public void deleteActivity(@CookieValue(name="jwt", defaultValue = "") String token, @PathVariable("id") UUID id) {
        if (!Authentication.verifyAuthentication(token, true)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        Optional<Activity> activity = activityRepository.findById(id);
        if (activity.isPresent()) {
            activityRepository.delete(activity.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity not found");
        }
    }

    @DeleteMapping("/{activityId}/response/{responseId}")
    public void cancelParticipation(@CookieValue(name="jwt", defaultValue = "") String token, @PathVariable("activityId") UUID activityId, @PathVariable("responseId") String responseId) {
        if (!Authentication.verifyAuthentication(token, false)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        // Since it is currently impossible to delete a response from a form using the Google Forms API,
        // we will instead mark the response as deleted in the database
        Optional<Activity> optionalActivity = activityRepository.findById(activityId);
        if (optionalActivity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity not found");
        }

        FormResponse response = googleClient.getResponse(optionalActivity.get().getExternalId(), responseId);
        if (response == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Response not found"
            );
        }

        // non-admins can only delete their own responses
        JwtClaims claims = Authentication.getClaims(token);
        if (!claims.isAdmin() && !claims.getEmail().equals(response.getRespondentEmail())) {
            throw new ResponseStatusException(
                    HttpStatusCode.valueOf(403), "Invalid credentials"
            );
        }

        Activity activity = optionalActivity.get();
        List<String> deletedResponses = activity.getDeletedResponses();
        deletedResponses.add(responseId);
        activity.setDeletedResponses(deletedResponses);

        activityRepository.save(activity);
    }

    public void activityDeadline() {
        Flux<Activity> activities = Flux.fromIterable(activityRepository.findAll())
                .filter(activity ->
                        activity.getSignUpDeadline().isBefore(Instant.now()) &&
                        !activity.isProcessed());
        for (Activity activity : activities.toIterable()) {
            LOGGER.info("Processing activity: " + activity.getTitle());
            List<FormResponse> responses = googleClient.getResponses(activity.getExternalId());
            if (responses == null) {
                LOGGER.info("No responses found for activity: " + activity.getTitle());
                activity.setProcessed(true);
                activityRepository.save(activity);
                continue;
            }
            responses = responses.stream().filter(
                    response -> !activity.getDeletedResponses().contains(response.getResponseId())
                            && Instant.parse(response.getCreateTime()).isBefore(activity.getSignUpDeadline())
            ).toList();
            for (FormResponse response : responses) {
                Transaction transaction = new Transaction();
                transaction.setAmount(activity.getPrice());
                transaction.setMemberEmail(response.getRespondentEmail());
                transaction.setDescription("Participation fee for activity: " + activity.getTitle());
                transaction.setPaid(false);
                TransactionType transactionType = new TransactionType();
                transactionType.setActivityId(activity.getActivityId());
                TransactionWrapper transactionWrapper = new TransactionWrapper();
                transactionWrapper.setTransaction(transaction);
                transactionWrapper.setTransaction_type(transactionType);
                producer.sendTransaction(transactionWrapper);
            }
            activity.setProcessed(true);
            activityRepository.save(activity);
        }
    }
}
