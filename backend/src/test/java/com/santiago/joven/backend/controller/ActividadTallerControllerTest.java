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

import com.santiago.joven.backend.dto.ActividadTallerRequest;
import com.santiago.joven.backend.dto.ActividadTallerResponse;
import com.santiago.joven.backend.dto.ActividadTallerUpdate;
import com.santiago.joven.backend.model.enums.EstadoActividad;
import com.santiago.joven.backend.service.ActividadTallerService;
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

@WebMvcTest(ActividadTallerController.class)
class ActividadTallerControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private ActividadTallerService service;
  @MockitoBean private com.santiago.joven.backend.security.JwtTokenProvider jwtTokenProvider;
  @MockitoBean private com.santiago.joven.backend.security.CustomUserDetailsService userDetailsService;

  private final UUID id = UUID.randomUUID();
  private final ActividadTallerResponse response =
      ActividadTallerResponse.builder()
          .id(id)
          .titulo("Taller de prueba")
          .descripcion("Descripción")
          .categoriaId(UUID.randomUUID())
          .fechaHora(LocalDateTime.now())
          .activo(true)
          .estado(EstadoActividad.CONFIRMADO)
          .build();

  @Test
  @WithMockUser
  void findAll_debeRetornar200() throws Exception {
    when(service.findAll()).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/actividades-talleres"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findById_cuandoExiste_debeRetornar200() throws Exception {
    when(service.findById(id)).thenReturn(response);

    mockMvc
        .perform(get("/api/v1/actividades-talleres/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findById_cuandoNoExiste_debeRetornar404() throws Exception {
    when(service.findById(id)).thenThrow(new EntityNotFoundException("not found"));

    mockMvc
        .perform(get("/api/v1/actividades-talleres/{id}", id))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(404));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_CREATE_ACTIVITY")
  void create_debeRetornar201_conLocationHeader() throws Exception {
    when(service.create(any(ActividadTallerRequest.class))).thenReturn(response);

    mockMvc
        .perform(
            post("/api/v1/actividades-talleres")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                    {
                      "titulo": "Taller de prueba",
                      "descripcion": "Descripción del taller",
                      "categoriaId": "%s",
                      "fechaHora": "2025-06-15T10:00:00"
                    }
                    """
                        .formatted(UUID.randomUUID())))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(header().string("Location", "http://localhost/api/v1/actividades-talleres/" + id));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_CREATE_ACTIVITY")
  void create_cuandoErrorDeValidacion_debeRetornar400() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/actividades-talleres")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {}
                        """))
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_EDIT_ACTIVITY")
  void update_debeRetornar200() throws Exception {
    when(service.update(any(UUID.class), any(ActividadTallerUpdate.class))).thenReturn(response);

    mockMvc
        .perform(
            put("/api/v1/actividades-talleres/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"titulo": "Actualizado"}
                        """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()));
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_DELETE_ACTIVITY")
  void delete_debeRetornar204() throws Exception {
    mockMvc
        .perform(delete("/api/v1/actividades-talleres/{id}", id))
        .andExpect(status().isNoContent());
  }

  @Test
  @WithMockUser(authorities = "PERMISSION_DELETE_ACTIVITY")
  void delete_cuandoNoExiste_debeRetornar404() throws Exception {
    doThrow(new EntityNotFoundException("not found")).when(service).delete(id);

    mockMvc
        .perform(delete("/api/v1/actividades-talleres/{id}", id))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser
  void findByCategoriaId_debeRetornar200() throws Exception {
    var catId = UUID.randomUUID();
    when(service.findByCategoriaId(catId)).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/actividades-talleres/por-categoria/{categoriaId}", catId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findByEstado_debeRetornar200() throws Exception {
    when(service.findByEstado(EstadoActividad.CONFIRMADO)).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/actividades-talleres/por-estado/{estado}", "CONFIRMADO"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findByFechaHoraBetween_debeRetornar200() throws Exception {
    when(service.findByFechaHoraBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
        .thenReturn(List.of(response));

    mockMvc
        .perform(
            get("/api/v1/actividades-talleres/entre-fechas")
                .param("inicio", "2025-06-01T00:00:00")
                .param("fin", "2025-06-30T23:59:00"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findActivas_debeRetornar200() throws Exception {
    when(service.findActivas()).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/actividades-talleres/activas"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }

  @Test
  @WithMockUser
  void findProximas_debeRetornar200() throws Exception {
    when(service.findProximas()).thenReturn(List.of(response));

    mockMvc
        .perform(get("/api/v1/actividades-talleres/proximas"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()));
  }
}
