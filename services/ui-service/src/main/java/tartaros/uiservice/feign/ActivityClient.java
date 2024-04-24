package tartaros.uiservice.feign;

import com.google.api.services.forms.v1.model.FormResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import tartaros.uiservice.model.Activity;
import tartaros.uiservice.model.FormQuestion;

import java.util.List;

@FeignClient("activity-service")
public interface ActivityClient {
    @PostMapping("/activity")
    Activity createActivity(@RequestHeader("Cookie") String cookie, Activity activity);

    @GetMapping("/activity")
    List<Activity> getActivities(@RequestHeader("Cookie") String cookie);

    @GetMapping("/activity/{id}")
    Activity getActivity(@RequestHeader("Cookie") String cookie, @PathVariable String id);

    @GetMapping("/activity/{activityId}/response/{responseId}")
    FormResponse getActivityResponse(@RequestHeader("Cookie") String cookie, @PathVariable("activityId") String activityId, @PathVariable("responseId") String responseId);

    @GetMapping("/activity/{activityId}/response")
    List<FormResponse> getActivityResponses(@RequestHeader("Cookie") String cookie, @PathVariable("activityId") String activityId);

    @GetMapping("/activity/{id}/questions")
    List<FormQuestion> getQuestions(@RequestHeader("Cookie") String cookie, @PathVariable("id") String id);

    @PutMapping("/activity/{id}")
    Activity updateActivity(@RequestHeader("Cookie") String cookie, @PathVariable String id, Activity activity);

    @DeleteMapping("/activity/{id}")
    void deleteActivity(@RequestHeader("Cookie") String cookie, @PathVariable String id);

    @GetMapping("/activity/{activityId}/signup")
    String hasSignedUp(@RequestHeader("Cookie") String cookie, @PathVariable("activityId") String activityId, @RequestParam("email") String email);

    @DeleteMapping("/activity/{activityId}/response/{responseId}")
    void cancelParticipation(@RequestHeader("Cookie") String cookie, @PathVariable("activityId") String activityId, @PathVariable("responseId") String responseId);
}