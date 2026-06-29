package com.santiago.joven.backend.dto;

import java.util.UUID;
import lombok.Builder;

/** DTO de salida para un recurso de {@code SaludMental}. */
@Builder
public record SaludMentalResponse(
    UUID id,
    String titulo,
    String descripcion,
    String icono,
    String telefono,
    String enlace,
    Integer orden) {}
