package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.CursoDestacadoRequest;
import com.santiago.joven.backend.dto.CursoDestacadoResponse;
import com.santiago.joven.backend.dto.CursoDestacadoUpdate;
import com.santiago.joven.backend.service.CursoDestacadoService;
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
@RequestMapping("/api/v1/cursos-destacados")
@RequiredArgsConstructor
@Tag(name = "Cursos Destacados", description = "Gestion de cursos destacados")
public class CursoDestacadoController {

  private final CursoDestacadoService service;

  @Operation(summary = "Listar cursos destacados", description = "Publico")
  @GetMapping("")
  public ResponseEntity<List<CursoDestacadoResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @Operation(summary = "Buscar curso destacado por ID", description = "Publico")
  @GetMapping("/{id}")
  public ResponseEntity<CursoDestacadoResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @Operation(
      summary = "Crear curso destacado",
      description = "Requiere permiso CREATE_COURSE",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_CREATE_COURSE')")
  @PostMapping("")
  public ResponseEntity<CursoDestacadoResponse> create(
      @Valid @RequestBody CursoDestacadoRequest request) {
    var response = service.create(request);
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @Operation(
      summary = "Actualizar curso destacado",
      description = "Requiere permiso EDIT_COURSE",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_EDIT_COURSE')")
  @PutMapping("/{id}")
  public ResponseEntity<CursoDestacadoResponse> update(
      @PathVariable UUID id, @Valid @RequestBody CursoDestacadoUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  @Operation(
      summary = "Eliminar curso destacado",
      description = "Requiere permiso DELETE_COURSE",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_DELETE_COURSE')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Buscar cursos por categoria", description = "Publico")
  @GetMapping("/por-categoria/{categoriaId}")
  public ResponseEntity<List<CursoDestacadoResponse>> findByCategoriaId(
      @PathVariable UUID categoriaId) {
    return ResponseEntity.ok(service.findByCategoriaId(categoriaId));
  }

  @Operation(summary = "Listar cursos activos", description = "Publico")
  @GetMapping("/activos")
  public ResponseEntity<List<CursoDestacadoResponse>> findActivos() {
    return ResponseEntity.ok(service.findActivos());
  }
}
