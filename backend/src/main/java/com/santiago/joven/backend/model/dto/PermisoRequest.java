package com.santiago.joven.backend.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/** DTO de entrada para crear un {@code Permiso}. */
@Builder
public record PermisoRequest(@NotBlank String nombre, String descripcion, String modulo) {}
