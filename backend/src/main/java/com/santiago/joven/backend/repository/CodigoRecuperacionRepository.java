package com.santiago.joven.backend.repository;

import com.santiago.joven.backend.model.entity.CodigoRecuperacion;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CodigoRecuperacionRepository extends JpaRepository<CodigoRecuperacion, UUID> {

  Optional<CodigoRecuperacion> findByEmailAndCodigoAndUsadoFalseAndExpiracionAfter(
      String email, String codigo, LocalDateTime now);

  @Modifying
  @Query("UPDATE CodigoRecuperacion c SET c.usado = true WHERE c.email = :email AND c.usado = false")
  int marcarCodigosActivosComoUsados(String email);

  @Modifying
  @Query("DELETE FROM CodigoRecuperacion c WHERE c.expiracion < :fecha")
  int deleteExpiradosAntesDe(LocalDateTime fecha);
}
