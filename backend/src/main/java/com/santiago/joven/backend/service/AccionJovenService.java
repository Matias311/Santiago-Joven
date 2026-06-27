package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.AccionJovenRequest;
import com.santiago.joven.backend.dto.AccionJovenResponse;
import com.santiago.joven.backend.dto.AccionJovenUpdate;
import java.util.List;
import java.util.UUID;

/** Servicio para la entidad {@link com.santiago.joven.backend.model.entity.AccionJoven}. */
public interface AccionJovenService {

  /** Lista todos los registros. */
  List<AccionJovenResponse> findAll();

  /** Busca por ID. @throws jakarta.persistence.EntityNotFoundException si no existe */
  AccionJovenResponse findById(UUID id);

  /** Crea un nuevo registro. */
  AccionJovenResponse create(AccionJovenRequest request);

  /**
   * Actualiza un registro existente. @throws jakarta.persistence.EntityNotFoundException si no
   * existe
   */
  AccionJovenResponse update(UUID id, AccionJovenUpdate update);

  /**
   * Elimina un registro por ID. @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  void delete(UUID id);

  /** Lista las acciones activas. */
  List<AccionJovenResponse> findActivas();
}
