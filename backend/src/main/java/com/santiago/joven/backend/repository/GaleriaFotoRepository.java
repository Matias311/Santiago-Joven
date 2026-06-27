package com.santiago.joven.backend.repository;

import com.santiago.joven.backend.model.entity.GaleriaFoto;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repositorio para la entidad {@link GaleriaFoto}. */
@Repository
public interface GaleriaFotoRepository extends JpaRepository<GaleriaFoto, UUID> {

  /**
   * Lista las fotos de una actividad, ordenadas por {@code orden}.
   *
   * @param actividadId UUID de la actividad
   * @return lista ordenada de fotos
   */
  List<GaleriaFoto> findByActividadIdOrderByOrdenAsc(UUID actividadId);
}
