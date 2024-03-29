package tartaros.financialservice.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tartaros.financialservice.db.entity.WebshopTransaction;
import tartaros.financialservice.db.repository.WebshopTransactionRepository;

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
