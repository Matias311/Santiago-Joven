package com.santiago.joven.backend.mapper;

import com.santiago.joven.backend.dto.ActividadTallerRequest;
import com.santiago.joven.backend.dto.ActividadTallerResponse;
import com.santiago.joven.backend.dto.ActividadTallerUpdate;
import com.santiago.joven.backend.model.entity.ActividadTaller;
import org.springframework.stereotype.Component;

/** Mapper para {@link ActividadTaller}. */
@Component
public class ActividadTallerMapper {

  /** Convierte entidad a DTO de respuesta. */
  public ActividadTallerResponse toResponse(ActividadTaller entity) {
    return new ActividadTallerResponse(
        entity.getId(),
        entity.getTitulo(),
        entity.getDescripcion(),
        entity.getCategoria() != null ? entity.getCategoria().getId() : null,
        entity.getFechaHora(),
        entity.getActivo(),
        entity.getCantidadMaximaParticipantes(),
        entity.getImagen(),
        entity.getUbicacion() != null ? entity.getUbicacion().getId() : null,
        entity.getEnlaceInscripcion(),
        entity.getInscritos(),
        entity.getEstado(),
        entity.getUsuarioCreador() != null ? entity.getUsuarioCreador().getId() : null);
  }

  /** Convierte DTO de creacion a entidad. */
  public ActividadTaller toEntity(ActividadTallerRequest request) {
    var entity = new ActividadTaller();
    entity.setTitulo(request.titulo());
    entity.setDescripcion(request.descripcion());
    entity.setFechaHora(request.fechaHora());
    entity.setCantidadMaximaParticipantes(request.cantidadMaximaParticipantes());
    entity.setImagen(request.imagen());
    entity.setEnlaceInscripcion(request.enlaceInscripcion());
    entity.setEstado(request.estado());
    return entity;
  }

  /** Actualiza entidad existente con datos del DTO (solo campos no nulos). */
  public void updateEntity(ActividadTallerUpdate update, ActividadTaller entity) {
    if (update.titulo() != null) entity.setTitulo(update.titulo());
    if (update.descripcion() != null) entity.setDescripcion(update.descripcion());
    if (update.fechaHora() != null) entity.setFechaHora(update.fechaHora());
    if (update.activo() != null) entity.setActivo(update.activo());
    if (update.cantidadMaximaParticipantes() != null)
      entity.setCantidadMaximaParticipantes(update.cantidadMaximaParticipantes());
    if (update.imagen() != null) entity.setImagen(update.imagen());
    if (update.enlaceInscripcion() != null) entity.setEnlaceInscripcion(update.enlaceInscripcion());
    if (update.inscritos() != null) entity.setInscritos(update.inscritos());
    if (update.estado() != null) entity.setEstado(update.estado());
  }
}
