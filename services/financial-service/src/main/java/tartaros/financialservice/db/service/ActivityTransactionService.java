package tartaros.financialservice.db.service;

import tartaros.financialservice.db.entity.ActivityTransaction;
import tartaros.financialservice.db.entity.Transaction;

import java.util.List;
import java.util.UUID;

// Interface
public interface ActivityTransactionService {

    // Save operation
    ActivityTransaction saveActivityTransaction(ActivityTransaction activityTransaction);

    // Read operation
    List<ActivityTransaction> fetchActivityTransactionList();

    // Delete operation
    void deleteTransactionById(Long transactionId);
}
