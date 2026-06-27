package com.santiago.joven.backend.model.dto;

import com.santiago.joven.backend.model.enums.TipoCambio;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

/** DTO de salida para un registro de {@code Auditoria}. */
@Builder
public record AuditoriaResponse(
    UUID id,
    String entidadTipo,
    UUID entidadId,
    UUID usuarioId,
    TipoCambio tipoCambio,
    String cambiosAnteriores,
    String cambiosNuevos,
    LocalDateTime fecha) {}
