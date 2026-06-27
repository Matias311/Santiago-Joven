package com.santiago.joven.backend.model.dto;

import java.util.UUID;
import lombok.Builder;

/** DTO de salida para una {@code Asesoria}. */
@Builder
public record AsesoriaResponse(
    UUID id,
    String titulo,
    UUID categoriaId,
    String definicion,
    String objetivos,
    String metodologia,
    String imagen,
    Boolean activo,
    Integer orden,
    UUID usuarioCreadorId) {}
