package com.santiago.joven.backend.mapper;

import com.santiago.joven.backend.dto.UbicacionRequest;
import com.santiago.joven.backend.dto.UbicacionResponse;
import com.santiago.joven.backend.dto.UbicacionUpdate;
import com.santiago.joven.backend.model.entity.Ubicacion;
import org.springframework.stereotype.Component;

/** Mapper para {@link Ubicacion}. */
@Component
public class UbicacionMapper {

  /** Convierte entidad a DTO de respuesta. */
  public UbicacionResponse toResponse(Ubicacion entity) {
    return new UbicacionResponse(
        entity.getId(),
        entity.getNombre(),
        entity.getDireccion(),
        entity.getCiudad(),
        entity.getLatitud(),
        entity.getLongitud());
  }

  /** Convierte DTO de creacion a entidad. */
  public Ubicacion toEntity(UbicacionRequest request) {
    var entity = new Ubicacion();
    entity.setNombre(request.nombre());
    entity.setDireccion(request.direccion());
    entity.setCiudad(request.ciudad());
    entity.setLatitud(request.latitud());
    entity.setLongitud(request.longitud());
    return entity;
  }

  /** Actualiza entidad existente con datos del DTO (solo campos no nulos). */
  public void updateEntity(UbicacionUpdate update, Ubicacion entity) {
    if (update.nombre() != null) entity.setNombre(update.nombre());
    if (update.direccion() != null) entity.setDireccion(update.direccion());
    if (update.ciudad() != null) entity.setCiudad(update.ciudad());
    if (update.latitud() != null) entity.setLatitud(update.latitud());
    if (update.longitud() != null) entity.setLongitud(update.longitud());
  }
}
