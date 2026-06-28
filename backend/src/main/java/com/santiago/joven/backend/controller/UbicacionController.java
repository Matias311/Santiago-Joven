package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.UbicacionRequest;
import com.santiago.joven.backend.dto.UbicacionResponse;
import com.santiago.joven.backend.dto.UbicacionUpdate;
import com.santiago.joven.backend.service.UbicacionService;
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

/** REST controller para {@link Ubicacion}. */
@RestController
@RequestMapping("/api/v1/ubicaciones")
@RequiredArgsConstructor
public class UbicacionController {

  private final UbicacionService service;

  /** Lista todos los registros. */
  @GetMapping("")
  public ResponseEntity<List<UbicacionResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  /** Busca por ID. */
  @GetMapping("/{id}")
  public ResponseEntity<UbicacionResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  /** Busca por ciudad. */
  @GetMapping("/por-ciudad/{ciudad}")
  public ResponseEntity<List<UbicacionResponse>> findByCiudad(@PathVariable String ciudad) {
    return ResponseEntity.ok(service.findByCiudad(ciudad));
  }

  /** Crea un nuevo registro. */
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

  /** Actualiza un registro existente. */
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_LOCATIONS')")
  @PutMapping("/{id}")
  public ResponseEntity<UbicacionResponse> update(
      @PathVariable UUID id, @Valid @RequestBody UbicacionUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  /** Elimina un registro por ID. */
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_LOCATIONS')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
