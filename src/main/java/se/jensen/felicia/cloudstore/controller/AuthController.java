package se.jensen.felicia.cloudstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.jensen.felicia.cloudstore.dto.AuthRequestDTO;
import se.jensen.felicia.cloudstore.dto.UserResponseDTO;
import se.jensen.felicia.cloudstore.mapper.UserMapper;
import se.jensen.felicia.cloudstore.model.User;
import se.jensen.felicia.cloudstore.repository.UserRepository;
//import se.jensen.felicia.cloudstore.security.JwtSigner;
import se.jensen.felicia.cloudstore.dto.AuthResponseDTO;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
   // private final JwtSigner jwtSigner;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    //lägg till jwtsigner här
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, UserMapper userMapper) {
        //this.jwtSigner = jwtSigner;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequestDTO){
       authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.email(), authRequestDTO.password())
        );
    //String token = jwtSigner.generateToken(authRequestDTO.email());
    User user = userRepository.findByEmail(authRequestDTO.email())
            .orElseThrow(() -> new RuntimeException("User not found after authentication"));
        UserResponseDTO userResponse = userMapper.toDTO(user);
    return ResponseEntity.ok(new AuthResponseDTO("hej", userResponse));
    }
}
