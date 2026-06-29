package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.UbicacionRequest;
import com.santiago.joven.backend.dto.UbicacionResponse;
import com.santiago.joven.backend.dto.UbicacionUpdate;
import com.santiago.joven.backend.service.UbicacionService;
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
@RequestMapping("/api/v1/ubicaciones")
@RequiredArgsConstructor
@Tag(name = "Ubicaciones", description = "Gestion de ubicaciones")
public class UbicacionController {

  private final UbicacionService service;

  @Operation(summary = "Listar ubicaciones", description = "Publico")
  @GetMapping("")
  public ResponseEntity<List<UbicacionResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @Operation(summary = "Buscar ubicacion por ID", description = "Publico")
  @GetMapping("/{id}")
  public ResponseEntity<UbicacionResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @Operation(
      summary = "Crear ubicacion",
      description = "Requiere permiso MANAGE_LOCATIONS",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_LOCATIONS')")
  @PostMapping("")
  public ResponseEntity<UbicacionResponse> create(@Valid @RequestBody UbicacionRequest request) {
    var response = service.create(request);
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @Operation(
      summary = "Actualizar ubicacion",
      description = "Requiere permiso MANAGE_LOCATIONS",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_LOCATIONS')")
  @PutMapping("/{id}")
  public ResponseEntity<UbicacionResponse> update(
      @PathVariable UUID id, @Valid @RequestBody UbicacionUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  @Operation(
      summary = "Eliminar ubicacion",
      description = "Requiere permiso MANAGE_LOCATIONS",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_LOCATIONS')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Buscar ubicaciones por ciudad", description = "Publico")
  @GetMapping("/por-ciudad/{ciudad}")
  public ResponseEntity<List<UbicacionResponse>> findByCiudad(@PathVariable String ciudad) {
    return ResponseEntity.ok(service.findByCiudad(ciudad));
  }
}
