package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.ResenaCalificacionRequest;
import com.santiago.joven.backend.dto.ResenaCalificacionResponse;
import com.santiago.joven.backend.dto.ResenaCalificacionUpdate;
import com.santiago.joven.backend.mapper.ResenaCalificacionMapper;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import com.santiago.joven.backend.repository.ResenaCalificacionRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementacion de {@link ResenaCalificacionService}. */
@Service
@RequiredArgsConstructor
@Transactional
public class ResenaCalificacionServiceImpl implements ResenaCalificacionService {

  private final ResenaCalificacionRepository repository;
  private final ResenaCalificacionMapper mapper;

  @Override
  @Transactional(readOnly = true)
  public List<ResenaCalificacionResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public ResenaCalificacionResponse findById(UUID id) {
    return repository
        .findById(id)
        .map(mapper::toResponse)
        .orElseThrow(
            () -> new EntityNotFoundException("ResenaCalificacion no encontrada con id: " + id));
  }

  @Override
  @Transactional(readOnly = true)
  public List<ResenaCalificacionResponse> findByRecurso(UUID recursoId, TipoRecurso tipoRecurso) {
    return repository.findByRecursoIdAndTipoRecurso(recursoId, tipoRecurso).stream()
        .map(mapper::toResponse)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<ResenaCalificacionResponse> findByUsuarioId(UUID usuarioId) {
    return repository.findByUsuarioId(usuarioId).stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<ResenaCalificacionResponse> findByCalificacionMinima(Integer calificacion) {
    return repository.findByCalificacionGreaterThanEqual(calificacion).stream()
        .map(mapper::toResponse)
        .toList();
  }

  @Override
  public ResenaCalificacionResponse create(ResenaCalificacionRequest request) {
    var entity = mapper.toEntity(request);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public ResenaCalificacionResponse update(UUID id, ResenaCalificacionUpdate update) {
    var entity =
        repository
            .findById(id)
            .orElseThrow(
                () ->
                    new EntityNotFoundException("ResenaCalificacion no encontrada con id: " + id));
    mapper.updateEntity(update, entity);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("ResenaCalificacion no encontrada con id: " + id);
    }
    repository.deleteById(id);
  }
}
