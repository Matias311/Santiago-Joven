package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.CategoriaRequest;
import com.santiago.joven.backend.dto.CategoriaResponse;
import com.santiago.joven.backend.dto.CategoriaUpdate;
import com.santiago.joven.backend.model.enums.TipoCategoria;
import java.util.List;
import java.util.UUID;

/** Servicio para la entidad {@link com.santiago.joven.backend.model.entity.Categoria}. */
public interface CategoriaService {

  /** Lista todos los registros. */
  List<CategoriaResponse> findAll();

  /** Busca por ID. @throws jakarta.persistence.EntityNotFoundException si no existe */
  CategoriaResponse findById(UUID id);

  /**
   * Busca una categoria por su nombre exacto. @throws jakarta.persistence.EntityNotFoundException
   * si no existe
   */
  CategoriaResponse findByNombre(String nombre);

  /** Lista categorias por tipo. */
  List<CategoriaResponse> findByTipo(TipoCategoria tipo);

  /** Crea un nuevo registro. */
  CategoriaResponse create(CategoriaRequest request);

  /**
   * Actualiza un registro existente. @throws jakarta.persistence.EntityNotFoundException si no
   * existe
   */
  CategoriaResponse update(UUID id, CategoriaUpdate update);

  /**
   * Elimina un registro por ID. @throws jakarta.persistence.EntityNotFoundException si no existe
   */
  void delete(UUID id);
}
