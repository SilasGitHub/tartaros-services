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
    private String memberEmail;
    private Double amount;
    private String description;
}

