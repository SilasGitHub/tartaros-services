package tartaros.financialservice.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.util.UUID;

@Entity
@Data
@Table(name="Transaction")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Transaction implements Serializable {
    @Id
    @GeneratedValue
    private Long transactionId;
    private Long memberId;
    private Double amount;
    private String description;
    private boolean paid = false;

}

