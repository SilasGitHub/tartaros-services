package tartaros.uiservice.model;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class MembershipTransaction {
    private Transaction transaction;
    private MembershipType membershipType;

    private UUID membershipTransactionId;
    private UUID membershipTypeId;
}
