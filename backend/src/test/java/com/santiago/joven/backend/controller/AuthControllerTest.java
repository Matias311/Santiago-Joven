package com.santiago.joven.backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.santiago.joven.backend.dto.LoginResponse;
import com.santiago.joven.backend.dto.UsuarioRequest;
import com.santiago.joven.backend.dto.UsuarioResponse;
import com.santiago.joven.backend.model.enums.NombreRol;
import com.santiago.joven.backend.repository.RolRepository;
import com.santiago.joven.backend.security.JwtTokenProvider;
import com.santiago.joven.backend.service.UsuarioService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private AuthenticationManager authenticationManager;
  @MockitoBean private JwtTokenProvider jwtTokenProvider;
  @MockitoBean private UsuarioService usuarioService;
  @MockitoBean private RolRepository rolRepository;
  @MockitoBean private PasswordEncoder passwordEncoder;
  @MockitoBean private com.santiago.joven.backend.security.CustomUserDetailsService userDetailsService;

  private final UUID userId = UUID.randomUUID();

  @Test
  void login_conCredencialesValidas_debeRetornar200() throws Exception {
    var usuarioResponse =
        UsuarioResponse.builder().id(userId).email("test@test.cl").build();
    when(usuarioService.findByEmail("test@test.cl")).thenReturn(usuarioResponse);
    when(usuarioService.obtenerRoles(userId)).thenReturn(List.of());
    when(jwtTokenProvider.generateToken(any(UUID.class), any(String.class), any(List.class)))
        .thenReturn("token123");

    mockMvc
        .perform(
            post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"email": "test@test.cl", "password": "password123"}
                        """))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value("token123"));
  }

  @Test
  void login_conEmailInvalido_debeRetornar400() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"email": "invalido", "password": "password123"}
                        """))
        .andExpect(status().isBadRequest());
  }

  @Test
  void register_conDatosValidos_debeRetornar201() throws Exception {
    var usuarioResponse =
        UsuarioResponse.builder().id(userId).email("test@test.cl").build();
    var rol = new com.santiago.joven.backend.model.entity.Rol();
    var rolId = UUID.randomUUID();
    rol.setId(rolId);

    when(usuarioService.existsByEmail("test@test.cl")).thenReturn(false);
    when(passwordEncoder.encode("password123")).thenReturn("encoded");
    when(usuarioService.create(any(UsuarioRequest.class))).thenReturn(usuarioResponse);
    when(rolRepository.findByNombre(NombreRol.USER)).thenReturn(java.util.Optional.of(rol));
    when(jwtTokenProvider.generateToken(any(UUID.class), any(String.class), any(List.class)))
        .thenReturn("token456");

    mockMvc
        .perform(
            post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"email": "test@test.cl", "password": "password123", "nombre": "Test", "apellido": "User"}
                        """))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.token").value("token456"));
  }

  @Test
  void register_cuandoEmailYaExiste_debeRetornar409() throws Exception {
    when(usuarioService.existsByEmail("test@test.cl")).thenReturn(true);

    mockMvc
        .perform(
            post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"email": "test@test.cl", "password": "password123", "nombre": "Test", "apellido": "User"}
                        """))
        .andExpect(status().isConflict());
  }

  @Test
  void register_conDatosInvalidos_debeRetornar400() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"email": "invalido", "password": "123"}
                        """))
        .andExpect(status().isBadRequest());
  }
}
