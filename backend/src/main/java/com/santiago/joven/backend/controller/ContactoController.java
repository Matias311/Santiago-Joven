package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.ContactoRequest;
import com.santiago.joven.backend.dto.ContactoResponse;
import com.santiago.joven.backend.dto.ContactoUpdate;
import com.santiago.joven.backend.service.ContactoService;
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

/** REST controller para {@link com.santiago.joven.backend.model.entity.Contacto}. */
@RestController
@RequestMapping("/api/v1/contactos")
@RequiredArgsConstructor
public class ContactoController {

  private final ContactoService service;

  /** Lista todos los registros. */
  @GetMapping("")
  public ResponseEntity<List<ContactoResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  /** Busca por ID. */
  @GetMapping("/{id}")
  public ResponseEntity<ContactoResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  /** Crea un nuevo registro. */
  @PostMapping("")
  public ResponseEntity<ContactoResponse> create(@Valid @RequestBody ContactoRequest request) {
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
  public ResponseEntity<ContactoResponse> update(
      @PathVariable UUID id, @Valid @RequestBody ContactoUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  /** Elimina un registro por ID. */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  /** Lista los registros no respondidos. */
  @GetMapping("/no-respondidos")
  public ResponseEntity<List<ContactoResponse>> findNoRespondidos() {
    return ResponseEntity.ok(service.findNoRespondidos());
  }

  /** Busca por email. */
  @GetMapping("/por-email/{email}")
  public ResponseEntity<List<ContactoResponse>> findByEmail(@PathVariable String email) {
    return ResponseEntity.ok(service.findByEmail(email));
  }
}
