package com.santiago.joven.backend.mapper;

import com.santiago.joven.backend.dto.InscripcionRequest;
import com.santiago.joven.backend.dto.InscripcionResponse;
import com.santiago.joven.backend.dto.InscripcionUpdate;
import com.santiago.joven.backend.model.entity.Inscripcion;
import org.springframework.stereotype.Component;

/** Mapper para la entidad {@link Inscripcion}. */
@Component
public class InscripcionMapper {

  /** Convierte entidad a DTO de respuesta. */
  public InscripcionResponse toResponse(Inscripcion entity) {
    return new InscripcionResponse(
        entity.getId(),
        entity.getUsuario() != null ? entity.getUsuario().getId() : null,
        entity.getRecursoId(),
        entity.getTipoRecurso(),
        entity.getFechaInscripcion(),
        entity.getEstado(),
        entity.getNotas());
  }

  /** Convierte DTO de creacion a entidad. */
  public Inscripcion toEntity(InscripcionRequest request) {
    var entity = new Inscripcion();
    entity.setRecursoId(request.recursoId());
    entity.setTipoRecurso(request.tipoRecurso());
    entity.setEstado(request.estado());
    entity.setNotas(request.notas());
    return entity;
  }

  /** Actualiza entidad existente con datos del DTO (solo campos no nulos). */
  public void updateEntity(InscripcionUpdate update, Inscripcion entity) {
    if (update.estado() != null) entity.setEstado(update.estado());
    if (update.notas() != null) entity.setNotas(update.notas());
  }
}
