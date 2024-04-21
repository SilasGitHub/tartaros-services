package tartaros.activityservice.transaction;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class TransactionWrapper implements Serializable {
    private Transaction transaction;
    private TransactionType transaction_type;
}
