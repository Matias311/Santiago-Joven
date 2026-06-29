package com.santiago.joven.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.santiago.joven.backend.dto.CategoriaRequest;
import com.santiago.joven.backend.dto.CategoriaResponse;
import com.santiago.joven.backend.dto.CategoriaUpdate;
import com.santiago.joven.backend.mapper.CategoriaMapper;
import com.santiago.joven.backend.model.entity.Categoria;
import com.santiago.joven.backend.model.enums.TipoCategoria;
import com.santiago.joven.backend.repository.CategoriaRepository;
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
class CategoriaServiceImplTest {

  @Mock private CategoriaRepository repository;
  @Mock private CategoriaMapper mapper;
  @InjectMocks private CategoriaServiceImpl service;

  private UUID id;
  private Categoria entity;
  private CategoriaResponse response;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    entity = new Categoria();
    entity.setId(id);
    entity.setNombre("Arte y Cultura");
    entity.setTipo(TipoCategoria.ACTIVIDAD);
    response = new CategoriaResponse(id, "Arte y Cultura", null, null, null, null);
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
  void findByNombre_debeRetornar() {
    when(repository.findByNombre("Arte y Cultura")).thenReturn(Optional.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findByNombre("Arte y Cultura")).isEqualTo(response);
  }

  @Test
  void findByNombre_debeLanzarExcepcion_cuandoNoExiste() {
    when(repository.findByNombre("Inexistente")).thenReturn(Optional.empty());
    assertThatThrownBy(() -> service.findByNombre("Inexistente")).isInstanceOf(EntityNotFoundException.class);
  }

  @Test
  void findByTipo_debeFiltrar() {
    when(repository.findByTipo(TipoCategoria.ACTIVIDAD)).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findByTipo(TipoCategoria.ACTIVIDAD)).containsExactly(response);
  }

  @Test
  void create_debeGuardar() {
    var request = new CategoriaRequest("Nueva", null, null, null, null);
    when(mapper.toEntity(request)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.create(request)).isEqualTo(response);
  }

  @Test
  void update_debeActualizar() {
    var update = new CategoriaUpdate("Nuevo nombre", null, null, null, null);
    when(repository.findById(id)).thenReturn(Optional.of(entity));
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.update(id, update)).isEqualTo(response);
  }

  @Test
  void delete_debeLanzarExcepcion_cuandoNoExiste() {
    when(repository.existsById(id)).thenReturn(false);
    assertThatThrownBy(() -> service.delete(id)).isInstanceOf(EntityNotFoundException.class);
  }
}
