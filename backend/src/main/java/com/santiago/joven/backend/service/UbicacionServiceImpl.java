package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.UbicacionRequest;
import com.santiago.joven.backend.dto.UbicacionResponse;
import com.santiago.joven.backend.dto.UbicacionUpdate;
import com.santiago.joven.backend.mapper.UbicacionMapper;
import com.santiago.joven.backend.repository.UbicacionRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementacion de {@link UbicacionService}. */
@Service
@RequiredArgsConstructor
@Transactional
public class UbicacionServiceImpl implements UbicacionService {

  private final UbicacionRepository repository;
  private final UbicacionMapper mapper;

  @Override
  @Transactional(readOnly = true)
  public List<UbicacionResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public UbicacionResponse findById(UUID id) {
    return repository
        .findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new EntityNotFoundException("Ubicacion no encontrada con id: " + id));
  }

  @Override
  @Transactional(readOnly = true)
  public List<UbicacionResponse> findByCiudad(String ciudad) {
    return repository.findByCiudad(ciudad).stream().map(mapper::toResponse).toList();
  }

  @Override
  public UbicacionResponse create(UbicacionRequest request) {
    var entity = mapper.toEntity(request);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public UbicacionResponse update(UUID id, UbicacionUpdate update) {
    var entity =
        repository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Ubicacion no encontrada con id: " + id));
    mapper.updateEntity(update, entity);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("Ubicacion no encontrada con id: " + id);
    }
    repository.deleteById(id);
  }
}
