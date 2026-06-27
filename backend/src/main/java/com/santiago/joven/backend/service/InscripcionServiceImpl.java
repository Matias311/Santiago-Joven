package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.InscripcionRequest;
import com.santiago.joven.backend.dto.InscripcionResponse;
import com.santiago.joven.backend.dto.InscripcionUpdate;
import com.santiago.joven.backend.mapper.InscripcionMapper;
import com.santiago.joven.backend.model.entity.Inscripcion;
import com.santiago.joven.backend.model.enums.EstadoInscripcion;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import com.santiago.joven.backend.repository.InscripcionRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Implementacion de {@link InscripcionService}. */
@Service
@RequiredArgsConstructor
@Transactional
public class InscripcionServiceImpl implements InscripcionService {

  private final InscripcionRepository repository;
  private final InscripcionMapper mapper;

  @Override
  @Transactional(readOnly = true)
  public List<InscripcionResponse> findAll() {
    return repository.findAll().stream()
        .map(mapper::toResponse)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public InscripcionResponse findById(UUID id) {
    return repository.findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new EntityNotFoundException("Inscripcion no encontrada con id: " + id));
  }

  @Override
  @Transactional(readOnly = true)
  public List<InscripcionResponse> findByUsuarioId(UUID usuarioId) {
    return repository.findByUsuarioId(usuarioId).stream()
        .map(mapper::toResponse)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<InscripcionResponse> findByRecurso(UUID recursoId, TipoRecurso tipoRecurso) {
    return repository.findByRecursoIdAndTipoRecurso(recursoId, tipoRecurso).stream()
        .map(mapper::toResponse)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<InscripcionResponse> findByEstado(EstadoInscripcion estado) {
    return repository.findByEstado(estado).stream()
        .map(mapper::toResponse)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public long countByRecurso(UUID recursoId, TipoRecurso tipoRecurso) {
    return repository.countByRecursoIdAndTipoRecurso(recursoId, tipoRecurso);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByUsuarioAndRecurso(UUID usuarioId, UUID recursoId, TipoRecurso tipoRecurso) {
    return repository.existsByUsuarioIdAndRecursoIdAndTipoRecurso(usuarioId, recursoId, tipoRecurso);
  }

  @Override
  public InscripcionResponse create(InscripcionRequest request) {
    var entity = mapper.toEntity(request);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public InscripcionResponse update(UUID id, InscripcionUpdate update) {
    var entity = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Inscripcion no encontrada con id: " + id));
    mapper.updateEntity(update, entity);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("Inscripcion no encontrada con id: " + id);
    }
    repository.deleteById(id);
  }
}
