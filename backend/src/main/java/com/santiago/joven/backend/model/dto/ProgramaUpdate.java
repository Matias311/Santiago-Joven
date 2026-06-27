package com.santiago.joven.backend.model.dto;

import lombok.Builder;

/** DTO para actualizar un {@code Programa}. */
@Builder
public record ProgramaUpdate(
    String titulo,
    String descripcion,
    String definicion,
    String objetivos,
    String metodologia,
    String imagen,
    Boolean activo,
    Integer orden) {}
