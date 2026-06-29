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

import com.santiago.joven.backend.dto.AccionJovenRequest;
import com.santiago.joven.backend.dto.AccionJovenResponse;
import com.santiago.joven.backend.dto.AccionJovenUpdate;
import com.santiago.joven.backend.service.AccionJovenService;
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

@WebMvcTest(AccionJovenController.class)
class AccionJovenControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private AccionJovenService service;
  @MockitoBean private com.santiago.joven.backend.security.JwtTokenProvider jwtTokenProvider;
  @MockitoBean private com.santiago.joven.backend.security.CustomUserDetailsService userDetailsService;

  private final UUID id = UUID.randomUUID();
  private final AccionJovenResponse response = AccionJovenResponse.builder().id(id).build();

  @Test
  @WithMockUser
  void findAll_debeRetornar200() throws Exception {
    when(service.findAll()).thenReturn(List.of(response));
    mockMvc.perform(get("/api/v1/acciones-joven"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findById_cuandoExiste_debeRetornar200() throws Exception {
    when(service.findById(id)).thenReturn(response);
    mockMvc.perform(get("/api/v1/acciones-joven/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findById_cuandoNoExiste_debeRetornar404() throws Exception {
    when(service.findById(id)).thenThrow(new EntityNotFoundException("not found"));
    mockMvc.perform(get("/api/v1/acciones-joven/{id}", id))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_CREATE_PROGRAM")
  void create_debeRetornar201() throws Exception {
    when(service.create(any(AccionJovenRequest.class))).thenReturn(response);
    mockMvc.perform(post("/api/v1/acciones-joven")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {"titulo": "test", "descripcion": "desc"}
                    """))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"));
  }

  @Test
  @WithMockUser
  void create_cuandoErrorDeValidacion_debeRetornar400() throws Exception {
    mockMvc.perform(post("/api/v1/acciones-joven")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {}
                    """))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_EDIT_PROGRAM")
  void update_debeRetornar200() throws Exception {
    when(service.update(any(UUID.class), any(AccionJovenUpdate.class))).thenReturn(response);
    mockMvc.perform(put("/api/v1/acciones-joven/{id}", id)
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                    {"titulo": "test", "descripcion": "desc"}
                    """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_DELETE_PROGRAM")
  void delete_debeRetornar204() throws Exception {
    mockMvc.perform(delete("/api/v1/acciones-joven/{id}", id))
        .andExpect(status().isNoContent());
  }

  @Test
  @WithMockUser
  void delete_cuandoNoExiste_debeRetornar404() throws Exception {
    org.mockito.Mockito.doThrow(new EntityNotFoundException("not found")).when(service).delete(id);
    mockMvc.perform(delete("/api/v1/acciones-joven/{id}", id))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser
  void findActivas_debeRetornar200() throws Exception {
    when(service.findActivas()).thenReturn(List.of(response));
    mockMvc.perform(get("/api/v1/acciones-joven/activas"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }
}
