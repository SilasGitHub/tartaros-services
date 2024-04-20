package tartaros.financialservice.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
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
    private UUID memberId;
    private Double amount;
    private String description;
    private boolean paid = false;
    private LocalDateTime transactionTime;

}

