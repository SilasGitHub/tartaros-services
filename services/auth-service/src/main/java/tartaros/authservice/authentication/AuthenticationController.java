package tartaros.authservice.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.*;

@Controller
public class AuthenticationController {

    private final WorkspaceUserRepository userRepository;

    @Value("${jwt.public-key}")
    private String jwtPublicKey;

    @Value("${jwt.private-key}")
    private String jwtPrivateKey;

    @Value("${gateway.host}")
    private String gatewayHost;

    @Value("${gateway.port}")
    private String gatewayPort;

    public AuthenticationController(WorkspaceUserRepository userRepository) {
        userRepository.saveAll(getWorkspaceUsers());
        this.userRepository = userRepository;
    }

    @Scheduled(fixedDelay = 1000*60*5)
    public void syncUsers() {
        // Add mockdata for now, would be a call to the Google-service to sync with all current users in our Google Workspace
        userRepository.saveAll(getWorkspaceUsers());
    }

    @GetMapping("/")
    public void setJwt(@AuthenticationPrincipal OAuth2User principal, HttpServletResponse response) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        // Google account identifier
        String sub = principal.getAttribute("sub");
        System.out.println(sub);
        for (WorkspaceUser w : userRepository.findAll()) {
            System.out.println(w.getSub());
        }

        // We expect each user to be in our repository already as we sync with the Google Workspace every 5 minutes
        Optional<WorkspaceUser> optionalUser = Flux.fromIterable(userRepository.findAll()).filter(workspaceUser -> workspaceUser.getSub().equals(sub)).toStream().findFirst();

        if (optionalUser.isEmpty())  {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        WorkspaceUser user = optionalUser.get();

        KeyFactory factory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(Base64.decode(this.jwtPublicKey.getBytes(StandardCharsets.UTF_8)));
        PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(Base64.decode(this.jwtPrivateKey.getBytes(StandardCharsets.UTF_8)));
        RSAPublicKey publicKey = (RSAPublicKey) factory.generatePublic(publicSpec);
        RSAPrivateKey privateKey = (RSAPrivateKey) factory.generatePrivate(privateSpec);
        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);

        // Creating the token, can be expanded to include specific roles based on Google Workspace group membership later
        String token = JWT.create()
                // Set the jwt to expire after 4 hours, for now, no way to revoke it
                .withExpiresAt(Instant.now().plusSeconds(60*60*4))
                .withClaim("name", user.getName())
                .withClaim("email", (String) principal.getAttribute("email"))
                .withNotBefore(Instant.now().minusSeconds(1))
                .withIssuedAt(Instant.now())
                .withClaim("isAdmin", user.isAdmin())
                .sign(algorithm);

        response.addCookie(new Cookie("jwt", token));
        response.setStatus(HttpStatus.SEE_OTHER.value());
        // Return to index page
        response.sendRedirect("http://" + gatewayHost + ":" + gatewayPort);
    }

    private List<WorkspaceUser> getWorkspaceUsers() {
        List<WorkspaceUser> result = new ArrayList<>();

        result.add(new WorkspaceUser(UUID.randomUUID(), "109138331861590363980", "Silas de Graaf", true));
        result.add(new WorkspaceUser(UUID.randomUUID(), "115502612022482475640", "Joep Vorage", false));

        return result;
    }

}
