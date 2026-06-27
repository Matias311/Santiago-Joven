package com.santiago.joven.backend.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/** DTO de entrada para crear un {@code Contacto}. */
@Builder
public record ContactoRequest(
    @NotBlank String nombre,
    @NotBlank @Email String email,
    String telefono,
    @NotBlank String mensaje,
    String programaInteres) {}
