package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.CategoriaRequest;
import com.santiago.joven.backend.dto.CategoriaResponse;
import com.santiago.joven.backend.dto.CategoriaUpdate;
import com.santiago.joven.backend.model.enums.TipoCategoria;
import com.santiago.joven.backend.service.CategoriaService;
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

/** REST controller para {@link com.santiago.joven.backend.model.entity.Categoria}. */
@RestController
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
public class CategoriaController {

  private final CategoriaService service;

  /** Lista todos los registros. */
  @GetMapping("")
  public ResponseEntity<List<CategoriaResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  /** Busca por ID. */
  @GetMapping("/{id}")
  public ResponseEntity<CategoriaResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  /** Crea un nuevo registro. */
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

  /** Actualiza un registro existente. */
  @PutMapping("/{id}")
  public ResponseEntity<CategoriaResponse> update(
      @PathVariable UUID id, @Valid @RequestBody CategoriaUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  /** Elimina un registro por ID. */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  /** Busca por nombre exacto. */
  @GetMapping("/por-nombre/{nombre}")
  public ResponseEntity<CategoriaResponse> findByNombre(@PathVariable String nombre) {
    return ResponseEntity.ok(service.findByNombre(nombre));
  }

  /** Busca por tipo. */
  @GetMapping("/por-tipo/{tipo}")
  public ResponseEntity<List<CategoriaResponse>> findByTipo(@PathVariable TipoCategoria tipo) {
    return ResponseEntity.ok(service.findByTipo(tipo));
  }
}
