package se.jensen.felicia.cloudstore.dto;

public record UserResponseDTO(
        Long userId,
        String role,
        String firstname,
        String lastname,
        String email,
        int totalPoints,
        String membershipTier
) {
}