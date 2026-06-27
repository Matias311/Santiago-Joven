package com.santiago.joven.backend.model.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

/** DTO de salida para un {@code Contacto}. */
@Builder
public record ContactoResponse(
    UUID id,
    String nombre,
    String email,
    String telefono,
    String mensaje,
    String programaInteres,
    LocalDateTime fechaContacto,
    Boolean respondido,
    String respuesta,
    UUID usuarioRespondioId,
    LocalDateTime fechaRespuesta) {}
