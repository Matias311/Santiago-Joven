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

import com.santiago.joven.backend.dto.CursoDestacadoRequest;
import com.santiago.joven.backend.dto.CursoDestacadoResponse;
import com.santiago.joven.backend.dto.CursoDestacadoUpdate;
import com.santiago.joven.backend.service.CursoDestacadoService;
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

@WebMvcTest(CursoDestacadoController.class)
class CursoDestacadoControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private CursoDestacadoService service;
  @MockitoBean private com.santiago.joven.backend.security.JwtTokenProvider jwtTokenProvider;
  @MockitoBean private com.santiago.joven.backend.security.CustomUserDetailsService userDetailsService;

  private final UUID id = UUID.randomUUID();
  private final UUID categoriaId = UUID.randomUUID();
  private final CursoDestacadoResponse response =
      CursoDestacadoResponse.builder()
          .id(id)
          .titulo("Curso de prueba")
          .descripcion("Descripción")
          .objetivo("Objetivo")
          .categoriaId(categoriaId)
          .activo(true)
          .build();

  @Test
  @WithMockUser
  void findAll_debeRetornar200() throws Exception {
    when(service.findAll()).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/cursos-destacados"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findById_cuandoExiste_debeRetornar200() throws Exception {
    when(service.findById(id)).thenReturn(response);

    mockMvc
        .perform(get("/api/v1/cursos-destacados/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findById_cuandoNoExiste_debeRetornar404() throws Exception {
    when(service.findById(id)).thenThrow(new EntityNotFoundException("not found"));

    mockMvc
        .perform(get("/api/v1/cursos-destacados/{id}", id))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404));
  }

  @Test
  @WithMockUser
  void findByCategoriaId_debeRetornar200() throws Exception {
    when(service.findByCategoriaId(categoriaId)).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/cursos-destacados/por-categoria/{categoriaId}", categoriaId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findActivos_debeRetornar200() throws Exception {
    when(service.findActivos()).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/cursos-destacados/activos"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_CREATE_COURSE")
  void create_debeRetornar201_conLocationHeader() throws Exception {
    when(service.create(any(CursoDestacadoRequest.class))).thenReturn(response);

    mockMvc
        .perform(
            post("/api/v1/cursos-destacados")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "titulo": "Curso Test",
                      "descripcion": "Descripción",
                      "objetivo": "Objetivo",
                      "categoriaId": "%s"
                    }
                    """
                        .formatted(categoriaId.toString())))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(header().string("Location", "http://localhost/api/v1/cursos-destacados/" + id));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_CREATE_COURSE")
  void create_cuandoErrorDeValidacion_debeRetornar400() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/cursos-destacados")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {}
                        """))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_EDIT_COURSE")
  void update_debeRetornar200() throws Exception {
    when(service.update(any(UUID.class), any(CursoDestacadoUpdate.class))).thenReturn(response);

    mockMvc
        .perform(
            put("/api/v1/cursos-destacados/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"titulo": "Actualizado"}
                        """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_DELETE_COURSE")
  void delete_debeRetornar204() throws Exception {
    mockMvc
        .perform(delete("/api/v1/cursos-destacados/{id}", id))
        .andExpect(status().isNoContent());
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_DELETE_COURSE")
  void delete_cuandoNoExiste_debeRetornar404() throws Exception {
    doThrow(new EntityNotFoundException("not found")).when(service).delete(id);

    mockMvc
        .perform(delete("/api/v1/cursos-destacados/{id}", id))
        .andExpect(status().isNotFound());
  }
}
