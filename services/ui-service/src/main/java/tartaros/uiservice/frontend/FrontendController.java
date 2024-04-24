package tartaros.uiservice.frontend;

import com.google.api.services.forms.v1.model.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import tartaros.uiservice.authentication.Authentication;
import tartaros.uiservice.authentication.JwtClaims;
import tartaros.uiservice.feign.ActivityClient;
import tartaros.uiservice.feign.FinancialClient;
import tartaros.uiservice.model.*;

import java.io.IOException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class FrontendController {

    @Value("${gateway.host}")
    private String gatewayHost;

    @Value("${gateway.port}")
    private String gatewayPort;

    @Autowired
    private ActivityClient activityClient;

    @Autowired
    private FinancialClient financialClient;

    @GetMapping("/")
    public String index(@CookieValue(name="jwt", defaultValue = "") String token, Model model) {
        boolean loggedIn = Authentication.verifyAuthentication(token, false);
        if (!loggedIn) {
            return "index";
        }

        JwtClaims claims = Authentication.getClaims(token);
        model.addAttribute("name", claims.getName());
        model.addAttribute("isAdmin", claims.isAdmin());

        return "member";
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.setStatus(HttpStatus.SEE_OTHER.value());
        // Return to index page
        response.sendRedirect("http://" + gatewayHost + ":" + gatewayPort);
    }

    @GetMapping("/activities")
    public String activities(@CookieValue(name="jwt", defaultValue = "") String token, Model model) {
        boolean loggedIn = Authentication.verifyAuthentication(token, false);
        if (!loggedIn) {
            return "index";
        }

        List<Activity> result = activityClient.getActivities("jwt=" + token);
        model.addAttribute("activities", result);

        return "activities";
    }

    @GetMapping("/activities/{id}")
    public String activities(@CookieValue(name="jwt", defaultValue = "") String token, Model model, @PathVariable("id") String id) {
        boolean loggedIn = Authentication.verifyAuthentication(token, false);
        if (!loggedIn) {
            return "index";
        }

        Activity activity = activityClient.getActivity("jwt=" + token, id);
        model.addAttribute("activity", activity);
        model.addAttribute("deadlinePassed", activity.getSignUpDeadline() != null && activity.getSignUpDeadline().isBefore(Instant.now()));

        List<String> visibleQuestions = activity.getVisibleQuestions();
        if (visibleQuestions == null || visibleQuestions.isEmpty()) {
            model.addAttribute("participantsLimitReached", false);
            return "activity";
        }

        List<FormQuestion> questions = activityClient.getQuestions("jwt=" + token, id);
        questions = questions.stream().filter(q -> visibleQuestions.contains(q.getQuestionId())).toList();
        model.addAttribute("questions", questions);

        List<FormResponse> responses = activityClient.getActivityResponses("jwt=" + token, id);
        model.addAttribute("participantsLimitReached", activity.getMaxParticipants() != null && responses.size() >= activity.getMaxParticipants() && activity.getMaxParticipants() != 0);

        List<List<FormAnswer>> visibleResponses = new ArrayList<>();
        for (FormResponse response : responses) {
            List<FormAnswer> visibleResponse = new ArrayList<>();
            for (FormQuestion question : questions) {
                Map<String, Object> answerValue = response.getAnswers().get(question.getQuestionId());
                if (answerValue == null) {
                    visibleResponse.add(new FormAnswer(question.getQuestionId(), ""));
                }
                List<Map<String, String>> textAnswersToParse = ((Map<String, List<Map<String, String>>>) answerValue.get("textAnswers")).get("answers");

                List<String> answers = new ArrayList<>();
                for (Map<String, String> textAnswerToParse : textAnswersToParse) {
                    for (Map.Entry<String, String> entry : textAnswerToParse.entrySet()) {
                        answers.add(entry.getValue());
                    }
                }
                visibleResponse.add(new FormAnswer(question.getQuestionId(), String.join(", ", answers)));
            }
            visibleResponses.add(visibleResponse);
        }
        model.addAttribute("responses", visibleResponses);

        String responseId = activityClient.hasSignedUp("jwt=" + token, id, Authentication.getClaims(token).getEmail());
        model.addAttribute("responseId", responseId);

        return "activity";
    }

    @GetMapping("/financials")
    public String financials(@CookieValue(name="jwt", defaultValue = "") String token, Model model) {
        boolean loggedIn = Authentication.verifyAuthentication(token, false);
        if (!loggedIn) {
            return "index";
        }

        List<MembershipType> membershipTypes = financialClient.getMembershipTypes("jwt=" + token);
        Map<UUID, MembershipType> membershipTypesMap = new HashMap<>();
        for (MembershipType membershipType : membershipTypes) {
            membershipTypesMap.put(membershipType.getMembershipTypeId(), membershipType);
        }

        List<Membership> memberships = financialClient.getMemberships("jwt=" + token);
        memberships = memberships.stream().filter(m -> m.getMemberEmail().equals(Authentication.getClaims(token).getEmail())).toList();
        memberships.stream().forEach(m -> m.setMembershipType(membershipTypesMap.get(m.getMembershipTypeId())));
        model.addAttribute("memberships", memberships);

        List<Transaction> transactions = financialClient.getTransactions("jwt=" + token);
        model.addAttribute("transactions", transactions);

        return "financials";
    }

    @GetMapping("/admin/activities")
    public String adminActivities(@CookieValue(name="jwt", defaultValue = "") String token, Model model) {
        boolean loggedIn = Authentication.verifyAuthentication(token, true);
        if (!loggedIn) {
            return "index";
        }

        List<Activity> activities = activityClient.getActivities("jwt=" + token);
        model.addAttribute("activities", activities);

        return "admin-activities";
    }

    @GetMapping("/admin/activities/{id}")
    public String adminActivities(@CookieValue(name="jwt", defaultValue = "") String token, Model model, @PathVariable("id") String id) {
        boolean loggedIn = Authentication.verifyAuthentication(token, true);
        if (!loggedIn) {
            return "index";
        }

        Activity activity = activityClient.getActivity("jwt=" + token, id);
        model.addAttribute("activity", activity);

        List<FormQuestion> questions = activityClient.getQuestions("jwt=" + token, id);
        model.addAttribute("questions", questions);

        List<FormResponse> responses = activityClient.getActivityResponses("jwt=" + token, id);

        List<List<FormAnswer>> visibleResponses = new ArrayList<>();
        for (FormResponse response : responses) {
            List<FormAnswer> visibleResponse = new ArrayList<>();
            for (FormQuestion question : questions) {
                Map<String, Object> answerValue = response.getAnswers().get(question.getQuestionId());
                if (answerValue == null) {
                    visibleResponse.add(new FormAnswer(question.getQuestionId(), ""));
                }
                List<Map<String, String>> textAnswersToParse = ((Map<String, List<Map<String, String>>>) answerValue.get("textAnswers")).get("answers");

                List<String> answers = new ArrayList<>();
                for (Map<String, String> textAnswerToParse : textAnswersToParse) {
                    for (Map.Entry<String, String> entry : textAnswerToParse.entrySet()) {
                        answers.add(entry.getValue());
                    }
                }
                visibleResponse.add(new FormAnswer(question.getQuestionId(), String.join(", ", answers)));
            }
            visibleResponses.add(visibleResponse);
        }
        model.addAttribute("responses", visibleResponses);

        return "admin-activity";
    }

    @GetMapping("/admin/transactions")
    public String adminTransactions(@CookieValue(name="jwt", defaultValue = "") String token, Model model) {
        boolean loggedIn = Authentication.verifyAuthentication(token, true);
        if (!loggedIn) {
            return "index";
        }

        List<Transaction> transactions = financialClient.getTransactions("jwt=" + token);

        List<MembershipTransaction> membershipTransactions = financialClient.getAllMembershipTransactions("jwt=" + token);

        List<MembershipType> membershipTypes = financialClient.getMembershipTypes("jwt=" + token);
        Map<UUID, MembershipType> membershipTypesMap = new HashMap<>();
        for (MembershipType membershipType : membershipTypes) {
            membershipTypesMap.put(membershipType.getMembershipTypeId(), membershipType);
        }
        for (MembershipTransaction membershipTransaction : membershipTransactions) {
            membershipTransaction.setMembershipType(membershipTypesMap.get(membershipTransaction.getMembershipTypeId()));
        }

        List<ActivityTransaction> activityTransactions = financialClient.getAllActivityTransactions("jwt=" + token);

        List<Activity> activities = activityClient.getActivities("jwt=" + token);
        Map<UUID, Activity> activitiesMap = new HashMap<>();
        for (Activity activity : activities) {
            activitiesMap.put(activity.getActivityId(), activity);
        }
        for (ActivityTransaction activityTransaction : activityTransactions) {
            activityTransaction.setActivity(activitiesMap.get(activityTransaction.getActivityId()));
        }

        Set<UUID> transactionIds = membershipTransactions.stream().map(mt -> mt.getTransaction().getTransactionId()).collect(Collectors.toSet());
        transactionIds.addAll(activityTransactions.stream().map(at -> at.getTransaction().getTransactionId()).collect(Collectors.toSet()));

        List<Transaction> remainingTransactions = transactions.stream().filter(t -> !transactionIds.contains(t.getTransactionId())).toList();

        model.addAttribute("membershipTransactions", membershipTransactions);
        model.addAttribute("activityTransactions", activityTransactions);
        model.addAttribute("remainingTransactions", remainingTransactions);

        return "admin-transactions";
    }

    @GetMapping("/admin/memberships")
    public String adminMemberships(@CookieValue(name="jwt", defaultValue = "") String token, Model model) {
        boolean loggedIn = Authentication.verifyAuthentication(token, true);
        if (!loggedIn) {
            return "index";
        }

        List<MembershipType> membershipTypes = financialClient.getMembershipTypes("jwt=" + token);
        Map<UUID, MembershipType> membershipTypesMap = new HashMap<>();
        for (MembershipType membershipType : membershipTypes) {
            membershipTypesMap.put(membershipType.getMembershipTypeId(), membershipType);
        }
        model.addAttribute("membershipTypes", membershipTypes);

        List<Membership> memberships = financialClient.getMemberships("jwt=" + token);
        memberships.stream().forEach(m -> m.setMembershipType(membershipTypesMap.get(m.getMembershipTypeId())));
        model.addAttribute("memberships", memberships);

        return "admin-memberships";
    }

    @GetMapping("/admin/membershipTypes")
    public String adminMembershipTypes(@CookieValue(name="jwt", defaultValue = "") String token, Model model) {
        boolean loggedIn = Authentication.verifyAuthentication(token, true);
        if (!loggedIn) {
            return "index";
        }

        List<MembershipType> membershipTypes = financialClient.getMembershipTypes("jwt=" + token);
        model.addAttribute("membershipTypes", membershipTypes);

        return "admin-membership-types";
    }
}

