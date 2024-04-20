package tartaros.financialservice.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tartaros.financialservice.db.entity.ActivityTransaction;
import tartaros.financialservice.db.entity.Transaction;
import tartaros.financialservice.db.entity.TransactionWrapper;
import tartaros.financialservice.db.entity.WebshopTransaction;
import tartaros.financialservice.db.repository.ActivityTransactionRepository;
import tartaros.financialservice.db.repository.TransactionRepository;
import tartaros.financialservice.db.service.ActivityTransactionService;
import tartaros.financialservice.db.service.TransactionService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class IndexController {

    @Autowired private TransactionService transactionService;
    @Autowired private ActivityTransactionService activityTransactionService;
    @Autowired private TransactionRepository transactionRepository;
    @Autowired private ActivityTransactionRepository activityTransactionRepository;

    @GetMapping("/transaction")
    public void getAllTransactions() {
    }

    @GetMapping("/transaction/membership")
    public void getAllMembershipTransactions() {
    }

    @PostMapping("/financial/activity")
    public TransactionWrapper createActivityTranaction(@RequestBody TransactionWrapper wrapper) {
        transactionService.saveTransaction(wrapper.getTransaction());
        ObjectMapper mapper = new JsonMapper();
        try {
            ActivityTransaction activityTransaction = mapper.treeToValue(wrapper.getTransaction_type(), ActivityTransaction.class);
            activityTransaction.setTransaction(wrapper.getTransaction());
            activityTransactionService.saveActivityTransaction(activityTransaction);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return wrapper;

    }

    @GetMapping("/financial/activity")
    public List<TransactionWrapper> getAllActivityTransactions() {
        List<TransactionWrapper> wrappers = new ArrayList<>();
        Iterable<ActivityTransaction> activityTransactions= activityTransactionRepository.findAll();
        Iterable<Transaction> transactions= transactionRepository.findAll();
        ObjectMapper mapper = new JsonMapper();

        for (ActivityTransaction at : activityTransactions) {
            for (Transaction t : transactions) {
                if (t.getTransactionId() == at.getTransaction().getTransactionId()) {
                    TransactionWrapper wrapper = new TransactionWrapper();
                    wrapper.setTransaction(t);
                    wrapper.setTransaction_type(mapper.valueToTree(at););
                    wrappers.add(wrapper);
                }
            }
        }
        return wrappers;
    }

    @GetMapping("/transaction/webshop")
    public void getAllWebshopTransactions() {
    }

    @GetMapping("/financial/transaction/activityTransaction")
    public void testActivityTransaction(@RequestBody TransactionWrapper t) {
        transactionService.saveTransaction(t.getTransaction());

        if (t.getTransaction_type().get("type").asText().equals("activity")) {
            ObjectMapper mapper = new JsonMapper();
            try {
                ActivityTransaction activityTransaction = mapper.treeToValue(t.getTransaction_type(), ActivityTransaction.class);
                activityTransaction.setTransaction(t.getTransaction());
                activityTransactionService.saveActivityTransaction(activityTransaction);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        for (Object i : activityTransactionRepository.fetchActivityTransactions()) {
            System.out.println(i);
        }
    }


    @GetMapping("/transaction/{transactionId}")
    public void getTransactionById(@PathVariable Long transactionId) {

    }

    @PostMapping("/membership")
    public void createMembership() {

    }

    @PutMapping("/transaction/{transactionId}")
    public void updateTransactionById(@PathVariable Long transactionId) {

    }

    @PutMapping("/membership/{membershipId}")
    public void updateMembershipById(@PathVariable Long membershipId) {

    }

    @DeleteMapping("/transaction/{transactionId}")
    public void deleteTransactionById(@PathVariable Long transactionId) {

    }

    @DeleteMapping("/membership/{membershipId}")
    public void deleteMembershipById(@PathVariable Long transactionId) {

    }
}
