package com.santiago.joven.backend.model.dto;

import lombok.Builder;

/** DTO para actualizar una {@code AccionJoven}. */
@Builder
public record AccionJovenUpdate(String titulo, String descripcion, String imagen, Boolean activo) {}
