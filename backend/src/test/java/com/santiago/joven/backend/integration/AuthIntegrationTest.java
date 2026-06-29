package com.santiago.joven.backend.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.dto.LoginRequest;
import com.santiago.joven.backend.dto.LoginResponse;
import com.santiago.joven.backend.dto.UsuarioRequest;
import com.santiago.joven.backend.dto.InscripcionRequest;
import com.santiago.joven.backend.dto.InscripcionResponse;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestClient;

class AuthIntegrationTest extends BaseIntegrationTest {

  @Autowired private JdbcTemplate jdbcTemplate;

  @Test
  void registerYLogin_flowCompleto() {
    var registerBody = UsuarioRequest.builder()
        .email("test-flow@santiagojoven.org")
        .password("password123")
        .nombre("Test")
        .apellido("User")
        .build();

    var registerResponse = client().post()
        .uri("/api/v1/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .body(registerBody)
        .retrieve()
        .toEntity(LoginResponse.class);

    assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(registerResponse.getBody()).isNotNull();
    assertThat(registerResponse.getBody().token()).isNotBlank();
    assertThat(registerResponse.getBody().email()).isEqualTo("test-flow@santiagojoven.org");

    var loginBody = new LoginRequest("test-flow@santiagojoven.org", "password123");
    var loginResponse = client().post()
        .uri("/api/v1/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .body(loginBody)
        .retrieve()
        .toEntity(LoginResponse.class);

    assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(loginResponse.getBody()).isNotNull();
    assertThat(loginResponse.getBody().token()).isNotBlank();
  }

  @Test
  void login_conEmailCorrectoPasswordIncorrecto_debeRetornar401() {
    var email = "login-pass-wrong@santiagojoven.org";
    client().post()
        .uri("/api/v1/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .body(UsuarioRequest.builder()
            .email(email).password("correcto").nombre("T").apellido("U").build())
        .retrieve()
        .toBodilessEntity();

    var response = client().post()
        .uri("/api/v1/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .body(new LoginRequest(email, "incorrecto"))
        .retrieve()
        .onStatus(s -> s == HttpStatus.UNAUTHORIZED, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }

  @Test
  void login_conEmailInexistente_debeRetornar401() {
    var response = client().post()
        .uri("/api/v1/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .body(new LoginRequest("no-existe@test.cl", "wrong"))
        .retrieve()
        .onStatus(s -> s == HttpStatus.UNAUTHORIZED, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }

  @Test
  void register_conEmailDuplicado_debeRetornar409() {
    var body = UsuarioRequest.builder()
        .email("duplicado-it@santiagojoven.org")
        .password("password123")
        .nombre("Test")
        .apellido("User")
        .build();

    client().post()
        .uri("/api/v1/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .body(body)
        .retrieve()
        .toBodilessEntity();

    var response = client().post()
        .uri("/api/v1/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .body(body)
        .retrieve()
        .onStatus(s -> s == HttpStatus.CONFLICT, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
  }

  @Test
  void register_conEmailInvalido_debeRetornar400() {
    var body = UsuarioRequest.builder()
        .email("mal-email")
        .password("password123")
        .nombre("Test")
        .apellido("User")
        .build();

    var response = client().post()
        .uri("/api/v1/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .body(body)
        .retrieve()
        .onStatus(s -> s == HttpStatus.BAD_REQUEST, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void register_conPasswordVacio_debeRetornar400() {
    var body = UsuarioRequest.builder()
        .email("pw-vacio@santiagojoven.org")
        .password("")
        .nombre("Test")
        .apellido("User")
        .build();

    var response = client().post()
        .uri("/api/v1/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .body(body)
        .retrieve()
        .onStatus(s -> s == HttpStatus.BAD_REQUEST, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void login_conUsuarioInactivo_debeRetornar401() {
    var email = "inactivo@santiagojoven.org";
    client().post()
        .uri("/api/v1/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .body(UsuarioRequest.builder()
            .email(email).password("password123").nombre("Inactivo").apellido("User").build())
        .retrieve()
        .toBodilessEntity();

    jdbcTemplate.update("UPDATE usuarios SET activo = false WHERE email = ?", email);

    var response = client().post()
        .uri("/api/v1/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .body(new LoginRequest(email, "password123"))
        .retrieve()
        .onStatus(s -> s == HttpStatus.UNAUTHORIZED, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
  }

  @Test
  void endpointProtegido_conTokenInvalido_debeRetornar403() {
    var request = InscripcionRequest.builder()
        .usuarioId(UUID.randomUUID())
        .recursoId(UUID.randomUUID())
        .tipoRecurso(TipoRecurso.CURSO)
        .build();

    var response = RestClient.builder()
        .baseUrl("http://localhost:" + port)
        .defaultHeader("Authorization", "Bearer token-invalido")
        .build()
        .post()
        .uri("/api/v1/inscripciones")
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .onStatus(s -> s == HttpStatus.FORBIDDEN, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }
}
