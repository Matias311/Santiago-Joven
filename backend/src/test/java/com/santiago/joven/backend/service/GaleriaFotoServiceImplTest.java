package com.santiago.joven.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.santiago.joven.backend.dto.GaleriaFotoRequest;
import com.santiago.joven.backend.dto.GaleriaFotoResponse;
import com.santiago.joven.backend.dto.GaleriaFotoUpdate;
import com.santiago.joven.backend.mapper.GaleriaFotoMapper;
import com.santiago.joven.backend.model.entity.GaleriaFoto;
import com.santiago.joven.backend.repository.GaleriaFotoRepository;
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
class GaleriaFotoServiceImplTest {

  @Mock private GaleriaFotoRepository repository;
  @Mock private GaleriaFotoMapper mapper;
  @InjectMocks private GaleriaFotoServiceImpl service;

  private UUID id;
  private GaleriaFoto entity;
  private GaleriaFotoResponse response;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    entity = new GaleriaFoto();
    entity.setId(id);
    response = new GaleriaFotoResponse(id, null, null, null, null, null);
  }

  @Test
  void findByActividadId_debeFiltrar() {
    var actId = UUID.randomUUID();
    when(repository.findByActividadIdOrderByOrdenAsc(actId)).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findByActividadId(actId)).containsExactly(response);
  }

  @Test
  void findById_debeRetornar() {
    when(repository.findById(id)).thenReturn(Optional.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findById(id)).isEqualTo(response);
  }

  @Test
  void create_debeGuardar() {
    var request = new GaleriaFotoRequest(UUID.randomUUID(), "https://i.santiagojoven.org/foto.jpg", "Titulo", "Desc", 1);
    when(mapper.toEntity(request)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.create(request)).isEqualTo(response);
  }
}
