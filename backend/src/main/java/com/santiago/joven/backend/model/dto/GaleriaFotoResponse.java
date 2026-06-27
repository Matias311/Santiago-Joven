package com.santiago.joven.backend.model.dto;

import java.util.UUID;
import lombok.Builder;

/** DTO de salida para una {@code GaleriaFoto}. */
@Builder
public record GaleriaFotoResponse(
    UUID id,
    UUID actividadId,
    String urlImagen,
    String titulo,
    String descripcion,
    Integer orden) {}
