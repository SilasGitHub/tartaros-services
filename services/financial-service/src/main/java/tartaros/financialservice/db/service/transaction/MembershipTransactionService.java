package tartaros.financialservice.db.service.transaction;

import tartaros.financialservice.db.entity.MembershipTransaction;

import java.util.List;
import java.util.UUID;

public interface MembershipTransactionService {

    MembershipTransaction saveMembershipTransaction(MembershipTransaction membershipTransaction);

    List<MembershipTransaction> fetchMembershipTransactionList();

    // Delete operation
    void deleteMembershipTransactionById(UUID membershipTransactionId);
}
