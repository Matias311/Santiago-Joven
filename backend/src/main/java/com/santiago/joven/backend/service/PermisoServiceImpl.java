package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.PermisoRequest;
import com.santiago.joven.backend.dto.PermisoResponse;
import com.santiago.joven.backend.dto.PermisoUpdate;
import com.santiago.joven.backend.mapper.PermisoMapper;
import com.santiago.joven.backend.repository.PermisoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementacion de {@link PermisoService}. */
@Service
@RequiredArgsConstructor
@Transactional
public class PermisoServiceImpl implements PermisoService {

  private final PermisoRepository repository;
  private final PermisoMapper mapper;

  @Override
  @Transactional(readOnly = true)
  public List<PermisoResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public PermisoResponse findById(UUID id) {
    return repository
        .findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new EntityNotFoundException("Permiso no encontrado con id: " + id));
  }

  @Override
  @Transactional(readOnly = true)
  public PermisoResponse findByNombre(String nombre) {
    return repository
        .findByNombre(nombre)
        .map(mapper::toResponse)
        .orElseThrow(
            () -> new EntityNotFoundException("Permiso no encontrado con nombre: " + nombre));
  }

  @Override
  @Transactional(readOnly = true)
  public List<PermisoResponse> findByModulo(String modulo) {
    return repository.findByModulo(modulo).stream().map(mapper::toResponse).toList();
  }

  @Override
  public PermisoResponse create(PermisoRequest request) {
    var entity = mapper.toEntity(request);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public PermisoResponse update(UUID id, PermisoUpdate update) {
    var entity =
        repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Permiso no encontrado con id: " + id));
    mapper.updateEntity(update, entity);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("Permiso no encontrado con id: " + id);
    }
    repository.deleteById(id);
  }
}
