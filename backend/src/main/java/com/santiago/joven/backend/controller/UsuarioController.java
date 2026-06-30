package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.AsignarRolesRequest;
import com.santiago.joven.backend.dto.UsuarioRequest;
import com.santiago.joven.backend.dto.UsuarioResponse;
import com.santiago.joven.backend.dto.UsuarioUpdate;
import com.santiago.joven.backend.security.UsuarioSecurity;
import com.santiago.joven.backend.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@Tag(
    name = "Usuarios",
    description = "Gestion de usuarios: administradores solo lectura, moderadores gestionan permisos, usuarios acceden a sus propios datos")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

  private final UsuarioService service;
  private final UsuarioSecurity usuarioSecurity;

  @Operation(
      summary = "Listar usuarios",
      description = "Requiere rol ADMIN o MODERATOR (solo lectura)")
  @GetMapping("")
  public ResponseEntity<List<UsuarioResponse>> findAll() {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (!usuarioSecurity.canViewUsers(auth)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    return ResponseEntity.ok(service.findAll());
  }

  @Operation(
      summary = "Buscar usuario por ID",
      description = "Requiere rol ADMIN/MODERATOR, o que el ID corresponda al propio usuario")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "403", description = "No autorizado para consultar este usuario"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
      })
  @GetMapping("/{id}")
  public ResponseEntity<UsuarioResponse> findById(@PathVariable UUID id) {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (!usuarioSecurity.canViewUsers(auth) && !usuarioSecurity.isSelf(id, auth)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    return ResponseEntity.ok(service.findById(id));
  }

  @Operation(
      summary = "Buscar usuario por email",
      description = "Requiere rol ADMIN o MODERATOR (solo lectura)")
  @GetMapping("/por-email/{email}")
  public ResponseEntity<UsuarioResponse> findByEmail(@PathVariable String email) {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (!usuarioSecurity.canViewUsers(auth)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    return ResponseEntity.ok(service.findByEmail(email));
  }

  @Operation(
      summary = "Verificar si el email existe",
      description = "Requiere rol ADMIN o MODERATOR (solo lectura)")
  @GetMapping("/exists-email/{email}")
  public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (!usuarioSecurity.canViewUsers(auth)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    return ResponseEntity.ok(service.existsByEmail(email));
  }

  @Operation(
      summary = "Crear usuario",
      description = "Requiere rol MODERATOR")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "403", description = "No autorizado"),
        @ApiResponse(responseCode = "409", description = "Email duplicado")
      })
  @PostMapping("")
  public ResponseEntity<UsuarioResponse> create(@Valid @RequestBody UsuarioRequest request) {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (!usuarioSecurity.canManageRoles(auth)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    var response = service.create(request);
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @Operation(
      summary = "Actualizar perfil propio",
      description =
          "Cada usuario puede actualizar sus propios datos (nombre, apellido, telefono, email, password). "
              + "El campo activo se ignora en actualizaciones propias. "
              + "Ni ADMIN ni MODERATOR pueden modificar datos de otros usuarios.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "403", description = "No autorizado para actualizar este usuario"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
        @ApiResponse(responseCode = "409", description = "Email duplicado u otro conflicto de datos")
      })
  @PutMapping("/{id}")
  public ResponseEntity<UsuarioResponse> update(
      @PathVariable UUID id, @Valid @RequestBody UsuarioUpdate update) {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (!usuarioSecurity.isSelf(id, auth)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    return ResponseEntity.ok(service.updateOwnProfile(id, update));
  }

  @Operation(
      summary = "Asignar roles a un usuario",
      description = "Requiere rol MODERATOR (gestion de permisos)")
  @PutMapping("/{id}/roles")
  public ResponseEntity<Void> asignarRoles(
      @PathVariable UUID id, @Valid @RequestBody AsignarRolesRequest request) {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (!usuarioSecurity.canManageRoles(auth)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    service.asignarRoles(id, request.rolIds());
    return ResponseEntity.noContent().build();
  }

  @Operation(
      summary = "Eliminar usuario",
      description = "Requiere rol MODERATOR")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (!usuarioSecurity.canManageRoles(auth)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
