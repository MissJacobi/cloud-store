package se.jensen.felicia.cloudstore.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.felicia.cloudstore.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional //För att hålla databsen ren när man kör testern och inte sparar testUsers.
public class UserRepositoryIT {
    @Autowired
    private UserRepository userRepository;

    @org.springframework.test.context.bean.override.mockito.MockitoBean
    private se.jensen.felicia.cloudstore.security.JwtSigner jwtSigner;

    @Test
    void testSaveAndFindUser(){
        //Arrange
        User user = new User();
        user.setEmail("test@test.se");
        user.setPassword("123testing");
        user.setRole("admin");
        user.setFirstName("test");
        user.setLastName("testing");
        userRepository.save(user);

        //Act
        Optional<User> result = userRepository.findByEmail("test@test.se");

        //Assert
        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("test@test.se");
    }
}
