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
import tartaros.financialservice.db.repository.TransactionRepository;
import tartaros.financialservice.db.service.TransactionService;
import tartaros.financialservice.rabbitmq.publisher.RabbitMQProducer;

@RestController
public class IndexController {

    @Autowired private TransactionService transactionService;
    @Autowired private TransactionRepository transactionRepository;
    @Autowired private RabbitMQProducer producer;

    @GetMapping("/transaction")
    public void getAllTransactions() {
    }

    @GetMapping("/transaction/membership")
    public void getAllMembershipTransactions() {
    }

    @GetMapping("/transaction/activity")
    public String getAllActivityTransactions() {
        StringBuilder result = new StringBuilder();
        for (Transaction transaction : transactionRepository.findAll()) {
            result.append(transaction.toString());
        }
        return result.toString();
    }

    @GetMapping("/transaction/webshop")
    public void getAllWebshopTransactions() {
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
