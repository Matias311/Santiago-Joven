package com.santiago.joven.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.santiago.joven.backend.dto.AsesoriaRequest;
import com.santiago.joven.backend.dto.AsesoriaResponse;
import com.santiago.joven.backend.dto.AsesoriaUpdate;
import com.santiago.joven.backend.mapper.AsesoriaMapper;
import com.santiago.joven.backend.model.entity.Asesoria;
import com.santiago.joven.backend.repository.AsesoriaRepository;
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
class AsesoriaServiceImplTest {

  @Mock private AsesoriaRepository repository;
  @Mock private AsesoriaMapper mapper;
  @InjectMocks private AsesoriaServiceImpl service;

  private UUID id;
  private Asesoria entity;
  private AsesoriaResponse response;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    entity = new Asesoria();
    entity.setId(id);
    response = new AsesoriaResponse(id, null, null, null, null, null, null, null, null, null);
  }

  @Test
  void findActivas_debeRetornar() {
    when(repository.findByActivoTrueOrderByOrdenAsc()).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findActivas()).containsExactly(response);
  }

  @Test
  void findByCategoriaId_debeFiltrar() {
    var catId = UUID.randomUUID();
    when(repository.findByCategoriaIdOrderByOrdenAsc(catId)).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findByCategoriaId(catId)).containsExactly(response);
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
    var request = new AsesoriaRequest("Titulo", UUID.randomUUID(), "Def", "Obj", "Met", null, null);
    when(mapper.toEntity(request)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.create(request)).isEqualTo(response);
  }

  @Test
  void delete_debeLanzarExcepcion_cuandoNoExiste() {
    when(repository.existsById(id)).thenReturn(false);
    assertThatThrownBy(() -> service.delete(id)).isInstanceOf(EntityNotFoundException.class);
  }
}
