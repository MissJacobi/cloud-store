package se.jensen.felicia.cloudstore.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.felicia.cloudstore.dto.UserRequestDTO;
import se.jensen.felicia.cloudstore.dto.UserResponseDTO;
import se.jensen.felicia.cloudstore.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO DTO){
        UserResponseDTO response = userService.saveUser(DTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserByIndex(@PathVariable Long id){
        UserResponseDTO userFromDB = userService.getUserWithID(id);
        return ResponseEntity.ok(userFromDB);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO DTO){
        UserResponseDTO user = userService.updateUser(DTO, id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
