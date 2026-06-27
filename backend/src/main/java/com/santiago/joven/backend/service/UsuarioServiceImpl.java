package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.UsuarioRequest;
import com.santiago.joven.backend.dto.UsuarioResponse;
import com.santiago.joven.backend.dto.UsuarioUpdate;
import com.santiago.joven.backend.mapper.UsuarioMapper;
import com.santiago.joven.backend.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementacion de {@link UsuarioService}. */
@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

  private final UsuarioRepository repository;
  private final UsuarioMapper mapper;

  @Override
  @Transactional(readOnly = true)
  public List<UsuarioResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public UsuarioResponse findById(UUID id) {
    return repository
        .findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + id));
  }

  @Override
  @Transactional(readOnly = true)
  public UsuarioResponse findByEmail(String email) {
    return repository
        .findByEmail(email)
        .map(mapper::toResponse)
        .orElseThrow(
            () -> new EntityNotFoundException("Usuario no encontrado con email: " + email));
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByEmail(String email) {
    return repository.existsByEmail(email);
  }

  @Override
  public UsuarioResponse create(UsuarioRequest request) {
    var entity = mapper.toEntity(request);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public UsuarioResponse update(UUID id, UsuarioUpdate update) {
    var entity =
        repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + id));
    mapper.updateEntity(update, entity);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("Usuario no encontrado con id: " + id);
    }
    repository.deleteById(id);
  }
}
