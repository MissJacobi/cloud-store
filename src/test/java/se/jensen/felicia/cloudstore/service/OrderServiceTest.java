package se.jensen.felicia.cloudstore.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.jensen.felicia.cloudstore.dto.OrdersDTO;
import se.jensen.felicia.cloudstore.model.Orders;
import se.jensen.felicia.cloudstore.repository.OrdersRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrdersRepository ordersRepository;

    @InjectMocks
    private OrdersService ordersService;

    @Test
    void testGetOrderById_Success(){
        // Arrange
        Orders mockOrder = new Orders();
        mockOrder.setId(1L);
        mockOrder.setTotalAmount(299.00);

        when(ordersRepository.findById(1L)).thenReturn(Optional.of(mockOrder));
        // Act
        OrdersDTO result = ordersService.getOrderById(1L);

        //Assert
        assertNotNull(result);
        assertEquals(299.00, result.totalAmount());

        verify(ordersRepository, times(1)).findById(1L);

    }
}
