package com.santiago.joven.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.santiago.joven.backend.dto.RolRequest;
import com.santiago.joven.backend.dto.RolResponse;
import com.santiago.joven.backend.mapper.RolMapper;
import com.santiago.joven.backend.model.entity.Rol;
import com.santiago.joven.backend.model.enums.NombreRol;
import com.santiago.joven.backend.repository.RolRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RolServiceImplTest {

  @Mock private RolRepository repository;
  @Mock private RolMapper mapper;
  @InjectMocks private RolServiceImpl service;

  private UUID id;
  private Rol entity;
  private RolResponse response;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    entity = new Rol(NombreRol.USER);
    entity.setId(id);
    response = new RolResponse(id, NombreRol.USER, null);
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
  void findByNombre_debeRetornar() {
    when(repository.findByNombre(NombreRol.USER)).thenReturn(Optional.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findByNombre(NombreRol.USER)).isEqualTo(response);
  }

  @Test
  void create_debeGuardar() {
    var request = new RolRequest(NombreRol.VOLUNTEER, null);
    when(mapper.toEntity(request)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.create(request)).isEqualTo(response);
  }
}
