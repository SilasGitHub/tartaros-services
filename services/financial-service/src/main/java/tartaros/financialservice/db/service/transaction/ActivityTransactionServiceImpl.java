package tartaros.financialservice.db.service.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tartaros.financialservice.db.entity.ActivityTransaction;
import tartaros.financialservice.db.repository.ActivityTransactionRepository;

import java.util.List;

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

    // Read operation
    @Override public List<ActivityTransaction> fetchActivityTransactionList()
    {
        return (List<ActivityTransaction>)
                activityTransactionRepository.findAll();
    }
}
