package com.santiago.joven.backend.model.dto;

import com.santiago.joven.backend.model.enums.EstadoActividad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

/** DTO de entrada para crear una {@code ActividadTaller}. */
@Builder
public record ActividadTallerRequest(
    @NotBlank String titulo,
    @NotBlank String descripcion,
    @NotNull UUID categoriaId,
    @NotNull LocalDateTime fechaHora,
    Integer cantidadMaximaParticipantes,
    String imagen,
    UUID ubicacionId,
    String enlaceInscripcion,
    EstadoActividad estado) {}
