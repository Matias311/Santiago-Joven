package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.AccionJovenRequest;
import com.santiago.joven.backend.dto.AccionJovenResponse;
import com.santiago.joven.backend.dto.AccionJovenUpdate;
import com.santiago.joven.backend.service.AccionJovenService;
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

/** REST controller para {@link com.santiago.joven.backend.model.entity.AccionJoven}. */
@RestController
@RequestMapping("/api/v1/acciones-joven")
@RequiredArgsConstructor
public class AccionJovenController {

  private final AccionJovenService service;

  /** Lista todos los registros. */
  @GetMapping("")
  public ResponseEntity<List<AccionJovenResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  /** Busca por ID. */
  @GetMapping("/{id}")
  public ResponseEntity<AccionJovenResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  /** Crea un nuevo registro. */
  @PostMapping("")
  public ResponseEntity<AccionJovenResponse> create(
      @Valid @RequestBody AccionJovenRequest request) {
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
  public ResponseEntity<AccionJovenResponse> update(
      @PathVariable UUID id, @Valid @RequestBody AccionJovenUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  /** Elimina un registro por ID. */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  /** Lista las entidades activas. */
  @GetMapping("/activas")
  public ResponseEntity<List<AccionJovenResponse>> findActivas() {
    return ResponseEntity.ok(service.findActivas());
  }
}
