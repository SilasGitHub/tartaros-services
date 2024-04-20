package tartaros.financialservice.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tartaros.financialservice.db.entity.*;
import tartaros.financialservice.db.service.membership.MembershipService;
import tartaros.financialservice.db.service.membership.MembershipTypeService;
import tartaros.financialservice.db.service.transaction.ActivityTransactionService;
import tartaros.financialservice.db.service.transaction.MembershipTransactionService;
import tartaros.financialservice.db.service.transaction.TransactionService;
import tartaros.financialservice.db.service.transaction.WebshopTransactionService;

import java.util.List;
import java.util.UUID;

@RestController
public class IndexController {

    @Autowired private TransactionService transactionService;
    @Autowired private ActivityTransactionService activityTransactionService;
    @Autowired private WebshopTransactionService webshopTransactionService;
    @Autowired private MembershipTransactionService membershipTransactionService;
    @Autowired private MembershipService membershipService;
    @Autowired private MembershipTypeService membershipTypeService;


    @GetMapping("/financial/transaction")
    public List<Transaction> getAllTransactions() {
        return transactionService.fetchTransactionList();
    }

    @PostMapping("/financial/transaction/activity")
    public TransactionWrapper createActivityTransaction(@RequestBody TransactionWrapper wrapper) {
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

    @GetMapping("/financial/transaction/activity")
    public List<ActivityTransaction> getAllActivityTransactions() {
        return activityTransactionService.fetchActivityTransactionList();
    }

    @GetMapping("/financial/transaction/webshop")
    public List<WebshopTransaction> getAllWebshopTransactions() {
        return webshopTransactionService.fetchWebshopTransactionList();
    }

    @GetMapping("/financial/transaction/membership")
    public List<MembershipTransaction> getAllMembershipTransactions() {
        return membershipTransactionService.fetchMembershipTransactionList();
    }


    @GetMapping("/financial/transaction/{transactionId}")
    public Transaction getTransactionById(@PathVariable UUID transactionId) {
        return transactionService.getTransactionById(transactionId);
    }

    @PutMapping("/financial/transaction/{transactionId}")
    public Transaction updateTransactionById(@PathVariable UUID transactionId, @RequestBody Transaction transaction) {
        return transactionService.updateTransaction(transaction, transactionId);
    }


    @DeleteMapping("/financial/transaction/{transactionId}")
    public ResponseEntity deleteTransactionById(@PathVariable UUID transactionId) {
        transactionService.deleteTransactionById(transactionId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/financial/membership")
    public List<Membership> getAllMemberships() {
        return membershipService.fetchMembershipList();
    }

    @PostMapping("/financial/membership")
    public Membership createMembership(@RequestBody Membership membership) {
        return membershipService.createMembership(membership);
    }

    @DeleteMapping("/financial/membership/{membershipId}")
    public void deleteMembershipById(@PathVariable Long membershipId) {
        membershipService.deleteMembershipById(membershipId);
    }

    @GetMapping("/financial/membership/type")
    public List<MembershipType> getAllMembershipTypes() {
        return membershipTypeService.fetchMembershipTypeList();
    }

    @PostMapping("/financial/membership/type")
    public MembershipType createMembershipType(@RequestBody MembershipType membershipType) {
        return membershipTypeService.saveMembershipType(membershipType);
    }

    @DeleteMapping("/financial/membership/type/{membershipTypeId}")
    public void deleteMembershipTypeById(@PathVariable Long membershipTypeId) {
        membershipTypeService.deleteMembershipTypeById(membershipTypeId);
    }

    @PutMapping("/financial/membership/type/{membershipTypeId}")
    public MembershipType updateMembershipTypeById(@PathVariable Long membershipTypeId, @RequestBody MembershipType membershipType) {
        return membershipTypeService.updateMembershipType(membershipType, membershipTypeId);
    }


}
