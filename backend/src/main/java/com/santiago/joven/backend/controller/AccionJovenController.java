package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.AccionJovenRequest;
import com.santiago.joven.backend.dto.AccionJovenResponse;
import com.santiago.joven.backend.dto.AccionJovenUpdate;
import com.santiago.joven.backend.service.AccionJovenService;
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
@RequestMapping("/api/v1/acciones-joven")
@RequiredArgsConstructor
@Tag(name = "Accion Joven", description = "Gestion de acciones joven")
public class AccionJovenController {

  private final AccionJovenService service;

  @Operation(summary = "Listar acciones joven", description = "Publico")
  @GetMapping("")
  public ResponseEntity<List<AccionJovenResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @Operation(summary = "Buscar accion joven por ID", description = "Publico")
  @GetMapping("/{id}")
  public ResponseEntity<AccionJovenResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @Operation(
      summary = "Crear accion joven",
      description = "Requiere permiso CREATE_PROGRAM",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_CREATE_PROGRAM')")
  @PostMapping("")
  public ResponseEntity<AccionJovenResponse> create(
      @Valid @RequestBody AccionJovenRequest request) {
    var response = service.create(request);
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @Operation(
      summary = "Actualizar accion joven",
      description = "Requiere permiso EDIT_PROGRAM",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_EDIT_PROGRAM')")
  @PutMapping("/{id}")
  public ResponseEntity<AccionJovenResponse> update(
      @PathVariable UUID id, @Valid @RequestBody AccionJovenUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  @Operation(
      summary = "Eliminar accion joven",
      description = "Requiere permiso DELETE_PROGRAM",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_DELETE_PROGRAM')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Listar acciones joven activas", description = "Publico")
  @GetMapping("/activas")
  public ResponseEntity<List<AccionJovenResponse>> findActivas() {
    return ResponseEntity.ok(service.findActivas());
  }
}
