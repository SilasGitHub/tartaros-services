package com.example.financial.db.repository;


import com.example.financial.db.entity.WebshopTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface WebshopTransactionRepository
        extends CrudRepository<WebshopTransaction, Long> {
}