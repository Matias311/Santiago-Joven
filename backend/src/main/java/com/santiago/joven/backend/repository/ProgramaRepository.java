package com.santiago.joven.backend.repository;

import com.santiago.joven.backend.model.entity.Programa;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repositorio para la entidad {@link Programa}. */
@Repository
public interface ProgramaRepository extends JpaRepository<Programa, UUID> {

  /**
   * Lista todos los programas activos ordenados por {@code orden}.
   *
   * @return lista de programas activos
   */
  List<Programa> findByActivoTrueOrderByOrdenAsc();
}
