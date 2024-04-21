package tartaros.financialservice.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tartaros.financialservice.db.entity.Membership;

import java.util.UUID;

@Repository
public interface MembershipRepository extends CrudRepository<Membership, UUID> {
}
