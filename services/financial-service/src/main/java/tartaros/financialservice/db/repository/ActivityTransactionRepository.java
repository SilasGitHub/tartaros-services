package tartaros.financialservice.db.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tartaros.financialservice.db.entity.ActivityTransaction;

import java.util.Collection;

@Repository

public interface ActivityTransactionRepository
        extends CrudRepository<ActivityTransaction, Long> {

    @Query(value="SELECT at.activityId, t.transactionId, t.memberId, t.amount, t.paid, at.activityTransactionId FROM ActivityTransaction at inner join at.transaction t", nativeQuery=true)
    Collection<Object> fetchActivityTransactions();
}