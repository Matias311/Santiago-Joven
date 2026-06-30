package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.GaleriaFotoRequest;
import com.santiago.joven.backend.dto.GaleriaFotoResponse;
import com.santiago.joven.backend.dto.GaleriaFotoUpdate;
import com.santiago.joven.backend.service.GaleriaFotoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/api/v1/galeria-fotos")
@RequiredArgsConstructor
@Tag(name = "Galeria de Fotos", description = "Gestion de galeria de fotos")
public class GaleriaFotoController {

  private final GaleriaFotoService service;

  @Operation(summary = "Listar fotos", description = "Publico")
  @GetMapping("")
  public ResponseEntity<List<GaleriaFotoResponse>> findAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @Operation(summary = "Buscar foto por ID", description = "Publico")
  @GetMapping("/{id}")
  public ResponseEntity<GaleriaFotoResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(service.findById(id));
  }

  @Operation(
      summary = "Crear foto",
      description = "Requiere permiso MANAGE_GALLERY. actividadId debe referirse a una ActividadTaller existente.",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Foto creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "404", description = "ActividadTaller no encontrada"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
      })
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_GALLERY')")
  @PostMapping("")
  public ResponseEntity<GaleriaFotoResponse> create(
      @Valid @RequestBody GaleriaFotoRequest request) {
    var response = service.create(request);
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @Operation(
      summary = "Actualizar foto",
      description = "Requiere permiso MANAGE_GALLERY",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_GALLERY')")
  @PutMapping("/{id}")
  public ResponseEntity<GaleriaFotoResponse> update(
      @PathVariable UUID id, @Valid @RequestBody GaleriaFotoUpdate update) {
    return ResponseEntity.ok(service.update(id, update));
  }

  @Operation(
      summary = "Eliminar foto",
      description = "Requiere permiso MANAGE_GALLERY",
      security = {@SecurityRequirement(name = "bearerAuth")})
  @PreAuthorize("hasAuthority('PERMISSION_MANAGE_GALLERY')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Buscar fotos por actividad", description = "Publico")
  @GetMapping("/por-actividad/{actividadId}")
  public ResponseEntity<List<GaleriaFotoResponse>> findByActividadId(
      @PathVariable UUID actividadId) {
    return ResponseEntity.ok(service.findByActividadId(actividadId));
  }
}
