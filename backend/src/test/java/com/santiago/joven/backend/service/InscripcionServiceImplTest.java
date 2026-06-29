package com.santiago.joven.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.santiago.joven.backend.dto.InscripcionRequest;
import com.santiago.joven.backend.dto.InscripcionResponse;
import com.santiago.joven.backend.dto.InscripcionUpdate;
import com.santiago.joven.backend.mapper.InscripcionMapper;
import com.santiago.joven.backend.model.entity.ActividadTaller;
import com.santiago.joven.backend.model.entity.Inscripcion;
import com.santiago.joven.backend.model.entity.Usuario;
import com.santiago.joven.backend.model.enums.EstadoInscripcion;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import com.santiago.joven.backend.repository.ActividadTallerRepository;
import com.santiago.joven.backend.repository.InscripcionRepository;
import com.santiago.joven.backend.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InscripcionServiceImplTest {

  @Mock private InscripcionRepository repository;
  @Mock private ActividadTallerRepository actividadTallerRepository;
  @Mock private UsuarioRepository usuarioRepository;
  @Mock private InscripcionMapper mapper;
  @InjectMocks private InscripcionServiceImpl service;

  private UUID id;
  private Inscripcion entity;
  private InscripcionResponse response;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    entity = new Inscripcion();
    entity.setId(id);
    entity.setTipoRecurso(TipoRecurso.CURSO);
    response = new InscripcionResponse(id, null, null, null, null, null, null);
  }

  @Test
  void findByUsuario_debeFiltrar() {
    var userId = UUID.randomUUID();
    when(repository.findByUsuarioId(userId)).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findByUsuarioId(userId)).containsExactly(response);
  }

  @Test
  void findByRecurso_debeFiltrar() {
    when(repository.findByRecursoIdAndTipoRecurso(id, TipoRecurso.CURSO)).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findByRecurso(id, TipoRecurso.CURSO)).containsExactly(response);
  }

  @Test
  void countByRecurso_debeContar() {
    when(repository.countByRecursoIdAndTipoRecurso(id, TipoRecurso.CURSO)).thenReturn(5L);
    assertThat(service.countByRecurso(id, TipoRecurso.CURSO)).isEqualTo(5);
  }

  @Test
  void existsByUsuarioYRecurso_debeVerificar() {
    var userId = UUID.randomUUID();
    when(repository.existsByUsuarioIdAndRecursoIdAndTipoRecurso(userId, id, TipoRecurso.CURSO))
        .thenReturn(true);
    assertThat(service.existsByUsuarioAndRecurso(userId, id, TipoRecurso.CURSO)).isTrue();
  }

  @Test
  void create_debeGuardarInscripcion() {
    var request = new InscripcionRequest(UUID.randomUUID(), id, TipoRecurso.CURSO, null, null);
    var usuario = new Usuario();
    usuario.setActivo(true);
    when(mapper.toEntity(request)).thenReturn(entity);
    when(usuarioRepository.findById(request.usuarioId())).thenReturn(Optional.of(usuario));
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);

    assertThat(service.create(request)).isEqualTo(response);
    verify(actividadTallerRepository, never()).findById(any());
  }

  @Test
  void create_debeLanzarExcepcion_cuandoUsuarioInactivo() {
    var request = new InscripcionRequest(UUID.randomUUID(), id, TipoRecurso.CURSO, null, null);
    var usuario = new Usuario();
    usuario.setActivo(false);
    when(usuarioRepository.findById(request.usuarioId())).thenReturn(Optional.of(usuario));

    assertThatThrownBy(() -> service.create(request))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("no está activo");
  }

  @Test
  void create_debeLanzarExcepcion_cuandoUsuarioNoExiste() {
    var request = new InscripcionRequest(UUID.randomUUID(), id, TipoRecurso.CURSO, null, null);
    when(usuarioRepository.findById(request.usuarioId())).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.create(request))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("Usuario no encontrado");
  }

  @Test
  void create_enActividadConCupoDisponible_debeIncrementarInscritos() {
    var actividadId = UUID.randomUUID();
    var request = new InscripcionRequest(UUID.randomUUID(), actividadId, TipoRecurso.ACTIVIDAD, null, null);
    var usuario = new Usuario();
    var actividad = new ActividadTaller();
    actividad.setId(actividadId);
    actividad.setTitulo("Taller de guitarra");
    actividad.setCantidadMaximaParticipantes(10);
    actividad.setInscritos(3);

    when(actividadTallerRepository.findById(actividadId)).thenReturn(Optional.of(actividad));
    when(mapper.toEntity(request)).thenReturn(entity);
    when(usuarioRepository.findById(request.usuarioId())).thenReturn(Optional.of(usuario));
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);
    when(actividadTallerRepository.save(actividad)).thenReturn(actividad);

    service.create(request);

    assertThat(actividad.getInscritos()).isEqualTo(4);
    verify(actividadTallerRepository).save(actividad);
  }

  @Test
  void create_enActividadSinCupo_exactamenteAlLimite_debeRechazar() {
    var actividadId = UUID.randomUUID();
    var request = new InscripcionRequest(UUID.randomUUID(), actividadId, TipoRecurso.ACTIVIDAD, null, null);
    var actividad = new ActividadTaller();
    actividad.setId(actividadId);
    actividad.setTitulo("Taller de guitarra");
    actividad.setCantidadMaximaParticipantes(10);
    actividad.setInscritos(10);

    when(actividadTallerRepository.findById(actividadId)).thenReturn(Optional.of(actividad));

    assertThatThrownBy(() -> service.create(request))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("cupo máximo")
        .hasMessageContaining("10");

    verify(repository, never()).save(any());
    verify(actividadTallerRepository, never()).save(any());
  }

  @Test
  void create_enActividadConCupoIlimitado_debePermitir() {
    var actividadId = UUID.randomUUID();
    var request = new InscripcionRequest(UUID.randomUUID(), actividadId, TipoRecurso.ACTIVIDAD, null, null);
    var usuario = new Usuario();
    var actividad = new ActividadTaller();
    actividad.setId(actividadId);
    actividad.setTitulo("Taller sin cupo");
    actividad.setCantidadMaximaParticipantes(null);
    actividad.setInscritos(0);

    when(actividadTallerRepository.findById(actividadId)).thenReturn(Optional.of(actividad));
    when(mapper.toEntity(request)).thenReturn(entity);
    when(usuarioRepository.findById(request.usuarioId())).thenReturn(Optional.of(usuario));
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);
    when(actividadTallerRepository.save(actividad)).thenReturn(actividad);

    service.create(request);

    assertThat(actividad.getInscritos()).isEqualTo(1);
  }

  @Test
  void create_enActividadQueNoExiste_debeLanzarExcepcion() {
    var actividadId = UUID.randomUUID();
    var request = new InscripcionRequest(UUID.randomUUID(), actividadId, TipoRecurso.ACTIVIDAD, null, null);

    when(actividadTallerRepository.findById(actividadId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.create(request))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("Actividad no encontrada");
  }
}
