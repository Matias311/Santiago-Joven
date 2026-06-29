package com.santiago.joven.backend.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.dto.LoginRequest;
import com.santiago.joven.backend.dto.LoginResponse;
import com.santiago.joven.backend.dto.RecuperarRequest;
import com.santiago.joven.backend.dto.RestablecerRequest;
import com.santiago.joven.backend.dto.UsuarioRequest;
import com.santiago.joven.backend.dto.InscripcionRequest;
import com.santiago.joven.backend.dto.ErrorResponse;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import java.time.LocalDateTime;
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
  void registerYVerificarRolYPermisos() {
    var email = "rol-permiso-test@santiagojoven.org";
    var body = UsuarioRequest.builder()
        .email(email)
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

    var userId = jdbcTemplate.queryForObject(
        "SELECT id FROM usuarios WHERE email = ?", UUID.class, email);

    var rolNames = jdbcTemplate.queryForList(
        "SELECT r.nombre FROM roles r "
            + "JOIN usuarios_roles ur ON ur.rol_id = r.id "
            + "WHERE ur.usuario_id = ?",
        String.class, userId);

    assertThat(rolNames).containsExactly("USER");

    var permisos = jdbcTemplate.queryForList(
        "SELECT p.nombre FROM permisos p "
            + "JOIN roles_permisos rp ON rp.permiso_id = p.id "
            + "JOIN roles r ON r.id = rp.rol_id "
            + "WHERE r.nombre = 'USER'",
        String.class);

    assertThat(permisos).isEmpty();
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

  @Test
  void recuperar_conEmailValido_generaCodigoEnDb() {
    var email = "recuperar-flow@santiagojoven.org";
    client().post()
        .uri("/api/v1/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .body(UsuarioRequest.builder()
            .email(email).password("password123").nombre("T").apellido("U").build())
        .retrieve()
        .toBodilessEntity();

    var response = client().post()
        .uri("/api/v1/auth/recuperar")
        .contentType(MediaType.APPLICATION_JSON)
        .body(new RecuperarRequest(email))
        .retrieve()
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    var codigos = jdbcTemplate.queryForList(
        "SELECT codigo FROM codigos_recuperacion WHERE email = ? AND usado = false",
        String.class, email);
    assertThat(codigos).hasSize(1);
    assertThat(codigos.get(0)).hasSize(5);
  }

  @Test
  void recuperar_conEmailInexistente_retorna200() {
    var response = client().post()
        .uri("/api/v1/auth/recuperar")
        .contentType(MediaType.APPLICATION_JSON)
        .body(new RecuperarRequest("no-existe@test.cl"))
        .retrieve()
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void restablecer_conCodigoValido_actualizaPassword() {
    var email = "restablecer-flow@santiagojoven.org";
    client().post()
        .uri("/api/v1/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .body(UsuarioRequest.builder()
            .email(email).password("password123").nombre("T").apellido("U").build())
        .retrieve()
        .toBodilessEntity();

    client().post()
        .uri("/api/v1/auth/recuperar")
        .contentType(MediaType.APPLICATION_JSON)
        .body(new RecuperarRequest(email))
        .retrieve()
        .toBodilessEntity();

    var codigo = jdbcTemplate.queryForObject(
        "SELECT codigo FROM codigos_recuperacion WHERE email = ? AND usado = false",
        String.class, email);

    var response = client().post()
        .uri("/api/v1/auth/restablecer")
        .contentType(MediaType.APPLICATION_JSON)
        .body(new RestablecerRequest(email, codigo, "new-password-123"))
        .retrieve()
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    var loginResponse = client().post()
        .uri("/api/v1/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .body(new LoginRequest(email, "new-password-123"))
        .retrieve()
        .toEntity(LoginResponse.class);

    assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(loginResponse.getBody()).isNotNull();
    assertThat(loginResponse.getBody().token()).isNotBlank();
  }

  @Test
  void restablecer_conCodigoInvalido_retorna400() {
    var response = client().post()
        .uri("/api/v1/auth/restablecer")
        .contentType(MediaType.APPLICATION_JSON)
        .body(new RestablecerRequest("test@test.cl", "00000", "new-password-123"))
        .retrieve()
        .onStatus(s -> s == HttpStatus.BAD_REQUEST, (req, res) -> {})
        .toEntity(ErrorResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().message()).contains("Codigo invalido");
  }

  @Test
  void restablecer_conCodigoExpirado_retorna400() {
    var email = "codigo-expirado@santiagojoven.org";
    client().post()
        .uri("/api/v1/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .body(UsuarioRequest.builder()
            .email(email).password("password123").nombre("T").apellido("U").build())
        .retrieve()
        .toBodilessEntity();

    client().post()
        .uri("/api/v1/auth/recuperar")
        .contentType(MediaType.APPLICATION_JSON)
        .body(new RecuperarRequest(email))
        .retrieve()
        .toBodilessEntity();

    var codigo = jdbcTemplate.queryForObject(
        "SELECT codigo FROM codigos_recuperacion WHERE email = ? AND usado = false",
        String.class, email);
    jdbcTemplate.update(
        "UPDATE codigos_recuperacion SET expiracion = ? WHERE email = ?",
        LocalDateTime.now().minusMinutes(1), email);

    var response = client().post()
        .uri("/api/v1/auth/restablecer")
        .contentType(MediaType.APPLICATION_JSON)
        .body(new RestablecerRequest(email, codigo, "new-password-123"))
        .retrieve()
        .onStatus(s -> s == HttpStatus.BAD_REQUEST, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }
}
