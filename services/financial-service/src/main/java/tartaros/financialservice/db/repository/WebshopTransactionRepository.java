package tartaros.financialservice.db.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tartaros.financialservice.db.entity.WebshopTransaction;

import java.util.UUID;

@Repository

public interface WebshopTransactionRepository
        extends CrudRepository<WebshopTransaction, UUID> {
}