package com.santiago.joven.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.santiago.joven.backend.dto.UbicacionRequest;
import com.santiago.joven.backend.dto.UbicacionResponse;
import com.santiago.joven.backend.mapper.UbicacionMapper;
import com.santiago.joven.backend.model.entity.Ubicacion;
import com.santiago.joven.backend.repository.UbicacionRepository;
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
class UbicacionServiceImplTest {

  @Mock private UbicacionRepository repository;
  @Mock private UbicacionMapper mapper;
  @InjectMocks private UbicacionServiceImpl service;

  private UUID id;
  private Ubicacion entity;
  private UbicacionResponse response;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    entity = new Ubicacion();
    entity.setId(id);
    response = new UbicacionResponse(id, null, null, null, null, null);
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
  void findByCiudad_debeFiltrar() {
    when(repository.findByCiudad("Santiago")).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findByCiudad("Santiago")).containsExactly(response);
  }

  @Test
  void create_debeGuardar() {
    var request = new UbicacionRequest("Centro Comunitario La Moneda", "Av. La Moneda 1234", "Santiago", null, null);
    when(mapper.toEntity(request)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.create(request)).isEqualTo(response);
  }
}
