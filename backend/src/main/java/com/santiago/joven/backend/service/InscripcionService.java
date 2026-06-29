package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.InscripcionRequest;
import com.santiago.joven.backend.dto.InscripcionResponse;
import com.santiago.joven.backend.dto.InscripcionUpdate;
import com.santiago.joven.backend.model.enums.EstadoInscripcion;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import java.util.List;
import java.util.UUID;

/** Servicio para la entidad {@link com.santiago.joven.backend.model.entity.Inscripcion}. */
public interface InscripcionService {

  /** Lista todos los registros de inscripciones. */
  List<InscripcionResponse> findAll();

  /**
   * Busca una inscripción por su ID.
   *
   * @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  InscripcionResponse findById(UUID id);

  /**
   * Busca inscripciones por ID de usuario.
   *
   * @param usuarioId ID del usuario
   */
  List<InscripcionResponse> findByUsuarioId(UUID usuarioId);

  /**
   * Busca inscripciones por recurso y tipo.
   *
   * @param recursoId ID del recurso
   * @param tipoRecurso tipo del recurso
   */
  List<InscripcionResponse> findByRecurso(UUID recursoId, TipoRecurso tipoRecurso);

  /**
   * Busca inscripciones por estado.
   *
   * @param estado estado de la inscripción
   */
  List<InscripcionResponse> findByEstado(EstadoInscripcion estado);

  /**
   * Cuenta inscripciones por recurso y tipo.
   *
   * @param recursoId ID del recurso
   * @param tipoRecurso tipo del recurso
   */
  long countByRecurso(UUID recursoId, TipoRecurso tipoRecurso);

  /**
   * Verifica si existe una inscripción de un usuario para un recurso.
   *
   * @param usuarioId ID del usuario
   * @param recursoId ID del recurso
   * @param tipoRecurso tipo del recurso
   */
  boolean existsByUsuarioAndRecurso(UUID usuarioId, UUID recursoId, TipoRecurso tipoRecurso);

  /** Crea una nueva inscripción. */
  InscripcionResponse create(InscripcionRequest request);

  /**
   * Actualiza una inscripción existente.
   *
   * @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  InscripcionResponse update(UUID id, InscripcionUpdate update);

  /**
   * Elimina una inscripción por su ID.
   *
   * @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  void delete(UUID id);
}
