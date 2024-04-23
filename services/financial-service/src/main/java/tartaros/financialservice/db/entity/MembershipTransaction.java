package tartaros.financialservice.db.entity;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.UUID;

@Entity
@Data
@Table(name="membership_transaction")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MembershipTransaction {
    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "transaction_id_fk", referencedColumnName = "transaction_id")
    private Transaction transaction;

    @Id
    @GeneratedValue
    private UUID membershipTransactionId;
    @NotNull
    private UUID membershipTypeId;
}
