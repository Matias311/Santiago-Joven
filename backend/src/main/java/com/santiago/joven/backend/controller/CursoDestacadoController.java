package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.CursoDestacadoRequest;
import com.santiago.joven.backend.dto.CursoDestacadoResponse;
import com.santiago.joven.backend.dto.CursoDestacadoUpdate;
import com.santiago.joven.backend.service.CursoDestacadoService;
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

/** REST controller para {@link CursoDestacado}. */
@RestController
@RequestMapping("/api/v1/cursos-destacados")
@RequiredArgsConstructor
public class CursoDestacadoController {

  private final CursoDestacadoService service;

  /** Lista todos los registros. */
  @GetMapping("")
  public ResponseEntity<List<CursoDestacadoResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  /** Busca por ID. */
  @GetMapping("/{id}")
  public ResponseEntity<CursoDestacadoResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  /** Crea un nuevo registro. */
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

  /** Actualiza un registro existente. */
  @PutMapping("/{id}")
  public ResponseEntity<CursoDestacadoResponse> update(
      @PathVariable UUID id, @Valid @RequestBody CursoDestacadoUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  /** Elimina un registro por ID. */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  /** Busca por categoria. */
  @GetMapping("/por-categoria/{categoriaId}")
  public ResponseEntity<List<CursoDestacadoResponse>> findByCategoriaId(
      @PathVariable UUID categoriaId) {
    return ResponseEntity.ok(service.findByCategoriaId(categoriaId));
  }

  /** Lista los registros activos. */
  @GetMapping("/activos")
  public ResponseEntity<List<CursoDestacadoResponse>> findActivos() {
    return ResponseEntity.ok(service.findActivos());
  }
}
