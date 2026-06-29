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

import com.santiago.joven.backend.dto.CategoriaRequest;
import com.santiago.joven.backend.dto.CategoriaResponse;
import com.santiago.joven.backend.dto.CategoriaUpdate;
import com.santiago.joven.backend.model.enums.TipoCategoria;
import com.santiago.joven.backend.service.CategoriaService;
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

@WebMvcTest(CategoriaController.class)
class CategoriaControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private CategoriaService service;
  @MockitoBean private com.santiago.joven.backend.security.JwtTokenProvider jwtTokenProvider;
  @MockitoBean private com.santiago.joven.backend.security.CustomUserDetailsService userDetailsService;

  private final UUID id = UUID.randomUUID();
  private final CategoriaResponse response =
      CategoriaResponse.builder()
          .id(id)
          .nombre("Categoría de prueba")
          .descripcion("Descripción")
          .tipo(TipoCategoria.GENERAL)
          .build();

  @Test
  @WithMockUser
  void findAll_debeRetornar200() throws Exception {
    when(service.findAll()).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/categorias"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findById_cuandoExiste_debeRetornar200() throws Exception {
    when(service.findById(id)).thenReturn(response);

    mockMvc
        .perform(get("/api/v1/categorias/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findById_cuandoNoExiste_debeRetornar404() throws Exception {
    when(service.findById(id)).thenThrow(new EntityNotFoundException("not found"));

    mockMvc
        .perform(get("/api/v1/categorias/{id}", id))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_GALLERY")
  void create_debeRetornar201_conLocationHeader() throws Exception {
    when(service.create(any(CategoriaRequest.class))).thenReturn(response);

    mockMvc
        .perform(
            post("/api/v1/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "nombre": "Nueva categoría",
                      "tipo": "GENERAL"
                    }
                    """))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(header().string("Location", "http://localhost/api/v1/categorias/" + id));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_GALLERY")
  void create_cuandoErrorDeValidacion_debeRetornar400() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {}
                        """))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_GALLERY")
  void update_debeRetornar200() throws Exception {
    when(service.update(any(UUID.class), any(CategoriaUpdate.class))).thenReturn(response);

    mockMvc
        .perform(
            put("/api/v1/categorias/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"nombre": "Actualizado"}
                        """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_GALLERY")
  void delete_debeRetornar204() throws Exception {
    mockMvc
        .perform(delete("/api/v1/categorias/{id}", id))
        .andExpect(status().isNoContent());
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_MANAGE_GALLERY")
  void delete_cuandoNoExiste_debeRetornar404() throws Exception {
    doThrow(new EntityNotFoundException("not found")).when(service).delete(id);

    mockMvc
        .perform(delete("/api/v1/categorias/{id}", id))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser
  void findByNombre_debeRetornar200() throws Exception {
    when(service.findByNombre("Categoría de prueba")).thenReturn(response);

    mockMvc
        .perform(get("/api/v1/categorias/por-nombre/{nombre}", "Categoría de prueba"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findByNombre_cuandoNoExiste_debeRetornar404() throws Exception {
    when(service.findByNombre("inexistente")).thenThrow(new EntityNotFoundException("not found"));

    mockMvc
        .perform(get("/api/v1/categorias/por-nombre/{nombre}", "inexistente"))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser
  void findByTipo_debeRetornar200() throws Exception {
    when(service.findByTipo(TipoCategoria.ACTIVIDAD)).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/categorias/por-tipo/{tipo}", "ACTIVIDAD"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }
}
