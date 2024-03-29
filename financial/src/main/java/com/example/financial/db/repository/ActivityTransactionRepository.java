package com.example.financial.db.repository;

import com.example.financial.db.entity.ActivityTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ActivityTransactionRepository
        extends CrudRepository<ActivityTransaction, Long> {
}