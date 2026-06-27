package com.santiago.joven.backend.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

/** DTO de entrada para crear un {@code CursoDestacado}. */
@Builder
public record CursoDestacadoRequest(
    @NotBlank String titulo,
    @NotBlank String descripcion,
    String eslogan,
    @NotBlank String objetivo,
    @NotNull UUID categoriaId,
    String imagen,
    Integer orden,
    String enlaceInscripcion) {}
