package com.example.financial.db.service;
import com.example.financial.db.entity.ActivityTransaction;
import com.example.financial.db.entity.Transaction;
import com.example.financial.db.entity.WebshopTransaction;

import java.util.List;
import java.util.UUID;

// Interface
public interface WebshopTransactionService {

    // Save operation
    WebshopTransaction saveActivityTransaction(WebshopTransaction activityTransaction);

    // Delete operation
    void deleteTransactionById(Long transactionId);
}
