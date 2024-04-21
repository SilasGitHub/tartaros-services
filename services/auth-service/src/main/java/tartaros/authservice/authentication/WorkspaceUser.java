package tartaros.authservice.authentication;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WorkspaceUser {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @NotNull
    private String sub;

    @NotNull
    private String name;

    @NotNull
    private boolean isAdmin;
}
