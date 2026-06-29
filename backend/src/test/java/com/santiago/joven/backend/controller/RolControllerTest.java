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

import com.santiago.joven.backend.dto.RolRequest;
import com.santiago.joven.backend.dto.RolResponse;
import com.santiago.joven.backend.dto.RolUpdate;
import com.santiago.joven.backend.model.enums.NombreRol;
import com.santiago.joven.backend.service.RolService;
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

@WebMvcTest(RolController.class)
class RolControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private RolService service;
  @MockitoBean private com.santiago.joven.backend.security.JwtTokenProvider jwtTokenProvider;
  @MockitoBean private com.santiago.joven.backend.security.CustomUserDetailsService userDetailsService;

  private final UUID id = UUID.randomUUID();
  private final RolResponse response = RolResponse.builder().id(id).build();

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_ROLES")
  void findAll_debeRetornar200() throws Exception {
    when(service.findAll()).thenReturn(List.of(response));
    mockMvc.perform(get("/api/v1/roles"))
        .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_ROLES")
  void findById_cuandoExiste_debeRetornar200() throws Exception {
    when(service.findById(id)).thenReturn(response);
    mockMvc.perform(get("/api/v1/roles/{id}", id))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_ROLES")
  void findById_cuandoNoExiste_debeRetornar404() throws Exception {
    when(service.findById(id)).thenThrow(new EntityNotFoundException("not found"));
    mockMvc.perform(get("/api/v1/roles/{id}", id)).andExpect(status().isNotFound());
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_ROLES")
  void findByNombre_debeRetornar200() throws Exception {
    when(service.findByNombre(NombreRol.USER)).thenReturn(response);
    mockMvc.perform(get("/api/v1/roles/por-nombre/{nombre}", "USER"))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_ROLES")
  void create_debeRetornar201() throws Exception {
    when(service.create(any(RolRequest.class))).thenReturn(response);
    mockMvc.perform(post("/api/v1/roles").contentType(MediaType.APPLICATION_JSON).content("""
            {"nombre": "USER"}
            """)).andExpect(status().isCreated()).andExpect(header().exists("Location"));
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_ROLES")
  void create_cuandoErrorDeValidacion_debeRetornar400() throws Exception {
    mockMvc.perform(post("/api/v1/roles").contentType(MediaType.APPLICATION_JSON).content("""
            {}
            """)).andExpect(status().isBadRequest());
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_ROLES")
  void update_debeRetornar200() throws Exception {
    when(service.update(any(UUID.class), any(RolUpdate.class))).thenReturn(response);
    mockMvc.perform(put("/api/v1/roles/{id}", id).contentType(MediaType.APPLICATION_JSON).content("""
            {"nombre": "USER"}
            """)).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_ROLES")
  void delete_debeRetornar204() throws Exception {
    mockMvc.perform(delete("/api/v1/roles/{id}", id)).andExpect(status().isNoContent());
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_ROLES")
  void delete_cuandoNoExiste_debeRetornar404() throws Exception {
    org.mockito.Mockito.doThrow(new EntityNotFoundException("not found")).when(service).delete(id);
    mockMvc.perform(delete("/api/v1/roles/{id}", id)).andExpect(status().isNotFound());
  }
}
