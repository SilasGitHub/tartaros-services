package tartaros.financialservice.db.service;

import tartaros.financialservice.db.entity.Transaction;

import java.util.List;
import java.util.UUID;

// Interface
public interface TransactionService {

    // Save operation
    Transaction saveTransaction(Transaction transaction);

    // Read operation
    List<Transaction> fetchTransactionList();

    // Update operation
    Transaction updateTransaction(Transaction department,
                                Long transactionId);

    // Delete operation
    void deleteTransactionById(Long transactionId);
}
