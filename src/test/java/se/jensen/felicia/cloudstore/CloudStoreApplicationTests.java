package se.jensen.felicia.cloudstore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CloudStoreApplicationTests {

    @org.springframework.test.context.bean.override.mockito.MockitoBean
    private se.jensen.felicia.cloudstore.security.JwtSigner jwtSigner;

    @Test
    void contextLoads() {
    }

}
