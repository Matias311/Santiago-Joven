package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.AuditoriaResponse;
import java.util.List;
import java.util.UUID;

/** Servicio para la entidad {@link com.santiago.joven.backend.model.entity.Auditoria}. */
public interface AuditoriaService {

  /** Lista todos los registros de auditoria. */
  List<AuditoriaResponse> findAll();

  /**
   * Busca un registro de auditoria por ID. @throws jakarta.persistence.EntityNotFoundException si
   * no existe
   */
  AuditoriaResponse findById(UUID id);

  /** Busca registros por tipo y ID de entidad auditada. */
  List<AuditoriaResponse> findByEntidad(String entidadTipo, UUID entidadId);

  /** Busca registros de auditoria por ID de usuario. */
  List<AuditoriaResponse> findByUsuario(UUID usuarioId);
}
