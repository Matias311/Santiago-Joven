package com.santiago.joven.backend.dto;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

/** DTO de salida para una {@code Ubicacion}. */
@Builder
public record UbicacionResponse(
    UUID id,
    String nombre,
    String direccion,
    String ciudad,
    BigDecimal latitud,
    BigDecimal longitud) {}
