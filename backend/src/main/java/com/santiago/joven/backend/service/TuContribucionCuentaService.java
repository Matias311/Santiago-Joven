package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.TuContribucionCuentaRequest;
import com.santiago.joven.backend.dto.TuContribucionCuentaResponse;
import com.santiago.joven.backend.dto.TuContribucionCuentaUpdate;
import java.util.List;
import java.util.UUID;

/** Servicio para la entidad TuContribucionCuenta. */
public interface TuContribucionCuentaService {

  /** Lista todos los registros. */
  List<TuContribucionCuentaResponse> findAll();

  /** Busca por ID. */
  TuContribucionCuentaResponse findById(UUID id);

  /** Busca el registro activo. */
  TuContribucionCuentaResponse findActivo();

  /** Crea un nuevo registro. */
  TuContribucionCuentaResponse create(TuContribucionCuentaRequest request);

  /** Actualiza un registro existente. */
  TuContribucionCuentaResponse update(UUID id, TuContribucionCuentaUpdate update);

  /** Elimina un registro por ID. */
  void delete(UUID id);
}
