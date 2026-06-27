package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.TuContribucionCuentaRequest;
import com.santiago.joven.backend.dto.TuContribucionCuentaResponse;
import com.santiago.joven.backend.dto.TuContribucionCuentaUpdate;
import com.santiago.joven.backend.service.TuContribucionCuentaService;
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

/** REST controller para {@link TuContribucionCuenta}. */
@RestController
@RequestMapping("/api/v1/tu-contribucion-cuenta")
@RequiredArgsConstructor
public class TuContribucionCuentaController {

  private final TuContribucionCuentaService service;

  /** Lista todos los registros. */
  @GetMapping("")
  public ResponseEntity<List<TuContribucionCuentaResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  /** Busca por ID. */
  @GetMapping("/{id}")
  public ResponseEntity<TuContribucionCuentaResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  /** Busca el registro activo. */
  @GetMapping("/activo")
  public ResponseEntity<TuContribucionCuentaResponse> findActivo() {
    return ResponseEntity.ok(service.findActivo());
  }

  /** Crea un nuevo registro. */
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

  /** Actualiza un registro existente. */
  @PutMapping("/{id}")
  public ResponseEntity<TuContribucionCuentaResponse> update(
      @PathVariable UUID id, @Valid @RequestBody TuContribucionCuentaUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  /** Elimina un registro por ID. */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
