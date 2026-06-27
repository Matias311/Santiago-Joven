package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.AuditoriaResponse;
import com.santiago.joven.backend.service.AuditoriaService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auditoria")
@RequiredArgsConstructor
/** REST controller para {@link com.santiago.joven.backend.model.entity.Auditoria}. */
public class AuditoriaController {

  private final AuditoriaService service;

  /** Lista todos los registros. */
  @GetMapping("")
  public ResponseEntity<List<AuditoriaResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  /** Busca por ID. */
  @GetMapping("/{id}")
  public ResponseEntity<AuditoriaResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  /** Busca por entidad y recurso. */
  @GetMapping("/por-entidad")
  public ResponseEntity<List<AuditoriaResponse>> findByEntidad(
      @RequestParam String entidadTipo, @RequestParam UUID entidadId) {
    return ResponseEntity.ok(service.findByEntidad(entidadTipo, entidadId));
  }

  /** Busca por usuario. */
  @GetMapping("/por-usuario/{usuarioId}")
  public ResponseEntity<List<AuditoriaResponse>> findByUsuario(@PathVariable UUID usuarioId) {
    return ResponseEntity.ok(service.findByUsuario(usuarioId));
  }
}
