package com.santiago.joven.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.santiago.joven.backend.dto.AccionJovenRequest;
import com.santiago.joven.backend.dto.AccionJovenResponse;
import com.santiago.joven.backend.dto.AccionJovenUpdate;
import com.santiago.joven.backend.mapper.AccionJovenMapper;
import com.santiago.joven.backend.model.entity.AccionJoven;
import com.santiago.joven.backend.repository.AccionJovenRepository;
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
class AccionJovenServiceImplTest {

  @Mock private AccionJovenRepository repository;
  @Mock private AccionJovenMapper mapper;
  @InjectMocks private AccionJovenServiceImpl service;

  private UUID id;
  private AccionJoven entity;
  private AccionJovenResponse response;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    entity = new AccionJoven();
    entity.setId(id);
    entity.setTitulo("Reciclatón en Parque O'Higgins");
    entity.setDescripcion("Jornada de reciclaje comunitario.");
    entity.setActivo(true);
    response = new AccionJovenResponse(id, "Reciclatón en Parque O'Higgins", "Jornada de reciclaje comunitario.", null, true, null);
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
  void create_debeGuardarYRetornar() {
    var request = new AccionJovenRequest("Nueva", "Desc", null);
    when(mapper.toEntity(request)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.create(request)).isEqualTo(response);
  }

  @Test
  void update_debeActualizar() {
    var update = new AccionJovenUpdate("Titulo nuevo", null, null, null);
    when(repository.findById(id)).thenReturn(Optional.of(entity));
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.update(id, update)).isEqualTo(response);
  }

  @Test
  void update_debeLanzarExcepcion_cuandoNoExiste() {
    when(repository.findById(id)).thenReturn(Optional.empty());
    assertThatThrownBy(() -> service.update(id, new AccionJovenUpdate(null, null, null, null)))
        .isInstanceOf(EntityNotFoundException.class);
  }

  @Test
  void delete_debeEliminar_cuandoExiste() {
    when(repository.existsById(id)).thenReturn(true);
    service.delete(id);
    verify(repository).deleteById(id);
  }

  @Test
  void delete_debeLanzarExcepcion_cuandoNoExiste() {
    when(repository.existsById(id)).thenReturn(false);
    assertThatThrownBy(() -> service.delete(id)).isInstanceOf(EntityNotFoundException.class);
  }

  @Test
  void findActivas_debeRetornarSoloActivas() {
    when(repository.findByActivoTrue()).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findActivas()).containsExactly(response);
  }
}
