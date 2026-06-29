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

import com.santiago.joven.backend.dto.UsuarioRequest;
import com.santiago.joven.backend.dto.UsuarioResponse;
import com.santiago.joven.backend.dto.UsuarioUpdate;
import com.santiago.joven.backend.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UsuarioController.class)
@WithMockUser(authorities = "PERMISSION_MANAGE_USERS")
class UsuarioControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private UsuarioService service;
  @MockitoBean private com.santiago.joven.backend.security.JwtTokenProvider jwtTokenProvider;
  @MockitoBean private com.santiago.joven.backend.security.CustomUserDetailsService userDetailsService;

  private final UUID id = UUID.randomUUID();
  private final UsuarioResponse response =
      UsuarioResponse.builder()
          .id(id)
          .email("test@example.com")
          .nombre("Juan")
          .apellido("Pérez")
          .activo(true)
          .build();

  @Test
  void findAll_debeRetornar200() throws Exception {
    when(service.findAll()).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/usuarios"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  void findById_cuandoExiste_debeRetornar200() throws Exception {
    when(service.findById(id)).thenReturn(response);

    mockMvc
        .perform(get("/api/v1/usuarios/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  void findById_cuandoNoExiste_debeRetornar404() throws Exception {
    when(service.findById(id)).thenThrow(new EntityNotFoundException("not found"));

    mockMvc
        .perform(get("/api/v1/usuarios/{id}", id))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404));
  }

  @Test
  void findByEmail_debeRetornar200() throws Exception {
    when(service.findByEmail("test@example.com")).thenReturn(response);

    mockMvc
        .perform(get("/api/v1/usuarios/por-email/{email}", "test@example.com"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  void findByEmail_cuandoNoExiste_debeRetornar404() throws Exception {
    when(service.findByEmail("notfound@example.com"))
        .thenThrow(new EntityNotFoundException("not found"));

    mockMvc
        .perform(get("/api/v1/usuarios/por-email/{email}", "notfound@example.com"))
        .andExpect(status().isNotFound());
  }

  @Test
  void existsByEmail_debeRetornarTrue() throws Exception {
    when(service.existsByEmail("test@example.com")).thenReturn(true);

    mockMvc
        .perform(get("/api/v1/usuarios/exists-email/{email}", "test@example.com"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(true));
  }

  @Test
  void existsByEmail_debeRetornarFalse() throws Exception {
    when(service.existsByEmail("notfound@example.com")).thenReturn(false);

    mockMvc
        .perform(get("/api/v1/usuarios/exists-email/{email}", "notfound@example.com"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(false));
  }

  @Test
  void create_debeRetornar201_conLocationHeader() throws Exception {
    when(service.create(any(UsuarioRequest.class))).thenReturn(response);

    mockMvc
        .perform(
            post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "email": "nuevo@example.com",
                      "password": "password123",
                      "nombre": "Nuevo",
                      "apellido": "Usuario"
                    }
                    """))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(header().string("Location", "http://localhost/api/v1/usuarios/" + id));
  }

  @Test
  void create_cuandoErrorDeValidacion_debeRetornar400() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"email": "invalido"}
                        """))
        .andExpect(status().isBadRequest());
  }

  @Test
  void update_debeRetornar200() throws Exception {
    when(service.update(any(UUID.class), any(UsuarioUpdate.class))).thenReturn(response);

    mockMvc
        .perform(
            put("/api/v1/usuarios/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"nombre": "Actualizado"}
                        """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  void delete_debeRetornar204() throws Exception {
    mockMvc
        .perform(delete("/api/v1/usuarios/{id}", id))
        .andExpect(status().isNoContent());
  }

  @Test
  void delete_cuandoNoExiste_debeRetornar404() throws Exception {
    doThrow(new EntityNotFoundException("not found")).when(service).delete(id);

    mockMvc
        .perform(delete("/api/v1/usuarios/{id}", id))
        .andExpect(status().isNotFound());
  }
}
