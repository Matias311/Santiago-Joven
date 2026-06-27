package com.santiago.joven.backend.repository;

import com.santiago.joven.backend.model.entity.Asesoria;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio para la entidad {@link Asesoria}. */
public interface AsesoriaRepository extends JpaRepository<Asesoria, UUID> {

  /**
   * Lista las asesorias de una categoria, ordenadas por {@code orden}.
   *
   * @param categoriaId UUID de la categoria
   * @return lista ordenada de asesorias
   */
  List<Asesoria> findByCategoriaIdOrderByOrdenAsc(UUID categoriaId);

  /**
   * Lista todas las asesorias activas ordenadas por {@code orden}.
   *
   * @return lista de asesorias activas
   */
  List<Asesoria> findByActivoTrueOrderByOrdenAsc();
}
