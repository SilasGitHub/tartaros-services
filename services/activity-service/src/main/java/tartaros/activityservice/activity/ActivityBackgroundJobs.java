package tartaros.activityservice.activity;

import jakarta.inject.Inject;
import org.jobrunr.jobs.annotations.Job;
import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.scheduling.cron.Cron;
import org.springframework.stereotype.Service;

@Service
public class ActivityBackgroundJobs {
    @Inject
    private JobScheduler jobScheduler;

    @Inject
    private ActivityController activityController;

    public void main() {
        jobScheduler.scheduleRecurrently(Cron.every15seconds(), () -> activityController.activityDeadline());
    }
}
