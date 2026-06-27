package com.santiago.joven.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/** DTO de entrada para crear un recurso de {@code SaludMental}. */
@Builder
public record SaludMentalRequest(
    @NotBlank String titulo,
    String descripcion,
    String icono,
    String telefono,
    String enlace,
    Integer orden) {}
