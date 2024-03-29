package tartaros.financialservice.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tartaros.financialservice.db.entity.ActivityTransaction;

@Repository

public interface ActivityTransactionRepository
        extends CrudRepository<ActivityTransaction, Long> {
}