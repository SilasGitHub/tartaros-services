package com.example.financial.db.service;

import com.example.financial.db.entity.ActivityTransaction;
import com.example.financial.db.entity.WebshopTransaction;
import com.example.financial.db.repository.ActivityTransactionRepository;
import com.example.financial.db.repository.WebshopTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Annotation
@Service

// Class
public class ActivityTransactionServiceImpl
        implements ActivityTransactionService {

    @Autowired
    private ActivityTransactionRepository activityTransactionRepository;
    @Override
    public ActivityTransaction saveActivityTransaction(ActivityTransaction activityTransaction) {
        return activityTransactionRepository.save(activityTransaction);
    }

    @Override
    public void deleteTransactionById(Long transactionId) {
        activityTransactionRepository.deleteById(transactionId);

    }
}
