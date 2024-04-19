package tartaros.activityservice.transaction;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Transaction implements Serializable {
    private UUID transactionId;
    private UUID memberId;
    private Double amount;
    private String description;
    private boolean paid = false;
}

