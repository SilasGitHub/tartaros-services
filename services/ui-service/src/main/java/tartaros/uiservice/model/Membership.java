package tartaros.uiservice.model;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class Membership {
    private UUID membershipId;
    private UUID membershipTypeId;
    private String memberEmail;
    private MembershipType membershipType;
    private Instant nextPaymentDate;
    private Instant startDate;
    private Instant endDate;
}
