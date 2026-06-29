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

import com.santiago.joven.backend.dto.ProgramaRequest;
import com.santiago.joven.backend.dto.ProgramaResponse;
import com.santiago.joven.backend.dto.ProgramaUpdate;
import com.santiago.joven.backend.service.ProgramaService;
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

@WebMvcTest(ProgramaController.class)
class ProgramaControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private ProgramaService service;
  @MockitoBean private com.santiago.joven.backend.security.JwtTokenProvider jwtTokenProvider;
  @MockitoBean private com.santiago.joven.backend.security.CustomUserDetailsService userDetailsService;

  private final UUID id = UUID.randomUUID();
  private final ProgramaResponse response =
      ProgramaResponse.builder()
          .id(id)
          .titulo("Programa de prueba")
          .descripcion("Descripción")
          .definicion("Definición")
          .objetivos("Objetivos")
          .metodologia("Metodología")
          .activo(true)
          .build();

  @Test
  @WithMockUser
  void findAll_debeRetornar200() throws Exception {
    when(service.findAll()).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/programas"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()))
        .andExpect(jsonPath("$[0].titulo").value("Programa de prueba"));
  }

  @Test
  @WithMockUser
  void findActivos_debeRetornar200() throws Exception {
    when(service.findActivos()).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/programas/activos"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findById_cuandoExiste_debeRetornar200() throws Exception {
    when(service.findById(id)).thenReturn(response);

    mockMvc
        .perform(get("/api/v1/programas/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findById_cuandoNoExiste_debeRetornar404() throws Exception {
    when(service.findById(id)).thenThrow(new EntityNotFoundException("not found"));

    mockMvc
        .perform(get("/api/v1/programas/{id}", id))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_CREATE_PROGRAM")
  void create_debeRetornar201_conLocationHeader() throws Exception {
    when(service.create(any(ProgramaRequest.class))).thenReturn(response);

    mockMvc
        .perform(
            post("/api/v1/programas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "titulo": "Programa Test",
                      "descripcion": "Descripción",
                      "definicion": "Definición",
                      "objetivos": "Objetivos",
                      "metodologia": "Metodología"
                    }
                    """))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(header().string("Location", "http://localhost/api/v1/programas/" + id));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_CREATE_PROGRAM")
  void create_cuandoErrorDeValidacion_debeRetornar400() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/programas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {}
                        """))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_EDIT_PROGRAM")
  void update_debeRetornar200() throws Exception {
    when(service.update(any(UUID.class), any(ProgramaUpdate.class))).thenReturn(response);

    mockMvc
        .perform(
            put("/api/v1/programas/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"titulo": "Actualizado"}
                        """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_DELETE_PROGRAM")
  void delete_debeRetornar204() throws Exception {
    mockMvc
        .perform(delete("/api/v1/programas/{id}", id))
        .andExpect(status().isNoContent());
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_DELETE_PROGRAM")
  void delete_cuandoNoExiste_debeRetornar404() throws Exception {
    doThrow(new EntityNotFoundException("not found")).when(service).delete(id);

    mockMvc
        .perform(delete("/api/v1/programas/{id}", id))
        .andExpect(status().isNotFound());
  }
}
