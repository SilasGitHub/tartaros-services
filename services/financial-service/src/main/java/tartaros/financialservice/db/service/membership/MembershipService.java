package tartaros.financialservice.db.service.membership;

import tartaros.financialservice.db.entity.Membership;
import tartaros.financialservice.db.entity.MembershipType;

import java.util.List;

public interface MembershipService {

    Membership createMembership(Membership membership);

    Membership saveMembership(Membership membership);

    Membership fetchMembershipById(Long membershipId);

    List<Membership> fetchMembershipList();

    void deleteMembershipById(Long membershipId);

    void doTransaction(Membership membership, MembershipType membershipType);
}
