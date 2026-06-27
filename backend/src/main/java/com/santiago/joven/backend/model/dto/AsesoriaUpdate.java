package com.santiago.joven.backend.model.dto;

import java.util.UUID;
import lombok.Builder;

/** DTO para actualizar una {@code Asesoria}. */
@Builder
public record AsesoriaUpdate(
    String titulo,
    UUID categoriaId,
    String definicion,
    String objetivos,
    String metodologia,
    String imagen,
    Boolean activo,
    Integer orden) {}
