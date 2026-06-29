package com.santiago.joven.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.santiago.joven.backend.dto.ProgramaRequest;
import com.santiago.joven.backend.dto.ProgramaResponse;
import com.santiago.joven.backend.mapper.ProgramaMapper;
import com.santiago.joven.backend.model.entity.Programa;
import com.santiago.joven.backend.repository.ProgramaRepository;
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
class ProgramaServiceImplTest {

  @Mock private ProgramaRepository repository;
  @Mock private ProgramaMapper mapper;
  @InjectMocks private ProgramaServiceImpl service;

  private UUID id;
  private Programa entity;
  private ProgramaResponse response;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    entity = new Programa();
    entity.setId(id);
    response = new ProgramaResponse(id, null, null, null, null, null, null, null, null, null);
  }

  @Test
  void findActivos_debeRetornar() {
    when(repository.findByActivoTrueOrderByOrdenAsc()).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findActivos()).containsExactly(response);
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
    var request = new ProgramaRequest("Titulo", "Desc", "Def", "Obj", "Met", null, null);
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
