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

import com.santiago.joven.backend.dto.GaleriaFotoRequest;
import com.santiago.joven.backend.dto.GaleriaFotoResponse;
import com.santiago.joven.backend.dto.GaleriaFotoUpdate;
import com.santiago.joven.backend.service.GaleriaFotoService;
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

@WebMvcTest(GaleriaFotoController.class)
class GaleriaFotoControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private GaleriaFotoService service;
  @MockitoBean private com.santiago.joven.backend.security.JwtTokenProvider jwtTokenProvider;
  @MockitoBean private com.santiago.joven.backend.security.CustomUserDetailsService userDetailsService;

  private final UUID id = UUID.randomUUID();
  private final GaleriaFotoResponse response = GaleriaFotoResponse.builder().id(id).build();

  @Test @WithMockUser
  void findAll_debeRetornar200() throws Exception {
    when(service.findAll()).thenReturn(List.of(response));
    mockMvc.perform(get("/api/v1/galeria-fotos"))
        .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test @WithMockUser
  void findById_cuandoExiste_debeRetornar200() throws Exception {
    when(service.findById(id)).thenReturn(response);
    mockMvc.perform(get("/api/v1/galeria-fotos/{id}", id))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test @WithMockUser
  void findById_cuandoNoExiste_debeRetornar404() throws Exception {
    when(service.findById(id)).thenThrow(new EntityNotFoundException("not found"));
    mockMvc.perform(get("/api/v1/galeria-fotos/{id}", id)).andExpect(status().isNotFound());
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_GALLERY")
  void create_debeRetornar201() throws Exception {
    when(service.create(any(GaleriaFotoRequest.class))).thenReturn(response);
    mockMvc.perform(post("/api/v1/galeria-fotos").contentType(MediaType.APPLICATION_JSON).content("""
            {"actividadId": "%s", "urlImagen": "https://example.com/img.jpg"}
            """.formatted(UUID.randomUUID()))).andExpect(status().isCreated()).andExpect(header().exists("Location"));
  }

  @Test @WithMockUser
  void create_cuandoErrorDeValidacion_debeRetornar400() throws Exception {
    mockMvc.perform(post("/api/v1/galeria-fotos").contentType(MediaType.APPLICATION_JSON).content("""
            {}
            """)).andExpect(status().isBadRequest());
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_GALLERY")
  void update_debeRetornar200() throws Exception {
    when(service.update(any(UUID.class), any(GaleriaFotoUpdate.class))).thenReturn(response);
    mockMvc.perform(put("/api/v1/galeria-fotos/{id}", id).contentType(MediaType.APPLICATION_JSON).content("""
            {"actividadId": "%s", "urlImagen": "https://example.com/img.jpg"}
            """.formatted(UUID.randomUUID()))).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test @WithMockUser(authorities = "PERMISSION_MANAGE_GALLERY")
  void delete_debeRetornar204() throws Exception {
    mockMvc.perform(delete("/api/v1/galeria-fotos/{id}", id)).andExpect(status().isNoContent());
  }

  @Test @WithMockUser
  void delete_cuandoNoExiste_debeRetornar404() throws Exception {
    org.mockito.Mockito.doThrow(new EntityNotFoundException("not found")).when(service).delete(id);
    mockMvc.perform(delete("/api/v1/galeria-fotos/{id}", id)).andExpect(status().isNotFound());
  }

  @Test @WithMockUser
  void findByActividadId_debeRetornar200() throws Exception {
    var actividadId = UUID.randomUUID();
    when(service.findByActividadId(actividadId)).thenReturn(List.of(response));
    mockMvc.perform(get("/api/v1/galeria-fotos/por-actividad/{actividadId}", actividadId))
        .andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(id.toString()));
  }
}
