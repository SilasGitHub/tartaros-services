package tartaros.activityservice.transaction;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TransactionType implements Serializable {
    String type = "activity";
    UUID activityId;
}
