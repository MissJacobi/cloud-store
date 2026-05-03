package se.jensen.felicia.cloudstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @Email @NotBlank(message = "Can't be left empty.")
        String email,

        @NotBlank(message = "Can't be left empty.")
        String firstname,

        @NotBlank(message = "Can't be left empty..")
        String lastname,

        @NotBlank(message = "Can't be left empty.")
        String role,

        @NotBlank
        @Pattern(regexp = "[A-Za-z0-9]", message = "Only letters and numbers.")
        @Size(min = 8, max = 40, message = "Password needs to be between 8 and 40 characters.")
        String password
) {
}
