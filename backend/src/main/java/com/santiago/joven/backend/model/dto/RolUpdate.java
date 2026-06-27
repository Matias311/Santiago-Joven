package com.santiago.joven.backend.model.dto;

import lombok.Builder;

/** DTO para actualizar un {@code Rol}. */
@Builder
public record RolUpdate(String descripcion) {}
