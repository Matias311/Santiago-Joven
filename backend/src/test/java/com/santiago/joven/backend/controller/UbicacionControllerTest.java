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

import com.santiago.joven.backend.dto.UbicacionRequest;
import com.santiago.joven.backend.dto.UbicacionResponse;
import com.santiago.joven.backend.dto.UbicacionUpdate;
import com.santiago.joven.backend.service.UbicacionService;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UbicacionController.class)
class UbicacionControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private UbicacionService service;
  @MockitoBean private com.santiago.joven.backend.security.JwtTokenProvider jwtTokenProvider;
  @MockitoBean private com.santiago.joven.backend.security.CustomUserDetailsService userDetailsService;

  private final UUID id = UUID.randomUUID();
  private final UbicacionResponse response =
      UbicacionResponse.builder()
          .id(id)
          .nombre("Ubicación de prueba")
          .direccion("Dirección")
          .ciudad("Ciudad")
          .latitud(new BigDecimal("19.4326"))
          .longitud(new BigDecimal("-99.1332"))
          .build();

  @Test
  @WithMockUser
  void findAll_debeRetornar200() throws Exception {
    when(service.findAll()).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/ubicaciones"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()))
        .andExpect(jsonPath("$[0].nombre").value("Ubicación de prueba"));
  }

  @Test
  @WithMockUser
  void findById_cuandoExiste_debeRetornar200() throws Exception {
    when(service.findById(id)).thenReturn(response);

    mockMvc
        .perform(get("/api/v1/ubicaciones/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findById_cuandoNoExiste_debeRetornar404() throws Exception {
    when(service.findById(id)).thenThrow(new EntityNotFoundException("not found"));

    mockMvc
        .perform(get("/api/v1/ubicaciones/{id}", id))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404));
  }

  @Test
  @WithMockUser
  void findByCiudad_debeRetornar200() throws Exception {
    when(service.findByCiudad("Ciudad")).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/ubicaciones/por-ciudad/{ciudad}", "Ciudad"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_LOCATIONS")
  void create_debeRetornar201_conLocationHeader() throws Exception {
    when(service.create(any(UbicacionRequest.class))).thenReturn(response);

    mockMvc
        .perform(
            post("/api/v1/ubicaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"nombre": "Ubicación Test"}
                        """))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(header().string("Location", "http://localhost/api/v1/ubicaciones/" + id));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_LOCATIONS")
  void create_cuandoErrorDeValidacion_debeRetornar400() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/ubicaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {}
                        """))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_LOCATIONS")
  void update_debeRetornar200() throws Exception {
    when(service.update(any(UUID.class), any(UbicacionUpdate.class))).thenReturn(response);

    mockMvc
        .perform(
            put("/api/v1/ubicaciones/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"nombre": "Actualizado"}
                        """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_LOCATIONS")
  void delete_debeRetornar204() throws Exception {
    mockMvc
        .perform(delete("/api/v1/ubicaciones/{id}", id))
        .andExpect(status().isNoContent());
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_LOCATIONS")
  void delete_cuandoNoExiste_debeRetornar404() throws Exception {
    doThrow(new EntityNotFoundException("not found")).when(service).delete(id);

    mockMvc
        .perform(delete("/api/v1/ubicaciones/{id}", id))
        .andExpect(status().isNotFound());
  }
}
