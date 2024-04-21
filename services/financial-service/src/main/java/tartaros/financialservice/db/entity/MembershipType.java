package tartaros.financialservice.db.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@Table(name="membership_type")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MembershipType {
    @Id
    @GeneratedValue
    private UUID membershipTypeId;
    private String name;
    private Double price;
    private Integer duration;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Collection<Membership> memberships = new LinkedHashSet<>();
}
