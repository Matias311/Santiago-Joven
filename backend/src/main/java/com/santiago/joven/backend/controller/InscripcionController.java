package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.InscripcionRequest;
import com.santiago.joven.backend.dto.InscripcionResponse;
import com.santiago.joven.backend.dto.InscripcionUpdate;
import com.santiago.joven.backend.model.enums.EstadoInscripcion;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import com.santiago.joven.backend.service.InscripcionService;
import jakarta.validation.Valid;
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

/** REST controller para {@link Inscripcion}. */
@RestController
@RequestMapping("/api/v1/inscripciones")
@RequiredArgsConstructor
public class InscripcionController {

  private final InscripcionService service;

  /** Lista todos los registros. */
  @GetMapping("")
  public ResponseEntity<List<InscripcionResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  /** Busca por ID. */
  @GetMapping("/{id}")
  public ResponseEntity<InscripcionResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  /** Crea un nuevo registro. */
  @PostMapping("")
  public ResponseEntity<InscripcionResponse> create(
      @Valid @RequestBody InscripcionRequest request) {
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
  public ResponseEntity<InscripcionResponse> update(
      @PathVariable UUID id, @Valid @RequestBody InscripcionUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  /** Elimina un registro por ID. */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  /** Busca por usuario. */
  @GetMapping("/por-usuario/{usuarioId}")
  public ResponseEntity<List<InscripcionResponse>> findByUsuarioId(@PathVariable UUID usuarioId) {
    return ResponseEntity.ok(service.findByUsuarioId(usuarioId));
  }

  /** Busca por recurso. */
  @GetMapping("/por-recurso")
  public ResponseEntity<List<InscripcionResponse>> findByRecurso(
      @RequestParam UUID recursoId, @RequestParam TipoRecurso tipoRecurso) {
    return ResponseEntity.ok(service.findByRecurso(recursoId, tipoRecurso));
  }

  /** Busca por estado. */
  @GetMapping("/por-estado/{estado}")
  public ResponseEntity<List<InscripcionResponse>> findByEstado(
      @PathVariable EstadoInscripcion estado) {
    return ResponseEntity.ok(service.findByEstado(estado));
  }

  /** Cuenta registros por recurso. */
  @GetMapping("/count-por-recurso")
  public ResponseEntity<Long> countByRecurso(
      @RequestParam UUID recursoId, @RequestParam TipoRecurso tipoRecurso) {
    return ResponseEntity.ok(service.countByRecurso(recursoId, tipoRecurso));
  }

  /** Verifica si existe inscripcion. */
  @GetMapping("/exists")
  public ResponseEntity<Boolean> existsByUsuarioAndRecurso(
      @RequestParam UUID usuarioId,
      @RequestParam UUID recursoId,
      @RequestParam TipoRecurso tipoRecurso) {
    return ResponseEntity.ok(service.existsByUsuarioAndRecurso(usuarioId, recursoId, tipoRecurso));
  }
}
