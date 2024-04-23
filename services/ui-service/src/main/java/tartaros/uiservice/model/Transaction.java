package tartaros.uiservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class Transaction {
    private UUID transactionId;
    private String memberEmail;
    private Double amount;
    private String description;
    private boolean paid;
    private Instant transactionTime;
}
