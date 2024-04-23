package tartaros.financialservice.db.service.membership;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tartaros.financialservice.db.entity.Membership;
import tartaros.financialservice.db.entity.MembershipTransaction;
import tartaros.financialservice.db.entity.MembershipType;
import tartaros.financialservice.db.entity.Transaction;
import tartaros.financialservice.db.repository.MembershipRepository;
import tartaros.financialservice.db.service.transaction.MembershipTransactionService;
import tartaros.financialservice.db.service.transaction.TransactionService;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class MembershipServceImpl implements MembershipService {

    @Autowired private MembershipRepository membershipRepository;
    @Autowired private MembershipTypeService membershipTypeService;
    @Autowired private MembershipTransactionService membershipTransactionService;
    @Autowired private TransactionService transactionService;

    @Override
    public Membership createMembership(Membership membership) {
        membership.setNextPaymentDate(membership.getStartDate());
        membership = membershipRepository.save(membership);
        MembershipType membershipType = membershipTypeService.fetchMembershipTypeById(membership.getMembershipTypeId());
        membershipType.getMemberships().add(membership);
        membershipTypeService.saveMembershipType(membershipType);
        return membership;
    }

    @Override
    public Membership saveMembership(Membership membership) {
        return membershipRepository.save(membership);
    }

    @Override
    public Membership fetchMembershipById(UUID membershipId) {
        return membershipRepository.findById(membershipId).get();
    }

    @Override
    public List<Membership> fetchMembershipList() {
        return (List<Membership>) membershipRepository.findAll();
    }

    @Override
    public void deleteMembershipById(UUID membershipId) {
        Membership membership = membershipRepository.findById(membershipId).get();
        membershipTypeService.fetchMembershipTypeById(membership.getMembershipTypeId()).getMemberships().remove(membership);
        membershipRepository.deleteById(membershipId);
    }

    @Override
    public void createTransactionFromMembership(Membership membership, MembershipType membershipType) {
        Transaction t = new Transaction();
        t.setAmount(membershipType.getPrice());
        t.setMemberEmail(membership.getMemberEmail());
        t.setDescription(membershipType.getName());
        t.setTransactionTime(Instant.now());
        transactionService.saveTransaction(t);
        MembershipTransaction mt = new MembershipTransaction();
        mt.setMembershipTypeId(membershipType.getMembershipTypeId());
        mt.setTransaction(t);
        membershipTransactionService.saveMembershipTransaction(mt);
    }
}
