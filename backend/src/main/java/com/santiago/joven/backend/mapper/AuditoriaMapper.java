package com.santiago.joven.backend.mapper;

import com.santiago.joven.backend.dto.AuditoriaResponse;
import com.santiago.joven.backend.model.entity.Auditoria;
import org.springframework.stereotype.Component;

/** Mapper para {@link Auditoria}. */
@Component
public class AuditoriaMapper {

  /** Convierte entidad a DTO de respuesta. */
  public AuditoriaResponse toResponse(Auditoria entity) {
    return new AuditoriaResponse(
        entity.getId(),
        entity.getEntidadTipo(),
        entity.getEntidadId(),
        entity.getUsuario() != null ? entity.getUsuario().getId() : null,
        entity.getTipoCambio(),
        entity.getCambiosAnteriores(),
        entity.getCambiosNuevos(),
        entity.getFecha());
  }
}
