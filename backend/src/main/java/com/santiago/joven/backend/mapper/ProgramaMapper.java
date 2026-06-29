package com.santiago.joven.backend.mapper;

import com.santiago.joven.backend.dto.ProgramaRequest;
import com.santiago.joven.backend.dto.ProgramaResponse;
import com.santiago.joven.backend.dto.ProgramaUpdate;
import com.santiago.joven.backend.model.entity.Programa;
import org.springframework.stereotype.Component;

/** Mapper para la entidad {@link Programa}. */
@Component
public class ProgramaMapper {

  /** Convierte entidad a DTO de respuesta. */
  public ProgramaResponse toResponse(Programa entity) {
    return new ProgramaResponse(
        entity.getId(),
        entity.getTitulo(),
        entity.getDescripcion(),
        entity.getDefinicion(),
        entity.getObjetivos(),
        entity.getMetodologia(),
        entity.getImagen(),
        entity.getActivo(),
        entity.getOrden(),
        entity.getUsuarioCreador() != null ? entity.getUsuarioCreador().getId() : null);
  }

  /** Convierte DTO de creacion a entidad. */
  public Programa toEntity(ProgramaRequest request) {
    var entity = new Programa();
    entity.setTitulo(request.titulo());
    entity.setDescripcion(request.descripcion());
    entity.setDefinicion(request.definicion());
    entity.setObjetivos(request.objetivos());
    entity.setMetodologia(request.metodologia());
    entity.setImagen(request.imagen());
    entity.setOrden(request.orden());
    return entity;
  }

  /** Actualiza entidad existente con datos del DTO (solo campos no nulos). */
  public void updateEntity(ProgramaUpdate update, Programa entity) {
    if (update.titulo() != null) entity.setTitulo(update.titulo());
    if (update.descripcion() != null) entity.setDescripcion(update.descripcion());
    if (update.definicion() != null) entity.setDefinicion(update.definicion());
    if (update.objetivos() != null) entity.setObjetivos(update.objetivos());
    if (update.metodologia() != null) entity.setMetodologia(update.metodologia());
    if (update.imagen() != null) entity.setImagen(update.imagen());
    if (update.activo() != null) entity.setActivo(update.activo());
    if (update.orden() != null) entity.setOrden(update.orden());
  }
}
