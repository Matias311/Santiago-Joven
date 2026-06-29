package com.santiago.joven.backend.dto;

import com.santiago.joven.backend.model.enums.NombreRol;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/** DTO de entrada para crear un {@code Rol}. */
@Builder
public record RolRequest(@NotNull NombreRol nombre, String descripcion) {}
