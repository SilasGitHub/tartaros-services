package tartaros.financialservice.db.service.membership;

import tartaros.financialservice.db.entity.MembershipType;

import java.util.List;

public interface MembershipTypeService {

    MembershipType saveMembershipType(MembershipType membershipType);

    MembershipType fetchMembershipTypeById(Long membershipTypeId);

    List<MembershipType> fetchMembershipTypeList();

    void deleteMembershipTypeById(Long membershipTypeId);

    MembershipType updateMembershipType(MembershipType membershipType, Long membershipTypeId);
}
