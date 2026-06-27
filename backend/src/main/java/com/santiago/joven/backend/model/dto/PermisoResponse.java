package com.santiago.joven.backend.model.dto;

import java.util.UUID;
import lombok.Builder;

/** DTO de salida para un {@code Permiso}. */
@Builder
public record PermisoResponse(UUID id, String nombre, String descripcion, String modulo) {}
