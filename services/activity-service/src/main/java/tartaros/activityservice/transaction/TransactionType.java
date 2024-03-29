package tartaros.activityservice.transaction;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TransactionType implements Serializable {
    String type = "activity";
    Long activityId;
}
