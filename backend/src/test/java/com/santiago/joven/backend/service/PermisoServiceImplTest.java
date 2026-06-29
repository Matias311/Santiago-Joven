package com.santiago.joven.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.santiago.joven.backend.dto.PermisoRequest;
import com.santiago.joven.backend.dto.PermisoResponse;
import com.santiago.joven.backend.mapper.PermisoMapper;
import com.santiago.joven.backend.model.entity.Permiso;
import com.santiago.joven.backend.repository.PermisoRepository;
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
class PermisoServiceImplTest {

  @Mock private PermisoRepository repository;
  @Mock private PermisoMapper mapper;
  @InjectMocks private PermisoServiceImpl service;

  private UUID id;
  private Permiso entity;
  private PermisoResponse response;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    entity = new Permiso();
    entity.setId(id);
    response = new PermisoResponse(id, null, null, null);
  }

  @Test
  void findAll_debeRetornar() {
    when(repository.findAll()).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findAll()).containsExactly(response);
  }

  @Test
  void findById_debeRetornar() {
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
  void create_debeGuardar() {
    var request = new PermisoRequest("PERMISSION_TEST", null, "TEST");
    when(mapper.toEntity(request)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.create(request)).isEqualTo(response);
  }

  @Test
  void findByModulo_debeFiltrar() {
    when(repository.findByModulo("USERS")).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findByModulo("USERS")).containsExactly(response);
  }
}
