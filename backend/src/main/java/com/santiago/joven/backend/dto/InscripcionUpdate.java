package com.santiago.joven.backend.dto;

import com.santiago.joven.backend.model.enums.EstadoInscripcion;
import lombok.Builder;

/** DTO para actualizar una {@code Inscripcion}. */
@Builder
public record InscripcionUpdate(EstadoInscripcion estado, String notas) {}
