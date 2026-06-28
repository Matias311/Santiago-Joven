package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.SaludMentalRequest;
import com.santiago.joven.backend.dto.SaludMentalResponse;
import com.santiago.joven.backend.dto.SaludMentalUpdate;
import com.santiago.joven.backend.service.SaludMentalService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/salud-mental")
@RequiredArgsConstructor
@Tag(name = "Salud Mental", description = "Gestion de recursos de salud mental")
public class SaludMentalController {

  private final SaludMentalService service;

  @Operation(summary = "Listar recursos de salud mental", description = "Publico")
  @GetMapping("")
  public ResponseEntity<List<SaludMentalResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @Operation(summary = "Buscar recurso de salud mental por ID", description = "Publico")
  @GetMapping("/{id}")
  public ResponseEntity<SaludMentalResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @Operation(summary = "Listar recursos ordenados", description = "Publico")
  @GetMapping("/ordenados")
  public ResponseEntity<List<SaludMentalResponse>> findAllOrdered() {
    return ResponseEntity.ok(service.findAllOrdered());
  }

  @Operation(
      summary = "Crear recurso de salud mental",
      description = "Requiere permiso EDIT_HEALTH",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_EDIT_HEALTH')")
  @PostMapping("")
  public ResponseEntity<SaludMentalResponse> create(
      @Valid @RequestBody SaludMentalRequest request) {
    var response = service.create(request);
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @Operation(
      summary = "Actualizar recurso de salud mental",
      description = "Requiere permiso EDIT_HEALTH",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_EDIT_HEALTH')")
  @PutMapping("/{id}")
  public ResponseEntity<SaludMentalResponse> update(
      @PathVariable UUID id, @Valid @RequestBody SaludMentalUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  @Operation(
      summary = "Eliminar recurso de salud mental",
      description = "Requiere permiso EDIT_HEALTH",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_EDIT_HEALTH')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
