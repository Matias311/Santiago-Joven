package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.TuContribucionCuentaRequest;
import com.santiago.joven.backend.dto.TuContribucionCuentaResponse;
import com.santiago.joven.backend.dto.TuContribucionCuentaUpdate;
import com.santiago.joven.backend.service.TuContribucionCuentaService;
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
@RequestMapping("/api/v1/tu-contribucion-cuenta")
@RequiredArgsConstructor
@Tag(name = "Tu Contribucion Cuenta", description = "Gestion de contribuciones")
public class TuContribucionCuentaController {

  private final TuContribucionCuentaService service;

  @Operation(summary = "Listar contribuciones", description = "Publico")
  @GetMapping("")
  public ResponseEntity<List<TuContribucionCuentaResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @Operation(summary = "Buscar contribucion por ID", description = "Publico")
  @GetMapping("/{id}")
  public ResponseEntity<TuContribucionCuentaResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @Operation(
      summary = "Crear contribucion",
      description = "Requiere permiso MANAGE_USERS",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_USERS')")
  @PostMapping("")
  public ResponseEntity<TuContribucionCuentaResponse> create(
      @Valid @RequestBody TuContribucionCuentaRequest request) {
    var response = service.create(request);
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @Operation(
      summary = "Actualizar contribucion",
      description = "Requiere permiso MANAGE_USERS",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_USERS')")
  @PutMapping("/{id}")
  public ResponseEntity<TuContribucionCuentaResponse> update(
      @PathVariable UUID id, @Valid @RequestBody TuContribucionCuentaUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  @Operation(
      summary = "Eliminar contribucion",
      description = "Requiere permiso MANAGE_USERS",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_USERS')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Buscar contribucion activa", description = "Publico")
  @GetMapping("/activo")
  public ResponseEntity<TuContribucionCuentaResponse> findActivo() {
    return ResponseEntity.ok(service.findActivo());
  }
}
