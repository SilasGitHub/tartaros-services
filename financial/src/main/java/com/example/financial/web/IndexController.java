package com.example.financial.web;

import com.example.financial.db.entity.ActivityTransaction;
import com.example.financial.db.entity.Transaction;
import com.example.financial.db.entity.TransactionWrapper;
import com.example.financial.db.entity.WebshopTransaction;
import com.example.financial.db.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
public class IndexController {

    @Autowired private TransactionService transactionService;

    @GetMapping("/transaction")
    public void getAllTransactions() {
    }

    @GetMapping("/transaction/membership")
    public void getAllMembershipTransactions() {
    }

    @GetMapping("/transaction/activity")
    public void getAllActivityTransactions() {
    }

    @GetMapping("/transaction/webshop")
    public void getAllWebshopTransactions() {
    }

    @PostMapping("/transaction")
    public ResponseEntity createTransaction(@RequestBody TransactionWrapper transaction) {
        System.out.println(transaction.getTransaction());
        System.out.println(transaction.getTransaction_type());
        ObjectMapper jsonObjectMapper = new JsonMapper();
        try {
            if (transaction.getTransaction_type().get("type").asText().equals("webshop")) {

                WebshopTransaction w = jsonObjectMapper.treeToValue(transaction.getTransaction_type(), WebshopTransaction.class);
                System.out.println(w.getCount());

            } else if (transaction.getTransaction_type().get("type").asText().equals("activity")) {
                ActivityTransaction a = jsonObjectMapper.treeToValue(transaction.getTransaction_type(), ActivityTransaction.class);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.OK);

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
