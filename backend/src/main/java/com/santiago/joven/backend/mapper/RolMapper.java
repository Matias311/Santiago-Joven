package com.santiago.joven.backend.mapper;

import com.santiago.joven.backend.dto.RolRequest;
import com.santiago.joven.backend.dto.RolResponse;
import com.santiago.joven.backend.dto.RolUpdate;
import com.santiago.joven.backend.model.entity.Rol;
import org.springframework.stereotype.Component;

/** Mapper para {@link Rol}. */
@Component
public class RolMapper {

  /** Convierte entidad a DTO de respuesta. */
  public RolResponse toResponse(Rol entity) {
    return new RolResponse(
        entity.getId(),
        entity.getNombre(),
        entity.getDescripcion());
  }

  /** Convierte DTO de creacion a entidad. */
  public Rol toEntity(RolRequest request) {
    var entity = new Rol();
    entity.setNombre(request.nombre());
    entity.setDescripcion(request.descripcion());
    return entity;
  }

  /** Actualiza entidad existente con datos del DTO (solo campos no nulos). */
  public void updateEntity(RolUpdate update, Rol entity) {
    if (update.descripcion() != null) entity.setDescripcion(update.descripcion());
  }
}
