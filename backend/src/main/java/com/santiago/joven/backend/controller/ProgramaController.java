package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.ProgramaRequest;
import com.santiago.joven.backend.dto.ProgramaResponse;
import com.santiago.joven.backend.dto.ProgramaUpdate;
import com.santiago.joven.backend.service.ProgramaService;
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
@RequestMapping("/api/v1/programas")
@RequiredArgsConstructor
@Tag(name = "Programas", description = "Gestion de programas")
public class ProgramaController {

  private final ProgramaService service;

  @Operation(summary = "Listar programas", description = "Publico")
  @GetMapping("")
  public ResponseEntity<List<ProgramaResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @Operation(summary = "Buscar programa por ID", description = "Publico")
  @GetMapping("/{id}")
  public ResponseEntity<ProgramaResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @Operation(
      summary = "Crear programa",
      description = "Requiere permiso CREATE_PROGRAM",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_CREATE_PROGRAM')")
  @PostMapping("")
  public ResponseEntity<ProgramaResponse> create(@Valid @RequestBody ProgramaRequest request) {
    var response = service.create(request);
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @Operation(
      summary = "Actualizar programa",
      description = "Requiere permiso EDIT_PROGRAM",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_EDIT_PROGRAM')")
  @PutMapping("/{id}")
  public ResponseEntity<ProgramaResponse> update(
      @PathVariable UUID id, @Valid @RequestBody ProgramaUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  @Operation(
      summary = "Eliminar programa",
      description = "Requiere permiso DELETE_PROGRAM",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_DELETE_PROGRAM')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Listar programas activos", description = "Publico")
  @GetMapping("/activos")
  public ResponseEntity<List<ProgramaResponse>> findActivos() {
    return ResponseEntity.ok(service.findActivos());
  }
}
