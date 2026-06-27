package com.santiago.joven.backend.model.dto;

import com.santiago.joven.backend.model.enums.EstadoActividad;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

/** DTO para actualizar una {@code ActividadTaller}. */
@Builder
public record ActividadTallerUpdate(
    String titulo,
    String descripcion,
    UUID categoriaId,
    LocalDateTime fechaHora,
    Boolean activo,
    Integer cantidadMaximaParticipantes,
    String imagen,
    UUID ubicacionId,
    String enlaceInscripcion,
    Integer inscritos,
    EstadoActividad estado) {}
