package com.example.financial.db.service;
import com.example.financial.db.entity.ActivityTransaction;
import com.example.financial.db.entity.Transaction;

import java.util.List;
import java.util.UUID;

// Interface
public interface ActivityTransactionService {

    // Save operation
    ActivityTransaction saveActivityTransaction(ActivityTransaction activityTransaction);

    // Delete operation
    void deleteTransactionById(Long transactionId);
}
