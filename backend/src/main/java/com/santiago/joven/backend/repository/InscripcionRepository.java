package com.santiago.joven.backend.repository;

import com.santiago.joven.backend.model.entity.Inscripcion;
import com.santiago.joven.backend.model.enums.EstadoInscripcion;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio para la entidad {@link Inscripcion}. */
@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, UUID> {

  /**
   * Lista inscripciones de un usuario.
   *
   * @param usuarioId UUID del usuario
   * @return lista de inscripciones del usuario
   */
  List<Inscripcion> findByUsuarioId(UUID usuarioId);

  /**
   * Lista inscripciones a un recurso especifico.
   *
   * @param recursoId UUID del recurso
   * @param tipoRecurso tipo del recurso (ACTIVIDAD, CURSO, ASESORIA)
   * @return lista de inscripciones
   */
  List<Inscripcion> findByRecursoIdAndTipoRecurso(UUID recursoId, TipoRecurso tipoRecurso);

  /**
   * Lista inscripciones por estado.
   *
   * @param estado valor del enum {@link EstadoInscripcion}
   * @return lista de inscripciones
   */
  List<Inscripcion> findByEstado(EstadoInscripcion estado);

  /**
   * Cuenta cuantos usuarios estan inscritos en un recurso.
   *
   * @param recursoId UUID del recurso
   * @param tipoRecurso tipo del recurso
   * @return cantidad de inscripciones
   */
  long countByRecursoIdAndTipoRecurso(UUID recursoId, TipoRecurso tipoRecurso);

  /**
   * Verifica si un usuario ya esta inscrito en un recurso.
   *
   * @param usuarioId UUID del usuario
   * @param recursoId UUID del recurso
   * @param tipoRecurso tipo del recurso
   * @return true si ya existe la inscripcion
   */
  boolean existsByUsuarioIdAndRecursoIdAndTipoRecurso(
      UUID usuarioId, UUID recursoId, TipoRecurso tipoRecurso);
}
