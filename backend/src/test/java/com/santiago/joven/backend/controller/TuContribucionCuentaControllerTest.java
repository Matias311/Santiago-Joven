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

import com.santiago.joven.backend.dto.TuContribucionCuentaRequest;
import com.santiago.joven.backend.dto.TuContribucionCuentaResponse;
import com.santiago.joven.backend.dto.TuContribucionCuentaUpdate;
import com.santiago.joven.backend.service.TuContribucionCuentaService;
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

@WebMvcTest(TuContribucionCuentaController.class)
class TuContribucionCuentaControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private TuContribucionCuentaService service;
  @MockitoBean private com.santiago.joven.backend.security.JwtTokenProvider jwtTokenProvider;
  @MockitoBean private com.santiago.joven.backend.security.CustomUserDetailsService userDetailsService;

  private final UUID id = UUID.randomUUID();
  private final TuContribucionCuentaResponse response = TuContribucionCuentaResponse.builder().id(id).build();

  @Test @WithMockUser
  void findAll_debeRetornar200() throws Exception {
    when(service.findAll()).thenReturn(List.of(response));
    mockMvc.perform(get("/api/v1/tu-contribucion-cuenta"))
        .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test @WithMockUser
  void findById_cuandoExiste_debeRetornar200() throws Exception {
    when(service.findById(id)).thenReturn(response);
    mockMvc.perform(get("/api/v1/tu-contribucion-cuenta/{id}", id))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test @WithMockUser
  void findById_cuandoNoExiste_debeRetornar404() throws Exception {
    when(service.findById(id)).thenThrow(new EntityNotFoundException("not found"));
    mockMvc.perform(get("/api/v1/tu-contribucion-cuenta/{id}", id)).andExpect(status().isNotFound());
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_USERS")
  void create_debeRetornar201() throws Exception {
    when(service.create(any(TuContribucionCuentaRequest.class))).thenReturn(response);
    mockMvc.perform(post("/api/v1/tu-contribucion-cuenta").contentType(MediaType.APPLICATION_JSON).content("""
            {"titulo": "test", "descripcion": "desc", "linkGoogleForms": "https://forms.google.com/"}
            """)).andExpect(status().isCreated()).andExpect(header().exists("Location"));
  }

  @Test @WithMockUser
  void create_cuandoErrorDeValidacion_debeRetornar400() throws Exception {
    mockMvc.perform(post("/api/v1/tu-contribucion-cuenta").contentType(MediaType.APPLICATION_JSON).content("""
            {}
            """)).andExpect(status().isBadRequest());
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_USERS")
  void update_debeRetornar200() throws Exception {
    when(service.update(any(UUID.class), any(TuContribucionCuentaUpdate.class))).thenReturn(response);
    mockMvc.perform(put("/api/v1/tu-contribucion-cuenta/{id}", id).contentType(MediaType.APPLICATION_JSON).content("""
            {"titulo": "test"}
            """)).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_USERS")
  void delete_debeRetornar204() throws Exception {
    mockMvc.perform(delete("/api/v1/tu-contribucion-cuenta/{id}", id)).andExpect(status().isNoContent());
  }

  @Test @WithMockUser
  void delete_cuandoNoExiste_debeRetornar404() throws Exception {
    org.mockito.Mockito.doThrow(new EntityNotFoundException("not found")).when(service).delete(id);
    mockMvc.perform(delete("/api/v1/tu-contribucion-cuenta/{id}", id)).andExpect(status().isNotFound());
  }

  @Test @WithMockUser
  void findActivo_debeRetornar200() throws Exception {
    when(service.findActivo()).thenReturn(response);
    mockMvc.perform(get("/api/v1/tu-contribucion-cuenta/activo"))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id.toString()));
  }
}
