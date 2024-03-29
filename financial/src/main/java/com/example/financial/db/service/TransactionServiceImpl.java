package com.example.financial.db.service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.example.financial.db.entity.Transaction;
import com.example.financial.db.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Annotation
@Service

// Class
public class TransactionServiceImpl
        implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    // Save operation
    @Override
    public Transaction saveTransaction(Transaction transaction)
    {
        return transactionRepository.save(transaction);
    }

    // Read operation
    @Override public List<Transaction> fetchTransactionList()
    {
        return (List<Transaction>)
                transactionRepository.findAll();
    }

    // Update operation
    @Override
    public Transaction
    updateTransaction(Transaction transaction,
                     Long transactionId)
    {
        Transaction depDB
                = transactionRepository.findById(transactionId)
                .get();

        if (Objects.nonNull(transaction.getMemberId())) {
            depDB.setMemberId(
                    transaction.getMemberId());
        }

        if (Objects.nonNull(
                transaction.getAmount())) {
            depDB.setAmount(
                    transaction.getAmount());
        }

        return transactionRepository.save(depDB);
    }

    // Delete operation
    @Override
    public void deleteTransactionById(Long transactionId)
    {
        transactionRepository.deleteById(transactionId);
    }
}
