package tartaros.financialservice.db.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tartaros.financialservice.db.entity.ActivityTransaction;
import tartaros.financialservice.db.entity.Transaction;

import java.util.Collection;
import java.util.UUID;

@Repository

public interface ActivityTransactionRepository
        extends CrudRepository<ActivityTransaction, UUID> {
    long deleteByTransaction(Transaction transaction);
}