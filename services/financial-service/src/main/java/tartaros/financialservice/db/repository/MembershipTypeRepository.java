package tartaros.financialservice.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tartaros.financialservice.db.entity.MembershipType;

import java.util.UUID;

@Repository
public interface MembershipTypeRepository extends CrudRepository<MembershipType, UUID> {
}
