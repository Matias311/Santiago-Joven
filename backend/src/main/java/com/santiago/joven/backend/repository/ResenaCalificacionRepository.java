package com.santiago.joven.backend.repository;

import com.santiago.joven.backend.model.entity.ResenaCalificacion;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio para la entidad {@link ResenaCalificacion}. */
public interface ResenaCalificacionRepository extends JpaRepository<ResenaCalificacion, UUID> {

  /**
   * Lista resenas de un recurso especifico.
   *
   * @param recursoId UUID del recurso
   * @param tipoRecurso tipo del recurso
   * @return lista de resenas
   */
  List<ResenaCalificacion> findByRecursoIdAndTipoRecurso(UUID recursoId, TipoRecurso tipoRecurso);

  /**
   * Lista resenas escritas por un usuario.
   *
   * @param usuarioId UUID del usuario
   * @return lista de resenas del usuario
   */
  List<ResenaCalificacion> findByUsuarioId(UUID usuarioId);

  /**
   * Lista resenas con calificacion igual o superior a la indicada.
   *
   * @param calificacion calificacion minima (1-5)
   * @return lista de resenas que cumplen el filtro
   */
  List<ResenaCalificacion> findByCalificacionGreaterThanEqual(Integer calificacion);
}
