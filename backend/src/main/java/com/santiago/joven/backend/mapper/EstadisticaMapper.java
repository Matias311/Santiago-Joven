package com.santiago.joven.backend.mapper;

import com.santiago.joven.backend.dto.EstadisticaRequest;
import com.santiago.joven.backend.dto.EstadisticaResponse;
import com.santiago.joven.backend.dto.EstadisticaUpdate;
import com.santiago.joven.backend.model.entity.Estadistica;
import org.springframework.stereotype.Component;

/** Mapper para la entidad {@link Estadistica}. */
@Component
public class EstadisticaMapper {

  /** Convierte entidad a DTO de respuesta. */
  public EstadisticaResponse toResponse(Estadistica entity) {
    return new EstadisticaResponse(
        entity.getId(),
        entity.getTipoRecurso(),
        entity.getRecursoId(),
        entity.getTotalInscritos(),
        entity.getTotalAsistentes(),
        entity.getTotalResenas(),
        entity.getPromedioCalificacion(),
        entity.getFechaActualizacion());
  }

  /** Convierte DTO de creacion a entidad. */
  public Estadistica toEntity(EstadisticaRequest request) {
    var entity = new Estadistica();
    entity.setTipoRecurso(request.tipoRecurso());
    entity.setRecursoId(request.recursoId());
    return entity;
  }

  /** Actualiza entidad existente con datos del DTO (solo campos no nulos). */
  public void updateEntity(EstadisticaUpdate update, Estadistica entity) {
    if (update.totalInscritos() != null) entity.setTotalInscritos(update.totalInscritos());
    if (update.totalAsistentes() != null) entity.setTotalAsistentes(update.totalAsistentes());
    if (update.totalResenas() != null) entity.setTotalResenas(update.totalResenas());
    if (update.promedioCalificacion() != null)
      entity.setPromedioCalificacion(update.promedioCalificacion());
  }
}
