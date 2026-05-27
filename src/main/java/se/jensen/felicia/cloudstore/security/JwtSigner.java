/*
package se.jensen.felicia.cloudstore.security;

import org.springframework.beans.factory.annotation.Value;
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


    public JwtSigner(@Value("${JWT_PRIVATE_KEY}") String privatePem,
                     @Value("${JWT_PUBLIC_KEY}") String publicPem ) throws Exception {

        // Rensar bort alla typer av vanliga PEM-headers och footers, samt alla radbrytningar/blanksteg
        String base64private = privatePem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replaceAll("\\s+", ""); // Tar bort alla radbrytningar och mellanslag

        String base64public = publicPem
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replace("-----BEGIN RSA PUBLIC KEY-----", "")
                .replace("-----END RSA PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] keyBytesPrivate = Base64.getDecoder().decode(base64private);
        byte[] keyBytesPublic = Base64.getDecoder().decode(base64public);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.privateKey = (RSAPrivateKey) keyFactory.generatePrivate(new PKCS8EncodedKeySpec(keyBytesPrivate));
        this.publicKey = (RSAPublicKey) keyFactory.generatePublic(new X509EncodedKeySpec(keyBytesPublic));
    }

    public String generateToken(String email){
        Instant now = Instant.now();

        return Jwts.builder()
                .issuer("http://localhost:8080")
                .subject(email)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusSeconds(300))) //5 min
                .signWith(privateKey) //signerar med privat nyckel
                .compact();

    }


}
*/