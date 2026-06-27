package com.santiago.joven.backend.repository;

import com.santiago.joven.backend.model.entity.Programa;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio para la entidad {@link Programa}. */
public interface ProgramaRepository extends JpaRepository<Programa, UUID> {

  /**
   * Lista todos los programas activos ordenados por {@code orden}.
   *
   * @return lista de programas activos
   */
  List<Programa> findByActivoTrueOrderByOrdenAsc();
}
