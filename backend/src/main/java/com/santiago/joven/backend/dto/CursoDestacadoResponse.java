package com.santiago.joven.backend.dto;

import java.util.UUID;
import lombok.Builder;

/** DTO de salida para un {@code CursoDestacado}. */
@Builder
public record CursoDestacadoResponse(
    UUID id,
    String titulo,
    String descripcion,
    String eslogan,
    String objetivo,
    UUID categoriaId,
    String imagen,
    Boolean activo,
    Integer orden,
    String enlaceInscripcion,
    UUID usuarioCreadorId) {}
