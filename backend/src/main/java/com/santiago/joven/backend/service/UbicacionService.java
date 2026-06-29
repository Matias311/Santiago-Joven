package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.UbicacionRequest;
import com.santiago.joven.backend.dto.UbicacionResponse;
import com.santiago.joven.backend.dto.UbicacionUpdate;
import java.util.List;
import java.util.UUID;

/** Servicio para la entidad Ubicacion. */
public interface UbicacionService {

  /** Lista todos los registros. */
  List<UbicacionResponse> findAll();

  /** Busca por ID. */
  UbicacionResponse findById(UUID id);

  /** Busca por ciudad. */
  List<UbicacionResponse> findByCiudad(String ciudad);

  /** Crea un nuevo registro. */
  UbicacionResponse create(UbicacionRequest request);

  /** Actualiza un registro existente. */
  UbicacionResponse update(UUID id, UbicacionUpdate update);

  /** Elimina un registro por ID. */
  void delete(UUID id);
}
