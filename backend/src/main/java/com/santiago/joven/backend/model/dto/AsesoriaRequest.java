package com.santiago.joven.backend.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

/** DTO de entrada para crear una {@code Asesoria}. */
@Builder
public record AsesoriaRequest(
    @NotBlank String titulo,
    @NotNull UUID categoriaId,
    @NotBlank String definicion,
    @NotBlank String objetivos,
    @NotBlank String metodologia,
    String imagen,
    Integer orden) {}
