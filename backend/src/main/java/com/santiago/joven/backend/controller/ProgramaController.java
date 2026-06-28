package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.ProgramaRequest;
import com.santiago.joven.backend.dto.ProgramaResponse;
import com.santiago.joven.backend.dto.ProgramaUpdate;
import com.santiago.joven.backend.service.ProgramaService;
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

/** REST controller para {@link Programa}. */
@RestController
@RequestMapping("/api/v1/programas")
@RequiredArgsConstructor
public class ProgramaController {

  private final ProgramaService service;

  /** Lista todos los registros. */
  @GetMapping("")
  public ResponseEntity<List<ProgramaResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  /** Busca por ID. */
  @GetMapping("/{id}")
  public ResponseEntity<ProgramaResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  /** Crea un nuevo registro. */
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

  /** Actualiza un registro existente. */
  @PreAuthorize("hasAuthority('PERMISSION_EDIT_PROGRAM')")
  @PutMapping("/{id}")
  public ResponseEntity<ProgramaResponse> update(
      @PathVariable UUID id, @Valid @RequestBody ProgramaUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  /** Elimina un registro por ID. */
  @PreAuthorize("hasAuthority('PERMISSION_DELETE_PROGRAM')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  /** Lista los registros activos. */
  @GetMapping("/activos")
  public ResponseEntity<List<ProgramaResponse>> findActivos() {
    return ResponseEntity.ok(service.findActivos());
  }
}
