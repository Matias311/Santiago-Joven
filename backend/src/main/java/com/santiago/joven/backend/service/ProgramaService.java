package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.ProgramaRequest;
import com.santiago.joven.backend.dto.ProgramaResponse;
import com.santiago.joven.backend.dto.ProgramaUpdate;
import java.util.List;
import java.util.UUID;

/** Servicio para la entidad {@link com.santiago.joven.backend.model.entity.Programa}. */
public interface ProgramaService {

  /** Lista todos los registros de programas. */
  List<ProgramaResponse> findAll();

  /**
   * Busca un programa por su ID.
   *
   * @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  ProgramaResponse findById(UUID id);

  /** Crea un nuevo programa. */
  ProgramaResponse create(ProgramaRequest request);

  /**
   * Actualiza un programa existente.
   *
   * @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  ProgramaResponse update(UUID id, ProgramaUpdate update);

  /**
   * Elimina un programa por su ID.
   *
   * @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  void delete(UUID id);

  /** Lista los programas activos ordenados. */
  List<ProgramaResponse> findActivos();
}
