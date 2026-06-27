package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.ProgramaRequest;
import com.santiago.joven.backend.dto.ProgramaResponse;
import com.santiago.joven.backend.dto.ProgramaUpdate;
import com.santiago.joven.backend.mapper.ProgramaMapper;
import com.santiago.joven.backend.repository.ProgramaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementacion de {@link ProgramaService}. */
@Service
@RequiredArgsConstructor
@Transactional
public class ProgramaServiceImpl implements ProgramaService {

  private final ProgramaRepository repository;
  private final ProgramaMapper mapper;

  @Override
  @Transactional(readOnly = true)
  public List<ProgramaResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public ProgramaResponse findById(UUID id) {
    return repository
        .findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new EntityNotFoundException("Programa no encontrado con id: " + id));
  }

  @Override
  public ProgramaResponse create(ProgramaRequest request) {
    var entity = mapper.toEntity(request);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public ProgramaResponse update(UUID id, ProgramaUpdate update) {
    var entity =
        repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Programa no encontrado con id: " + id));
    mapper.updateEntity(update, entity);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("Programa no encontrado con id: " + id);
    }
    repository.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ProgramaResponse> findActivos() {
    return repository.findByActivoTrueOrderByOrdenAsc().stream().map(mapper::toResponse).toList();
  }
}
