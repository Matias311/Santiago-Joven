package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.ActividadTallerRequest;
import com.santiago.joven.backend.dto.ActividadTallerResponse;
import com.santiago.joven.backend.dto.ActividadTallerUpdate;
import com.santiago.joven.backend.mapper.ActividadTallerMapper;
import com.santiago.joven.backend.model.enums.EstadoActividad;
import com.santiago.joven.backend.repository.ActividadTallerRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementacion de {@link ActividadTallerService}. */
@Service
@RequiredArgsConstructor
@Transactional
public class ActividadTallerServiceImpl implements ActividadTallerService {

  private final ActividadTallerRepository repository;
  private final ActividadTallerMapper mapper;

  @Override
  @Transactional(readOnly = true)
  public List<ActividadTallerResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public ActividadTallerResponse findById(UUID id) {
    return repository
        .findById(id)
        .map(mapper::toResponse)
        .orElseThrow(
            () -> new EntityNotFoundException("ActividadTaller no encontrada con id: " + id));
  }

  @Override
  @Transactional(readOnly = true)
  public List<ActividadTallerResponse> findByCategoriaId(UUID categoriaId) {
    return repository.findByCategoriaId(categoriaId).stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<ActividadTallerResponse> findByEstado(EstadoActividad estado) {
    return repository.findByEstado(estado).stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<ActividadTallerResponse> findByFechaHoraBetween(
      LocalDateTime inicio, LocalDateTime fin) {
    return repository.findByFechaHoraBetween(inicio, fin).stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<ActividadTallerResponse> findActivas() {
    return repository.findByActivoTrueOrderByFechaHoraAsc().stream()
        .map(mapper::toResponse)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<ActividadTallerResponse> findProximas() {
    return repository.findTop10ByOrderByFechaHoraAsc().stream().map(mapper::toResponse).toList();
  }

  @Override
  public ActividadTallerResponse create(ActividadTallerRequest request) {
    var entity = mapper.toEntity(request);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public ActividadTallerResponse update(UUID id, ActividadTallerUpdate update) {
    var entity =
        repository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("ActividadTaller no encontrada con id: " + id));
    mapper.updateEntity(update, entity);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("ActividadTaller no encontrada con id: " + id);
    }
    repository.deleteById(id);
  }
}
