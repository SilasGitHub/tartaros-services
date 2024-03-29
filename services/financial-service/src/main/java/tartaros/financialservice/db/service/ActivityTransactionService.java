package tartaros.financialservice.db.service;

import tartaros.financialservice.db.entity.ActivityTransaction;

import java.util.List;
import java.util.UUID;

// Interface
public interface ActivityTransactionService {

    // Save operation
    ActivityTransaction saveActivityTransaction(ActivityTransaction activityTransaction);

    // Delete operation
    void deleteTransactionById(Long transactionId);
}
