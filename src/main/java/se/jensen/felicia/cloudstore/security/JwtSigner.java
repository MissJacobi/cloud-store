
package se.jensen.felicia.cloudstore.security;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;



@Component
public class JwtSigner {
    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;
    private final String issuer;
    private final long expirationSeconds;


    public JwtSigner(@Value("${jwt.private-key}") String privatePem,
                     @Value("${jwt.public-key}") String publicPem,
                     @Value("${jwt.issuer}") String issuer,
                     @Value("${jwt.expiration-seconds}") long expirationSeconds) throws Exception {


        String base64private = privatePem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        String base64public = publicPem
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] keyBytesPrivate = Base64.getDecoder().decode(base64private);
        byte[] keyBytesPublic = Base64.getDecoder().decode(base64public);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.privateKey = (RSAPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytesPrivate));
        this.publicKey = (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(keyBytesPublic));
        this.issuer = issuer;
        this.expirationSeconds = expirationSeconds;
    }


    public String generateToken(String email){
        Instant now = Instant.now();

        return Jwts.builder()
                .issuer(issuer)
                .subject(email)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(expirationSeconds))) //1 h
                .signWith(privateKey) //signerar med privat nyckel
                .compact();

    }

    //Extracts email from token
    public String extractEmail(String token){
        return extractAllClaims(token).getSubject();
    }

    //Validate user e-mail with token e-mail, token haven't expired
    public boolean isTokenValid(String token, UserDetails userDetails){
        String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    //Sees if token has expired
    private boolean isTokenExpired(String token){
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    //Validate token with public key, if anything changes or the token is signed with wrong key. Throws exception
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(publicKey)
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
