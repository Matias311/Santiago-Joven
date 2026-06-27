package com.santiago.joven.backend.dto;

import java.math.BigDecimal;
import lombok.Builder;

/** DTO para actualizar una {@code Estadistica}. */
@Builder
public record EstadisticaUpdate(
    Integer totalInscritos,
    Integer totalAsistentes,
    Integer totalResenas,
    BigDecimal promedioCalificacion) {}
