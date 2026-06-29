package com.santiago.joven.backend.mapper;

import com.santiago.joven.backend.dto.UsuarioRequest;
import com.santiago.joven.backend.dto.UsuarioResponse;
import com.santiago.joven.backend.dto.UsuarioUpdate;
import com.santiago.joven.backend.model.entity.Usuario;
import org.springframework.stereotype.Component;

/** Mapper para la entidad {@link Usuario}. */
@Component
public class UsuarioMapper {

  /** Convierte entidad a DTO de respuesta. */
  public UsuarioResponse toResponse(Usuario entity) {
    return new UsuarioResponse(
        entity.getId(),
        entity.getEmail(),
        entity.getNombre(),
        entity.getApellido(),
        entity.getTelefono(),
        entity.getActivo());
  }

  /** Convierte DTO de creacion a entidad. */
  public Usuario toEntity(UsuarioRequest request) {
    var entity = new Usuario();
    entity.setEmail(request.email());
    entity.setPassword(request.password());
    entity.setNombre(request.nombre());
    entity.setApellido(request.apellido());
    entity.setTelefono(request.telefono());
    return entity;
  }

  /** Actualiza entidad existente con datos del DTO (solo campos no nulos). */
  public void updateEntity(UsuarioUpdate update, Usuario entity) {
    if (update.email() != null) entity.setEmail(update.email());
    if (update.password() != null) entity.setPassword(update.password());
    if (update.nombre() != null) entity.setNombre(update.nombre());
    if (update.apellido() != null) entity.setApellido(update.apellido());
    if (update.telefono() != null) entity.setTelefono(update.telefono());
    if (update.activo() != null) entity.setActivo(update.activo());
  }
}
