package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.CategoriaRequest;
import com.santiago.joven.backend.dto.CategoriaResponse;
import com.santiago.joven.backend.dto.CategoriaUpdate;
import com.santiago.joven.backend.model.enums.TipoCategoria;
import com.santiago.joven.backend.service.CategoriaService;
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
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorias", description = "Gestion de categorias")
public class CategoriaController {

  private final CategoriaService service;

  @Operation(summary = "Listar categorias", description = "Publico")
  @GetMapping("")
  public ResponseEntity<List<CategoriaResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @Operation(summary = "Buscar categoria por ID", description = "Publico")
  @GetMapping("/{id}")
  public ResponseEntity<CategoriaResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @Operation(
      summary = "Crear categoria",
      description = "Requiere permiso MANAGE_GALLERY",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_GALLERY')")
  @PostMapping("")
  public ResponseEntity<CategoriaResponse> create(@Valid @RequestBody CategoriaRequest request) {
    var response = service.create(request);
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @Operation(
      summary = "Actualizar categoria",
      description = "Requiere permiso MANAGE_GALLERY",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_GALLERY')")
  @PutMapping("/{id}")
  public ResponseEntity<CategoriaResponse> update(
      @PathVariable UUID id, @Valid @RequestBody CategoriaUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  @Operation(
      summary = "Eliminar categoria",
      description = "Requiere permiso MANAGE_GALLERY",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_GALLERY')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Buscar categoria por nombre", description = "Publico")
  @GetMapping("/por-nombre/{nombre}")
  public ResponseEntity<CategoriaResponse> findByNombre(@PathVariable String nombre) {
    return ResponseEntity.ok(service.findByNombre(nombre));
  }

  @Operation(summary = "Buscar categorias por tipo", description = "Publico")
  @GetMapping("/por-tipo/{tipo}")
  public ResponseEntity<List<CategoriaResponse>> findByTipo(@PathVariable TipoCategoria tipo) {
    return ResponseEntity.ok(service.findByTipo(tipo));
  }
}
