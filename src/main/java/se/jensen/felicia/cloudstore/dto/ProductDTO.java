package se.jensen.felicia.cloudstore.dto;

public record ProductDTO(
        Long id,
        String title,
        double price,
        int quantity,
        String description,
        String category,
        String image
) {
}
