package com.santiago.joven.backend.model.dto;

import com.santiago.joven.backend.model.enums.TipoRecurso;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

/** DTO de entrada para crear una {@code Estadistica}. */
@Builder
public record EstadisticaRequest(@NotNull TipoRecurso tipoRecurso, @NotNull UUID recursoId) {}
