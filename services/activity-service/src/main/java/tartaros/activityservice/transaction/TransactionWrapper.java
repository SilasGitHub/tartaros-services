package tartaros.activityservice.transaction;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TransactionWrapper implements Serializable {
    public TransactionWrapper(Transaction transaction, TransactionType transaction_type) {
        this.transaction = transaction;
        this.transaction_type = transaction_type;
    }
    private Transaction transaction;
    private TransactionType transaction_type;
}
