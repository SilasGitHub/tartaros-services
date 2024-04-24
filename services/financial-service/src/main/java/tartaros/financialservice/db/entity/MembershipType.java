package tartaros.financialservice.db.entity;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

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
    @NotNull
    private String name;
    @NotNull
    private Double price;
    // Duration in months
    @NotNull
    private Integer duration;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Collection<Membership> memberships = new LinkedHashSet<>();
}
