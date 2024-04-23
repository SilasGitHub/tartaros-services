package tartaros.uiservice.model;

import lombok.*;

import java.util.Collection;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class MembershipType {
    private UUID membershipTypeId;
    private String name;
    private Double price;
    private Integer duration;

    private Collection<Membership> memberships;
}
