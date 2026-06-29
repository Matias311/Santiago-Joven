package com.santiago.joven.backend.dto;

import java.util.UUID;
import lombok.Builder;

/** DTO de salida para un {@code Programa}. */
@Builder
public record ProgramaResponse(
    UUID id,
    String titulo,
    String descripcion,
    String definicion,
    String objetivos,
    String metodologia,
    String imagen,
    Boolean activo,
    Integer orden,
    UUID usuarioCreadorId) {}
