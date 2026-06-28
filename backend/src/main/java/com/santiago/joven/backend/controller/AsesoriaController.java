package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.AsesoriaRequest;
import com.santiago.joven.backend.dto.AsesoriaResponse;
import com.santiago.joven.backend.dto.AsesoriaUpdate;
import com.santiago.joven.backend.service.AsesoriaService;
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

/** REST controller para {@link com.santiago.joven.backend.model.entity.Asesoria}. */
@RestController
@RequestMapping("/api/v1/asesorias")
@RequiredArgsConstructor
public class AsesoriaController {

  private final AsesoriaService service;

  /** Lista todos los registros. */
  @GetMapping("")
  public ResponseEntity<List<AsesoriaResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  /** Busca por ID. */
  @GetMapping("/{id}")
  public ResponseEntity<AsesoriaResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  /** Crea un nuevo registro. */
  @PreAuthorize("hasAuthority('PERMISSION_CREATE_ASESORIA')")
  @PostMapping("")
  public ResponseEntity<AsesoriaResponse> create(@Valid @RequestBody AsesoriaRequest request) {
    var response = service.create(request);
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  /** Actualiza un registro existente. */
  @PreAuthorize("hasAuthority('PERMISSION_EDIT_ASESORIA')")
  @PutMapping("/{id}")
  public ResponseEntity<AsesoriaResponse> update(
      @PathVariable UUID id, @Valid @RequestBody AsesoriaUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  /** Elimina un registro por ID. */
  @PreAuthorize("hasAuthority('PERMISSION_DELETE_ASESORIA')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  /** Busca por categoria. */
  @GetMapping("/por-categoria/{categoriaId}")
  public ResponseEntity<List<AsesoriaResponse>> findByCategoriaId(@PathVariable UUID categoriaId) {
    return ResponseEntity.ok(service.findByCategoriaId(categoriaId));
  }

  /** Lista las entidades activas. */
  @GetMapping("/activas")
  public ResponseEntity<List<AsesoriaResponse>> findActivas() {
    return ResponseEntity.ok(service.findActivas());
  }
}
