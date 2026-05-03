package se.jensen.felicia.cloudstore.dto;

import java.time.LocalDateTime;
import java.util.List;

public record OrdersDTO(Long id,
                        LocalDateTime orderDate,
                        double totalAmount,
                        List<OrderItemDTO> items) {
}