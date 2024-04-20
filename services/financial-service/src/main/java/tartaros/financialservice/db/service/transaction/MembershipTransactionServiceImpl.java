package tartaros.financialservice.db.service.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tartaros.financialservice.db.entity.MembershipTransaction;
import tartaros.financialservice.db.repository.MembershipTransactionRepository;

import java.util.List;
@Service
public class MembershipTransactionServiceImpl implements MembershipTransactionService{

    @Autowired
    private MembershipTransactionRepository membershipTransactionRepository;


    @Override
    public MembershipTransaction saveMembershipTransaction(MembershipTransaction membershipTransaction) {
        return membershipTransactionRepository.save(membershipTransaction);
    }

    @Override
    public List<MembershipTransaction> fetchMembershipTransactionList() {
        return (List<MembershipTransaction>) membershipTransactionRepository.findAll();
    }

    @Override
    public void deleteMembershipTransactionById(Long membershipTransactionId) {
        membershipTransactionRepository.deleteById(membershipTransactionId);
    }
}
