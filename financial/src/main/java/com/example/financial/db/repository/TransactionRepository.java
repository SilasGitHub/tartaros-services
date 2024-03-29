package com.example.financial.db.repository;


import com.example.financial.db.entity.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

// Annotation
@Repository

// Interface extending CrudRepository
public interface TransactionRepository
        extends CrudRepository<Transaction, Long> {
}
