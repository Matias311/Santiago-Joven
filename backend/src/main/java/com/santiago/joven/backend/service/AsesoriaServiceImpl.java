package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.AsesoriaRequest;
import com.santiago.joven.backend.dto.AsesoriaResponse;
import com.santiago.joven.backend.dto.AsesoriaUpdate;
import com.santiago.joven.backend.mapper.AsesoriaMapper;
import com.santiago.joven.backend.repository.AsesoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementacion de {@link AsesoriaService}. */
@Service
@RequiredArgsConstructor
@Transactional
public class AsesoriaServiceImpl implements AsesoriaService {

  private final AsesoriaRepository repository;
  private final AsesoriaMapper mapper;

  @Override
  @Transactional(readOnly = true)
  public List<AsesoriaResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public AsesoriaResponse findById(UUID id) {
    return repository
        .findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new EntityNotFoundException("Asesoria no encontrada con id: " + id));
  }

  @Override
  @Transactional(readOnly = true)
  public List<AsesoriaResponse> findByCategoriaId(UUID categoriaId) {
    return repository.findByCategoriaIdOrderByOrdenAsc(categoriaId).stream()
        .map(mapper::toResponse)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<AsesoriaResponse> findActivas() {
    return repository.findByActivoTrueOrderByOrdenAsc().stream().map(mapper::toResponse).toList();
  }

  @Override
  public AsesoriaResponse create(AsesoriaRequest request) {
    var entity = mapper.toEntity(request);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public AsesoriaResponse update(UUID id, AsesoriaUpdate update) {
    var entity =
        repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Asesoria no encontrada con id: " + id));
    mapper.updateEntity(update, entity);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("Asesoria no encontrada con id: " + id);
    }
    repository.deleteById(id);
  }
}
