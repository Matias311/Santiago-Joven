package com.santiago.joven.backend.repository;

import com.santiago.joven.backend.model.entity.SaludMental;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio para la entidad {@link SaludMental}. */
public interface SaludMentalRepository extends JpaRepository<SaludMental, UUID> {

  /**
   * Lista todos los recursos de salud mental ordenados por {@code orden}.
   *
   * @return lista ordenada de recursos
   */
  List<SaludMental> findAllByOrderByOrdenAsc();
}
