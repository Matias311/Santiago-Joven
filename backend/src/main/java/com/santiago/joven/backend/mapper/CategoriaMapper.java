package com.santiago.joven.backend.mapper;

import com.santiago.joven.backend.dto.CategoriaRequest;
import com.santiago.joven.backend.dto.CategoriaResponse;
import com.santiago.joven.backend.dto.CategoriaUpdate;
import com.santiago.joven.backend.model.entity.Categoria;
import org.springframework.stereotype.Component;

/** Mapper para {@link Categoria}. */
@Component
public class CategoriaMapper {

  /** Convierte entidad a DTO de respuesta. */
  public CategoriaResponse toResponse(Categoria entity) {
    return new CategoriaResponse(
        entity.getId(),
        entity.getNombre(),
        entity.getDescripcion(),
        entity.getIcono(),
        entity.getColor(),
        entity.getTipo());
  }

  /** Convierte DTO de creacion a entidad. */
  public Categoria toEntity(CategoriaRequest request) {
    var entity = new Categoria();
    entity.setNombre(request.nombre());
    entity.setDescripcion(request.descripcion());
    entity.setIcono(request.icono());
    entity.setColor(request.color());
    entity.setTipo(request.tipo());
    return entity;
  }

  /** Actualiza entidad existente con datos del DTO (solo campos no nulos). */
  public void updateEntity(CategoriaUpdate update, Categoria entity) {
    if (update.nombre() != null) entity.setNombre(update.nombre());
    if (update.descripcion() != null) entity.setDescripcion(update.descripcion());
    if (update.icono() != null) entity.setIcono(update.icono());
    if (update.color() != null) entity.setColor(update.color());
    if (update.tipo() != null) entity.setTipo(update.tipo());
  }
}
