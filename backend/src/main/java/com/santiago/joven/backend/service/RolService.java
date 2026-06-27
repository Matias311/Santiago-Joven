package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.RolRequest;
import com.santiago.joven.backend.dto.RolResponse;
import com.santiago.joven.backend.dto.RolUpdate;
import com.santiago.joven.backend.model.enums.NombreRol;
import java.util.List;
import java.util.UUID;

/** Servicio para la entidad Rol. */
public interface RolService {

  /** Lista todos los registros. */
  List<RolResponse> findAll();

  /** Busca por ID. */
  RolResponse findById(UUID id);

  /** Busca por nombre. */
  RolResponse findByNombre(NombreRol nombre);

  /** Crea un nuevo registro. */
  RolResponse create(RolRequest request);

  /** Actualiza un registro existente. */
  RolResponse update(UUID id, RolUpdate update);

  /** Elimina un registro por ID. */
  void delete(UUID id);
}
