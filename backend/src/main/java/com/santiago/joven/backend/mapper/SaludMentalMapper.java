package com.santiago.joven.backend.mapper;

import com.santiago.joven.backend.dto.SaludMentalRequest;
import com.santiago.joven.backend.dto.SaludMentalResponse;
import com.santiago.joven.backend.dto.SaludMentalUpdate;
import com.santiago.joven.backend.model.entity.SaludMental;
import org.springframework.stereotype.Component;

/** Mapper para {@link SaludMental}. */
@Component
public class SaludMentalMapper {

  /** Convierte entidad a DTO de respuesta. */
  public SaludMentalResponse toResponse(SaludMental entity) {
    return new SaludMentalResponse(
        entity.getId(),
        entity.getTitulo(),
        entity.getDescripcion(),
        entity.getIcono(),
        entity.getTelefono(),
        entity.getEnlace(),
        entity.getOrden());
  }

  /** Convierte DTO de creacion a entidad. */
  public SaludMental toEntity(SaludMentalRequest request) {
    var entity = new SaludMental();
    entity.setTitulo(request.titulo());
    entity.setDescripcion(request.descripcion());
    entity.setIcono(request.icono());
    entity.setTelefono(request.telefono());
    entity.setEnlace(request.enlace());
    entity.setOrden(request.orden());
    return entity;
  }

  /** Actualiza entidad existente con datos del DTO (solo campos no nulos). */
  public void updateEntity(SaludMentalUpdate update, SaludMental entity) {
    if (update.titulo() != null) entity.setTitulo(update.titulo());
    if (update.descripcion() != null) entity.setDescripcion(update.descripcion());
    if (update.icono() != null) entity.setIcono(update.icono());
    if (update.telefono() != null) entity.setTelefono(update.telefono());
    if (update.enlace() != null) entity.setEnlace(update.enlace());
    if (update.orden() != null) entity.setOrden(update.orden());
  }
}
