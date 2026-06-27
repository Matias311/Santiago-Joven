package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.AccionJovenRequest;
import com.santiago.joven.backend.dto.AccionJovenResponse;
import com.santiago.joven.backend.dto.AccionJovenUpdate;
import com.santiago.joven.backend.mapper.AccionJovenMapper;
import com.santiago.joven.backend.repository.AccionJovenRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementacion de {@link AccionJovenService}. */
@Service
@RequiredArgsConstructor
@Transactional
public class AccionJovenServiceImpl implements AccionJovenService {

  private final AccionJovenRepository repository;
  private final AccionJovenMapper mapper;

  @Override
  @Transactional(readOnly = true)
  public List<AccionJovenResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public AccionJovenResponse findById(UUID id) {
    return repository
        .findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new EntityNotFoundException("AccionJoven no encontrado con id: " + id));
  }

  @Override
  public AccionJovenResponse create(AccionJovenRequest request) {
    var entity = mapper.toEntity(request);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public AccionJovenResponse update(UUID id, AccionJovenUpdate update) {
    var entity =
        repository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("AccionJoven no encontrado con id: " + id));
    mapper.updateEntity(update, entity);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("AccionJoven no encontrado con id: " + id);
    }
    repository.deleteById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<AccionJovenResponse> findActivas() {
    return repository.findByActivoTrue().stream().map(mapper::toResponse).toList();
  }
}
