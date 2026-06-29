package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.InscripcionRequest;
import com.santiago.joven.backend.dto.InscripcionResponse;
import com.santiago.joven.backend.dto.InscripcionUpdate;
import com.santiago.joven.backend.mapper.InscripcionMapper;
import com.santiago.joven.backend.model.entity.ActividadTaller;
import com.santiago.joven.backend.model.enums.EstadoInscripcion;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import com.santiago.joven.backend.repository.ActividadTallerRepository;
import com.santiago.joven.backend.repository.InscripcionRepository;
import com.santiago.joven.backend.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InscripcionServiceImpl implements InscripcionService {

  private final InscripcionRepository repository;
  private final ActividadTallerRepository actividadTallerRepository;
  private final UsuarioRepository usuarioRepository;
  private final InscripcionMapper mapper;

  @Override
  @Transactional(readOnly = true)
  public List<InscripcionResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public InscripcionResponse findById(UUID id) {
    return repository
        .findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new EntityNotFoundException("Inscripcion no encontrada con id: " + id));
  }

  @Override
  @Transactional(readOnly = true)
  public List<InscripcionResponse> findByUsuarioId(UUID usuarioId) {
    return repository.findByUsuarioId(usuarioId).stream().map(mapper::toResponse).toList();
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
    return repository.findByEstado(estado).stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public long countByRecurso(UUID recursoId, TipoRecurso tipoRecurso) {
    return repository.countByRecursoIdAndTipoRecurso(recursoId, tipoRecurso);
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByUsuarioAndRecurso(
      UUID usuarioId, UUID recursoId, TipoRecurso tipoRecurso) {
    return repository.existsByUsuarioIdAndRecursoIdAndTipoRecurso(
        usuarioId, recursoId, tipoRecurso);
  }

  @Override
  public InscripcionResponse create(InscripcionRequest request) {
    if (request.tipoRecurso() == TipoRecurso.ACTIVIDAD) {
      validarCupoActividad(request.recursoId());
    }

    var entity = mapper.toEntity(request);

    var usuario =
        usuarioRepository
            .findById(request.usuarioId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Usuario no encontrado con id: " + request.usuarioId()));

    if (Boolean.FALSE.equals(usuario.getActivo())) {
      throw new IllegalStateException("El usuario no está activo");
    }

    if (repository.existsByUsuarioIdAndRecursoIdAndTipoRecurso(
        request.usuarioId(), request.recursoId(), request.tipoRecurso())) {
      throw new IllegalArgumentException(
          "Ya existe una inscripcion para este usuario, recurso y tipo");
    }

    entity.setUsuario(usuario);

    if (request.tipoRecurso() == TipoRecurso.ACTIVIDAD) {
      incrementarInscritos(request.recursoId());
    }

    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public InscripcionResponse update(UUID id, InscripcionUpdate update) {
    var entity =
        repository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Inscripcion no encontrada con id: " + id));
    mapper.updateEntity(update, entity);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("Inscripcion no encontrada con id: " + id);
    }

    var inscripcion =
        repository
            .findById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Inscripcion no encontrada con id: " + id));

    if (inscripcion.getTipoRecurso() == TipoRecurso.ACTIVIDAD) {
      decrementarInscritos(inscripcion.getRecursoId());
    }

    repository.deleteById(id);
  }

  private void validarCupoActividad(UUID actividadId) {
    var actividad =
        actividadTallerRepository
            .findById(actividadId)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Actividad no encontrada con id: " + actividadId));

    var maximo = actividad.getCantidadMaximaParticipantes();
    if (maximo != null
        && actividad.getInscritos() != null
        && actividad.getInscritos() >= maximo) {
      throw new IllegalStateException(
          "La actividad \""
              + actividad.getTitulo()
              + "\" ha alcanzado su cupo máximo de "
              + maximo
              + " participantes");
    }
  }

  private void incrementarInscritos(UUID actividadId) {
    var actividad =
        actividadTallerRepository
            .findById(actividadId)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Actividad no encontrada con id: " + actividadId));
    var inscritos = actividad.getInscritos();
    actividad.setInscritos(inscritos == null ? 1 : inscritos + 1);
    actividadTallerRepository.save(actividad);
  }

  private void decrementarInscritos(UUID actividadId) {
    var actividad =
        actividadTallerRepository
            .findById(actividadId)
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Actividad no encontrada con id: " + actividadId));
    var inscritos = actividad.getInscritos();
    if (inscritos != null && inscritos > 0) {
      actividad.setInscritos(inscritos - 1);
      actividadTallerRepository.save(actividad);
    }
  }
}
