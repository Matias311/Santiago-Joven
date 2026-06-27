package com.santiago.joven.backend.model.dto;

import com.santiago.joven.backend.model.enums.TipoRecurso;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

/** DTO de entrada para crear una {@code ResenaCalificacion}. */
@Builder
public record ResenaCalificacionRequest(
    @NotNull UUID usuarioId,
    @NotNull UUID recursoId,
    @NotNull TipoRecurso tipoRecurso,
    @NotNull @Min(1) @Max(5) Integer calificacion,
    String comentario) {}
