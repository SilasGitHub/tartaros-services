package tartaros.financialservice.db.entity;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@Table(name="membership")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Membership {
    @Id
    @GeneratedValue
    private UUID membershipId;
    @NotNull
    private UUID membershipTypeId;
    @NotNull
    private String memberEmail;
    private Instant nextPaymentDate;
    @NotNull
    private Instant startDate;
    private Instant endDate;
}
