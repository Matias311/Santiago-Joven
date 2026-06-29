package com.santiago.joven.backend.mapper;

import com.santiago.joven.backend.dto.CursoDestacadoRequest;
import com.santiago.joven.backend.dto.CursoDestacadoResponse;
import com.santiago.joven.backend.dto.CursoDestacadoUpdate;
import com.santiago.joven.backend.model.entity.CursoDestacado;
import org.springframework.stereotype.Component;

/** Mapper para {@link CursoDestacado}. */
@Component
public class CursoDestacadoMapper {

  /** Convierte entidad a DTO de respuesta. */
  public CursoDestacadoResponse toResponse(CursoDestacado entity) {
    return new CursoDestacadoResponse(
        entity.getId(),
        entity.getTitulo(),
        entity.getDescripcion(),
        entity.getEslogan(),
        entity.getObjetivo(),
        entity.getCategoria() != null ? entity.getCategoria().getId() : null,
        entity.getImagen(),
        entity.getActivo(),
        entity.getOrden(),
        entity.getEnlaceInscripcion(),
        entity.getUsuarioCreador() != null ? entity.getUsuarioCreador().getId() : null);
  }

  /** Convierte DTO de creacion a entidad. */
  public CursoDestacado toEntity(CursoDestacadoRequest request) {
    var entity = new CursoDestacado();
    entity.setTitulo(request.titulo());
    entity.setDescripcion(request.descripcion());
    entity.setEslogan(request.eslogan());
    entity.setObjetivo(request.objetivo());
    entity.setImagen(request.imagen());
    entity.setOrden(request.orden());
    entity.setEnlaceInscripcion(request.enlaceInscripcion());
    return entity;
  }

  /** Actualiza entidad existente con datos del DTO (solo campos no nulos). */
  public void updateEntity(CursoDestacadoUpdate update, CursoDestacado entity) {
    if (update.titulo() != null) entity.setTitulo(update.titulo());
    if (update.descripcion() != null) entity.setDescripcion(update.descripcion());
    if (update.eslogan() != null) entity.setEslogan(update.eslogan());
    if (update.objetivo() != null) entity.setObjetivo(update.objetivo());
    if (update.imagen() != null) entity.setImagen(update.imagen());
    if (update.activo() != null) entity.setActivo(update.activo());
    if (update.orden() != null) entity.setOrden(update.orden());
    if (update.enlaceInscripcion() != null) entity.setEnlaceInscripcion(update.enlaceInscripcion());
  }
}
