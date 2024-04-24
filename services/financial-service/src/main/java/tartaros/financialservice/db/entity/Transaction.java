package tartaros.financialservice.db.entity;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Table(name="transaction")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "transaction_id")
    private UUID transactionId;
    @NotNull
    private String memberEmail;
    @NotNull
    private Double amount;
    @NotNull
    private String description;
    private boolean paid = false;
    @NotNull
    private Instant transactionTime;
}

