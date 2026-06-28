package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.RolRequest;
import com.santiago.joven.backend.dto.RolResponse;
import com.santiago.joven.backend.dto.RolUpdate;
import com.santiago.joven.backend.model.enums.NombreRol;
import com.santiago.joven.backend.service.RolService;
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
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('PERMISSION_MANAGE_ROLES')")
@Tag(name = "Roles", description = "Gestion de roles (solo admin)")
@SecurityRequirement(name = "bearerAuth")
public class RolController {

  private final RolService service;

  @Operation(summary = "Listar roles")
  @GetMapping("")
  public ResponseEntity<List<RolResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @Operation(summary = "Buscar rol por ID")
  @GetMapping("/{id}")
  public ResponseEntity<RolResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @Operation(summary = "Buscar rol por nombre")
  @GetMapping("/por-nombre/{nombre}")
  public ResponseEntity<RolResponse> findByNombre(@PathVariable NombreRol nombre) {
    return ResponseEntity.ok(service.findByNombre(nombre));
  }

  @Operation(summary = "Crear rol")
  @PostMapping("")
  public ResponseEntity<RolResponse> create(@Valid @RequestBody RolRequest request) {
    var response = service.create(request);
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @Operation(summary = "Actualizar rol")
  @PutMapping("/{id}")
  public ResponseEntity<RolResponse> update(
      @PathVariable UUID id, @Valid @RequestBody RolUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  @Operation(summary = "Eliminar rol")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
