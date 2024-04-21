package tartaros.financialservice.db.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import tartaros.financialservice.db.entity.Membership;
import tartaros.financialservice.db.entity.MembershipTransaction;
import tartaros.financialservice.db.entity.MembershipType;
import tartaros.financialservice.db.entity.Transaction;
import tartaros.financialservice.db.service.membership.MembershipService;
import tartaros.financialservice.db.service.membership.MembershipTypeService;
import tartaros.financialservice.db.service.transaction.MembershipTransactionService;
import tartaros.financialservice.db.service.transaction.TransactionService;

import java.time.LocalDateTime;
import java.util.Collection;
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
    @Scheduled(cron = "0 0 0 * * ?")
    public void activityDeadlineTask() {
        List<MembershipType> membershipTypes = membershipTypeService.fetchMembershipTypeList();
        for (MembershipType membershipType : membershipTypes) {
            Collection<Membership> memberships = membershipType.getMemberships();
            for (Membership membership : memberships) {
                LocalDateTime payDate = membership.getNextPaymentDate();
                if (payDate.isBefore(LocalDateTime.now())) {
                    membership.setNextPaymentDate(payDate.plusMonths(membershipType.getDuration()));
                    membershipService.saveMembership(membership);
                    membershipService.doTransaction(membership, membershipType);
                }
            }
        }
    }
}
