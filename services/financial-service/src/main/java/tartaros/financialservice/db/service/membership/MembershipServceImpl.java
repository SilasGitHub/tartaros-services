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

import java.time.LocalDateTime;
import java.util.List;
@Service
public class MembershipServceImpl implements MembershipService {

    @Autowired private MembershipRepository membershipRepository;
    @Autowired private MembershipTypeService membershipTypeService;
    @Autowired private MembershipTransactionService membershipTransactionService;
    @Autowired private TransactionService transactionService;

    @Override
    public Membership createMembership(Membership membership) {
        MembershipType membershipType = membershipTypeService.fetchMembershipTypeById(membership.getMembershipType());
        membershipType.getMemberships().add(membership);
        membership.setNextPaymentDate(LocalDateTime.now().plusSeconds(membershipType.getDuration()));
        doTransaction(membership, membershipType);
        membershipTypeService.saveMembershipType(membershipType);
        return membershipRepository.save(membership);
    }

    @Override
    public Membership saveMembership(Membership membership) {
        return membershipRepository.save(membership);
    }



    @Override
    public Membership fetchMembershipById(Long membershipId) {
        return membershipRepository.findById(membershipId).get();
    }

    @Override
    public List<Membership> fetchMembershipList() {
        return (List<Membership>) membershipRepository.findAll();
    }

    @Override
    public void deleteMembershipById(Long membershipId) {
        membershipRepository.deleteById(membershipId);
    }

    @Override
    public void doTransaction(Membership membership, MembershipType membershipType) {
        Transaction t = new Transaction();
        t.setAmount(membershipType.getPrice());
        t.setMemberEmail(membership.getMemberEmail());
        t.setDescription(membershipType.getName());
        t.setTransactionTime(LocalDateTime.now());
        transactionService.saveTransaction(t);
        MembershipTransaction mt = new MembershipTransaction();
        mt.setMembershipType(membershipType.getMembershipType());
        mt.setTransaction(t);
        membershipTransactionService.saveMembershipTransaction(mt);
    }
}
