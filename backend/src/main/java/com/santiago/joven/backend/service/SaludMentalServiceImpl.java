package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.SaludMentalRequest;
import com.santiago.joven.backend.dto.SaludMentalResponse;
import com.santiago.joven.backend.dto.SaludMentalUpdate;
import com.santiago.joven.backend.mapper.SaludMentalMapper;
import com.santiago.joven.backend.repository.SaludMentalRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementacion de {@link SaludMentalService}. */
@Service
@RequiredArgsConstructor
@Transactional
public class SaludMentalServiceImpl implements SaludMentalService {

  private final SaludMentalRepository repository;
  private final SaludMentalMapper mapper;

  @Override
  @Transactional(readOnly = true)
  public List<SaludMentalResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public SaludMentalResponse findById(UUID id) {
    return repository
        .findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new EntityNotFoundException("SaludMental no encontrado con id: " + id));
  }

  @Override
  @Transactional(readOnly = true)
  public List<SaludMentalResponse> findAllOrdered() {
    return repository.findAllByOrderByOrdenAsc().stream().map(mapper::toResponse).toList();
  }

  @Override
  public SaludMentalResponse create(SaludMentalRequest request) {
    var entity = mapper.toEntity(request);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public SaludMentalResponse update(UUID id, SaludMentalUpdate update) {
    var entity =
        repository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("SaludMental no encontrado con id: " + id));
    mapper.updateEntity(update, entity);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("SaludMental no encontrado con id: " + id);
    }
    repository.deleteById(id);
  }
}
