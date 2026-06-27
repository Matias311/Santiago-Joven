package com.santiago.joven.backend.dto;

import lombok.Builder;

/** DTO para actualizar una {@code GaleriaFoto}. */
@Builder
public record GaleriaFotoUpdate(
    String urlImagen, String titulo, String descripcion, Integer orden) {}
