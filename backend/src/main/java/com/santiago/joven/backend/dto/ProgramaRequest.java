package com.santiago.joven.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/** DTO de entrada para crear un {@code Programa}. */
@Builder
public record ProgramaRequest(
    @NotBlank String titulo,
    @NotBlank String descripcion,
    @NotBlank String definicion,
    @NotBlank String objetivos,
    @NotBlank String metodologia,
    String imagen,
    Integer orden) {}
