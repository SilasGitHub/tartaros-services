package tartaros.financialservice.db.service;

import tartaros.financialservice.db.entity.ActivityTransaction;
import tartaros.financialservice.db.entity.WebshopTransaction;

import java.util.List;
import java.util.UUID;

// Interface
public interface WebshopTransactionService {

    // Save operation
    WebshopTransaction saveWebshopTransaction(WebshopTransaction webshopTransaction);

    // Read operation
    List<WebshopTransaction> fetchWebshopTransactionList();

    // Delete operation
    void deleteTransactionById(Long transactionId);
}
