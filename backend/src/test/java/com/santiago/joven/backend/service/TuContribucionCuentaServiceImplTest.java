package com.santiago.joven.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.santiago.joven.backend.dto.TuContribucionCuentaRequest;
import com.santiago.joven.backend.dto.TuContribucionCuentaResponse;
import com.santiago.joven.backend.mapper.TuContribucionCuentaMapper;
import com.santiago.joven.backend.model.entity.TuContribucionCuenta;
import com.santiago.joven.backend.repository.TuContribucionCuentaRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TuContribucionCuentaServiceImplTest {

  @Mock private TuContribucionCuentaRepository repository;
  @Mock private TuContribucionCuentaMapper mapper;
  @InjectMocks private TuContribucionCuentaServiceImpl service;

  private UUID id;
  private TuContribucionCuenta entity;
  private TuContribucionCuentaResponse response;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    entity = new TuContribucionCuenta();
    entity.setId(id);
    response = new TuContribucionCuentaResponse(id, null, null, null, null);
  }

  @Test
  void findActivo_debeRetornar() {
    when(repository.findByActivoTrue()).thenReturn(Optional.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findActivo()).isEqualTo(response);
  }

  @Test
  void create_debeGuardar() {
    var request = new TuContribucionCuentaRequest("Tu contribución cuenta", "Desc", "https://forms.gle/ejemplo");
    when(mapper.toEntity(request)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.create(request)).isEqualTo(response);
  }
}
