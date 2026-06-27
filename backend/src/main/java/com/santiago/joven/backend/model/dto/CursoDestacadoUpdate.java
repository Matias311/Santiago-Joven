package com.santiago.joven.backend.model.dto;

import java.util.UUID;
import lombok.Builder;

/** DTO para actualizar un {@code CursoDestacado}. */
@Builder
public record CursoDestacadoUpdate(
    String titulo,
    String descripcion,
    String eslogan,
    String objetivo,
    UUID categoriaId,
    String imagen,
    Boolean activo,
    Integer orden,
    String enlaceInscripcion) {}
