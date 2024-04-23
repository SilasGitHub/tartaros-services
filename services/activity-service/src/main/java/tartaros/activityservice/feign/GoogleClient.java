package tartaros.activityservice.feign;

import com.google.api.services.forms.v1.model.FormResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import tartaros.activityservice.model.FormQuestion;

import java.util.List;

@FeignClient("google-service")
public interface GoogleClient {
    @PostMapping("/google/forms/{createdBy}")
    String createForm(@PathVariable("createdBy") String createdBy);

    @GetMapping("/google/forms/{id}/responses")
    List<FormResponse> getResponses(@PathVariable("id") String formId);

    @GetMapping("/google/forms/{activityId}/response/{responseId}")
    FormResponse getResponse(@PathVariable("activityId") String formId, @PathVariable("responseId") String responseId);

    @GetMapping("/google/forms/{id}/questions")
    List<FormQuestion> getQuestions(@PathVariable("id") String formId);
}
