package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.CursoDestacadoRequest;
import com.santiago.joven.backend.dto.CursoDestacadoResponse;
import com.santiago.joven.backend.dto.CursoDestacadoUpdate;
import java.util.List;
import java.util.UUID;

/** Servicio para la entidad {@link com.santiago.joven.backend.model.entity.CursoDestacado}. */
public interface CursoDestacadoService {

  /** Lista todos los registros de cursos destacados. */
  List<CursoDestacadoResponse> findAll();

  /**
   * Busca un curso destacado por su ID.
   *
   * @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  CursoDestacadoResponse findById(UUID id);

  /**
   * Busca cursos destacados por ID de categoría.
   *
   * @param categoriaId ID de la categoría
   */
  List<CursoDestacadoResponse> findByCategoriaId(UUID categoriaId);

  /** Lista los cursos destacados activos ordenados. */
  List<CursoDestacadoResponse> findActivos();

  /** Crea un nuevo curso destacado. */
  CursoDestacadoResponse create(CursoDestacadoRequest request);

  /**
   * Actualiza un curso destacado existente.
   *
   * @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  CursoDestacadoResponse update(UUID id, CursoDestacadoUpdate update);

  /**
   * Elimina un curso destacado por su ID.
   *
   * @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  void delete(UUID id);
}
