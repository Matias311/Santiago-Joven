package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.CategoriaRequest;
import com.santiago.joven.backend.dto.CategoriaResponse;
import com.santiago.joven.backend.dto.CategoriaUpdate;
import com.santiago.joven.backend.mapper.CategoriaMapper;
import com.santiago.joven.backend.model.enums.TipoCategoria;
import com.santiago.joven.backend.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementacion de {@link CategoriaService}. */
@Service
@RequiredArgsConstructor
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

  private final CategoriaRepository repository;
  private final CategoriaMapper mapper;

  @Override
  @Transactional(readOnly = true)
  public List<CategoriaResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public CategoriaResponse findById(UUID id) {
    return repository
        .findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada con id: " + id));
  }

  @Override
  @Transactional(readOnly = true)
  public CategoriaResponse findByNombre(String nombre) {
    return repository
        .findByNombre(nombre)
        .map(mapper::toResponse)
        .orElseThrow(
            () -> new EntityNotFoundException("Categoria no encontrada con nombre: " + nombre));
  }

  @Override
  @Transactional(readOnly = true)
  public List<CategoriaResponse> findByTipo(TipoCategoria tipo) {
    return repository.findByTipo(tipo).stream().map(mapper::toResponse).toList();
  }

  @Override
  public CategoriaResponse create(CategoriaRequest request) {
    var entity = mapper.toEntity(request);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public CategoriaResponse update(UUID id, CategoriaUpdate update) {
    var entity =
        repository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Categoria no encontrada con id: " + id));
    mapper.updateEntity(update, entity);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("Categoria no encontrada con id: " + id);
    }
    repository.deleteById(id);
  }
}
