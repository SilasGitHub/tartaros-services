package tartaros.financialservice.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tartaros.financialservice.db.entity.MembershipType;

@Repository
public interface MembershipTypeRepository extends CrudRepository<MembershipType, Long> {
}
