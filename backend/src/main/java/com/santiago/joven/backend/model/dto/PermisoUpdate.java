package com.santiago.joven.backend.model.dto;

import lombok.Builder;

/** DTO para actualizar un {@code Permiso}. */
@Builder
public record PermisoUpdate(String descripcion, String modulo) {}
