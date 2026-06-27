package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.EstadisticaRequest;
import com.santiago.joven.backend.dto.EstadisticaResponse;
import com.santiago.joven.backend.dto.EstadisticaUpdate;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import com.santiago.joven.backend.service.EstadisticaService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** REST controller para {@link Estadistica}. */
@RestController
@RequestMapping("/api/v1/estadisticas")
@RequiredArgsConstructor
public class EstadisticaController {

  private final EstadisticaService service;

  /** Lista todos los registros. */
  @GetMapping("")
  public ResponseEntity<List<EstadisticaResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  /** Busca por ID. */
  @GetMapping("/{id}")
  public ResponseEntity<EstadisticaResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  /** Crea un nuevo registro. */
  @PostMapping("")
  public ResponseEntity<EstadisticaResponse> create(
      @Valid @RequestBody EstadisticaRequest request) {
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
  public ResponseEntity<EstadisticaResponse> update(
      @PathVariable UUID id, @Valid @RequestBody EstadisticaUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  /** Elimina un registro por ID. */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  /** Busca por recurso. */
  @GetMapping("/por-recurso")
  public ResponseEntity<EstadisticaResponse> findByRecurso(
      @RequestParam UUID recursoId, @RequestParam TipoRecurso tipoRecurso) {
    return ResponseEntity.ok(service.findByRecurso(recursoId, tipoRecurso));
  }
}
