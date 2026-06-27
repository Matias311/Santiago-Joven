package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.SaludMentalRequest;
import com.santiago.joven.backend.dto.SaludMentalResponse;
import com.santiago.joven.backend.dto.SaludMentalUpdate;
import java.util.List;
import java.util.UUID;

/** Servicio para la entidad SaludMental. */
public interface SaludMentalService {

  /** Lista todos los registros. */
  List<SaludMentalResponse> findAll();

  /** Busca por ID. */
  SaludMentalResponse findById(UUID id);

  /** Lista todos ordenados. */
  List<SaludMentalResponse> findAllOrdered();

  /** Crea un nuevo registro. */
  SaludMentalResponse create(SaludMentalRequest request);

  /** Actualiza un registro existente. */
  SaludMentalResponse update(UUID id, SaludMentalUpdate update);

  /** Elimina un registro por ID. */
  void delete(UUID id);
}
