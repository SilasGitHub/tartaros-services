package tartaros.financialservice.db.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tartaros.financialservice.db.entity.Transaction;

import java.util.UUID;

// Annotation
@Repository

// Interface extending CrudRepository
public interface TransactionRepository
        extends CrudRepository<Transaction, Long> {
}
