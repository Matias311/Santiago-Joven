package com.santiago.joven.backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.santiago.joven.backend.dto.PermisoRequest;
import com.santiago.joven.backend.dto.PermisoResponse;
import com.santiago.joven.backend.dto.PermisoUpdate;
import com.santiago.joven.backend.service.PermisoService;
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

@WebMvcTest(PermisoController.class)
class PermisoControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private PermisoService service;
  @MockitoBean private com.santiago.joven.backend.security.JwtTokenProvider jwtTokenProvider;
  @MockitoBean private com.santiago.joven.backend.security.CustomUserDetailsService userDetailsService;

  private final UUID id = UUID.randomUUID();
  private final PermisoResponse response = PermisoResponse.builder().id(id).build();

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_ROLES")
  void findAll_debeRetornar200() throws Exception {
    when(service.findAll()).thenReturn(List.of(response));
    mockMvc.perform(get("/api/v1/permisos"))
        .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_ROLES")
  void findById_cuandoExiste_debeRetornar200() throws Exception {
    when(service.findById(id)).thenReturn(response);
    mockMvc.perform(get("/api/v1/permisos/{id}", id))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_ROLES")
  void findById_cuandoNoExiste_debeRetornar404() throws Exception {
    when(service.findById(id)).thenThrow(new EntityNotFoundException("not found"));
    mockMvc.perform(get("/api/v1/permisos/{id}", id)).andExpect(status().isNotFound());
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_ROLES")
  void create_debeRetornar201() throws Exception {
    when(service.create(any(PermisoRequest.class))).thenReturn(response);
    mockMvc.perform(post("/api/v1/permisos").contentType(MediaType.APPLICATION_JSON).content("""
            {"nombre": "test"}
            """)).andExpect(status().isCreated()).andExpect(header().exists("Location"));
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_ROLES")
  void create_cuandoErrorDeValidacion_debeRetornar400() throws Exception {
    mockMvc.perform(post("/api/v1/permisos").contentType(MediaType.APPLICATION_JSON).content("""
            {}
            """)).andExpect(status().isBadRequest());
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_ROLES")
  void update_debeRetornar200() throws Exception {
    when(service.update(any(UUID.class), any(PermisoUpdate.class))).thenReturn(response);
    mockMvc.perform(put("/api/v1/permisos/{id}", id).contentType(MediaType.APPLICATION_JSON).content("""
            {"nombre": "test"}
            """)).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_ROLES")
  void delete_debeRetornar204() throws Exception {
    mockMvc.perform(delete("/api/v1/permisos/{id}", id)).andExpect(status().isNoContent());
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_ROLES")
  void delete_cuandoNoExiste_debeRetornar404() throws Exception {
    org.mockito.Mockito.doThrow(new EntityNotFoundException("not found")).when(service).delete(id);
    mockMvc.perform(delete("/api/v1/permisos/{id}", id)).andExpect(status().isNotFound());
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_ROLES")
  void findByNombre_debeRetornar200() throws Exception {
    when(service.findByNombre("TEST")).thenReturn(response);
    mockMvc.perform(get("/api/v1/permisos/por-nombre/{nombre}", "TEST"))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_ROLES")
  void findByModulo_debeRetornar200() throws Exception {
    when(service.findByModulo("USUARIOS")).thenReturn(List.of(response));
    mockMvc.perform(get("/api/v1/permisos/por-modulo/{modulo}", "USUARIOS"))
        .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(id.toString()));
  }
}
