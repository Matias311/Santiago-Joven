package com.santiago.joven.backend.dto;

import lombok.Builder;

/** DTO para actualizar un recurso de {@code SaludMental}. */
@Builder
public record SaludMentalUpdate(
    String titulo,
    String descripcion,
    String icono,
    String telefono,
    String enlace,
    Integer orden) {}
