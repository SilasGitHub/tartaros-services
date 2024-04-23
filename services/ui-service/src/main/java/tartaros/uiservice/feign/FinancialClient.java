package tartaros.uiservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import tartaros.uiservice.model.*;

import java.util.List;

@FeignClient("financial-service")
public interface FinancialClient {
    @GetMapping("/financial/transaction")
    List<Transaction> getTransactions(@RequestHeader("Cookie") String cookie);

    @PostMapping("/financial/transaction")
    Transaction createTransaction(@RequestHeader("Cookie") String cookie, @RequestBody Transaction transaction);

    @GetMapping("/financial/transaction/activity")
    List<ActivityTransaction> getAllActivityTransactions(@RequestHeader("Cookie") String cookie);

    @GetMapping("/financial/transaction/webshop")
    List<WebshopTransaction> getAllWebshopTransactions(@RequestHeader("Cookie") String cookie);

    @GetMapping("/financial/transaction/membership")
    List<MembershipTransaction> getAllMembershipTransactions(@RequestHeader("Cookie") String cookie);

    @GetMapping("/financial/transaction/{transactionId}")
    Transaction getTransactionById(@RequestHeader("Cookie") String cookie, @PathVariable String transactionId);

    @PutMapping("/financial/transaction/{transactionId}")
    Transaction updateTransactionById(@RequestHeader("Cookie") String cookie, @PathVariable String transactionId, @RequestBody Transaction transaction);

    @DeleteMapping("/financial/transaction/{transactionId}")
    void deleteTransactionById(@RequestHeader("Cookie") String cookie, @PathVariable String transactionId);

    @GetMapping("/financial/membership")
    List<Membership> getMemberships(@RequestHeader("Cookie") String cookie);

    @PostMapping("/financial/membership")
    Membership createMembership(@RequestHeader("Cookie") String cookie, @RequestBody Membership membership);

    @DeleteMapping("/financial/membership/{membershipId}")
    void deleteMembership(@RequestHeader("Cookie") String cookie, @PathVariable String membershipId);

    @GetMapping("/financial/membershipType")
    List<MembershipType> getMembershipTypes(@RequestHeader("Cookie") String cookie);

    @PostMapping("/financial/membershipType")
    MembershipType createMembershipType(@RequestHeader("Cookie") String cookie, @RequestBody MembershipType membershipType);

    @DeleteMapping("/financial/membershipType/{membershipTypeId}")
    void deleteMembershipType(@RequestHeader("Cookie") String cookie, @PathVariable String membershipTypeId);

    @PutMapping("/financial/membershipType/{membershipTypeId}")
    MembershipType updateMembershipType(@RequestHeader("Cookie") String cookie, @PathVariable String membershipTypeId, @RequestBody MembershipType membershipType);
}