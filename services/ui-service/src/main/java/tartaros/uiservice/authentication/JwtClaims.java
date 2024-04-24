package tartaros.uiservice.authentication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtClaims {
    private String sub;
    private String email;
    private String name;
    private boolean isAdmin;
}
