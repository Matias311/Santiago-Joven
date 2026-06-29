package com.santiago.joven.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Solicitud de codigo OTP para recuperar contrasena")
public record RecuperarRequest(
    @Schema(description = "Email registrado del usuario", example = "usuario@santiagojoven.org")
        @NotBlank
        @Email
        String email) {}
