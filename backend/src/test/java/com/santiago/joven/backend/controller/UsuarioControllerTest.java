package com.santiago.joven.backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
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
import com.santiago.joven.backend.security.UsuarioSecurity;
import com.santiago.joven.backend.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UsuarioController.class)
@Import(UsuarioSecurity.class)
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
          .apellido("Perez")
          .activo(true)
          .build();

  /* ======== GET / ======== */

  @Test
  @WithMockUser(roles = "ADMIN")
  void findAll_comoAdmin_debeRetornar200() throws Exception {
    when(service.findAll()).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/usuarios"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser(roles = "MODERATOR")
  void findAll_comoModerador_debeRetornar200() throws Exception {
    when(service.findAll()).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/usuarios"))
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(roles = "USER")
  void findAll_comoUser_debeRetornar403() throws Exception {
    mockMvc
        .perform(get("/api/v1/usuarios"))
        .andExpect(status().isForbidden());
  }

  /* ======== GET /{id} ======== */

  @Test
  @WithMockUser(roles = "ADMIN")
  void findById_comoAdmin_cuandoExiste_debeRetornar200() throws Exception {
    when(service.findById(id)).thenReturn(response);

    mockMvc
        .perform(get("/api/v1/usuarios/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void findById_comoAdmin_cuandoNoExiste_debeRetornar404() throws Exception {
    when(service.findById(id)).thenThrow(new EntityNotFoundException("not found"));

    mockMvc
        .perform(get("/api/v1/usuarios/{id}", id))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404));
  }

  @Test
  @WithMockUser(roles = "USER")
  void findById_comoUser_ajeno_debeRetornar403() throws Exception {
    mockMvc
        .perform(get("/api/v1/usuarios/{id}", id))
        .andExpect(status().isForbidden());
  }

  /* ======== GET /por-email/{email} ======== */

  @Test
  @WithMockUser(roles = "ADMIN")
  void findByEmail_comoAdmin_debeRetornar200() throws Exception {
    when(service.findByEmail("test@example.com")).thenReturn(response);

    mockMvc
        .perform(get("/api/v1/usuarios/por-email/{email}", "test@example.com"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void findByEmail_comoAdmin_cuandoNoExiste_debeRetornar404() throws Exception {
    when(service.findByEmail("notfound@example.com"))
        .thenThrow(new EntityNotFoundException("not found"));

    mockMvc
        .perform(get("/api/v1/usuarios/por-email/{email}", "notfound@example.com"))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(roles = "USER")
  void findByEmail_comoUser_debeRetornar403() throws Exception {
    mockMvc
        .perform(get("/api/v1/usuarios/por-email/{email}", "test@example.com"))
        .andExpect(status().isForbidden());
  }

  /* ======== GET /exists-email/{email} ======== */

  @Test
  @WithMockUser(roles = "ADMIN")
  void existsByEmail_comoAdmin_debeRetornarTrue() throws Exception {
    when(service.existsByEmail("test@example.com")).thenReturn(true);

    mockMvc
        .perform(get("/api/v1/usuarios/exists-email/{email}", "test@example.com"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(true));
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void existsByEmail_comoAdmin_debeRetornarFalse() throws Exception {
    when(service.existsByEmail("notfound@example.com")).thenReturn(false);

    mockMvc
        .perform(get("/api/v1/usuarios/exists-email/{email}", "notfound@example.com"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(false));
  }

  @Test
  @WithMockUser(roles = "USER")
  void existsByEmail_comoUser_debeRetornar403() throws Exception {
    mockMvc
        .perform(get("/api/v1/usuarios/exists-email/{email}", "test@example.com"))
        .andExpect(status().isForbidden());
  }

  /* ======== POST / ======== */

  @Test
  @WithMockUser(roles = "MODERATOR")
  void create_comoModerador_debeRetornar201_conLocationHeader() throws Exception {
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
  @WithMockUser(roles = "MODERATOR")
  void create_comoModerador_cuandoErrorDeValidacion_debeRetornar400() throws Exception {
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
  @WithMockUser(roles = "ADMIN")
  void create_comoAdmin_debeRetornar403() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"email": "nuevo@test.cl", "password": "pass1234",
                         "nombre": "N", "apellido": "U"}
                        """))
        .andExpect(status().isForbidden());
  }

  /* ======== PUT /{id} ======== */

  @Test
  @WithMockUser(roles = "ADMIN")
  void update_comoAdmin_ajeno_debeRetornar403() throws Exception {
    mockMvc
        .perform(
            put("/api/v1/usuarios/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"nombre": "Hack"}
                        """))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "MODERATOR")
  void update_comoModerador_ajeno_debeRetornar403() throws Exception {
    mockMvc
        .perform(
            put("/api/v1/usuarios/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"nombre": "Hack"}
                        """))
        .andExpect(status().isForbidden());
  }

  /* ======== DELETE /{id} ======== */

  @Test
  @WithMockUser(roles = "MODERATOR")
  void delete_comoModerador_debeRetornar204() throws Exception {
    mockMvc
        .perform(delete("/api/v1/usuarios/{id}", id))
        .andExpect(status().isNoContent());
  }

  @Test
  @WithMockUser(roles = "MODERATOR")
  void delete_comoModerador_cuandoNoExiste_debeRetornar404() throws Exception {
    doThrow(new EntityNotFoundException("not found")).when(service).delete(id);

    mockMvc
        .perform(delete("/api/v1/usuarios/{id}", id))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void delete_comoAdmin_debeRetornar403() throws Exception {
    mockMvc
        .perform(delete("/api/v1/usuarios/{id}", id))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "USER")
  void delete_comoUser_debeRetornar403() throws Exception {
    mockMvc
        .perform(delete("/api/v1/usuarios/{id}", id))
        .andExpect(status().isForbidden());
  }

  /* ======== PUT /{id}/roles ======== */

  @Test
  @WithMockUser(roles = "MODERATOR")
  void asignarRoles_comoModerador_debeRetornar204() throws Exception {
    mockMvc
        .perform(
            put("/api/v1/usuarios/{id}/roles", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"rolIds": ["00000000-0000-0000-0000-000000000001"]}
                        """))
        .andExpect(status().isNoContent());
  }

  @Test
  @WithMockUser(roles = "ADMIN")
  void asignarRoles_comoAdmin_debeRetornar403() throws Exception {
    mockMvc
        .perform(
            put("/api/v1/usuarios/{id}/roles", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"rolIds": ["00000000-0000-0000-0000-000000000001"]}
                        """))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(roles = "USER")
  void asignarRoles_comoUser_debeRetornar403() throws Exception {
    mockMvc
        .perform(
            put("/api/v1/usuarios/{id}/roles", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"rolIds": ["00000000-0000-0000-0000-000000000001"]}
                        """))
        .andExpect(status().isForbidden());
  }
}
