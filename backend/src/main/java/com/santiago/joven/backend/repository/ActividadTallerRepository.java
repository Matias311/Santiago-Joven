package com.santiago.joven.backend.repository;

import com.santiago.joven.backend.model.entity.ActividadTaller;
import com.santiago.joven.backend.model.enums.EstadoActividad;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio para la entidad {@link ActividadTaller}. */
public interface ActividadTallerRepository extends JpaRepository<ActividadTaller, UUID> {

  /**
   * Lista actividades de una categoria especifica.
   *
   * @param categoriaId UUID de la categoria
   * @return lista de actividades
   */
  List<ActividadTaller> findByCategoriaId(UUID categoriaId);

  /**
   * Lista actividades filtradas por estado (CONFIRMADO, PENDIENTE, CANCELADO).
   *
   * @param estado valor del enum {@link EstadoActividad}
   * @return lista de actividades con ese estado
   */
  List<ActividadTaller> findByEstado(EstadoActividad estado);

  /**
   * Lista actividades dentro de un rango de fechas.
   *
   * @param inicio fecha inicial
   * @param fin fecha final
   * @return lista de actividades en el rango
   */
  List<ActividadTaller> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);

  /**
   * Lista actividades activas ordenadas por fecha ascendente.
   *
   * @return lista de actividades activas
   */
  List<ActividadTaller> findByActivoTrueOrderByFechaHoraAsc();

  /**
   * Obtiene las proximas 10 actividades mas cercanas.
   *
   * @return hasta 10 actividades
   */
  List<ActividadTaller> findTop10ByOrderByFechaHoraAsc();
}
