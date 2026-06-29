package com.santiago.joven.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.santiago.joven.backend.dto.ContactoRequest;
import com.santiago.joven.backend.dto.ContactoResponse;
import com.santiago.joven.backend.dto.ContactoUpdate;
import com.santiago.joven.backend.mapper.ContactoMapper;
import com.santiago.joven.backend.model.entity.Contacto;
import com.santiago.joven.backend.repository.ContactoRepository;
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
class ContactoServiceImplTest {

  @Mock private ContactoRepository repository;
  @Mock private ContactoMapper mapper;
  @InjectMocks private ContactoServiceImpl service;

  private UUID id;
  private Contacto entity;
  private ContactoResponse response;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    entity = new Contacto();
    entity.setId(id);
    response = new ContactoResponse(id, null, null, null, null, null, null, null, null, null, null);
  }

  @Test
  void findAll_debeRetornar() {
    when(repository.findAll()).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findAll()).containsExactly(response);
  }

  @Test
  void findById_debeRetornar() {
    when(repository.findById(id)).thenReturn(Optional.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findById(id)).isEqualTo(response);
  }

  @Test
  void findPendientes_debeRetornar() {
    when(repository.findByRespondidoFalseOrderByFechaContactoDesc()).thenReturn(List.of(entity));
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.findNoRespondidos()).containsExactly(response);
  }

  @Test
  void create_debeGuardar() {
    var request = new ContactoRequest("Sofía", "sofia.contreras@gmail.com", "+56 9 8765 4321", "Mensaje de prueba", "Talleres");
    when(mapper.toEntity(request)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.create(request)).isEqualTo(response);
  }

  @Test
  void update_debeActualizar() {
    var update = new ContactoUpdate(true, "Respuesta", UUID.randomUUID());
    when(repository.findById(id)).thenReturn(Optional.of(entity));
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toResponse(entity)).thenReturn(response);
    assertThat(service.update(id, update)).isEqualTo(response);
  }
}
