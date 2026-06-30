package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.UsuarioRequest;
import com.santiago.joven.backend.dto.UsuarioResponse;
import com.santiago.joven.backend.dto.UsuarioUpdate;
import com.santiago.joven.backend.model.enums.NombreRol;
import java.util.List;
import java.util.Set;
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

  /** Actualiza datos propios sin permitir cambios administrativos. */
  UsuarioResponse updateOwnProfile(UUID id, UsuarioUpdate update);

  /** Elimina un registro por ID. */
  void delete(UUID id);

  /** Asigna roles a un usuario. */
  void asignarRoles(UUID usuarioId, Set<UUID> rolIds);

  /** Obtiene los nombres de los roles de un usuario. */
  List<NombreRol> obtenerRoles(UUID usuarioId);
}
