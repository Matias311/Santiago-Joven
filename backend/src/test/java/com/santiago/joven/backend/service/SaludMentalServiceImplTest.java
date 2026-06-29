package com.santiago.joven.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.santiago.joven.backend.dto.SaludMentalRequest;
import com.santiago.joven.backend.dto.SaludMentalResponse;
import com.santiago.joven.backend.mapper.SaludMentalMapper;
import com.santiago.joven.backend.model.entity.SaludMental;
import com.santiago.joven.backend.repository.SaludMentalRepository;
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
class SaludMentalServiceImplTest {

  @Mock private SaludMentalRepository repository;
  @Mock private SaludMentalMapper mapper;
  @InjectMocks private SaludMentalServiceImpl service;

  private UUID id;
  private SaludMental entity;
  private SaludMentalResponse response;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    entity = new SaludMental();
    entity.setId(id);
    response = new SaludMentalResponse(id, null, null, null, null, null, null);
  }

  @Test
  void findAllOrdered_debeRetornar() {
    when(repository.findAllByOrderByOrdenAsc()).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findAllOrdered()).containsExactly(response);
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
    var request = new SaludMentalRequest("Línea *4141", "Prevención del suicidio", null, "*4141", null, 1);
    when(mapper.toEntity(request)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.create(request)).isEqualTo(response);
  }
}
