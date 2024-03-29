package tartaros.activityservice.activity;

import jakarta.inject.Inject;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@EnableScheduling
@Configuration
public class ActivityConfiguration {

    @Inject
    private ActivityController activityController;
    @Scheduled(fixedDelay = 15000)
    public void activityDeadlineTask() {
        activityController.activityDeadline();
    }
}
