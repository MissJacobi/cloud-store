package se.jensen.felicia.cloudstore.controller;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class OrdersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @org.springframework.test.context.bean.override.mockito.MockitoBean
    private se.jensen.felicia.cloudstore.security.JwtSigner jwtSigner;

    @Test
    @org.springframework.security.test.context.support.WithMockUser(username = "felicia")
    void testGetOrdersByUserEndpoint_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/orders/user/test@test.se"))
                .andExpect(status().isOk());
    }

}
