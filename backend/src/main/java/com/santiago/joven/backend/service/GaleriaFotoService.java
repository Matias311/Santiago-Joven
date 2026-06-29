package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.GaleriaFotoRequest;
import com.santiago.joven.backend.dto.GaleriaFotoResponse;
import com.santiago.joven.backend.dto.GaleriaFotoUpdate;
import java.util.List;
import java.util.UUID;

/** Servicio para la entidad {@link com.santiago.joven.backend.model.entity.GaleriaFoto}. */
public interface GaleriaFotoService {

  /** Lista todos los registros de fotos de galería. */
  List<GaleriaFotoResponse> findAll();

  /**
   * Busca una foto por su ID.
   *
   * @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  GaleriaFotoResponse findById(UUID id);

  /** Crea una nueva foto de galería. */
  GaleriaFotoResponse create(GaleriaFotoRequest request);

  /**
   * Actualiza una foto de galería existente.
   *
   * @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  GaleriaFotoResponse update(UUID id, GaleriaFotoUpdate update);

  /**
   * Elimina una foto por su ID.
   *
   * @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  void delete(UUID id);

  /**
   * Busca fotos por ID de actividad.
   *
   * @param actividadId ID de la actividad
   */
  List<GaleriaFotoResponse> findByActividadId(UUID actividadId);
}
