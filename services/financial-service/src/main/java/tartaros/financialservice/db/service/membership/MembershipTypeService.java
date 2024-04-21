package tartaros.financialservice.db.service.membership;

import tartaros.financialservice.db.entity.MembershipType;

import java.util.List;
import java.util.UUID;

public interface MembershipTypeService {

    MembershipType saveMembershipType(MembershipType membershipType);

    MembershipType fetchMembershipTypeById(UUID membershipTypeId);

    List<MembershipType> fetchMembershipTypeList();

    void deleteMembershipTypeById(UUID membershipTypeId);

    MembershipType updateMembershipType(MembershipType membershipType, UUID membershipTypeId);
}
