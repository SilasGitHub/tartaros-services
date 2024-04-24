package tartaros.financialservice.db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import tartaros.financialservice.db.entity.Membership;
import tartaros.financialservice.db.entity.MembershipType;
import tartaros.financialservice.db.service.membership.MembershipService;
import tartaros.financialservice.db.service.membership.MembershipTypeService;
import tartaros.financialservice.db.service.transaction.MembershipTransactionService;
import tartaros.financialservice.db.service.transaction.TransactionService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@EnableScheduling
@Configuration
public class MembershipScheduler {
    @Autowired
    MembershipTypeService membershipTypeService;
    @Autowired
    MembershipService membershipService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    MembershipTransactionService membershipTransactionService;

    //"0 0 0 * * ?"= every day at midnight
    //"*/30 * * * * *"= every 30 seconds
    @Scheduled(cron = "30 * * * * ?")
    public void createMembershipTransactions() {
        List<MembershipType> membershipTypes = membershipTypeService.fetchMembershipTypeList();
        for (MembershipType membershipType : membershipTypes) {
            List<Membership> memberships = membershipType.getMemberships().stream().filter(m -> m.getEndDate() == null || m.getEndDate().isAfter(Instant.now())).toList();
            for (Membership membership : memberships) {
                Instant payDate = membership.getNextPaymentDate();
                if (payDate.isBefore(Instant.now())) {
                    membership.setNextPaymentDate(LocalDateTime.ofInstant(payDate, ZoneOffset.ofHours(0)).plusMonths(membershipType.getDuration()).toInstant(ZoneOffset.ofHours(0)));
                    membershipService.saveMembership(membership);
                    membershipService.createTransactionFromMembership(membership, membershipType);
                }
            }
        }
    }
}
