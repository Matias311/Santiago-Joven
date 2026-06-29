package com.santiago.joven.backend.mapper;

import com.santiago.joven.backend.dto.ContactoRequest;
import com.santiago.joven.backend.dto.ContactoResponse;
import com.santiago.joven.backend.dto.ContactoUpdate;
import com.santiago.joven.backend.model.entity.Contacto;
import org.springframework.stereotype.Component;

/** Mapper para la entidad {@link Contacto}. */
@Component
public class ContactoMapper {

  /** Convierte entidad a DTO de respuesta. */
  public ContactoResponse toResponse(Contacto entity) {
    return new ContactoResponse(
        entity.getId(),
        entity.getNombre(),
        entity.getEmail(),
        entity.getTelefono(),
        entity.getMensaje(),
        entity.getProgramaInteres(),
        entity.getFechaContacto(),
        entity.getRespondido(),
        entity.getRespuesta(),
        entity.getUsuarioRespondio() != null ? entity.getUsuarioRespondio().getId() : null,
        entity.getFechaRespuesta());
  }

  /** Convierte DTO de creacion a entidad. */
  public Contacto toEntity(ContactoRequest request) {
    var entity = new Contacto();
    entity.setNombre(request.nombre());
    entity.setEmail(request.email());
    entity.setTelefono(request.telefono());
    entity.setMensaje(request.mensaje());
    entity.setProgramaInteres(request.programaInteres());
    return entity;
  }

  /** Actualiza entidad existente con datos del DTO (solo campos no nulos). */
  public void updateEntity(ContactoUpdate update, Contacto entity) {
    if (update.respondido() != null) entity.setRespondido(update.respondido());
    if (update.respuesta() != null) entity.setRespuesta(update.respuesta());
  }
}
