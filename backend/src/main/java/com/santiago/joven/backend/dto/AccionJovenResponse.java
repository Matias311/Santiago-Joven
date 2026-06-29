package com.santiago.joven.backend.dto;

import java.util.UUID;
import lombok.Builder;

/** DTO de salida para una {@code AccionJoven}. */
@Builder
public record AccionJovenResponse(
    UUID id,
    String titulo,
    String descripcion,
    String imagen,
    Boolean activo,
    UUID usuarioCreadorId) {}
