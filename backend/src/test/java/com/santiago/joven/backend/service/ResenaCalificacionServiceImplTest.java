package com.santiago.joven.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.santiago.joven.backend.dto.ResenaCalificacionRequest;
import com.santiago.joven.backend.dto.ResenaCalificacionResponse;
import com.santiago.joven.backend.mapper.ResenaCalificacionMapper;
import com.santiago.joven.backend.model.entity.ResenaCalificacion;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import com.santiago.joven.backend.repository.ResenaCalificacionRepository;
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
class ResenaCalificacionServiceImplTest {

  @Mock private ResenaCalificacionRepository repository;
  @Mock private ResenaCalificacionMapper mapper;
  @InjectMocks private ResenaCalificacionServiceImpl service;

  private UUID id;
  private ResenaCalificacion entity;
  private ResenaCalificacionResponse response;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    entity = new ResenaCalificacion();
    entity.setId(id);
    response = new ResenaCalificacionResponse(id, null, null, null, null, null);
  }

  @Test
  void findByRecurso_debeFiltrar() {
    when(repository.findByRecursoIdAndTipoRecurso(id, TipoRecurso.CURSO)).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findByRecurso(id, TipoRecurso.CURSO)).containsExactly(response);
  }

  @Test
  void findByUsuario_debeFiltrar() {
    var userId = UUID.randomUUID();
    when(repository.findByUsuarioId(userId)).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findByUsuarioId(userId)).containsExactly(response);
  }

  @Test
  void findById_debeRetornar() {
    when(repository.findById(id)).thenReturn(Optional.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findById(id)).isEqualTo(response);
  }

  @Test
  void create_debeGuardar() {
    var request = new ResenaCalificacionRequest(UUID.randomUUID(), id, TipoRecurso.CURSO, 5, "Excelente");
    when(mapper.toEntity(request)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.create(request)).isEqualTo(response);
  }
}
