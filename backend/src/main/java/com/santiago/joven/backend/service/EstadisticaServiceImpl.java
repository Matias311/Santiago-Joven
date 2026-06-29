package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.EstadisticaRequest;
import com.santiago.joven.backend.dto.EstadisticaResponse;
import com.santiago.joven.backend.dto.EstadisticaUpdate;
import com.santiago.joven.backend.mapper.EstadisticaMapper;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import com.santiago.joven.backend.repository.EstadisticaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementacion de {@link EstadisticaService}. */
@Service
@RequiredArgsConstructor
@Transactional
public class EstadisticaServiceImpl implements EstadisticaService {

  private final EstadisticaRepository repository;
  private final EstadisticaMapper mapper;

  @Override
  @Transactional(readOnly = true)
  public List<EstadisticaResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public EstadisticaResponse findById(UUID id) {
    return repository
        .findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new EntityNotFoundException("Estadistica no encontrado con id: " + id));
  }

  @Override
  public EstadisticaResponse create(EstadisticaRequest request) {
    var entity = mapper.toEntity(request);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public EstadisticaResponse update(UUID id, EstadisticaUpdate update) {
    var entity =
        repository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Estadistica no encontrado con id: " + id));
    mapper.updateEntity(update, entity);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("Estadistica no encontrado con id: " + id);
    }
    repository.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public EstadisticaResponse findByRecurso(UUID recursoId, TipoRecurso tipoRecurso) {
    return repository
        .findByRecursoIdAndTipoRecurso(recursoId, tipoRecurso)
        .map(mapper::toResponse)
        .orElseThrow(() -> new EntityNotFoundException("Estadistica no encontrada"));
  }
}
