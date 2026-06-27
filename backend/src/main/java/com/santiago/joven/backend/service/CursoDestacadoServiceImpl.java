package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.CursoDestacadoRequest;
import com.santiago.joven.backend.dto.CursoDestacadoResponse;
import com.santiago.joven.backend.dto.CursoDestacadoUpdate;
import com.santiago.joven.backend.mapper.CursoDestacadoMapper;
import com.santiago.joven.backend.repository.CursoDestacadoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementacion de {@link CursoDestacadoService}. */
@Service
@RequiredArgsConstructor
@Transactional
public class CursoDestacadoServiceImpl implements CursoDestacadoService {

  private final CursoDestacadoRepository repository;
  private final CursoDestacadoMapper mapper;

  @Override
  @Transactional(readOnly = true)
  public List<CursoDestacadoResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public CursoDestacadoResponse findById(UUID id) {
    return repository
        .findById(id)
        .map(mapper::toResponse)
        .orElseThrow(
            () -> new EntityNotFoundException("CursoDestacado no encontrado con id: " + id));
  }

  @Override
  @Transactional(readOnly = true)
  public List<CursoDestacadoResponse> findByCategoriaId(UUID categoriaId) {
    return repository.findByCategoriaIdOrderByOrdenAsc(categoriaId).stream()
        .map(mapper::toResponse)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<CursoDestacadoResponse> findActivos() {
    return repository.findByActivoTrueOrderByOrdenAsc().stream().map(mapper::toResponse).toList();
  }

  @Override
  public CursoDestacadoResponse create(CursoDestacadoRequest request) {
    var entity = mapper.toEntity(request);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public CursoDestacadoResponse update(UUID id, CursoDestacadoUpdate update) {
    var entity =
        repository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("CursoDestacado no encontrado con id: " + id));
    mapper.updateEntity(update, entity);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("CursoDestacado no encontrado con id: " + id);
    }
    repository.deleteById(id);
  }
}
