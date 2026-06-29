package com.santiago.joven.backend.mapper;

import com.santiago.joven.backend.dto.AsesoriaRequest;
import com.santiago.joven.backend.dto.AsesoriaResponse;
import com.santiago.joven.backend.dto.AsesoriaUpdate;
import com.santiago.joven.backend.model.entity.Asesoria;
import org.springframework.stereotype.Component;

/** Mapper para {@link Asesoria}. */
@Component
public class AsesoriaMapper {

  /** Convierte entidad a DTO de respuesta. */
  public AsesoriaResponse toResponse(Asesoria entity) {
    return new AsesoriaResponse(
        entity.getId(),
        entity.getTitulo(),
        entity.getCategoria() != null ? entity.getCategoria().getId() : null,
        entity.getDefinicion(),
        entity.getObjetivos(),
        entity.getMetodologia(),
        entity.getImagen(),
        entity.getActivo(),
        entity.getOrden(),
        entity.getUsuarioCreador() != null ? entity.getUsuarioCreador().getId() : null);
  }

  /** Convierte DTO de creacion a entidad. */
  public Asesoria toEntity(AsesoriaRequest request) {
    var entity = new Asesoria();
    entity.setTitulo(request.titulo());
    entity.setDefinicion(request.definicion());
    entity.setObjetivos(request.objetivos());
    entity.setMetodologia(request.metodologia());
    entity.setImagen(request.imagen());
    entity.setOrden(request.orden());
    return entity;
  }

  /** Actualiza entidad existente con datos del DTO (solo campos no nulos). */
  public void updateEntity(AsesoriaUpdate update, Asesoria entity) {
    if (update.titulo() != null) entity.setTitulo(update.titulo());
    if (update.definicion() != null) entity.setDefinicion(update.definicion());
    if (update.objetivos() != null) entity.setObjetivos(update.objetivos());
    if (update.metodologia() != null) entity.setMetodologia(update.metodologia());
    if (update.imagen() != null) entity.setImagen(update.imagen());
    if (update.activo() != null) entity.setActivo(update.activo());
    if (update.orden() != null) entity.setOrden(update.orden());
  }
}
