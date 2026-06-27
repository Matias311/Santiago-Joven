package com.santiago.joven.backend.repository;

import com.santiago.joven.backend.model.entity.TuContribucionCuenta;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio para la entidad {@link TuContribucionCuenta}. */
public interface TuContribucionCuentaRepository extends JpaRepository<TuContribucionCuenta, UUID> {

  /**
   * Busca el registro activo de "Tu contribucion cuenta".
   *
   * <p>Asume que solo un registro puede estar activo a la vez.
   *
   * @return Optional con el registro activo si existe
   */
  Optional<TuContribucionCuenta> findByActivoTrue();
}
