package tartaros.activityservice.transaction;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Transaction implements Serializable {
    private Long transactionId;
    private Long memberId;
    private Double amount;
    private String description;
    private boolean paid = false;
}

