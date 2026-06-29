package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.InscripcionRequest;
import com.santiago.joven.backend.dto.InscripcionResponse;
import com.santiago.joven.backend.dto.InscripcionUpdate;
import com.santiago.joven.backend.model.enums.EstadoInscripcion;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import com.santiago.joven.backend.service.InscripcionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@RequestMapping("/api/v1/inscripciones")
@RequiredArgsConstructor
@Tag(name = "Inscripciones", description = "Gestion de inscripciones")
public class InscripcionController {

  private final InscripcionService service;

  @Operation(summary = "Listar inscripciones", description = "Publico")
  @GetMapping("")
  public ResponseEntity<List<InscripcionResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @Operation(summary = "Buscar inscripcion por ID", description = "Publico")
  @GetMapping("/{id}")
  public ResponseEntity<InscripcionResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @Operation(
      summary = "Crear inscripcion",
      description = "Cualquier usuario autenticado puede inscribirse",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PostMapping("")
  @PreAuthorize("isAuthenticated()")
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

  @Operation(
      summary = "Actualizar inscripcion",
      description = "Requiere permiso MANAGE_USERS",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_USERS')")
  public ResponseEntity<InscripcionResponse> update(
      @PathVariable UUID id, @Valid @RequestBody InscripcionUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  @Operation(
      summary = "Eliminar inscripcion",
      description = "Requiere permiso MANAGE_USERS",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_USERS')")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Buscar inscripciones por usuario", description = "Publico")
  @GetMapping("/por-usuario/{usuarioId}")
  public ResponseEntity<List<InscripcionResponse>> findByUsuarioId(@PathVariable UUID usuarioId) {
    return ResponseEntity.ok(service.findByUsuarioId(usuarioId));
  }

  @Operation(summary = "Buscar inscripciones por recurso", description = "Publico")
  @GetMapping("/por-recurso")
  public ResponseEntity<List<InscripcionResponse>> findByRecurso(
      @RequestParam UUID recursoId, @RequestParam TipoRecurso tipoRecurso) {
    return ResponseEntity.ok(service.findByRecurso(recursoId, tipoRecurso));
  }

  @Operation(summary = "Buscar inscripciones por estado", description = "Publico")
  @GetMapping("/por-estado/{estado}")
  public ResponseEntity<List<InscripcionResponse>> findByEstado(
      @PathVariable EstadoInscripcion estado) {
    return ResponseEntity.ok(service.findByEstado(estado));
  }

  @Operation(summary = "Contar inscripciones por recurso", description = "Publico")
  @GetMapping("/count-por-recurso")
  public ResponseEntity<Long> countByRecurso(
      @RequestParam UUID recursoId, @RequestParam TipoRecurso tipoRecurso) {
    return ResponseEntity.ok(service.countByRecurso(recursoId, tipoRecurso));
  }

  @Operation(summary = "Verificar si existe inscripcion", description = "Publico")
  @GetMapping("/exists")
  public ResponseEntity<Boolean> existsByUsuarioAndRecurso(
      @RequestParam UUID usuarioId,
      @RequestParam UUID recursoId,
      @RequestParam TipoRecurso tipoRecurso) {
    return ResponseEntity.ok(service.existsByUsuarioAndRecurso(usuarioId, recursoId, tipoRecurso));
  }
}
