package com.santiago.joven.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/** DTO de entrada para crear una {@code AccionJoven}. */
@Builder
public record AccionJovenRequest(
    @NotBlank String titulo, @NotBlank String descripcion, String imagen) {}
