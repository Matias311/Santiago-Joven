package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.ContactoRequest;
import com.santiago.joven.backend.dto.ContactoResponse;
import com.santiago.joven.backend.dto.ContactoUpdate;
import com.santiago.joven.backend.service.ContactoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

/** REST controller para {@link com.santiago.joven.backend.model.entity.Contacto}. */
@RestController
@RequestMapping("/api/v1/contactos")
@RequiredArgsConstructor
@Tag(name = "Contactos", description = "Gestion de mensajes de contacto")
public class ContactoController {

  private final ContactoService service;

  /** Lista todos los registros. */
  @Operation(
      summary = "Listar contactos",
      description = "Requiere permiso MANAGE_USERS",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @GetMapping("")
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_USERS')")
  public ResponseEntity<List<ContactoResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  /** Busca por ID. */
  @Operation(
      summary = "Buscar contacto por ID",
      description = "Requiere permiso MANAGE_USERS",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_USERS')")
  public ResponseEntity<ContactoResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  /** Crea un nuevo registro. (publico, sin autenticacion) */
  @Operation(summary = "Crear contacto", description = "Endpoint publico, no requiere token")
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
  @Operation(
      summary = "Actualizar contacto",
      description = "Requiere permiso MANAGE_USERS",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PutMapping("/{id}")
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_USERS')")
  public ResponseEntity<ContactoResponse> update(
      @PathVariable UUID id, @Valid @RequestBody ContactoUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  /** Elimina un registro por ID. */
  @Operation(
      summary = "Eliminar contacto",
      description = "Requiere permiso MANAGE_USERS",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_USERS')")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  /** Lista los registros no respondidos. */
  @Operation(
      summary = "Listar contactos no respondidos",
      description = "Requiere permiso MANAGE_USERS",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @GetMapping("/no-respondidos")
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_USERS')")
  public ResponseEntity<List<ContactoResponse>> findNoRespondidos() {
    return ResponseEntity.ok(service.findNoRespondidos());
  }

  /** Busca por email. */
  @Operation(
      summary = "Buscar contacto por email",
      description = "Requiere permiso MANAGE_USERS",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @GetMapping("/por-email/{email}")
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_USERS')")
  public ResponseEntity<List<ContactoResponse>> findByEmail(@PathVariable String email) {
    return ResponseEntity.ok(service.findByEmail(email));
  }
}
