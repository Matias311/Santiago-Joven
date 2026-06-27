package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.ActividadTallerRequest;
import com.santiago.joven.backend.dto.ActividadTallerResponse;
import com.santiago.joven.backend.dto.ActividadTallerUpdate;
import com.santiago.joven.backend.model.enums.EstadoActividad;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/** Servicio para la entidad {@link com.santiago.joven.backend.model.entity.ActividadTaller}. */
public interface ActividadTallerService {

  /** Lista todos los registros. */
  List<ActividadTallerResponse> findAll();

  /** Busca por ID. @throws jakarta.persistence.EntityNotFoundException si no existe */
  ActividadTallerResponse findById(UUID id);

  /** Busca actividades por ID de categoria. */
  List<ActividadTallerResponse> findByCategoriaId(UUID categoriaId);

  /** Busca actividades por estado. */
  List<ActividadTallerResponse> findByEstado(EstadoActividad estado);

  /** Busca actividades entre dos fechas. */
  List<ActividadTallerResponse> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);

  /** Lista las actividades activas. */
  List<ActividadTallerResponse> findActivas();

  /** Lista las proximas actividades. */
  List<ActividadTallerResponse> findProximas();

  /** Crea un nuevo registro. */
  ActividadTallerResponse create(ActividadTallerRequest request);

  /**
   * Actualiza un registro existente. @throws jakarta.persistence.EntityNotFoundException si no
   * existe
   */
  ActividadTallerResponse update(UUID id, ActividadTallerUpdate update);

  /**
   * Elimina un registro por ID. @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  void delete(UUID id);
}
