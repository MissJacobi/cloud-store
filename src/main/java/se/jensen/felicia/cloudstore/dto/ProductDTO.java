package se.jensen.felicia.cloudstore.dto;

public record ProductDTO(
        Long id,
        String title,
        double price,
        String description,
        String category,
        String image
) {
}
