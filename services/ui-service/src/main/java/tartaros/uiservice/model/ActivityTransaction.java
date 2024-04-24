package tartaros.uiservice.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ActivityTransaction {
    private Transaction transaction;
    private Activity activity;

    private UUID activityTransactionId;
    private UUID activityId;
}
