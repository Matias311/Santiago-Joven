package com.santiago.joven.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Solicitud para restablecer contrasena usando codigo OTP")
public record RestablecerRequest(
    @Schema(description = "Email registrado del usuario", example = "usuario@santiagojoven.org")
        @NotBlank
        @Email
        String email,
    @Schema(description = "Codigo OTP de 5 digitos", example = "12345")
        @NotBlank
        @Pattern(regexp = "\\d{5}", message = "El codigo debe tener 5 digitos")
        String codigo,
    @Schema(description = "Nueva contrasena del usuario", example = "password123")
        @NotBlank
        @Size(min = 8)
        String nuevaPassword) {}
