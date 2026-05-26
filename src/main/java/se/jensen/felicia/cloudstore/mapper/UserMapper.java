package se.jensen.felicia.cloudstore.mapper;

import org.springframework.stereotype.Component;
import se.jensen.felicia.cloudstore.dto.UserRequestDTO;
import se.jensen.felicia.cloudstore.dto.UserResponseDTO;
import se.jensen.felicia.cloudstore.model.User;

@Component
public class UserMapper {

    public static User fromDTO(UserRequestDTO DTO){
        User user = new User();
        setUserValues(user, DTO);
        return user;
    }

    public static UserResponseDTO toDTO(User user){
        UserResponseDTO DTO = new UserResponseDTO(user.getId(), user.getRole(),user.getFirstName(), user.getLastName(), user.getEmail(), user.getTotalPoints(), user.getMembershipTier());
        return DTO;
    }

    private static void setUserValues(User user,  UserRequestDTO DTO){
        user.setEmail(DTO.email());
        user.setFirstName(DTO.firstname());
        user.setLastName(DTO.lastname());
        user.setRole(DTO.role());
        user.setPassword(DTO.password());
    }
}
