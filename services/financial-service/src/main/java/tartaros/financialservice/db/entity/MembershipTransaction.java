package tartaros.financialservice.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@Table(name="membership_transaction")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MembershipTransaction {
    @Id
    private UUID transactionId;
    private int membershipType;
}
