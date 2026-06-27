package com.santiago.joven.backend.model.dto;

import java.util.UUID;
import lombok.Builder;

/** DTO para actualizar un {@code Contacto} (responder). */
@Builder
public record ContactoUpdate(Boolean respondido, String respuesta, UUID usuarioRespondioId) {}
