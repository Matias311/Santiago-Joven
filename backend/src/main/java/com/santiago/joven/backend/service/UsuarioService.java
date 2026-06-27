package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.UsuarioRequest;
import com.santiago.joven.backend.dto.UsuarioResponse;
import com.santiago.joven.backend.dto.UsuarioUpdate;
import java.util.List;
import java.util.UUID;

/** Servicio para la entidad Usuario. */
public interface UsuarioService {

  /** Lista todos los registros. */
  List<UsuarioResponse> findAll();

  /** Busca por ID. */
  UsuarioResponse findById(UUID id);

  /** Busca por email. */
  UsuarioResponse findByEmail(String email);

  /** Verifica si existe un email. */
  boolean existsByEmail(String email);

  /** Crea un nuevo registro. */
  UsuarioResponse create(UsuarioRequest request);

  /** Actualiza un registro existente. */
  UsuarioResponse update(UUID id, UsuarioUpdate update);

  /** Elimina un registro por ID. */
  void delete(UUID id);
}
