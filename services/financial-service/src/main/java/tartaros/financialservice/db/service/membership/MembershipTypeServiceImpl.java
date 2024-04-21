package tartaros.financialservice.db.service.membership;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tartaros.financialservice.db.entity.MembershipType;
import tartaros.financialservice.db.repository.MembershipTypeRepository;

import java.util.List;
import java.util.UUID;

@Service
public class MembershipTypeServiceImpl implements MembershipTypeService {

    @Autowired
    MembershipTypeRepository membershipTypeRepository;
    @Override
    public MembershipType saveMembershipType(MembershipType membershipType) {
        return membershipTypeRepository.save(membershipType);
    }

    @Override
    public MembershipType fetchMembershipTypeById(UUID membershipTypeId) {
        return membershipTypeRepository.findById(membershipTypeId).get();
    }

    @Override
    public List<MembershipType> fetchMembershipTypeList() {
        return (List<MembershipType>) membershipTypeRepository.findAll();
    }

    @Override
    public void deleteMembershipTypeById(UUID membershipTypeId) {
        membershipTypeRepository.deleteById(membershipTypeId);
    }

    @Override
    public MembershipType updateMembershipType(MembershipType membershipType, UUID membershipTypeId) {
        MembershipType depDB = membershipTypeRepository.findById(membershipTypeId).get();
        if (membershipType.getDuration() != null) {
            depDB.setDuration(membershipType.getDuration());
        }
        if (membershipType.getPrice() != null) {
            depDB.setPrice(membershipType.getPrice());
        }
        if (membershipType.getName() != null) {
            depDB.setName(membershipType.getName());
        }
        return membershipTypeRepository.save(depDB);
    }
}
