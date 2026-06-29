package com.santiago.joven.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.santiago.joven.backend.dto.CursoDestacadoRequest;
import com.santiago.joven.backend.dto.CursoDestacadoResponse;
import com.santiago.joven.backend.dto.CursoDestacadoUpdate;
import com.santiago.joven.backend.mapper.CursoDestacadoMapper;
import com.santiago.joven.backend.model.entity.CursoDestacado;
import com.santiago.joven.backend.repository.CursoDestacadoRepository;
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
class CursoDestacadoServiceImplTest {

  @Mock private CursoDestacadoRepository repository;
  @Mock private CursoDestacadoMapper mapper;
  @InjectMocks private CursoDestacadoServiceImpl service;

  private UUID id;
  private CursoDestacado entity;
  private CursoDestacadoResponse response;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    entity = new CursoDestacado();
    entity.setId(id);
    response = new CursoDestacadoResponse(id, null, null, null, null, null, null, null, null, null, null);
  }

  @Test
  void findActivos_debeRetornar() {
    when(repository.findByActivoTrueOrderByOrdenAsc()).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findActivos()).containsExactly(response);
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
  void delete_debeLanzarExcepcion_cuandoNoExiste() {
    when(repository.existsById(id)).thenReturn(false);
    assertThatThrownBy(() -> service.delete(id)).isInstanceOf(EntityNotFoundException.class);
  }
}
