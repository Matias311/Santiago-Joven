package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.GaleriaFotoRequest;
import com.santiago.joven.backend.dto.GaleriaFotoResponse;
import com.santiago.joven.backend.dto.GaleriaFotoUpdate;
import com.santiago.joven.backend.service.GaleriaFotoService;
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

/** REST controller para {@link GaleriaFoto}. */
@RestController
@RequestMapping("/api/v1/galeria-fotos")
@RequiredArgsConstructor
public class GaleriaFotoController {

  private final GaleriaFotoService service;

  /** Lista todos los registros. */
  @GetMapping("")
  public ResponseEntity<List<GaleriaFotoResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  /** Busca por ID. */
  @GetMapping("/{id}")
  public ResponseEntity<GaleriaFotoResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  /** Crea un nuevo registro. */
  @PostMapping("")
  public ResponseEntity<GaleriaFotoResponse> create(
      @Valid @RequestBody GaleriaFotoRequest request) {
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
  public ResponseEntity<GaleriaFotoResponse> update(
      @PathVariable UUID id, @Valid @RequestBody GaleriaFotoUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  /** Elimina un registro por ID. */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  /** Busca por actividad. */
  @GetMapping("/por-actividad/{actividadId}")
  public ResponseEntity<List<GaleriaFotoResponse>> findByActividadId(
      @PathVariable UUID actividadId) {
    return ResponseEntity.ok(service.findByActividadId(actividadId));
  }
}
