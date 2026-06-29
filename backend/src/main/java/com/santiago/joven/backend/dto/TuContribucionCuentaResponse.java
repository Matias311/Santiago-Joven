package com.santiago.joven.backend.dto;

import java.util.UUID;
import lombok.Builder;

/** DTO de salida para {@code TuContribucionCuenta}. */
@Builder
public record TuContribucionCuentaResponse(
    UUID id, String titulo, String descripcion, String linkGoogleForms, Boolean activo) {}
