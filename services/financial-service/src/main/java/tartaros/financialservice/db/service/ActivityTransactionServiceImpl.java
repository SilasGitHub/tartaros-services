package tartaros.financialservice.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tartaros.financialservice.db.entity.ActivityTransaction;
import tartaros.financialservice.db.repository.ActivityTransactionRepository;

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
