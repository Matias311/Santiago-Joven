package com.santiago.joven.backend.model.dto;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Builder;

/**
 * DTO de entrada para crear una {@code Ubicacion}.
 */
@Builder
public record UbicacionRequest(
  @NotBlank String nombre,
  String direccion,
  String ciudad,
  BigDecimal latitud,
  BigDecimal longitud
) {}
