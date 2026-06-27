package com.santiago.joven.backend.repository;

import com.santiago.joven.backend.model.entity.AccionJoven;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio para la entidad {@link AccionJoven}. */
public interface AccionJovenRepository extends JpaRepository<AccionJoven, UUID> {

  /**
   * Lista todas las acciones activas.
   *
   * @return lista de acciones activas
   */
  List<AccionJoven> findByActivoTrue();
}
