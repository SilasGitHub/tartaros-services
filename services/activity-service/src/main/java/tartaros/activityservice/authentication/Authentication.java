package tartaros.activityservice.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.Verification;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

@Component
public class Authentication {

    private static String JWT_PUBLIC_KEY;

    @Value("${jwt.public-key}")
    public void setStaticName(String jwtPublicKey) {
        JWT_PUBLIC_KEY = jwtPublicKey;
    }

    public static boolean verifyAuthentication(String token, boolean claimAdmin) {
        Algorithm algorithm;
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(Base64.decodeBase64(Authentication.JWT_PUBLIC_KEY));
            RSAPublicKey publicKey = (RSAPublicKey) factory.generatePublic(publicSpec);
            algorithm = Algorithm.RSA256(publicKey);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ignored) {
            return false;
        }

        Verification verifierBuilder = JWT.require(algorithm);
        if (claimAdmin) {
            verifierBuilder.withClaim("isAdmin", true);
        }

        JWTVerifier verifier = verifierBuilder.build();

        try {
            verifier.verify(token);

            return true;
        } catch (JWTVerificationException ignored) {}

        return false;
    }

    public static JwtClaims getClaims(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        JwtClaims claims = new JwtClaims();

        claims.setSub(decodedJWT.getClaim("sub").asString());
        claims.setEmail(decodedJWT.getClaim("email").asString());
        claims.setAdmin(decodedJWT.getClaim("isAdmin").asBoolean());
        claims.setName(decodedJWT.getClaim("name").asString());

        return claims;
    }
}
