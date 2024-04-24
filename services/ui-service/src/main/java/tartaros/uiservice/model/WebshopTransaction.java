package tartaros.uiservice.model;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class WebshopTransaction {
    private Transaction transaction;
    private UUID webshopTransactionId;
    private UUID itemId;
    private Integer count;
}
