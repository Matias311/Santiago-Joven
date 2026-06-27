package com.santiago.joven.backend.mapper;

import com.santiago.joven.backend.dto.PermisoRequest;
import com.santiago.joven.backend.dto.PermisoResponse;
import com.santiago.joven.backend.dto.PermisoUpdate;
import com.santiago.joven.backend.model.entity.Permiso;
import org.springframework.stereotype.Component;

/** Mapper para {@link Permiso}. */
@Component
public class PermisoMapper {

  /** Convierte entidad a DTO de respuesta. */
  public PermisoResponse toResponse(Permiso entity) {
    return new PermisoResponse(
        entity.getId(),
        entity.getNombre(),
        entity.getDescripcion(),
        entity.getModulo());
  }

  /** Convierte DTO de creacion a entidad. */
  public Permiso toEntity(PermisoRequest request) {
    var entity = new Permiso();
    entity.setNombre(request.nombre());
    entity.setDescripcion(request.descripcion());
    entity.setModulo(request.modulo());
    return entity;
  }

  /** Actualiza entidad existente con datos del DTO (solo campos no nulos). */
  public void updateEntity(PermisoUpdate update, Permiso entity) {
    if (update.descripcion() != null) entity.setDescripcion(update.descripcion());
    if (update.modulo() != null) entity.setModulo(update.modulo());
  }
}
