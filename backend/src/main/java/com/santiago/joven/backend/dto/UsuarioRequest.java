package com.santiago.joven.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/** DTO de entrada para crear un {@code Usuario}. */
@Builder
public record UsuarioRequest(
    @NotBlank @Email String email,
    @NotBlank @Size(min = 8) String password,
    @NotBlank String nombre,
    @NotBlank String apellido,
    @Pattern(regexp = "\\d{9}", message = "El telefono debe tener exactamente 9 digitos") String telefono) {}
