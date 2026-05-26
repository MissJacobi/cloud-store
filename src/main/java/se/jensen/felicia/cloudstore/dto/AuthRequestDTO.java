package se.jensen.felicia.cloudstore.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDTO(
        @NotBlank(message = "Can't be empthy")
        String email,
        @NotBlank(message = "Can't be empthy")
        String password
) {
}
