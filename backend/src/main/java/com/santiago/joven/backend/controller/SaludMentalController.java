package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.SaludMentalRequest;
import com.santiago.joven.backend.dto.SaludMentalResponse;
import com.santiago.joven.backend.dto.SaludMentalUpdate;
import com.santiago.joven.backend.service.SaludMentalService;
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

/** REST controller para {@link SaludMental}. */
@RestController
@RequestMapping("/api/v1/salud-mental")
@RequiredArgsConstructor
public class SaludMentalController {

  private final SaludMentalService service;

  /** Lista todos los registros. */
  @GetMapping("")
  public ResponseEntity<List<SaludMentalResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  /** Busca por ID. */
  @GetMapping("/{id}")
  public ResponseEntity<SaludMentalResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  /** Lista ordenados. */
  @GetMapping("/ordenados")
  public ResponseEntity<List<SaludMentalResponse>> findAllOrdered() {
    return ResponseEntity.ok(service.findAllOrdered());
  }

  /** Crea un nuevo registro. */
  @PreAuthorize("hasAuthority('PERMISSION_EDIT_HEALTH')")
  @PostMapping("")
  public ResponseEntity<SaludMentalResponse> create(
      @Valid @RequestBody SaludMentalRequest request) {
    var response = service.create(request);
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  /** Actualiza un registro existente. */
  @PreAuthorize("hasAuthority('PERMISSION_EDIT_HEALTH')")
  @PutMapping("/{id}")
  public ResponseEntity<SaludMentalResponse> update(
      @PathVariable UUID id, @Valid @RequestBody SaludMentalUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  /** Elimina un registro por ID. */
  @PreAuthorize("hasAuthority('PERMISSION_EDIT_HEALTH')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
