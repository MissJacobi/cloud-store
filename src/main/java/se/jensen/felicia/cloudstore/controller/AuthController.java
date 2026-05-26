package se.jensen.felicia.cloudstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.jensen.felicia.cloudstore.security.JwtSigner;
import se.jensen.felicia.cloudstore.dto.LoginDTO;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtSigner jwtSigner;
    private final AuthenticationManager authenticationManager;

    public AuthController(JwtSigner jwtSigner, AuthenticationManager authenticationManager) {
        this.jwtSigner = jwtSigner;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){
       authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password())
        );
    String token = jwtSigner.generateToken(loginDTO.email());
    return ResponseEntity.ok(token);
    }
}
