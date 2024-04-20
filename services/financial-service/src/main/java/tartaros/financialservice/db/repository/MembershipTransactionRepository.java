package tartaros.financialservice.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tartaros.financialservice.db.entity.MembershipTransaction;
import tartaros.financialservice.db.entity.Transaction;

import java.util.UUID;

@Repository
public interface MembershipTransactionRepository extends CrudRepository<MembershipTransaction, Long> {
}
