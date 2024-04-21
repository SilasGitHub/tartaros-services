package tartaros.financialservice.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tartaros.financialservice.authentication.Authentication;
import tartaros.financialservice.authentication.JwtClaims;
import tartaros.financialservice.db.entity.*;
import tartaros.financialservice.db.service.membership.MembershipService;
import tartaros.financialservice.db.service.membership.MembershipTypeService;
import tartaros.financialservice.db.service.transaction.ActivityTransactionService;
import tartaros.financialservice.db.service.transaction.MembershipTransactionService;
import tartaros.financialservice.db.service.transaction.TransactionService;
import tartaros.financialservice.db.service.transaction.WebshopTransactionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/financial")
public class FinancialController {

    @Autowired private TransactionService transactionService;
    @Autowired private ActivityTransactionService activityTransactionService;
    @Autowired private WebshopTransactionService webshopTransactionService;
    @Autowired private MembershipTransactionService membershipTransactionService;
    @Autowired private MembershipService membershipService;
    @Autowired private MembershipTypeService membershipTypeService;


    @GetMapping("/transaction")
    public List<Transaction> getAllTransactions(@CookieValue(name="jwt", defaultValue = "") String token) {
        if (!Authentication.verifyAuthentication(token, false)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }

        List<Transaction> transactions = transactionService.fetchTransactionList();

        // non-admins can only see their own transactions
        JwtClaims claims = Authentication.getClaims(token);
        if (!claims.isAdmin()) {
            transactions = transactions.stream().filter(t -> t.getMemberEmail().equals(claims.getEmail())).toList();
        }

        return transactions;
    }

    @PostMapping("/transaction/activity")
    public Transaction createActivityTransaction(@CookieValue(name="jwt", defaultValue = "") String token, @RequestBody Transaction transaction) {
        if (!Authentication.verifyAuthentication(token, true)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        transaction.setTransactionTime(LocalDateTime.now());
        transaction = transactionService.saveTransaction(transaction);
        return transaction;
    }

    @GetMapping("/transaction/activity")
    public List<ActivityTransaction> getAllActivityTransactions(@CookieValue(name="jwt", defaultValue = "") String token) {
        if (!Authentication.verifyAuthentication(token, true)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        return activityTransactionService.fetchActivityTransactionList();
    }

    @GetMapping("/transaction/webshop")
    public List<WebshopTransaction> getAllWebshopTransactions(@CookieValue(name="jwt", defaultValue = "") String token) {
        if (!Authentication.verifyAuthentication(token, true)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        return webshopTransactionService.fetchWebshopTransactionList();
    }

    @GetMapping("/transaction/membership")
    public List<MembershipTransaction> getAllMembershipTransactions(@CookieValue(name="jwt", defaultValue = "") String token) {
        if (!Authentication.verifyAuthentication(token, true)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        return membershipTransactionService.fetchMembershipTransactionList();
    }


    @GetMapping("/transaction/{transactionId}")
    public Transaction getTransactionById(@CookieValue(name="jwt", defaultValue = "") String token, @PathVariable UUID transactionId) {
        if (!Authentication.verifyAuthentication(token, true)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        return transactionService.getTransactionById(transactionId);
    }

    @PutMapping("/transaction/{transactionId}")
    public Transaction updateTransactionById(@CookieValue(name="jwt", defaultValue = "") String token, @PathVariable UUID transactionId, @RequestBody Transaction transaction) {
        if (!Authentication.verifyAuthentication(token, true)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        return transactionService.updateTransaction(transaction, transactionId);
    }


    @DeleteMapping("/transaction/{transactionId}")
    public ResponseEntity deleteTransactionById(@CookieValue(name="jwt", defaultValue = "") String token, @PathVariable UUID transactionId) {
        if (!Authentication.verifyAuthentication(token, true)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        for (MembershipTransaction m : membershipTransactionService.fetchMembershipTransactionList()) {
            if (m.getTransaction().getTransactionId().equals(transactionId)) {
                membershipTransactionService.deleteMembershipTransactionById(m.getMembershipTransactionId());
            }
        }
        for (WebshopTransaction w : webshopTransactionService.fetchWebshopTransactionList()) {
            if (w.getTransaction().getTransactionId().equals(transactionId)) {
                webshopTransactionService.deleteWebshopTransactionById(w.getWebshopTransactionId());
            }
        }
        for (ActivityTransaction a : activityTransactionService.fetchActivityTransactionList()) {
            if (a.getTransaction().getTransactionId().equals(transactionId)) {
                activityTransactionService.deleteActivityTransactionById(a.getActivityTransactionId());
            }
        }
        transactionService.deleteTransactionById(transactionId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/membership")
    public List<Membership> getAllMemberships(@CookieValue(name="jwt", defaultValue = "") String token) {
        if (!Authentication.verifyAuthentication(token, true)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        return membershipService.fetchMembershipList();
    }

    @PostMapping("/membership")
    public Membership createMembership(@CookieValue(name="jwt", defaultValue = "") String token, @RequestBody Membership membership) {
        if (!Authentication.verifyAuthentication(token, true)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        return membershipService.createMembership(membership);
    }

    @DeleteMapping("/membership/{membershipId}")
    public void deleteMembershipById(@CookieValue(name="jwt", defaultValue = "") String token, @PathVariable UUID membershipId) {
        if (!Authentication.verifyAuthentication(token, true)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        membershipService.deleteMembershipById(membershipId);
    }

    @GetMapping("/membershipType")
    public List<MembershipType> getAllMembershipTypes(@CookieValue(name="jwt", defaultValue = "") String token) {
        if (!Authentication.verifyAuthentication(token, true)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        return membershipTypeService.fetchMembershipTypeList();
    }

    @PostMapping("/membershipType")
    public MembershipType createMembershipType(@CookieValue(name="jwt", defaultValue = "") String token, @RequestBody MembershipType membershipType) {
        if (!Authentication.verifyAuthentication(token, true)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        return membershipTypeService.saveMembershipType(membershipType);
    }

    @DeleteMapping("/membershipType/{membershipTypeId}")
    public void deleteMembershipTypeById(@CookieValue(name="jwt", defaultValue = "") String token, @PathVariable UUID membershipTypeId) {
        if (!Authentication.verifyAuthentication(token, true)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        membershipTypeService.deleteMembershipTypeById(membershipTypeId);
    }

    @PutMapping("/membershipType/{membershipTypeId}")
    public MembershipType updateMembershipTypeById(@CookieValue(name="jwt", defaultValue = "") String token, @PathVariable UUID membershipTypeId, @RequestBody MembershipType membershipType) {
        if (!Authentication.verifyAuthentication(token, true)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(403), "Invalid credentials");
        }
        return membershipTypeService.updateMembershipType(membershipType, membershipTypeId);
    }


}
