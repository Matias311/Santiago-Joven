package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.ActividadTallerRequest;
import com.santiago.joven.backend.dto.ActividadTallerResponse;
import com.santiago.joven.backend.dto.ActividadTallerUpdate;
import com.santiago.joven.backend.model.enums.EstadoActividad;
import com.santiago.joven.backend.service.ActividadTallerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/actividades-talleres")
@RequiredArgsConstructor
@Tag(name = "Actividades y Talleres", description = "Gestion de actividades y talleres")
public class ActividadTallerController {

  private final ActividadTallerService service;

  @Operation(summary = "Listar actividades y talleres", description = "Publico")
  @GetMapping("")
  public ResponseEntity<List<ActividadTallerResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @Operation(summary = "Buscar actividad/taller por ID", description = "Publico")
  @GetMapping("/{id}")
  public ResponseEntity<ActividadTallerResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @Operation(
      summary = "Crear actividad/taller",
      description = "Requiere permiso CREATE_ACTIVITY",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_CREATE_ACTIVITY')")
  @PostMapping("")
  public ResponseEntity<ActividadTallerResponse> create(
      @Valid @RequestBody ActividadTallerRequest request) {
    var response = service.create(request);
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @Operation(
      summary = "Actualizar actividad/taller",
      description = "Requiere permiso EDIT_ACTIVITY",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_EDIT_ACTIVITY')")
  @PutMapping("/{id}")
  public ResponseEntity<ActividadTallerResponse> update(
      @PathVariable UUID id, @Valid @RequestBody ActividadTallerUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  @Operation(
      summary = "Eliminar actividad/taller",
      description = "Requiere permiso DELETE_ACTIVITY",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_DELETE_ACTIVITY')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Buscar por categoria", description = "Publico")
  @GetMapping("/por-categoria/{categoriaId}")
  public ResponseEntity<List<ActividadTallerResponse>> findByCategoriaId(
      @PathVariable UUID categoriaId) {
    return ResponseEntity.ok(service.findByCategoriaId(categoriaId));
  }

  @Operation(summary = "Buscar por estado", description = "Publico")
  @GetMapping("/por-estado/{estado}")
  public ResponseEntity<List<ActividadTallerResponse>> findByEstado(
      @PathVariable EstadoActividad estado) {
    return ResponseEntity.ok(service.findByEstado(estado));
  }

  @Operation(summary = "Buscar entre fechas", description = "Publico")
  @GetMapping("/entre-fechas")
  public ResponseEntity<List<ActividadTallerResponse>> findByFechaHoraBetween(
      @RequestParam LocalDateTime inicio, @RequestParam LocalDateTime fin) {
    return ResponseEntity.ok(service.findByFechaHoraBetween(inicio, fin));
  }

  @Operation(summary = "Listar actividades activas", description = "Publico")
  @GetMapping("/activas")
  public ResponseEntity<List<ActividadTallerResponse>> findActivas() {
    return ResponseEntity.ok(service.findActivas());
  }

  @Operation(summary = "Listar proximas actividades", description = "Publico")
  @GetMapping("/proximas")
  public ResponseEntity<List<ActividadTallerResponse>> findProximas() {
    return ResponseEntity.ok(service.findProximas());
  }
}
