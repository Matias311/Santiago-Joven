package com.santiago.joven.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

/** DTO para actualizar parcialmente un {@code Usuario}. */
@Builder
public record UsuarioUpdate(
    @Email String email,
    @Size(min = 8) String password,
    String nombre,
    String apellido,
    @Pattern(regexp = "\\d{9}", message = "El telefono debe tener exactamente 9 digitos") String telefono,
    Boolean activo) {}
