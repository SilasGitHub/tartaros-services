package tartaros.activityservice.activity;


import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ActivityRepository extends CrudRepository<Activity, UUID> {
}
