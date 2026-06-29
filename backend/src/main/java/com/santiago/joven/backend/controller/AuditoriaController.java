package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.AuditoriaResponse;
import com.santiago.joven.backend.service.AuditoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auditoria")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('PERMISSION_VIEW_ANALYTICS')")
@Tag(name = "Auditoria", description = "Registros de auditoria (solo lectura)")
@SecurityRequirement(name = "bearerAuth")
public class AuditoriaController {

  private final AuditoriaService service;

  @Operation(summary = "Listar registros de auditoria")
  @GetMapping("")
  public ResponseEntity<List<AuditoriaResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @Operation(summary = "Buscar registro de auditoria por ID")
  @GetMapping("/{id}")
  public ResponseEntity<AuditoriaResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @Operation(summary = "Buscar por entidad")
  @GetMapping("/por-entidad")
  public ResponseEntity<List<AuditoriaResponse>> findByEntidad(
      @RequestParam String entidadTipo, @RequestParam UUID entidadId) {
    return ResponseEntity.ok(service.findByEntidad(entidadTipo, entidadId));
  }

  @Operation(summary = "Buscar por usuario")
  @GetMapping("/por-usuario/{usuarioId}")
  public ResponseEntity<List<AuditoriaResponse>> findByUsuario(@PathVariable UUID usuarioId) {
    return ResponseEntity.ok(service.findByUsuario(usuarioId));
  }
}
