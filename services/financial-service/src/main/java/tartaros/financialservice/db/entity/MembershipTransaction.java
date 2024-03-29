package tartaros.financialservice.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@Table(name="membership_transaction")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MembershipTransaction {
    @Id
    @GeneratedValue
    private Long transactionId;
    private int membershipType;
}
