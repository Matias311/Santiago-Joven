package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.EstadisticaRequest;
import com.santiago.joven.backend.dto.EstadisticaResponse;
import com.santiago.joven.backend.dto.EstadisticaUpdate;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import com.santiago.joven.backend.service.EstadisticaService;
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
@RequestMapping("/api/v1/estadisticas")
@RequiredArgsConstructor
@Tag(name = "Estadisticas", description = "Gestion de estadisticas")
public class EstadisticaController {

  private final EstadisticaService service;

  @Operation(summary = "Listar estadisticas", description = "Publico")
  @GetMapping("")
  public ResponseEntity<List<EstadisticaResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @Operation(summary = "Buscar estadistica por ID", description = "Publico")
  @GetMapping("/{id}")
  public ResponseEntity<EstadisticaResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @Operation(
      summary = "Crear estadistica",
      description = "Requiere permiso VIEW_ANALYTICS",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_VIEW_ANALYTICS')")
  @PostMapping("")
  public ResponseEntity<EstadisticaResponse> create(
      @Valid @RequestBody EstadisticaRequest request) {
    var response = service.create(request);
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @Operation(
      summary = "Actualizar estadistica",
      description = "Requiere permiso VIEW_ANALYTICS",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_VIEW_ANALYTICS')")
  @PutMapping("/{id}")
  public ResponseEntity<EstadisticaResponse> update(
      @PathVariable UUID id, @Valid @RequestBody EstadisticaUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  @Operation(
      summary = "Eliminar estadistica",
      description = "Requiere permiso VIEW_ANALYTICS",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_VIEW_ANALYTICS')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Buscar estadistica por recurso", description = "Publico")
  @GetMapping("/por-recurso")
  public ResponseEntity<EstadisticaResponse> findByRecurso(
      @RequestParam UUID recursoId, @RequestParam TipoRecurso tipoRecurso) {
    return ResponseEntity.ok(service.findByRecurso(recursoId, tipoRecurso));
  }
}
