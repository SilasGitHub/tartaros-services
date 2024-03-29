package tartaros.activityservice.activity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import tartaros.activityservice.transaction.Transaction;
import tartaros.activityservice.transaction.TransactionType;
import tartaros.activityservice.transaction.TransactionWrapper;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@EnableFeignClients
class ActivityController {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private GoogleClient googleClient;

    @Autowired
    private JmsTemplate jmsTemplate;

    public ActivityController(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @PostMapping("/activity")
    @ResponseStatus(HttpStatus.CREATED)
    public Activity addActivity(@RequestBody Activity activity) {
        activity.setExternalId(googleClient.createForm());
        return activityRepository.save(activity);
    }

    @GetMapping("/activity")
    public Iterable<Activity> getActivities() {
        return activityRepository.findAll();
    }

    @DeleteMapping("/activity/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable("id") UUID id) {
        Optional<Activity> activity = activityRepository.findById(id);
        if (activity.isPresent()) {
            activityRepository.delete(activity.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public void activityDeadline() {
        Flux<Activity> activities = Flux.fromIterable(activityRepository.findAll()).filter(activity -> activity.getSignUpDeadline().isBefore(LocalDateTime.now()));
        int i = 0;
        for (Activity activity : activities.toIterable()) {
            googleClient.getNumberOfResponses(activity.getExternalId());
            Transaction transaction = new Transaction();
            transaction.setAmount(activity.getPrice());
            transaction.setMemberId((long) i);
            transaction.setDescription("Test transaction");
            transaction.setPaid(false);
            TransactionType transactionType = new TransactionType();
            transactionType.setActivityId((long) i);
            i++;
            TransactionWrapper transactionWrapper = new TransactionWrapper();
            transactionWrapper.setTransaction(transaction);
            transactionWrapper.setTransaction_type(transactionType);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            try {
                String json = ow.writeValueAsString(transactionWrapper);
                jmsTemplate.convertAndSend("transactions", json);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

    }
}
