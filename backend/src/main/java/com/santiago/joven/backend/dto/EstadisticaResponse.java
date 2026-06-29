package com.santiago.joven.backend.dto;

import com.santiago.joven.backend.model.enums.TipoRecurso;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

/** DTO de salida para una {@code Estadistica}. */
@Builder
public record EstadisticaResponse(
    UUID id,
    TipoRecurso tipoRecurso,
    UUID recursoId,
    Integer totalInscritos,
    Integer totalAsistentes,
    Integer totalResenas,
    BigDecimal promedioCalificacion,
    LocalDateTime fechaActualizacion) {}
