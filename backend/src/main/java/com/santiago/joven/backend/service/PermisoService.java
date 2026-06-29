package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.PermisoRequest;
import com.santiago.joven.backend.dto.PermisoResponse;
import com.santiago.joven.backend.dto.PermisoUpdate;
import java.util.List;
import java.util.UUID;

/** Servicio para la entidad {@link com.santiago.joven.backend.model.entity.Permiso}. */
public interface PermisoService {

  /** Lista todos los registros de permisos. */
  List<PermisoResponse> findAll();

  /**
   * Busca un permiso por su ID.
   *
   * @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  PermisoResponse findById(UUID id);

  /**
   * Busca un permiso por su nombre.
   *
   * @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  PermisoResponse findByNombre(String nombre);

  /**
   * Busca permisos por módulo.
   *
   * @param modulo nombre del módulo
   */
  List<PermisoResponse> findByModulo(String modulo);

  /** Crea un nuevo permiso. */
  PermisoResponse create(PermisoRequest request);

  /**
   * Actualiza un permiso existente.
   *
   * @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  PermisoResponse update(UUID id, PermisoUpdate update);

  /**
   * Elimina un permiso por su ID.
   *
   * @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  void delete(UUID id);
}
