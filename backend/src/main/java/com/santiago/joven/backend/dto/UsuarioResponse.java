package com.santiago.joven.backend.dto;

import java.util.UUID;
import lombok.Builder;

/** DTO de salida para un {@code Usuario}. */
@Builder
public record UsuarioResponse(
    UUID id, String email, String nombre, String apellido, String telefono, Boolean activo) {}
