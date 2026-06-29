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

import com.santiago.joven.backend.dto.InscripcionRequest;
import com.santiago.joven.backend.dto.InscripcionResponse;
import com.santiago.joven.backend.dto.InscripcionUpdate;
import com.santiago.joven.backend.model.enums.EstadoInscripcion;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import com.santiago.joven.backend.service.InscripcionService;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(InscripcionController.class)
class InscripcionControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private InscripcionService service;
  @MockitoBean private com.santiago.joven.backend.security.JwtTokenProvider jwtTokenProvider;
  @MockitoBean private com.santiago.joven.backend.security.CustomUserDetailsService userDetailsService;

  private final UUID id = UUID.randomUUID();
  private final InscripcionResponse response =
      InscripcionResponse.builder()
          .id(id)
          .usuarioId(UUID.randomUUID())
          .recursoId(UUID.randomUUID())
          .tipoRecurso(TipoRecurso.CURSO)
          .fechaInscripcion(LocalDateTime.now())
          .estado(EstadoInscripcion.INSCRITO)
          .notas("nota")
          .build();

  @Test
  @WithMockUser
  void findAll_debeRetornar200() throws Exception {
    when(service.findAll()).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/inscripciones"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findById_cuandoExiste_debeRetornar200() throws Exception {
    when(service.findById(id)).thenReturn(response);

    mockMvc
        .perform(get("/api/v1/inscripciones/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findById_cuandoNoExiste_debeRetornar404() throws Exception {
    when(service.findById(id)).thenThrow(new EntityNotFoundException("not found"));

    mockMvc
        .perform(get("/api/v1/inscripciones/{id}", id))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404));
  }

  @Test
  @WithMockUser
  void create_debeRetornar201_conLocationHeader() throws Exception {
    when(service.create(any(InscripcionRequest.class))).thenReturn(response);

    mockMvc
        .perform(
            post("/api/v1/inscripciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "usuarioId": "%s",
                      "recursoId": "%s",
                      "tipoRecurso": "CURSO"
                    }
                    """
                        .formatted(UUID.randomUUID(), UUID.randomUUID())))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(header().string("Location", "http://localhost/api/v1/inscripciones/" + id));
  }

  @Test
  @WithMockUser
  void create_cuandoErrorDeValidacion_debeRetornar400() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/inscripciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"tipoRecurso": "INVALIDO"}
                        """))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_USERS")
  void update_debeRetornar200() throws Exception {
    when(service.update(any(UUID.class), any(InscripcionUpdate.class))).thenReturn(response);

    mockMvc
        .perform(
            put("/api/v1/inscripciones/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"estado": "INSCRITO"}
                        """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_USERS")
  void delete_debeRetornar204() throws Exception {
    mockMvc
        .perform(delete("/api/v1/inscripciones/{id}", id))
        .andExpect(status().isNoContent());
  }

  @Test
  @WithMockUser
  void delete_cuandoNoExiste_debeRetornar404() throws Exception {
    doThrow(new EntityNotFoundException("not found"))
        .when(service)
        .delete(id);

    mockMvc
        .perform(delete("/api/v1/inscripciones/{id}", id))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser
  void findByUsuarioId_debeRetornar200() throws Exception {
    var userId = UUID.randomUUID();
    when(service.findByUsuarioId(userId)).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/inscripciones/por-usuario/{usuarioId}", userId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findByRecurso_debeRetornar200() throws Exception {
    var recursoId = UUID.randomUUID();
    when(service.findByRecurso(recursoId, TipoRecurso.CURSO)).thenReturn(List.of(response));

    mockMvc
        .perform(
            get("/api/v1/inscripciones/por-recurso")
                .param("recursoId", recursoId.toString())
                .param("tipoRecurso", "CURSO"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findByEstado_debeRetornar200() throws Exception {
    when(service.findByEstado(EstadoInscripcion.INSCRITO)).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/inscripciones/por-estado/{estado}", "INSCRITO"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void countByRecurso_debeRetornar200() throws Exception {
    var recursoId = UUID.randomUUID();
    when(service.countByRecurso(recursoId, TipoRecurso.CURSO)).thenReturn(5L);

    mockMvc
        .perform(
            get("/api/v1/inscripciones/count-por-recurso")
                .param("recursoId", recursoId.toString())
                .param("tipoRecurso", "CURSO"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(5));
  }

  @Test
  @WithMockUser
  void existsByUsuarioAndRecurso_debeRetornar200() throws Exception {
    var userId = UUID.randomUUID();
    var recursoId = UUID.randomUUID();
    when(service.existsByUsuarioAndRecurso(userId, recursoId, TipoRecurso.CURSO)).thenReturn(true);

    mockMvc
        .perform(
            get("/api/v1/inscripciones/exists")
                .param("usuarioId", userId.toString())
                .param("recursoId", recursoId.toString())
                .param("tipoRecurso", "CURSO"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").value(true));
  }
}
