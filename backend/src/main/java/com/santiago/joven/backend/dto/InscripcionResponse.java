package com.santiago.joven.backend.dto;

import com.santiago.joven.backend.model.enums.EstadoInscripcion;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

/** DTO de salida para una {@code Inscripcion}. */
@Builder
public record InscripcionResponse(
    UUID id,
    UUID usuarioId,
    UUID recursoId,
    TipoRecurso tipoRecurso,
    LocalDateTime fechaInscripcion,
    EstadoInscripcion estado,
    String notas) {}
