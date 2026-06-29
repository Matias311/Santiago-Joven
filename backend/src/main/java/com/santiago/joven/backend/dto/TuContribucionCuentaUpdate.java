package com.santiago.joven.backend.dto;

import lombok.Builder;

/** DTO para actualizar {@code TuContribucionCuenta}. */
@Builder
public record TuContribucionCuentaUpdate(
    String titulo, String descripcion, String linkGoogleForms, Boolean activo) {}
