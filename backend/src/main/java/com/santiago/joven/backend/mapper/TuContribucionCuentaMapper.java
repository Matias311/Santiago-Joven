package com.santiago.joven.backend.mapper;

import com.santiago.joven.backend.dto.TuContribucionCuentaRequest;
import com.santiago.joven.backend.dto.TuContribucionCuentaResponse;
import com.santiago.joven.backend.dto.TuContribucionCuentaUpdate;
import com.santiago.joven.backend.model.entity.TuContribucionCuenta;
import org.springframework.stereotype.Component;

/** Mapper para {@link TuContribucionCuenta}. */
@Component
public class TuContribucionCuentaMapper {

  /** Convierte entidad a DTO de respuesta. */
  public TuContribucionCuentaResponse toResponse(TuContribucionCuenta entity) {
    return new TuContribucionCuentaResponse(
        entity.getId(),
        entity.getTitulo(),
        entity.getDescripcion(),
        entity.getLinkGoogleForms(),
        entity.getActivo());
  }

  /** Convierte DTO de creacion a entidad. */
  public TuContribucionCuenta toEntity(TuContribucionCuentaRequest request) {
    var entity = new TuContribucionCuenta();
    entity.setTitulo(request.titulo());
    entity.setDescripcion(request.descripcion());
    entity.setLinkGoogleForms(request.linkGoogleForms());
    return entity;
  }

  /** Actualiza entidad existente con datos del DTO (solo campos no nulos). */
  public void updateEntity(TuContribucionCuentaUpdate update, TuContribucionCuenta entity) {
    if (update.titulo() != null) entity.setTitulo(update.titulo());
    if (update.descripcion() != null) entity.setDescripcion(update.descripcion());
    if (update.linkGoogleForms() != null) entity.setLinkGoogleForms(update.linkGoogleForms());
    if (update.activo() != null) entity.setActivo(update.activo());
  }
}
