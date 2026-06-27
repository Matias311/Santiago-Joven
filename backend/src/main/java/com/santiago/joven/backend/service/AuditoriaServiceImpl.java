package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.AuditoriaResponse;
import com.santiago.joven.backend.mapper.AuditoriaMapper;
import com.santiago.joven.backend.repository.AuditoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementacion de {@link AuditoriaService}. */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuditoriaServiceImpl implements AuditoriaService {

  private final AuditoriaRepository repository;
  private final AuditoriaMapper mapper;

  @Override
  public List<AuditoriaResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  @Override
  public AuditoriaResponse findById(UUID id) {
    return repository
        .findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new EntityNotFoundException("Auditoria no encontrada con id: " + id));
  }

  @Override
  public List<AuditoriaResponse> findByEntidad(String entidadTipo, UUID entidadId) {
    return repository.findByEntidadTipoAndEntidadId(entidadTipo, entidadId).stream()
        .map(mapper::toResponse)
        .toList();
  }

  @Override
  public List<AuditoriaResponse> findByUsuario(UUID usuarioId) {
    return repository.findByUsuarioIdOrderByFechaDesc(usuarioId).stream()
        .map(mapper::toResponse)
        .toList();
  }
}
