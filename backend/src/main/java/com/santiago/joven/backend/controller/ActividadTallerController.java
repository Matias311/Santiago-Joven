package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.ActividadTallerRequest;
import com.santiago.joven.backend.dto.ActividadTallerResponse;
import com.santiago.joven.backend.dto.ActividadTallerUpdate;
import com.santiago.joven.backend.model.enums.EstadoActividad;
import com.santiago.joven.backend.service.ActividadTallerService;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
import org.springframework.security.access.prepost.PreAuthorize;

/** REST controller para {@link com.santiago.joven.backend.model.entity.ActividadTaller}. */
@RestController
@RequestMapping("/api/v1/actividades-talleres")
@RequiredArgsConstructor
public class ActividadTallerController {

  private final ActividadTallerService service;

  /** Lista todos los registros. */
  @GetMapping("")
  public ResponseEntity<List<ActividadTallerResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  /** Busca por ID. */
  @GetMapping("/{id}")
  public ResponseEntity<ActividadTallerResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  /** Crea un nuevo registro. */
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

  /** Actualiza un registro existente. */
  @PreAuthorize("hasAuthority('PERMISSION_EDIT_ACTIVITY')")
  @PutMapping("/{id}")
  public ResponseEntity<ActividadTallerResponse> update(
      @PathVariable UUID id, @Valid @RequestBody ActividadTallerUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  /** Elimina un registro por ID. */
  @PreAuthorize("hasAuthority('PERMISSION_DELETE_ACTIVITY')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  /** Busca por categoria. */
  @GetMapping("/por-categoria/{categoriaId}")
  public ResponseEntity<List<ActividadTallerResponse>> findByCategoriaId(
      @PathVariable UUID categoriaId) {
    return ResponseEntity.ok(service.findByCategoriaId(categoriaId));
  }

  /** Busca por estado. */
  @GetMapping("/por-estado/{estado}")
  public ResponseEntity<List<ActividadTallerResponse>> findByEstado(
      @PathVariable EstadoActividad estado) {
    return ResponseEntity.ok(service.findByEstado(estado));
  }

  /** Busca por rango de fechas. */
  @GetMapping("/entre-fechas")
  public ResponseEntity<List<ActividadTallerResponse>> findByFechaHoraBetween(
      @RequestParam LocalDateTime inicio, @RequestParam LocalDateTime fin) {
    return ResponseEntity.ok(service.findByFechaHoraBetween(inicio, fin));
  }

  /** Lista las entidades activas. */
  @GetMapping("/activas")
  public ResponseEntity<List<ActividadTallerResponse>> findActivas() {
    return ResponseEntity.ok(service.findActivas());
  }

  /** Lista las proximas entidades. */
  @GetMapping("/proximas")
  public ResponseEntity<List<ActividadTallerResponse>> findProximas() {
    return ResponseEntity.ok(service.findProximas());
  }
}
