package com.santiago.joven.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

/** DTO de entrada para crear una {@code GaleriaFoto}. */
@Builder
public record GaleriaFotoRequest(
    @NotNull UUID actividadId,
    @NotBlank String urlImagen,
    String titulo,
    String descripcion,
    Integer orden) {}
