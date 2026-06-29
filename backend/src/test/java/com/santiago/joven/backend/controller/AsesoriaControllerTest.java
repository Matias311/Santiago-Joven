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

import com.santiago.joven.backend.dto.AsesoriaRequest;
import com.santiago.joven.backend.dto.AsesoriaResponse;
import com.santiago.joven.backend.dto.AsesoriaUpdate;
import com.santiago.joven.backend.service.AsesoriaService;
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

@WebMvcTest(AsesoriaController.class)
class AsesoriaControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private AsesoriaService service;
  @MockitoBean private com.santiago.joven.backend.security.JwtTokenProvider jwtTokenProvider;
  @MockitoBean private com.santiago.joven.backend.security.CustomUserDetailsService userDetailsService;

  private final UUID id = UUID.randomUUID();
  private final AsesoriaResponse response = AsesoriaResponse.builder().id(id).build();

  @Test @WithMockUser
  void findAll_debeRetornar200() throws Exception {
    when(service.findAll()).thenReturn(List.of(response));
    mockMvc.perform(get("/api/v1/asesorias"))
        .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test @WithMockUser
  void findById_cuandoExiste_debeRetornar200() throws Exception {
    when(service.findById(id)).thenReturn(response);
    mockMvc.perform(get("/api/v1/asesorias/{id}", id))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test @WithMockUser
  void findById_cuandoNoExiste_debeRetornar404() throws Exception {
    when(service.findById(id)).thenThrow(new EntityNotFoundException("not found"));
    mockMvc.perform(get("/api/v1/asesorias/{id}", id)).andExpect(status().isNotFound());
  }

  @Test @WithMockUser(authorities = "PERMISSION_CREATE_ASESORIA")
  void create_debeRetornar201() throws Exception {
    when(service.create(any(AsesoriaRequest.class))).thenReturn(response);
    mockMvc.perform(post("/api/v1/asesorias").contentType(MediaType.APPLICATION_JSON).content("""
            {"titulo": "test", "categoriaId": "%s", "definicion": "def", "objetivos": "obj", "metodologia": "met"}
            """.formatted(UUID.randomUUID()))).andExpect(status().isCreated()).andExpect(header().exists("Location"));
  }

  @Test @WithMockUser
  void create_cuandoErrorDeValidacion_debeRetornar400() throws Exception {
    mockMvc.perform(post("/api/v1/asesorias").contentType(MediaType.APPLICATION_JSON).content("""
            {}
            """)).andExpect(status().isBadRequest());
  }

  @Test @WithMockUser(authorities = "PERMISSION_EDIT_ASESORIA")
  void update_debeRetornar200() throws Exception {
    when(service.update(any(UUID.class), any(AsesoriaUpdate.class))).thenReturn(response);
    mockMvc.perform(put("/api/v1/asesorias/{id}", id).contentType(MediaType.APPLICATION_JSON).content("""
            {"titulo": "test", "categoriaId": "%s", "definicion": "def", "objetivos": "obj", "metodologia": "met"}
            """.formatted(UUID.randomUUID()))).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test @WithMockUser(authorities = "PERMISSION_DELETE_ASESORIA")
  void delete_debeRetornar204() throws Exception {
    mockMvc.perform(delete("/api/v1/asesorias/{id}", id)).andExpect(status().isNoContent());
  }

  @Test @WithMockUser
  void delete_cuandoNoExiste_debeRetornar404() throws Exception {
    org.mockito.Mockito.doThrow(new EntityNotFoundException("not found")).when(service).delete(id);
    mockMvc.perform(delete("/api/v1/asesorias/{id}", id)).andExpect(status().isNotFound());
  }

  @Test @WithMockUser
  void findByCategoriaId_debeRetornar200() throws Exception {
    var catId = UUID.randomUUID();
    when(service.findByCategoriaId(catId)).thenReturn(List.of(response));
    mockMvc.perform(get("/api/v1/asesorias/por-categoria/{categoriaId}", catId))
        .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test @WithMockUser
  void findActivas_debeRetornar200() throws Exception {
    when(service.findActivas()).thenReturn(List.of(response));
    mockMvc.perform(get("/api/v1/asesorias/activas"))
        .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(id.toString()));
  }
}
