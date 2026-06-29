package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.TuContribucionCuentaRequest;
import com.santiago.joven.backend.dto.TuContribucionCuentaResponse;
import com.santiago.joven.backend.dto.TuContribucionCuentaUpdate;
import com.santiago.joven.backend.mapper.TuContribucionCuentaMapper;
import com.santiago.joven.backend.repository.TuContribucionCuentaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementacion de {@link TuContribucionCuentaService}. */
@Service
@RequiredArgsConstructor
@Transactional
public class TuContribucionCuentaServiceImpl implements TuContribucionCuentaService {

  private final TuContribucionCuentaRepository repository;
  private final TuContribucionCuentaMapper mapper;

  @Override
  @Transactional(readOnly = true)
  public List<TuContribucionCuentaResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public TuContribucionCuentaResponse findById(UUID id) {
    return repository
        .findById(id)
        .map(mapper::toResponse)
        .orElseThrow(
            () -> new EntityNotFoundException("TuContribucionCuenta no encontrado con id: " + id));
  }

  @Override
  @Transactional(readOnly = true)
  public TuContribucionCuentaResponse findActivo() {
    return repository
        .findByActivoTrue()
        .map(mapper::toResponse)
        .orElseThrow(() -> new EntityNotFoundException("No hay contribucion activa"));
  }

  @Override
  public TuContribucionCuentaResponse create(TuContribucionCuentaRequest request) {
    var entity = mapper.toEntity(request);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public TuContribucionCuentaResponse update(UUID id, TuContribucionCuentaUpdate update) {
    var entity =
        repository
            .findById(id)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "TuContribucionCuenta no encontrado con id: " + id));
    mapper.updateEntity(update, entity);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("TuContribucionCuenta no encontrado con id: " + id);
    }
    repository.deleteById(id);
  }
}
