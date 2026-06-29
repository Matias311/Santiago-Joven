package com.santiago.joven.backend.dto;

import lombok.Builder;

/** DTO para actualizar un {@code Rol}. */
@Builder
public record RolUpdate(String descripcion) {}
