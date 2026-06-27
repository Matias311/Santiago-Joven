package com.santiago.joven.backend.mapper;

import com.santiago.joven.backend.dto.ResenaCalificacionRequest;
import com.santiago.joven.backend.dto.ResenaCalificacionResponse;
import com.santiago.joven.backend.dto.ResenaCalificacionUpdate;
import com.santiago.joven.backend.model.entity.ResenaCalificacion;
import org.springframework.stereotype.Component;

/** Mapper para la entidad {@link ResenaCalificacion}. */
@Component
public class ResenaCalificacionMapper {

  /** Convierte entidad a DTO de respuesta. */
  public ResenaCalificacionResponse toResponse(ResenaCalificacion entity) {
    return new ResenaCalificacionResponse(
        entity.getId(),
        entity.getUsuario() != null ? entity.getUsuario().getId() : null,
        entity.getRecursoId(),
        entity.getTipoRecurso(),
        entity.getCalificacion(),
        entity.getComentario());
  }

  /** Convierte DTO de creacion a entidad. */
  public ResenaCalificacion toEntity(ResenaCalificacionRequest request) {
    var entity = new ResenaCalificacion();
    entity.setRecursoId(request.recursoId());
    entity.setTipoRecurso(request.tipoRecurso());
    entity.setCalificacion(request.calificacion());
    entity.setComentario(request.comentario());
    return entity;
  }

  /** Actualiza entidad existente con datos del DTO (solo campos no nulos). */
  public void updateEntity(ResenaCalificacionUpdate update, ResenaCalificacion entity) {
    if (update.calificacion() != null) entity.setCalificacion(update.calificacion());
    if (update.comentario() != null) entity.setComentario(update.comentario());
  }
}
