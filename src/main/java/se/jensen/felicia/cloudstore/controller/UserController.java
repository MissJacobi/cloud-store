package se.jensen.felicia.cloudstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import se.jensen.felicia.cloudstore.dto.UserRequestDTO;
import se.jensen.felicia.cloudstore.dto.UserResponseDTO;
import se.jensen.felicia.cloudstore.model.User;
import se.jensen.felicia.cloudstore.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

   /* @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new User());
        return "register";
    }*/

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO DTO){
        UserResponseDTO response = userService.saveUser(DTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserByIndex(@PathVariable Long id){
        UserResponseDTO userFromDB = userService.getUserWithID(id);
        return ResponseEntity.ok(userFromDB);
    }

    @GetMapping("/login")
    public String showLoginForm(){
        return "login";
    }

    @PutMapping
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
