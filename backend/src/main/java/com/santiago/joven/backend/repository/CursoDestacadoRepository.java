package com.santiago.joven.backend.repository;

import com.santiago.joven.backend.model.entity.CursoDestacado;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repositorio para la entidad {@link CursoDestacado}. */
@Repository
public interface CursoDestacadoRepository extends JpaRepository<CursoDestacado, UUID> {

  /**
   * Lista los cursos destacados de una categoria, ordenados por {@code orden}.
   *
   * @param categoriaId UUID de la categoria
   * @return lista ordenada de cursos
   */
  List<CursoDestacado> findByCategoriaIdOrderByOrdenAsc(UUID categoriaId);

  /**
   * Lista todos los cursos destacados activos ordenados por {@code orden}.
   *
   * @return lista de cursos activos
   */
  List<CursoDestacado> findByActivoTrueOrderByOrdenAsc();
}
