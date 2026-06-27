package com.santiago.joven.backend.repository;

import com.santiago.joven.backend.model.entity.Auditoria;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio para la entidad {@link Auditoria}. */
public interface AuditoriaRepository extends JpaRepository<Auditoria, UUID> {

  /**
   * Busca todos los cambios realizados sobre una entidad especifica.
   *
   * @param entidadTipo nombre del tipo de entidad (ASESORIA, CURSO, etc.)
   * @param entidadId UUID de la entidad
   * @return lista de registros de auditoria
   */
  List<Auditoria> findByEntidadTipoAndEntidadId(String entidadTipo, UUID entidadId);

  /**
   * Lista las auditorias realizadas por un usuario, ordenadas por fecha descendente.
   *
   * @param usuarioId UUID del usuario
   * @return lista de registros de auditoria
   */
  List<Auditoria> findByUsuarioIdOrderByFechaDesc(UUID usuarioId);
}
