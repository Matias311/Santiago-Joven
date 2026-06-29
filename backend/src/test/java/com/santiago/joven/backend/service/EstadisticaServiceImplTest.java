package com.santiago.joven.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.santiago.joven.backend.dto.EstadisticaRequest;
import com.santiago.joven.backend.dto.EstadisticaResponse;
import com.santiago.joven.backend.dto.EstadisticaUpdate;
import com.santiago.joven.backend.mapper.EstadisticaMapper;
import com.santiago.joven.backend.model.entity.Estadistica;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import com.santiago.joven.backend.repository.EstadisticaRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EstadisticaServiceImplTest {

  @Mock private EstadisticaRepository repository;
  @Mock private EstadisticaMapper mapper;
  @InjectMocks private EstadisticaServiceImpl service;

  private UUID id;
  private Estadistica entity;
  private EstadisticaResponse response;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    entity = new Estadistica();
    entity.setId(id);
    response = new EstadisticaResponse(id, null, null, null, null, null, null, null);
  }

  @Test
  void findByRecurso_debeRetornar() {
    when(repository.findByRecursoIdAndTipoRecurso(id, TipoRecurso.CURSO)).thenReturn(Optional.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findByRecurso(id, TipoRecurso.CURSO)).isEqualTo(response);
  }

  @Test
  void create_debeGuardar() {
    var request = new EstadisticaRequest(TipoRecurso.CURSO, id);
    when(mapper.toEntity(request)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.create(request)).isEqualTo(response);
  }

  @Test
  void update_debeActualizar() {
    var update = new EstadisticaUpdate(20, 15, 10, null);
    when(repository.findById(id)).thenReturn(Optional.of(entity));
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.update(id, update)).isEqualTo(response);
  }
}
