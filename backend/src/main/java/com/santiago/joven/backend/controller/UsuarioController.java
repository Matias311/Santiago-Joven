package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.UsuarioRequest;
import com.santiago.joven.backend.dto.UsuarioResponse;
import com.santiago.joven.backend.dto.UsuarioUpdate;
import com.santiago.joven.backend.service.UsuarioService;
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

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('PERMISSION_MANAGE_USERS')")
@Tag(name = "Usuarios", description = "Gestion de usuarios (solo admin)")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

  private final UsuarioService service;

  @Operation(summary = "Listar usuarios")
  @GetMapping("")
  public ResponseEntity<List<UsuarioResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @Operation(summary = "Buscar usuario por ID")
  @GetMapping("/{id}")
  public ResponseEntity<UsuarioResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @Operation(summary = "Buscar usuario por email")
  @GetMapping("/por-email/{email}")
  public ResponseEntity<UsuarioResponse> findByEmail(@PathVariable String email) {
    return ResponseEntity.ok(service.findByEmail(email));
  }

  @Operation(summary = "Verificar si el email existe")
  @GetMapping("/exists-email/{email}")
  public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
    return ResponseEntity.ok(service.existsByEmail(email));
  }

  @Operation(summary = "Crear usuario")
  @PostMapping("")
  public ResponseEntity<UsuarioResponse> create(@Valid @RequestBody UsuarioRequest request) {
    var response = service.create(request);
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @Operation(summary = "Actualizar usuario")
  @PutMapping("/{id}")
  public ResponseEntity<UsuarioResponse> update(
      @PathVariable UUID id, @Valid @RequestBody UsuarioUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  @Operation(summary = "Eliminar usuario")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
