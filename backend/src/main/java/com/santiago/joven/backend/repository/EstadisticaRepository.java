package com.santiago.joven.backend.repository;

import com.santiago.joven.backend.model.entity.Estadistica;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio para la entidad {@link Estadistica}. */
public interface EstadisticaRepository extends JpaRepository<Estadistica, UUID> {

  /**
   * Busca las estadisticas de un recurso especifico.
   *
   * @param recursoId UUID del recurso
   * @param tipoRecurso tipo del recurso
   * @return Optional con las estadisticas si existen
   */
  Optional<Estadistica> findByRecursoIdAndTipoRecurso(UUID recursoId, TipoRecurso tipoRecurso);
}
