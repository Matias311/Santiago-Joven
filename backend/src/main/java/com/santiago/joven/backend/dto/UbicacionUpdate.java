package com.santiago.joven.backend.dto;

import java.math.BigDecimal;
import lombok.Builder;

/** DTO para actualizar una {@code Ubicacion}. */
@Builder
public record UbicacionUpdate(
    String nombre, String direccion, String ciudad, BigDecimal latitud, BigDecimal longitud) {}
