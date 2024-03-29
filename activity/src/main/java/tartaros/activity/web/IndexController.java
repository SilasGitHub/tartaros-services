package tartaros.activity.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {
    @GetMapping("/activity")
    public void getAllActivities() {
        return
    }

    @GetMapping("/activity/{activityId}")
    public void getActivityById(@PathVariable Long activityId) {
        return
    }

    @GetMapping("/activity/{activityId}/signups")
    public void getSignupsForActivity(@PathVariable Long activityId) {
        return
    }

    @PostMapping("/activity")
    public void createActivity() {
        return
    }

    @PostMapping("/activity/{activityId}/signups")
    public void signUpForActivity() {
        return
    }

    @PutMapping("/activity/{activityId}")
    public void updateActivity(@PathVariable Long activityId) {
        return
    }

    @DeleteMapping("/activity/{activityId}")
    public void deleteActivityById(@PathVariable Long activityId) {
        return
    }

    @DeleteMapping("/activity/{activityId}/signups/{userId}")
    public void removeSignUpForActivity(@PathVariable Long activityId, @PathVariable Long userId) {
        return
    }
}
