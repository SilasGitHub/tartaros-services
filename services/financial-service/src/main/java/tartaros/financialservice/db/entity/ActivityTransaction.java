package tartaros.financialservice.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@Table(name="activity_transaction")
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivityTransaction {
    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "transaction_id_fk", referencedColumnName = "transaction_id")
    private Transaction transaction;

    @Id
    @GeneratedValue
    private UUID activityTransactionId;

    private UUID activityId;
}
