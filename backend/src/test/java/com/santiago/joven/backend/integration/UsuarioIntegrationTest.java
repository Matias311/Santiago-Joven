package com.santiago.joven.backend.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.dto.UsuarioRequest;
import com.santiago.joven.backend.dto.UsuarioResponse;
import com.santiago.joven.backend.dto.UsuarioUpdate;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;

class UsuarioIntegrationTest extends BaseIntegrationTest {

  @Autowired private JdbcTemplate jdbc;

  private String adminToken;
  private UUID adminUserId;
  private UUID testUserId;
  private String testUserEmail;

  @BeforeEach
  void setUp() {
    adminToken = null;
    adminUserId = null;
    testUserId = null;
    testUserEmail = null;

    var adminEmail = "admin-" + UUID.randomUUID() + "@santiagojoven.org";
    var adminReg = client().post()
        .uri("/api/v1/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .body(UsuarioRequest.builder()
            .email(adminEmail).password("password123").nombre("Admin").apellido("User").build())
        .retrieve()
        .toEntity(com.santiago.joven.backend.dto.LoginResponse.class);

    var adminRolId = jdbc.queryForObject(
        "SELECT id FROM roles WHERE nombre = ?", UUID.class, "ADMIN");
    jdbc.update(
        "INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (?, ?)",
        adminReg.getBody().userId(), adminRolId);

    var adminLogin = client().post()
        .uri("/api/v1/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .body(new com.santiago.joven.backend.dto.LoginRequest(adminEmail, "password123"))
        .retrieve()
        .toEntity(com.santiago.joven.backend.dto.LoginResponse.class);

    adminToken = adminLogin.getBody().token();
    adminUserId = adminLogin.getBody().userId();

    testUserEmail = "test-" + UUID.randomUUID() + "@santiagojoven.org";
    var testReg = client().post()
        .uri("/api/v1/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .body(UsuarioRequest.builder()
            .email(testUserEmail)
            .password("password123").nombre("Test").apellido("Subject").build())
        .retrieve()
        .toEntity(com.santiago.joven.backend.dto.LoginResponse.class);
    testUserId = testReg.getBody().userId();
  }

  @Test
  void listarUsuarios_comoAdmin_retornaLista() {
    var response = authClient(adminToken).get()
        .uri("/api/v1/usuarios")
        .retrieve()
        .toEntity(UsuarioResponse[].class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotEmpty();
  }

  @Test
  void listarUsuarios_sinToken_retorna403() {
    var response = client().get()
        .uri("/api/v1/usuarios")
        .retrieve()
        .onStatus(s -> s == HttpStatus.FORBIDDEN, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  @Test
  void listarUsuarios_conUsuarioComun_retorna403() {
    var userToken = registrarUsuarioComun();

    var response = authClient(userToken).get()
        .uri("/api/v1/usuarios")
        .retrieve()
        .onStatus(s -> s == HttpStatus.FORBIDDEN, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  @Test
  void obtenerUsuarioPorId_comoAdmin_retornaUsuario() {
    var response = authClient(adminToken).get()
        .uri("/api/v1/usuarios/{id}", testUserId)
        .retrieve()
        .toEntity(UsuarioResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().id()).isEqualTo(testUserId);
  }

  @Test
  void obtenerUsuarioPorId_noExistente_retorna404() {
    var response = authClient(adminToken).get()
        .uri("/api/v1/usuarios/{id}", UUID.randomUUID())
        .retrieve()
        .onStatus(s -> s == HttpStatus.NOT_FOUND, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void obtenerUsuarioPorEmail_comoAdmin_retornaUsuario() {
    var response = authClient(adminToken).get()
        .uri("/api/v1/usuarios/por-email/{email}", testUserEmail)
        .retrieve()
        .toEntity(UsuarioResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().email()).isEqualTo(testUserEmail);
  }

  @Test
  void obtenerUsuarioPorEmail_noExistente_retorna404() {
    var response = authClient(adminToken).get()
        .uri("/api/v1/usuarios/por-email/{email}",
            "no-existe-" + UUID.randomUUID() + "@santiagojoven.org")
        .retrieve()
        .onStatus(s -> s == HttpStatus.NOT_FOUND, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void existsEmail_existente_retornaTrue() {
    var response = authClient(adminToken).get()
        .uri("/api/v1/usuarios/exists-email/{email}", testUserEmail)
        .retrieve()
        .toEntity(Boolean.class);

    assertThat(response.getBody()).isTrue();
  }

  @Test
  void existsEmail_noExistente_retornaFalse() {
    var response = authClient(adminToken).get()
        .uri("/api/v1/usuarios/exists-email/{email}", "no-existe-" + UUID.randomUUID() + "@santiagojoven.org")
        .retrieve()
        .toEntity(Boolean.class);

    assertThat(response.getBody()).isFalse();
  }

  @Test
  void crearUsuario_comoAdmin_retorna201() {
    var request = UsuarioRequest.builder()
        .email("nuevo-" + UUID.randomUUID() + "@santiagojoven.org")
        .password("password123")
        .nombre("Nuevo")
        .apellido("Usuario")
        .build();

    var response = authClient(adminToken).post()
        .uri("/api/v1/usuarios")
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .toEntity(UsuarioResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().id()).isNotNull();
    assertThat(response.getBody().email()).isEqualTo(request.email());
  }

  @Test
  void crearUsuario_emailDuplicado_retorna409() {
    var email = "dup-" + UUID.randomUUID() + "@santiagojoven.org";
    adminCreaUsuario(email);

    var request = UsuarioRequest.builder()
        .email(email)
        .password("password456")
        .nombre("Duplicado")
        .apellido("Email")
        .build();

    var response = authClient(adminToken).post()
        .uri("/api/v1/usuarios")
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .onStatus(s -> s == HttpStatus.CONFLICT, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
  }

  @Test
  void actualizarUsuario_comoAdmin_retorna200() {
    var update = UsuarioUpdate.builder()
        .nombre("NombreActualizado")
        .apellido("ApellidoActualizado")
        .build();

    var response = authClient(adminToken).put()
        .uri("/api/v1/usuarios/{id}", testUserId)
        .contentType(MediaType.APPLICATION_JSON)
        .body(update)
        .retrieve()
        .toEntity(UsuarioResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().nombre()).isEqualTo("NombreActualizado");
    assertThat(response.getBody().apellido()).isEqualTo("ApellidoActualizado");
  }

  @Test
  void actualizarUsuario_noExistente_retorna404() {
    var update = UsuarioUpdate.builder().nombre("Nadie").build();

    var response = authClient(adminToken).put()
        .uri("/api/v1/usuarios/{id}", UUID.randomUUID())
        .contentType(MediaType.APPLICATION_JSON)
        .body(update)
        .retrieve()
        .onStatus(s -> s == HttpStatus.NOT_FOUND, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void eliminarUsuario_comoAdmin_retorna204() {
    var creado = adminCreaUsuario("delete-" + UUID.randomUUID() + "@santiagojoven.org");

    var response = authClient(adminToken).delete()
        .uri("/api/v1/usuarios/{id}", creado.id())
        .retrieve()
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  void eliminarUsuario_noExistente_retorna404() {
    var response = authClient(adminToken).delete()
        .uri("/api/v1/usuarios/{id}", UUID.randomUUID())
        .retrieve()
        .onStatus(s -> s == HttpStatus.NOT_FOUND, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void eliminarUsuario_conUsuarioComun_retorna403() {
    var userToken = registrarUsuarioComun();

    var response = authClient(userToken).delete()
        .uri("/api/v1/usuarios/{id}", UUID.randomUUID())
        .retrieve()
        .onStatus(s -> s == HttpStatus.FORBIDDEN, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  private String registrarUsuarioComun() {
    var email = "user-" + UUID.randomUUID() + "@santiagojoven.org";
    var res = client().post()
        .uri("/api/v1/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .body(UsuarioRequest.builder()
            .email(email).password("password123").nombre("User").apellido("Normal").build())
        .retrieve()
        .toEntity(com.santiago.joven.backend.dto.LoginResponse.class);
    return res.getBody().token();
  }

  private UsuarioResponse adminCreaUsuario(String email) {
    var request = UsuarioRequest.builder()
        .email(email)
        .password("password123")
        .nombre("Temp")
        .apellido("User")
        .build();
    return authClient(adminToken).post()
        .uri("/api/v1/usuarios")
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .toEntity(UsuarioResponse.class)
        .getBody();
  }
}
