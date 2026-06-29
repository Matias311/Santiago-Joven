package com.santiago.joven.backend.dto;

import com.santiago.joven.backend.model.enums.NombreRol;
import java.util.UUID;
import lombok.Builder;

/** DTO de salida para un {@code Rol}. */
@Builder
public record RolResponse(UUID id, NombreRol nombre, String descripcion) {}
