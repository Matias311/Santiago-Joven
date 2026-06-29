package com.santiago.joven.backend.mapper;

import com.santiago.joven.backend.dto.AccionJovenRequest;
import com.santiago.joven.backend.dto.AccionJovenResponse;
import com.santiago.joven.backend.dto.AccionJovenUpdate;
import com.santiago.joven.backend.model.entity.AccionJoven;
import org.springframework.stereotype.Component;

/** Mapper para la entidad {@link AccionJoven}. */
@Component
public class AccionJovenMapper {

  /** Convierte entidad a DTO de respuesta. */
  public AccionJovenResponse toResponse(AccionJoven entity) {
    return new AccionJovenResponse(
        entity.getId(),
        entity.getTitulo(),
        entity.getDescripcion(),
        entity.getImagen(),
        entity.getActivo(),
        entity.getUsuarioCreador() != null ? entity.getUsuarioCreador().getId() : null);
  }

  /** Convierte DTO de creacion a entidad. */
  public AccionJoven toEntity(AccionJovenRequest request) {
    var entity = new AccionJoven();
    entity.setTitulo(request.titulo());
    entity.setDescripcion(request.descripcion());
    entity.setImagen(request.imagen());
    return entity;
  }

  /** Actualiza entidad existente con datos del DTO (solo campos no nulos). */
  public void updateEntity(AccionJovenUpdate update, AccionJoven entity) {
    if (update.titulo() != null) entity.setTitulo(update.titulo());
    if (update.descripcion() != null) entity.setDescripcion(update.descripcion());
    if (update.imagen() != null) entity.setImagen(update.imagen());
    if (update.activo() != null) entity.setActivo(update.activo());
  }
}
