package tartaros.authservice.authentication;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface WorkspaceUserRepository extends CrudRepository<WorkspaceUser, UUID> {
}
