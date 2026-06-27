package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.RolRequest;
import com.santiago.joven.backend.dto.RolResponse;
import com.santiago.joven.backend.dto.RolUpdate;
import com.santiago.joven.backend.mapper.RolMapper;
import com.santiago.joven.backend.model.enums.NombreRol;
import com.santiago.joven.backend.repository.RolRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementacion de {@link RolService}. */
@Service
@RequiredArgsConstructor
@Transactional
public class RolServiceImpl implements RolService {

  private final RolRepository repository;
  private final RolMapper mapper;

  @Override
  @Transactional(readOnly = true)
  public List<RolResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public RolResponse findById(UUID id) {
    return repository
        .findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con id: " + id));
  }

  @Override
  @Transactional(readOnly = true)
  public RolResponse findByNombre(NombreRol nombre) {
    return repository
        .findByNombre(nombre)
        .map(mapper::toResponse)
        .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con nombre: " + nombre));
  }

  @Override
  public RolResponse create(RolRequest request) {
    var entity = mapper.toEntity(request);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public RolResponse update(UUID id, RolUpdate update) {
    var entity =
        repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado con id: " + id));
    mapper.updateEntity(update, entity);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("Rol no encontrado con id: " + id);
    }
    repository.deleteById(id);
  }
}
