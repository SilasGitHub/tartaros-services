package tartaros.financialservice.db.service.membership;

import tartaros.financialservice.db.entity.Membership;
import tartaros.financialservice.db.entity.MembershipType;

import java.util.List;
import java.util.UUID;

public interface MembershipService {

    Membership createMembership(Membership membership);

    Membership saveMembership(Membership membership);

    Membership fetchMembershipById(UUID membershipId);

    List<Membership> fetchMembershipList();

    void deleteMembershipById(UUID membershipId);

    void doTransaction(Membership membership, MembershipType membershipType);
}
