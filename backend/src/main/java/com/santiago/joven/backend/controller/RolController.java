package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.RolRequest;
import com.santiago.joven.backend.dto.RolResponse;
import com.santiago.joven.backend.dto.RolUpdate;
import com.santiago.joven.backend.model.enums.NombreRol;
import com.santiago.joven.backend.service.RolService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.security.access.prepost.PreAuthorize;

/** REST controller para {@link Rol}. */
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('PERMISSION_MANAGE_ROLES')")
public class RolController {

  private final RolService service;

  /** Lista todos los registros. */
  @GetMapping("")
  public ResponseEntity<List<RolResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  /** Busca por ID. */
  @GetMapping("/{id}")
  public ResponseEntity<RolResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  /** Busca por nombre. */
  @GetMapping("/por-nombre/{nombre}")
  public ResponseEntity<RolResponse> findByNombre(@PathVariable NombreRol nombre) {
    return ResponseEntity.ok(service.findByNombre(nombre));
  }

  /** Crea un nuevo registro. */
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

  /** Actualiza un registro existente. */
  @PutMapping("/{id}")
  public ResponseEntity<RolResponse> update(
      @PathVariable UUID id, @Valid @RequestBody RolUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  /** Elimina un registro por ID. */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
