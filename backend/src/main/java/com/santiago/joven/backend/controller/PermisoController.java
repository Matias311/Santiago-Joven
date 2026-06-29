package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.PermisoRequest;
import com.santiago.joven.backend.dto.PermisoResponse;
import com.santiago.joven.backend.dto.PermisoUpdate;
import com.santiago.joven.backend.service.PermisoService;
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
@RequestMapping("/api/v1/permisos")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('PERMISSION_MANAGE_ROLES')")
@Tag(name = "Permisos", description = "Gestion de permisos (requiere PERMISSION_MANAGE_ROLES)")
@SecurityRequirement(name = "bearerAuth")
public class PermisoController {

  private final PermisoService service;

  @Operation(summary = "Listar permisos", description = "Requiere PERMISSION_MANAGE_ROLES")
  @GetMapping("")
  public ResponseEntity<List<PermisoResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @Operation(summary = "Buscar permiso por ID", description = "Requiere PERMISSION_MANAGE_ROLES")
  @GetMapping("/{id}")
  public ResponseEntity<PermisoResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @Operation(summary = "Crear permiso", description = "Requiere PERMISSION_MANAGE_ROLES")
  @PostMapping("")
  public ResponseEntity<PermisoResponse> create(@Valid @RequestBody PermisoRequest request) {
    var response = service.create(request);
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @Operation(summary = "Actualizar permiso", description = "Requiere PERMISSION_MANAGE_ROLES")
  @PutMapping("/{id}")
  public ResponseEntity<PermisoResponse> update(
      @PathVariable UUID id, @Valid @RequestBody PermisoUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  @Operation(summary = "Eliminar permiso", description = "Requiere PERMISSION_MANAGE_ROLES")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Buscar permiso por nombre", description = "Requiere PERMISSION_MANAGE_ROLES")
  @GetMapping("/por-nombre/{nombre}")
  public ResponseEntity<PermisoResponse> findByNombre(@PathVariable String nombre) {
    return ResponseEntity.ok(service.findByNombre(nombre));
  }

  @Operation(summary = "Buscar permisos por modulo", description = "Requiere PERMISSION_MANAGE_ROLES")
  @GetMapping("/por-modulo/{modulo}")
  public ResponseEntity<List<PermisoResponse>> findByModulo(@PathVariable String modulo) {
    return ResponseEntity.ok(service.findByModulo(modulo));
  }
}
