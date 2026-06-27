package com.santiago.joven.backend.dto;

import lombok.Builder;

/** DTO para actualizar un {@code Permiso}. */
@Builder
public record PermisoUpdate(String descripcion, String modulo) {}
