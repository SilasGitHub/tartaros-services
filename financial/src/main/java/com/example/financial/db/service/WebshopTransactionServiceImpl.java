package com.example.financial.db.service;

import com.example.financial.db.entity.WebshopTransaction;
import com.example.financial.db.repository.WebshopTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Annotation
@Service

// Class
public class WebshopTransactionServiceImpl
        implements WebshopTransactionService {

    @Autowired
    private WebshopTransactionRepository webshopTransactionRepository;
    @Override
    public WebshopTransaction saveActivityTransaction(WebshopTransaction webshopTransaction) {
        return webshopTransactionRepository.save(webshopTransaction);
    }

    @Override
    public void deleteTransactionById(Long transactionId) {
        webshopTransactionRepository.deleteById(transactionId);

    }
}
