package se.jensen.felicia.cloudstore.dto;

public record OrderItemDTO(Long productId,
                           String productName,
                           double price,
                           int quantity) {
}
