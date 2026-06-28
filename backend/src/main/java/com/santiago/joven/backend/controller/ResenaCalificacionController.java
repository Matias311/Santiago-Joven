package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.ResenaCalificacionRequest;
import com.santiago.joven.backend.dto.ResenaCalificacionResponse;
import com.santiago.joven.backend.dto.ResenaCalificacionUpdate;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import com.santiago.joven.backend.service.ResenaCalificacionService;
import jakarta.validation.Valid;
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

/** REST controller para {@link ResenaCalificacion}. */
@RestController
@RequestMapping("/api/v1/resenas-calificaciones")
@RequiredArgsConstructor
public class ResenaCalificacionController {

  private final ResenaCalificacionService service;

  /** Lista todos los registros. */
  @GetMapping("")
  public ResponseEntity<List<ResenaCalificacionResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  /** Busca por ID. */
  @GetMapping("/{id}")
  public ResponseEntity<ResenaCalificacionResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  /** Busca por recurso. */
  @GetMapping("/por-recurso")
  public ResponseEntity<List<ResenaCalificacionResponse>> findByRecurso(
      @RequestParam UUID recursoId, @RequestParam TipoRecurso tipoRecurso) {
    return ResponseEntity.ok(service.findByRecurso(recursoId, tipoRecurso));
  }

  /** Busca por usuario. */
  @GetMapping("/por-usuario/{usuarioId}")
  public ResponseEntity<List<ResenaCalificacionResponse>> findByUsuarioId(
      @PathVariable UUID usuarioId) {
    return ResponseEntity.ok(service.findByUsuarioId(usuarioId));
  }

  /** Busca por calificacion minima. */
  @GetMapping("/por-calificacion-minima/{calificacion}")
  public ResponseEntity<List<ResenaCalificacionResponse>> findByCalificacionMinima(
      @PathVariable Integer calificacion) {
    return ResponseEntity.ok(service.findByCalificacionMinima(calificacion));
  }

  /** Crea un nuevo registro. */
  @PostMapping("")
  public ResponseEntity<ResenaCalificacionResponse> create(
      @Valid @RequestBody ResenaCalificacionRequest request) {
    var response = service.create(request);
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  /** Actualiza un registro existente. */
  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_USERS')")
  public ResponseEntity<ResenaCalificacionResponse> update(
      @PathVariable UUID id, @Valid @RequestBody ResenaCalificacionUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  /** Elimina un registro por ID. */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_USERS')")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
