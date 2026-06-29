package com.santiago.joven.backend.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.santiago.joven.backend.dto.AuditoriaResponse;
import com.santiago.joven.backend.service.AuditoriaService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuditoriaController.class)
class AuditoriaControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private AuditoriaService service;
  @MockitoBean private com.santiago.joven.backend.security.JwtTokenProvider jwtTokenProvider;
  @MockitoBean private com.santiago.joven.backend.security.CustomUserDetailsService userDetailsService;

  private final UUID id = UUID.randomUUID();
  private final AuditoriaResponse response = AuditoriaResponse.builder().id(id).build();

  @Test @WithMockUser(authorities = "PERMISSION_VIEW_ANALYTICS")
  void findAll_debeRetornar200() throws Exception {
    when(service.findAll()).thenReturn(List.of(response));
    mockMvc.perform(get("/api/v1/auditoria"))
        .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test @WithMockUser(authorities = "PERMISSION_VIEW_ANALYTICS")
  void findById_cuandoExiste_debeRetornar200() throws Exception {
    when(service.findById(id)).thenReturn(response);
    mockMvc.perform(get("/api/v1/auditoria/{id}", id))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test @WithMockUser(authorities = "PERMISSION_VIEW_ANALYTICS")
  void findById_cuandoNoExiste_debeRetornar404() throws Exception {
    when(service.findById(id)).thenThrow(new EntityNotFoundException("not found"));
    mockMvc.perform(get("/api/v1/auditoria/{id}", id)).andExpect(status().isNotFound());
  }

  @Test @WithMockUser(authorities = "PERMISSION_VIEW_ANALYTICS")
  void findByEntidad_debeRetornar200() throws Exception {
    when(service.findByEntidad("Usuario", id)).thenReturn(List.of(response));
    mockMvc.perform(get("/api/v1/auditoria/por-entidad")
            .param("entidadTipo", "Usuario").param("entidadId", id.toString()))
        .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test @WithMockUser(authorities = "PERMISSION_VIEW_ANALYTICS")
  void findByUsuario_debeRetornar200() throws Exception {
    var userId = UUID.randomUUID();
    when(service.findByUsuario(userId)).thenReturn(List.of(response));
    mockMvc.perform(get("/api/v1/auditoria/por-usuario/{usuarioId}", userId))
        .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(id.toString()));
  }
}
