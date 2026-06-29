package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.ContactoRequest;
import com.santiago.joven.backend.dto.ContactoResponse;
import com.santiago.joven.backend.dto.ContactoUpdate;
import java.util.List;
import java.util.UUID;

/** Servicio para la entidad {@link com.santiago.joven.backend.model.entity.Contacto}. */
public interface ContactoService {

  /** Lista todos los registros. */
  List<ContactoResponse> findAll();

  /** Busca por ID. @throws jakarta.persistence.EntityNotFoundException si no existe */
  ContactoResponse findById(UUID id);

  /** Crea un nuevo registro. */
  ContactoResponse create(ContactoRequest request);

  /**
   * Actualiza un registro existente. @throws jakarta.persistence.EntityNotFoundException si no
   * existe
   */
  ContactoResponse update(UUID id, ContactoUpdate update);

  /**
   * Elimina un registro por ID. @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  void delete(UUID id);

  /** Lista los contactos no respondidos. */
  List<ContactoResponse> findNoRespondidos();

  /** Busca contactos por email. */
  List<ContactoResponse> findByEmail(String email);
}
