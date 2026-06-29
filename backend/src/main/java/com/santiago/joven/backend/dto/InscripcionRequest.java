package com.santiago.joven.backend.dto;

import com.santiago.joven.backend.model.enums.EstadoInscripcion;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

/** DTO de entrada para crear una {@code Inscripcion}. */
@Builder
public record InscripcionRequest(
    @NotNull UUID usuarioId,
    @NotNull UUID recursoId,
    @NotNull TipoRecurso tipoRecurso,
    EstadoInscripcion estado,
    String notas) {}
