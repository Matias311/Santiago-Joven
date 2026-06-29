package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.AsesoriaRequest;
import com.santiago.joven.backend.dto.AsesoriaResponse;
import com.santiago.joven.backend.dto.AsesoriaUpdate;
import java.util.List;
import java.util.UUID;

/** Servicio para la entidad {@link com.santiago.joven.backend.model.entity.Asesoria}. */
public interface AsesoriaService {

  /** Lista todos los registros. */
  List<AsesoriaResponse> findAll();

  /** Busca por ID. @throws jakarta.persistence.EntityNotFoundException si no existe */
  AsesoriaResponse findById(UUID id);

  /** Busca asesorias por ID de categoria. */
  List<AsesoriaResponse> findByCategoriaId(UUID categoriaId);

  /** Lista las asesorias activas. */
  List<AsesoriaResponse> findActivas();

  /** Crea un nuevo registro. */
  AsesoriaResponse create(AsesoriaRequest request);

  /**
   * Actualiza un registro existente. @throws jakarta.persistence.EntityNotFoundException si no
   * existe
   */
  AsesoriaResponse update(UUID id, AsesoriaUpdate update);

  /**
   * Elimina un registro por ID. @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  void delete(UUID id);
}
