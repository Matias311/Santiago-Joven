package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.GaleriaFotoRequest;
import com.santiago.joven.backend.dto.GaleriaFotoResponse;
import com.santiago.joven.backend.dto.GaleriaFotoUpdate;
import com.santiago.joven.backend.mapper.GaleriaFotoMapper;
import com.santiago.joven.backend.repository.GaleriaFotoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementacion de {@link GaleriaFotoService}. */
@Service
@RequiredArgsConstructor
@Transactional
public class GaleriaFotoServiceImpl implements GaleriaFotoService {

  private final GaleriaFotoRepository repository;
  private final GaleriaFotoMapper mapper;

  @Override
  @Transactional(readOnly = true)
  public List<GaleriaFotoResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public GaleriaFotoResponse findById(UUID id) {
    return repository
        .findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new EntityNotFoundException("GaleriaFoto no encontrado con id: " + id));
  }

  @Override
  public GaleriaFotoResponse create(GaleriaFotoRequest request) {
    var entity = mapper.toEntity(request);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public GaleriaFotoResponse update(UUID id, GaleriaFotoUpdate update) {
    var entity =
        repository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("GaleriaFoto no encontrado con id: " + id));
    mapper.updateEntity(update, entity);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("GaleriaFoto no encontrado con id: " + id);
    }
    repository.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<GaleriaFotoResponse> findByActividadId(UUID actividadId) {
    return repository.findByActividadIdOrderByOrdenAsc(actividadId).stream()
        .map(mapper::toResponse)
        .toList();
  }
}
