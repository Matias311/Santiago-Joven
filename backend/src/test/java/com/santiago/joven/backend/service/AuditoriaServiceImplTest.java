package com.santiago.joven.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.santiago.joven.backend.dto.AuditoriaResponse;
import com.santiago.joven.backend.mapper.AuditoriaMapper;
import com.santiago.joven.backend.model.entity.Auditoria;
import com.santiago.joven.backend.model.enums.TipoCambio;
import com.santiago.joven.backend.repository.AuditoriaRepository;
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
class AuditoriaServiceImplTest {

  @Mock private AuditoriaRepository repository;
  @Mock private AuditoriaMapper mapper;
  @InjectMocks private AuditoriaServiceImpl service;

  private UUID id;
  private Auditoria entity;
  private AuditoriaResponse response;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    entity = new Auditoria();
    entity.setId(id);
    entity.setTipoCambio(TipoCambio.CREAR);
    response = new AuditoriaResponse(id, null, null, null, null, null, null, null);
  }

  @Test
  void findAll_debeRetornarTodos() {
    when(repository.findAll()).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findAll()).containsExactly(response);
  }

  @Test
  void findById_debeRetornar_cuandoExiste() {
    when(repository.findById(id)).thenReturn(Optional.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findById(id)).isEqualTo(response);
  }

  @Test
  void findById_debeLanzarExcepcion_cuandoNoExiste() {
    when(repository.findById(id)).thenReturn(Optional.empty());
    assertThatThrownBy(() -> service.findById(id)).isInstanceOf(EntityNotFoundException.class);
  }

  @Test
  void findByEntidad_debeFiltrar() {
    var entidadId = UUID.randomUUID();
    when(repository.findByEntidadTipoAndEntidadId("Usuario", entidadId)).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findByEntidad("Usuario", entidadId)).containsExactly(response);
  }

  @Test
  void findByUsuario_debeFiltrar() {
    var usuarioId = UUID.randomUUID();
    when(repository.findByUsuarioIdOrderByFechaDesc(usuarioId)).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findByUsuario(usuarioId)).containsExactly(response);
  }
}
