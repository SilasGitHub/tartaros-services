package tartaros.financialservice.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
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
    private Long membershipId;
    private String memberEmail;
    private Long membershipType;
    private LocalDateTime nextPaymentDate;
}
