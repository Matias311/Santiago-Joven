package com.santiago.joven.backend.model.dto;

import java.util.UUID;
import lombok.Builder;

/** DTO de salida para un {@code Usuario}. */
@Builder
public record UsuarioResponse(
    UUID id, String email, String nombre, String apellido, Boolean activo) {}
