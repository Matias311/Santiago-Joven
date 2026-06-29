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

import com.santiago.joven.backend.dto.ResenaCalificacionRequest;
import com.santiago.joven.backend.dto.ResenaCalificacionResponse;
import com.santiago.joven.backend.dto.ResenaCalificacionUpdate;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import com.santiago.joven.backend.service.ResenaCalificacionService;
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

@WebMvcTest(ResenaCalificacionController.class)
class ResenaCalificacionControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private ResenaCalificacionService service;
  @MockitoBean private com.santiago.joven.backend.security.JwtTokenProvider jwtTokenProvider;
  @MockitoBean private com.santiago.joven.backend.security.CustomUserDetailsService userDetailsService;

  private final UUID id = UUID.randomUUID();
  private final UUID usuarioId = UUID.randomUUID();
  private final UUID recursoId = UUID.randomUUID();
  private final ResenaCalificacionResponse response =
      ResenaCalificacionResponse.builder()
          .id(id)
          .usuarioId(usuarioId)
          .recursoId(recursoId)
          .tipoRecurso(TipoRecurso.CURSO)
          .calificacion(5)
          .comentario("Excelente curso")
          .build();

  @Test
  @WithMockUser
  void findAll_debeRetornar200() throws Exception {
    when(service.findAll()).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/resenas-calificaciones"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findById_cuandoExiste_debeRetornar200() throws Exception {
    when(service.findById(id)).thenReturn(response);

    mockMvc
        .perform(get("/api/v1/resenas-calificaciones/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findById_cuandoNoExiste_debeRetornar404() throws Exception {
    when(service.findById(id)).thenThrow(new EntityNotFoundException("not found"));

    mockMvc
        .perform(get("/api/v1/resenas-calificaciones/{id}", id))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404));
  }

  @Test
  @WithMockUser
  void findByRecurso_debeRetornar200() throws Exception {
    when(service.findByRecurso(recursoId, TipoRecurso.CURSO)).thenReturn(List.of(response));

    mockMvc
        .perform(
            get("/api/v1/resenas-calificaciones/por-recurso")
                .param("recursoId", recursoId.toString())
                .param("tipoRecurso", "CURSO"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findByUsuarioId_debeRetornar200() throws Exception {
    when(service.findByUsuarioId(usuarioId)).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/resenas-calificaciones/por-usuario/{usuarioId}", usuarioId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findByCalificacionMinima_debeRetornar200() throws Exception {
    when(service.findByCalificacionMinima(3)).thenReturn(List.of(response));

    mockMvc
        .perform(
            get("/api/v1/resenas-calificaciones/por-calificacion-minima/{calificacion}", 3))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void create_debeRetornar201_conLocationHeader() throws Exception {
    when(service.create(any(ResenaCalificacionRequest.class))).thenReturn(response);

    mockMvc
        .perform(
            post("/api/v1/resenas-calificaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "usuarioId": "%s",
                      "recursoId": "%s",
                      "tipoRecurso": "CURSO",
                      "calificacion": 5,
                      "comentario": "Excelente curso"
                    }
                    """
                        .formatted(usuarioId.toString(), recursoId.toString())))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(header().string(
            "Location", "http://localhost/api/v1/resenas-calificaciones/" + id));
  }

  @Test
  @WithMockUser
  void create_cuandoErrorDeValidacion_debeRetornar400() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/resenas-calificaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {}
                        """))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_USERS")
  void update_debeRetornar200() throws Exception {
    when(service.update(any(UUID.class), any(ResenaCalificacionUpdate.class))).thenReturn(response);

    mockMvc
        .perform(
            put("/api/v1/resenas-calificaciones/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"calificacion": 4}
                        """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_USERS")
  void delete_debeRetornar204() throws Exception {
    mockMvc
        .perform(delete("/api/v1/resenas-calificaciones/{id}", id))
        .andExpect(status().isNoContent());
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_USERS")
  void delete_cuandoNoExiste_debeRetornar404() throws Exception {
    doThrow(new EntityNotFoundException("not found")).when(service).delete(id);

    mockMvc
        .perform(delete("/api/v1/resenas-calificaciones/{id}", id))
        .andExpect(status().isNotFound());
  }
}
