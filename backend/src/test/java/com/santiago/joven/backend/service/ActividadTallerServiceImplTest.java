package com.santiago.joven.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.santiago.joven.backend.dto.ActividadTallerRequest;
import com.santiago.joven.backend.dto.ActividadTallerResponse;
import com.santiago.joven.backend.dto.ActividadTallerUpdate;
import com.santiago.joven.backend.mapper.ActividadTallerMapper;
import com.santiago.joven.backend.model.entity.ActividadTaller;
import com.santiago.joven.backend.model.entity.Categoria;
import com.santiago.joven.backend.model.entity.Ubicacion;
import com.santiago.joven.backend.model.enums.EstadoActividad;
import com.santiago.joven.backend.repository.ActividadTallerRepository;
import com.santiago.joven.backend.repository.CategoriaRepository;
import com.santiago.joven.backend.repository.UbicacionRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
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
class ActividadTallerServiceImplTest {

  @Mock private ActividadTallerRepository repository;
  @Mock private ActividadTallerMapper mapper;
  @Mock private CategoriaRepository categoriaRepository;
  @Mock private UbicacionRepository ubicacionRepository;
  @InjectMocks private ActividadTallerServiceImpl service;

  private UUID id;
  private ActividadTaller entity;
  private ActividadTallerResponse response;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    entity = new ActividadTaller();
    entity.setId(id);
    entity.setTitulo("Taller de guitarra popular");
    entity.setEstado(EstadoActividad.CONFIRMADO);
    response = new ActividadTallerResponse(id, null, null, null, null, null, null, null, null, null, null, null, null);
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
  void findByCategoriaId_debeFiltrar() {
    var catId = UUID.randomUUID();
    when(repository.findByCategoriaId(catId)).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findByCategoriaId(catId)).containsExactly(response);
  }

  @Test
  void findByEstado_debeFiltrar() {
    when(repository.findByEstado(EstadoActividad.CONFIRMADO)).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findByEstado(EstadoActividad.CONFIRMADO)).containsExactly(response);
  }

  @Test
  void findActivas_debeRetornar() {
    when(repository.findByActivoTrueOrderByFechaHoraAsc()).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findActivas()).containsExactly(response);
  }

  @Test
  void findProximas_debeRetornar() {
    when(repository.findTop10ByOrderByFechaHoraAsc()).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findProximas()).containsExactly(response);
  }

  @Test
  void create_debeGuardar() {
    var categoriaId = UUID.randomUUID();
    var request = new ActividadTallerRequest("Nuevo", "Desc", categoriaId, LocalDateTime.now(), null, null, null, null, null);
    var categoria = new Categoria();
    categoria.setId(categoriaId);
    when(categoriaRepository.findById(categoriaId)).thenReturn(Optional.of(categoria));
    when(mapper.toEntity(request)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.create(request)).isEqualTo(response);
  }

  @Test
  void update_debeActualizar() {
    var update = new ActividadTallerUpdate(null, null, null, null, null, null, null, null, null, null, null);
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
