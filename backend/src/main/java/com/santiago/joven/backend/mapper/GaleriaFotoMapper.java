package com.santiago.joven.backend.mapper;

import com.santiago.joven.backend.dto.GaleriaFotoRequest;
import com.santiago.joven.backend.dto.GaleriaFotoResponse;
import com.santiago.joven.backend.dto.GaleriaFotoUpdate;
import com.santiago.joven.backend.model.entity.GaleriaFoto;
import org.springframework.stereotype.Component;

/** Mapper para la entidad {@link GaleriaFoto}. */
@Component
public class GaleriaFotoMapper {

  /** Convierte entidad a DTO de respuesta. */
  public GaleriaFotoResponse toResponse(GaleriaFoto entity) {
    return new GaleriaFotoResponse(
        entity.getId(),
        entity.getActividad() != null ? entity.getActividad().getId() : null,
        entity.getUrlImagen(),
        entity.getTitulo(),
        entity.getDescripcion(),
        entity.getOrden());
  }

  /** Convierte DTO de creacion a entidad. */
  public GaleriaFoto toEntity(GaleriaFotoRequest request) {
    var entity = new GaleriaFoto();
    entity.setUrlImagen(request.urlImagen());
    entity.setTitulo(request.titulo());
    entity.setDescripcion(request.descripcion());
    entity.setOrden(request.orden());
    return entity;
  }

  /** Actualiza entidad existente con datos del DTO (solo campos no nulos). */
  public void updateEntity(GaleriaFotoUpdate update, GaleriaFoto entity) {
    if (update.urlImagen() != null) entity.setUrlImagen(update.urlImagen());
    if (update.titulo() != null) entity.setTitulo(update.titulo());
    if (update.descripcion() != null) entity.setDescripcion(update.descripcion());
    if (update.orden() != null) entity.setOrden(update.orden());
  }
}
