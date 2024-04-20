package tartaros.financialservice.db.service.transaction;

import tartaros.financialservice.db.entity.MembershipTransaction;

import java.util.List;

public interface MembershipTransactionService {

    MembershipTransaction saveMembershipTransaction(MembershipTransaction membershipTransaction);

    List<MembershipTransaction> fetchMembershipTransactionList();
}
