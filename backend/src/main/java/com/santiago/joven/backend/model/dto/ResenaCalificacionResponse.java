package com.santiago.joven.backend.model.dto;

import com.santiago.joven.backend.model.enums.TipoRecurso;
import java.util.UUID;
import lombok.Builder;

/** DTO de salida para una {@code ResenaCalificacion}. */
@Builder
public record ResenaCalificacionResponse(
    UUID id,
    UUID usuarioId,
    UUID recursoId,
    TipoRecurso tipoRecurso,
    Integer calificacion,
    String comentario) {}
