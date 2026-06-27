package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.ResenaCalificacionRequest;
import com.santiago.joven.backend.dto.ResenaCalificacionResponse;
import com.santiago.joven.backend.dto.ResenaCalificacionUpdate;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import java.util.List;
import java.util.UUID;

/** Servicio para la entidad ResenaCalificacion. */
public interface ResenaCalificacionService {

  /** Lista todos los registros. */
  List<ResenaCalificacionResponse> findAll();

  /** Busca por ID. */
  ResenaCalificacionResponse findById(UUID id);

  /** Lista resenas de un recurso. */
  List<ResenaCalificacionResponse> findByRecurso(UUID recursoId, TipoRecurso tipoRecurso);

  /** Lista resenas de un usuario. */
  List<ResenaCalificacionResponse> findByUsuarioId(UUID usuarioId);

  /** Lista resenas con calificacion minima. */
  List<ResenaCalificacionResponse> findByCalificacionMinima(Integer calificacion);

  /** Crea una nueva resena. */
  ResenaCalificacionResponse create(ResenaCalificacionRequest request);

  /** Actualiza una resena existente. */
  ResenaCalificacionResponse update(UUID id, ResenaCalificacionUpdate update);

  /** Elimina una resena por ID. */
  void delete(UUID id);
}
