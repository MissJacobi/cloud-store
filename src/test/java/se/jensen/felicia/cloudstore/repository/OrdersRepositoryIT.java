package se.jensen.felicia.cloudstore.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.jensen.felicia.cloudstore.model.Orders;
import se.jensen.felicia.cloudstore.model.User;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class OrdersRepositoryIT {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UserRepository userRepository;

    @org.springframework.test.context.bean.override.mockito.MockitoBean
    private se.jensen.felicia.cloudstore.security.JwtSigner jwtSigner;


    @Test
    void testSaveAndFindOrder(){
        // Arrange
        User testUser = new User();
        testUser.setEmail("test@test.se");
        testUser.setPassword("password");
        testUser.setRole("admin");
        testUser.setFirstName("Anna");
        testUser.setLastName("Andersson");
        userRepository.save(testUser);


        Orders orders = new Orders();
        orders.setTotalAmount(999.99);
        orders.setUser(testUser);
        orders.setOrderDate(LocalDateTime.now());
        ordersRepository.save(orders);

        // Act
        Optional<Orders> result = ordersRepository.findById(orders.getId());

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getTotalAmount()).isEqualTo(999.99);
        assertThat(result.get().getUser().getEmail()).isEqualTo("test@test.se");
    }
}

