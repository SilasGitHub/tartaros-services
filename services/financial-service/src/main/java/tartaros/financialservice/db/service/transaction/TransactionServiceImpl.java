package tartaros.financialservice.db.service.transaction;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tartaros.financialservice.db.entity.Transaction;
import tartaros.financialservice.db.repository.TransactionRepository;

// Annotation
@Service

// Class
public class TransactionServiceImpl
        implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    // Save operation
    @Override
    public Transaction saveTransaction(Transaction transaction)
    {
        return transactionRepository.save(transaction);
    }

    // Read operation
    @Override public List<Transaction> fetchTransactionList()
    {
        return (List<Transaction>)
                transactionRepository.findAll();
    }

    // Update operation
    @Override
    public Transaction
    updateTransaction(Transaction transaction,
                     UUID transactionId)
    {
        Transaction depDB
                = transactionRepository.findById(transactionId)
                .get();

        if (transaction.isPaid()) {
            depDB.setPaid(
                    transaction.isPaid());
        }

        if (Objects.nonNull(transaction.getTransactionTime())) {
            depDB.setTransactionTime(transaction.getTransactionTime());
        }

        if (Objects.nonNull(transaction.getMemberEmail())) {
            depDB.setMemberEmail(
                    transaction.getMemberEmail());
        }

        if (Objects.nonNull(
                transaction.getAmount())) {
            depDB.setAmount(
                    transaction.getAmount());
        }

        return transactionRepository.save(depDB);
    }

    @Override
    public Transaction getTransactionById(UUID transactionId) {
        return transactionRepository.findById(transactionId).get();
    }

    // Delete operation
    @Override
    public void deleteTransactionById(UUID transactionId)
    {
        transactionRepository.deleteById(transactionId);
    }
}
