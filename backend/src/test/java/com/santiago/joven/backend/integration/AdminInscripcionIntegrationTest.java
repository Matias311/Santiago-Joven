package com.santiago.joven.backend.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.dto.InscripcionRequest;
import com.santiago.joven.backend.dto.InscripcionResponse;
import com.santiago.joven.backend.dto.InscripcionUpdate;
import com.santiago.joven.backend.dto.LoginResponse;
import com.santiago.joven.backend.dto.UsuarioRequest;
import com.santiago.joven.backend.model.entity.ActividadTaller;
import com.santiago.joven.backend.model.enums.EstadoInscripcion;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import com.santiago.joven.backend.repository.ActividadTallerRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

class AdminInscripcionIntegrationTest extends BaseIntegrationTest {

  @Autowired private ActividadTallerRepository actividadTallerRepository;
  @Autowired private JdbcTemplate jdbcTemplate;

  private String adminToken;
  private UUID adminUserId;

  @BeforeEach
  void setUp() {
    var body = UsuarioRequest.builder()
        .email("admin-" + UUID.randomUUID() + "@santiagojoven.org")
        .password("password123")
        .nombre("Admin")
        .apellido("User")
        .build();

    var res = client().post()
        .uri("/api/v1/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .body(body)
        .retrieve()
        .toEntity(LoginResponse.class);

    var adminRolId = jdbcTemplate.queryForObject(
        "SELECT id FROM roles WHERE nombre = ?", UUID.class, "ADMIN");
    jdbcTemplate.update(
        "INSERT INTO usuarios_roles (usuario_id, rol_id) VALUES (?, ?)",
        res.getBody().userId(), adminRolId);

    var loginRes = client().post()
        .uri("/api/v1/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .body(new com.santiago.joven.backend.dto.LoginRequest(
            res.getBody().email(), "password123"))
        .retrieve()
        .toEntity(LoginResponse.class);

    adminToken = loginRes.getBody().token();
    adminUserId = loginRes.getBody().userId();
  }

  @Test
  void deleteInscripcion_conPermisoAdmin_debeRetornar204() {
    var inscripcionCreada = crearInscripcion(TipoRecurso.CURSO);

    var response = authClient(adminToken).delete()
        .uri("/api/v1/inscripciones/{id}", inscripcionCreada.getBody().id())
        .retrieve()
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
  }

  @Test
  void updateInscripcion_conPermisoAdmin_debeRetornar200() {
    var inscripcionCreada = crearInscripcion(TipoRecurso.CURSO);
    var update = InscripcionUpdate.builder()
        .estado(EstadoInscripcion.INSCRITO)
        .notas("Actualizado por admin")
        .build();

    var response = authClient(adminToken).put()
        .uri("/api/v1/inscripciones/{id}", inscripcionCreada.getBody().id())
        .contentType(MediaType.APPLICATION_JSON)
        .body(update)
        .retrieve()
        .toEntity(InscripcionResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().estado()).isEqualTo(EstadoInscripcion.INSCRITO);
  }

  @Test
  void flujoCompleto_actividadConInscripciones_deleteVerificaContador() {
    var actividad = new ActividadTaller();
    actividad.setTitulo("Actividad flujo completo");
    actividad.setDescripcion("Desc");
    actividad.setInscritos(0);
    actividad.setCantidadMaximaParticipantes(10);
    actividad.setFechaHora(LocalDateTime.now().plusDays(1));
    var actividadGuardada = actividadTallerRepository.save(actividad);
    var actividadId = actividadGuardada.getId();

    var u1 = registrarUsuario();
    var u2 = registrarUsuario();
    var u3 = registrarUsuario();

    var i1 = inscribir(u1.token(), u1.usuarioId(), actividadId);
    var i2 = inscribir(u2.token(), u2.usuarioId(), actividadId);
    var i3 = inscribir(u3.token(), u3.usuarioId(), actividadId);

    assertThat(i1.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(i2.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(i3.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    var actividadActualizada = actividadTallerRepository.findById(actividadId).orElseThrow();
    assertThat(actividadActualizada.getInscritos()).isEqualTo(3);

    authClient(adminToken).delete()
        .uri("/api/v1/inscripciones/{id}", i1.getBody().id())
        .retrieve()
        .toBodilessEntity();

    actividadActualizada = actividadTallerRepository.findById(actividadId).orElseThrow();
    assertThat(actividadActualizada.getInscritos()).isEqualTo(2);
  }

  private ResponseEntity<InscripcionResponse> inscribir(
      String userToken, UUID usuarioId, UUID actividadId) {
    var request = InscripcionRequest.builder()
        .usuarioId(usuarioId)
        .recursoId(actividadId)
        .tipoRecurso(TipoRecurso.ACTIVIDAD)
        .build();
    return authClient(userToken).post()
        .uri("/api/v1/inscripciones")
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .toEntity(InscripcionResponse.class);
  }

  private ResponseEntity<InscripcionResponse> crearInscripcion(TipoRecurso tipo) {
    var request = InscripcionRequest.builder()
        .usuarioId(adminUserId)
        .recursoId(UUID.randomUUID())
        .tipoRecurso(tipo)
        .build();
    return authClient(adminToken).post()
        .uri("/api/v1/inscripciones")
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .toEntity(InscripcionResponse.class);
  }

  private record UsuarioTest(String token, UUID usuarioId) {}

  private UsuarioTest registrarUsuario() {
    var email = "user-flujo-" + UUID.randomUUID() + "@santiagojoven.org";
    var res = client().post()
        .uri("/api/v1/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .body(UsuarioRequest.builder()
            .email(email).password("password123").nombre("Flujo").apellido("User").build())
        .retrieve()
        .toEntity(LoginResponse.class);
    return new UsuarioTest(res.getBody().token(), res.getBody().userId());
  }
}
