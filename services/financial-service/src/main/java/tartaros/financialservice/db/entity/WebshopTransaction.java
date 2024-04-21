package tartaros.financialservice.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@Table(name="webshop_transaction")
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebshopTransaction {
    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "transaction_id_fk", referencedColumnName = "transaction_id")
    private Transaction transaction;
    @Id
    @GeneratedValue
    private UUID webshopTransactionId;
    private UUID itemId;
    private int count;
}
