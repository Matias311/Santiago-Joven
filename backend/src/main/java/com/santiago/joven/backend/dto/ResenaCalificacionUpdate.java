package com.santiago.joven.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;

/** DTO para actualizar una {@code ResenaCalificacion}. */
@Builder
public record ResenaCalificacionUpdate(@Min(1) @Max(5) Integer calificacion, String comentario) {}
