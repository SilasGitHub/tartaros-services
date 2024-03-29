package tartaros.financialservice.db.service;

import tartaros.financialservice.db.entity.WebshopTransaction;

import java.util.List;
import java.util.UUID;

// Interface
public interface WebshopTransactionService {

    // Save operation
    WebshopTransaction saveActivityTransaction(WebshopTransaction activityTransaction);

    // Delete operation
    void deleteTransactionById(Long transactionId);
}
