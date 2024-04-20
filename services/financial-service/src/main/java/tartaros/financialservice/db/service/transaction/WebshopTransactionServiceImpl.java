package tartaros.financialservice.db.service.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tartaros.financialservice.db.entity.WebshopTransaction;
import tartaros.financialservice.db.repository.WebshopTransactionRepository;

import java.util.List;

// Annotation
@Service

// Class
public class WebshopTransactionServiceImpl
        implements WebshopTransactionService {

    @Autowired
    private WebshopTransactionRepository webshopTransactionRepository;

    @Override
    public WebshopTransaction saveWebshopTransaction(WebshopTransaction activityTransaction) {
        return webshopTransactionRepository.save(activityTransaction);
    }

    // Read operation
    @Override public List<WebshopTransaction> fetchWebshopTransactionList()
    {
        return (List<WebshopTransaction>)
                webshopTransactionRepository.findAll();
    }

    @Override
    public void deleteWebshopTransactionById(Long webshopTransactionId) {
        webshopTransactionRepository.deleteById(webshopTransactionId);
    }
}
