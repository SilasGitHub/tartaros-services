package tartaros.activityservice.activity;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("google-service")
public interface GoogleClient {
    @PostMapping("/google/forms")
    String createForm();

    @GetMapping("/google/forms/{id}/numberOfResponses")
    int getNumberOfResponses(@PathVariable("id") String formId);
}
