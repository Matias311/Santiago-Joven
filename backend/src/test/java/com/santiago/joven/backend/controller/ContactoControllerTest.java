package com.santiago.joven.backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.santiago.joven.backend.dto.ContactoRequest;
import com.santiago.joven.backend.dto.ContactoResponse;
import com.santiago.joven.backend.dto.ContactoUpdate;
import com.santiago.joven.backend.service.ContactoService;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ContactoController.class)
class ContactoControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private ContactoService service;
  @MockitoBean private com.santiago.joven.backend.security.JwtTokenProvider jwtTokenProvider;
  @MockitoBean private com.santiago.joven.backend.security.CustomUserDetailsService userDetailsService;

  private final UUID id = UUID.randomUUID();
  private final ContactoResponse response =
      ContactoResponse.builder()
          .id(id)
          .nombre("Juan Pérez")
          .email("juan@test.com")
          .mensaje("Mensaje de prueba")
          .fechaContacto(LocalDateTime.of(2026, 6, 1, 10, 0))
          .respondido(false)
          .build();

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_USERS")
  void findAll_debeRetornar200() throws Exception {
    when(service.findAll()).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/contactos"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_USERS")
  void findById_cuandoExiste_debeRetornar200() throws Exception {
    when(service.findById(id)).thenReturn(response);

    mockMvc
        .perform(get("/api/v1/contactos/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_USERS")
  void findById_cuandoNoExiste_debeRetornar404() throws Exception {
    when(service.findById(id)).thenThrow(new EntityNotFoundException("not found"));

    mockMvc
        .perform(get("/api/v1/contactos/{id}", id))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_USERS")
  void findNoRespondidos_debeRetornar200() throws Exception {
    when(service.findNoRespondidos()).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/contactos/no-respondidos"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_USERS")
  void findByEmail_debeRetornar200() throws Exception {
    when(service.findByEmail("juan@test.com")).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/contactos/por-email/{email}", "juan@test.com"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void create_debeRetornar201_conLocationHeader() throws Exception {
    when(service.create(any(ContactoRequest.class))).thenReturn(response);

    mockMvc
        .perform(
            post("/api/v1/contactos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "nombre": "Juan Pérez",
                      "email": "juan@test.com",
                      "mensaje": "Hola, me interesa el programa"
                    }
                    """))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(header().string("Location", "http://localhost/api/v1/contactos/" + id));
  }

  @Test
  @WithMockUser
  void create_cuandoErrorDeValidacion_debeRetornar400() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/contactos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {}
                        """))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_USERS")
  void update_debeRetornar200() throws Exception {
    var updated =
        ContactoResponse.builder()
            .id(id)
            .nombre("Juan Pérez")
            .email("juan@test.com")
            .mensaje("Mensaje de prueba")
            .respondido(true)
            .build();
    when(service.update(any(UUID.class), any(ContactoUpdate.class))).thenReturn(updated);

    mockMvc
        .perform(
            put("/api/v1/contactos/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"respondido": true}
                        """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_USERS")
  void delete_debeRetornar204() throws Exception {
    mockMvc
        .perform(delete("/api/v1/contactos/{id}", id))
        .andExpect(status().isNoContent());
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_USERS")
  void delete_cuandoNoExiste_debeRetornar404() throws Exception {
    doThrow(new EntityNotFoundException("not found")).when(service).delete(id);

    mockMvc
        .perform(delete("/api/v1/contactos/{id}", id))
        .andExpect(status().isNotFound());
  }
}
