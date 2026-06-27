package com.santiago.joven.backend.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/** DTO de entrada para {@code TuContribucionCuenta}. */
@Builder
public record TuContribucionCuentaRequest(
    @NotBlank String titulo, @NotBlank String descripcion, @NotBlank String linkGoogleForms) {}
