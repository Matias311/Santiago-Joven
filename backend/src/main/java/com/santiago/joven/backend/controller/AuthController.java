package com.santiago.joven.backend.controller;

import com.santiago.joven.backend.dto.LoginRequest;
import com.santiago.joven.backend.dto.LoginResponse;
import com.santiago.joven.backend.dto.UsuarioRequest;
import com.santiago.joven.backend.model.enums.NombreRol;
import com.santiago.joven.backend.repository.RolRepository;
import com.santiago.joven.backend.security.JwtTokenProvider;
import com.santiago.joven.backend.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Controlador de autenticacion (login y registro). */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticacion", description = "Endpoints publicos de login y registro")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final UsuarioService usuarioService;
  private final RolRepository rolRepository;
  private final PasswordEncoder passwordEncoder;

  @Operation(summary = "Iniciar sesion", description = "Autentica al usuario y devuelve un token JWT")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Login exitoso, token generado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "401", description = "Credenciales incorrectas")
      })
  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.email(), request.password()));

    var usuario = usuarioService.findByEmail(request.email());

    var roles =
        usuarioService.obtenerRoles(usuario.id()).stream()
            .map(Enum::name)
            .toList();

    var token = jwtTokenProvider.generateToken(usuario.id(), usuario.email(), roles);

    return ResponseEntity.ok(new LoginResponse(token, usuario.id(), usuario.email(), roles));
  }

  @Operation(summary = "Registrar usuario", description = "Crea un nuevo usuario con rol USER y devuelve un token JWT")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Usuario creado, token generado"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos"),
        @ApiResponse(responseCode = "409", description = "El email ya existe")
      })
  @PostMapping("/register")
  public ResponseEntity<LoginResponse> register(@Valid @RequestBody UsuarioRequest request) {
    if (usuarioService.existsByEmail(request.email())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    var encodedPassword = passwordEncoder.encode(request.password());
    var usuarioConPassword =
        UsuarioRequest.builder()
            .email(request.email())
            .password(encodedPassword)
            .nombre(request.nombre())
            .apellido(request.apellido())
            .build();

    var usuario = usuarioService.create(usuarioConPassword);

    var rolUser =
        rolRepository
            .findByNombre(NombreRol.USER)
            .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));
    usuarioService.asignarRoles(usuario.id(), Set.of(rolUser.getId()));

    var roles = java.util.List.of("USER");
    var token = jwtTokenProvider.generateToken(usuario.id(), usuario.email(), roles);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new LoginResponse(token, usuario.id(), usuario.email(), roles));
  }
}
