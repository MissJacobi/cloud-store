package se.jensen.felicia.cloudstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.jensen.felicia.cloudstore.dto.UserRequestDTO;
import se.jensen.felicia.cloudstore.dto.UserResponseDTO;
import se.jensen.felicia.cloudstore.mapper.UserMapper;
import se.jensen.felicia.cloudstore.model.User;
import se.jensen.felicia.cloudstore.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private final UserMapper userMapper;

    public UserResponseDTO saveUser(UserRequestDTO userDTO){
        if(userDTO.password() == null || userDTO.password().isBlank()){
            throw new IllegalArgumentException("Password is missing in request body");
        }
        User user = userMapper.fromDTO(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("Email already exists");
        }
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public UserResponseDTO updateUser(UserRequestDTO DTO, Long id){
        Optional<User> existing = userRepository.findById(id);
        if(existing.isPresent()){
            User user = existing.get();
            if (DTO.email() != null) {
                user.setEmail(DTO.email());
            }
            if (DTO.firstname() != null) {
                user.setFirstName(DTO.firstname());
            }
            if (DTO.lastname() != null) {
                user.setLastName(DTO.lastname());
            }
            if (DTO.password() != null && !DTO.password().isBlank()) {
                user.setPassword(passwordEncoder.encode(DTO.password()));
            }
            if (DTO.role() != null) {
                user.setRole(DTO.role());
            }
            User updated = userRepository.save(user);
            return userMapper.toDTO(updated);
        } else {
            throw new NoSuchElementException("Ingen användare i databasen med id: " + id);
        }
    }
    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new NoSuchElementException("Ingen användare i databasen med id: " + id);
        }
        userRepository.deleteById(id);
    }

    public UserResponseDTO getUserWithID(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        return userMapper.toDTO(user);
    }
}
