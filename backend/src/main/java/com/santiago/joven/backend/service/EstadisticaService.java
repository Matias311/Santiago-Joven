package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.EstadisticaRequest;
import com.santiago.joven.backend.dto.EstadisticaResponse;
import com.santiago.joven.backend.dto.EstadisticaUpdate;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import java.util.List;
import java.util.UUID;

/** Servicio para la entidad {@link com.santiago.joven.backend.model.entity.Estadistica}. */
public interface EstadisticaService {

  /** Lista todos los registros de estadísticas. */
  List<EstadisticaResponse> findAll();

  /**
   * Busca una estadística por su ID.
   *
   * @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  EstadisticaResponse findById(UUID id);

  /** Crea una nueva estadística. */
  EstadisticaResponse create(EstadisticaRequest request);

  /**
   * Actualiza una estadística existente.
   *
   * @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  EstadisticaResponse update(UUID id, EstadisticaUpdate update);

  /**
   * Elimina una estadística por su ID.
   *
   * @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  void delete(UUID id);

  /**
   * Busca una estadística por recurso y tipo.
   *
   * @throws jakarta.persistence.EntityNotFoundException si no se encuentra
   */
  EstadisticaResponse findByRecurso(UUID recursoId, TipoRecurso tipoRecurso);
}
