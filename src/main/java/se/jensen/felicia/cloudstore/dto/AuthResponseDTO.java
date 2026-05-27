package se.jensen.felicia.cloudstore.dto;

public record AuthResponseDTO(String token,
                              UserResponseDTO user) {
}
