package com.santiago.joven.backend.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.dto.InscripcionRequest;
import com.santiago.joven.backend.dto.InscripcionResponse;
import com.santiago.joven.backend.dto.LoginResponse;
import com.santiago.joven.backend.dto.UsuarioRequest;
import com.santiago.joven.backend.model.entity.ActividadTaller;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import com.santiago.joven.backend.repository.ActividadTallerRepository;
import java.time.LocalDateTime;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

class InscripcionIntegrationTest extends BaseIntegrationTest {

  @Autowired private ActividadTallerRepository actividadTallerRepository;
  @Autowired private JdbcTemplate jdbcTemplate;

  private UUID usuarioId;

  @BeforeEach
  void setUp() {
    var registerBody = UsuarioRequest.builder()
        .email("user-it-" + UUID.randomUUID() + "@santiagojoven.org")
        .password("password123")
        .nombre("Test")
        .apellido("User")
        .build();

    var response = client().post()
        .uri("/api/v1/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .body(registerBody)
        .retrieve()
        .toEntity(LoginResponse.class);

    token = response.getBody().token();
    usuarioId = response.getBody().userId();
  }

  @Test
  void create_debeRetornar201() {
    var request = InscripcionRequest.builder()
        .usuarioId(usuarioId)
        .recursoId(UUID.randomUUID())
        .tipoRecurso(TipoRecurso.CURSO)
        .build();

    var response = authClient().post()
        .uri("/api/v1/inscripciones")
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .toEntity(InscripcionResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().id()).isNotNull();
    assertThat(response.getBody().usuarioId()).isEqualTo(usuarioId);
  }

  @Test
  void create_conUsuarioIdInexistente_debeRetornar404() {
    var request = InscripcionRequest.builder()
        .usuarioId(UUID.randomUUID())
        .recursoId(UUID.randomUUID())
        .tipoRecurso(TipoRecurso.CURSO)
        .build();

    var response = authClient().post()
        .uri("/api/v1/inscripciones")
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .onStatus(s -> s == HttpStatus.NOT_FOUND, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void create_enActividadConRecursoInexistente_debeRetornar404() {
    var request = InscripcionRequest.builder()
        .usuarioId(usuarioId)
        .recursoId(UUID.randomUUID())
        .tipoRecurso(TipoRecurso.ACTIVIDAD)
        .build();

    var response = authClient().post()
        .uri("/api/v1/inscripciones")
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .onStatus(s -> s == HttpStatus.NOT_FOUND, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void create_duplicado_debeRetornar409() {
    var recursoId = UUID.randomUUID();

    var primera = InscripcionRequest.builder()
        .usuarioId(usuarioId)
        .recursoId(recursoId)
        .tipoRecurso(TipoRecurso.CURSO)
        .build();

    authClient().post()
        .uri("/api/v1/inscripciones")
        .contentType(MediaType.APPLICATION_JSON)
        .body(primera)
        .retrieve()
        .toBodilessEntity();

    var response = authClient().post()
        .uri("/api/v1/inscripciones")
        .contentType(MediaType.APPLICATION_JSON)
        .body(primera)
        .retrieve()
        .onStatus(s -> s == HttpStatus.CONFLICT, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
  }

  @Test
  void create_sinToken_debeRetornar403() {
    var request = InscripcionRequest.builder()
        .usuarioId(usuarioId)
        .recursoId(UUID.randomUUID())
        .tipoRecurso(TipoRecurso.CURSO)
        .build();

    var response = client().post()
        .uri("/api/v1/inscripciones")
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .onStatus(s -> s == HttpStatus.FORBIDDEN, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  @Test
  void create_enActividadSinLimite_debePermitirMultiples() {
    var actividad = new ActividadTaller();
    actividad.setTitulo("Taller sin limite");
    actividad.setDescripcion("Desc");
    actividad.setInscritos(0);
    actividad.setCantidadMaximaParticipantes(null);
    actividad.setFechaHora(LocalDateTime.now().plusDays(1));
    var actividadGuardada = actividadTallerRepository.save(actividad);

    var r1 = crearInscripcionActividad(actividadGuardada.getId());
    assertThat(r1.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    // segundo usuario para evitar duplicado
    var otro = registrarUsuario("otro-sinlimite-" + UUID.randomUUID() + "@santiagojoven.org");
    var r2 = authClient(otro.token).post()
        .uri("/api/v1/inscripciones")
        .contentType(MediaType.APPLICATION_JSON)
        .body(InscripcionRequest.builder()
            .usuarioId(otro.usuarioId)
            .recursoId(actividadGuardada.getId())
            .tipoRecurso(TipoRecurso.ACTIVIDAD)
            .build())
        .retrieve()
        .toEntity(InscripcionResponse.class);
    assertThat(r2.getStatusCode()).isEqualTo(HttpStatus.CREATED);
  }

  @Test
  void create_enActividadConCupo_respetaMaximo() {
    var actividad = new ActividadTaller();
    actividad.setTitulo("Taller con cupo");
    actividad.setDescripcion("Desc");
    actividad.setInscritos(0);
    actividad.setCantidadMaximaParticipantes(2);
    actividad.setFechaHora(LocalDateTime.now().plusDays(1));
    var actividadGuardada = actividadTallerRepository.save(actividad);
    var actividadId = actividadGuardada.getId();

    // usamos emails distintos para evitar duplicado
    var segunda = registrarSegundoUsuario();
    var tercera = registrarTercerUsuario();

    var r1 = crearInscripcionActividad(actividadId);
    assertThat(r1.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    var r2 = authClient(segunda.token).post()
        .uri("/api/v1/inscripciones")
        .contentType(MediaType.APPLICATION_JSON)
        .body(InscripcionRequest.builder()
            .usuarioId(segunda.usuarioId)
            .recursoId(actividadId)
            .tipoRecurso(TipoRecurso.ACTIVIDAD)
            .build())
        .retrieve()
        .onStatus(s -> s == HttpStatus.BAD_REQUEST, (req, res) -> {})
        .toEntity(InscripcionResponse.class);
    assertThat(r2.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    var r3 = authClient(tercera.token).post()
        .uri("/api/v1/inscripciones")
        .contentType(MediaType.APPLICATION_JSON)
        .body(InscripcionRequest.builder()
            .usuarioId(tercera.usuarioId)
            .recursoId(actividadId)
            .tipoRecurso(TipoRecurso.ACTIVIDAD)
            .build())
        .retrieve()
        .onStatus(s -> s == HttpStatus.BAD_REQUEST, (req, res) -> {})
        .toEntity(InscripcionResponse.class);
    assertThat(r3.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void create_conUsuarioInactivo_debeRetornar400() {
    // desactivamos el usuario directamente en BD
    jdbcTemplate.update("UPDATE usuarios SET activo = false WHERE id = ?", usuarioId);

    var request = InscripcionRequest.builder()
        .usuarioId(usuarioId)
        .recursoId(UUID.randomUUID())
        .tipoRecurso(TipoRecurso.CURSO)
        .build();

    var response = authClient().post()
        .uri("/api/v1/inscripciones")
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .onStatus(s -> s == HttpStatus.BAD_REQUEST, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void delete_sinPermiso_debeRetornar403() {
    var response = authClient().delete()
        .uri("/api/v1/inscripciones/{id}", UUID.randomUUID())
        .retrieve()
        .onStatus(s -> s == HttpStatus.FORBIDDEN, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
  }

  @Test
  void findById_inexistente_debeRetornar404() {
    var response = client().get()
        .uri("/api/v1/inscripciones/{id}", UUID.randomUUID())
        .retrieve()
        .onStatus(s -> s == HttpStatus.NOT_FOUND, (req, res) -> {})
        .toBodilessEntity();

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  private ResponseEntity<InscripcionResponse> crearInscripcionActividad(UUID actividadId) {
    var request = InscripcionRequest.builder()
        .usuarioId(usuarioId)
        .recursoId(actividadId)
        .tipoRecurso(TipoRecurso.ACTIVIDAD)
        .build();

    return authClient().post()
        .uri("/api/v1/inscripciones")
        .contentType(MediaType.APPLICATION_JSON)
        .body(request)
        .retrieve()
        .onStatus(s -> s == HttpStatus.BAD_REQUEST, (req, res) -> {})
        .toEntity(InscripcionResponse.class);
  }

  private record SegundoUsuario(String token, UUID usuarioId) {}

  private SegundoUsuario registrarSegundoUsuario() {
    return registrarUsuario("segundo-" + UUID.randomUUID() + "@santiagojoven.org");
  }

  private SegundoUsuario registrarTercerUsuario() {
    return registrarUsuario("tercer-" + UUID.randomUUID() + "@santiagojoven.org");
  }

  private SegundoUsuario registrarUsuario(String email) {
    var body = UsuarioRequest.builder()
        .email(email)
        .password("password123")
        .nombre("Otro")
        .apellido("User")
        .build();
    var res = client().post()
        .uri("/api/v1/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .body(body)
        .retrieve()
        .toEntity(LoginResponse.class);
    return new SegundoUsuario(res.getBody().token(), res.getBody().userId());
  }

  // ---- GET query tests ----

  @Test
  void findPorUsuarioId_debeRetornarInscripciones() {
    var recursoId = UUID.randomUUID();
    crearInscripcion(usuarioId, recursoId, TipoRecurso.CURSO);

    var lista = client().get()
        .uri("/api/v1/inscripciones/por-usuario/{usuarioId}", usuarioId)
        .retrieve()
        .toEntity(InscripcionResponse[].class)
        .getBody();

    assertThat(lista).isNotEmpty();
    assertThat(lista).allMatch(i -> i.usuarioId().equals(usuarioId));
  }

  @SuppressWarnings("unchecked")
  @Test
  void findPorRecurso_debeRetornarInscripciones() {
    var recursoId = UUID.randomUUID();
    crearInscripcion(usuarioId, recursoId, TipoRecurso.CURSO);

    var response = client().get()
        .uri("/api/v1/inscripciones/por-recurso?recursoId={recursoId}&tipoRecurso={tipo}",
            recursoId, TipoRecurso.CURSO)
        .retrieve()
        .toEntity(InscripcionResponse[].class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotEmpty();
  }

  @Test
  void exists_debeRetornarTrue_cuandoYaInscrito() {
    var recursoId = UUID.randomUUID();
    crearInscripcion(usuarioId, recursoId, TipoRecurso.ASESORIA);

    var response = client().get()
        .uri("/api/v1/inscripciones/exists?usuarioId={uid}&recursoId={rid}&tipoRecurso={tipo}",
            usuarioId, recursoId, TipoRecurso.ASESORIA)
        .retrieve()
        .toEntity(Boolean.class);

    assertThat(response.getBody()).isTrue();
  }

  @Test
  void exists_debeRetornarFalse_sinInscripcion() {
    var response = client().get()
        .uri("/api/v1/inscripciones/exists?usuarioId={uid}&recursoId={rid}&tipoRecurso={tipo}",
            UUID.randomUUID(), UUID.randomUUID(), TipoRecurso.CURSO)
        .retrieve()
        .toEntity(Boolean.class);

    assertThat(response.getBody()).isFalse();
  }

  @Test
  void countPorRecurso_debeRetornarNumeroCorrecto() {
    var recursoId = UUID.randomUUID();
    crearInscripcion(usuarioId, recursoId, TipoRecurso.ASESORIA);

    var otro = registrarUsuario("count-" + UUID.randomUUID() + "@santiagojoven.org");
    crearInscripcion(otro.usuarioId, recursoId, TipoRecurso.ASESORIA);

    var response = client().get()
        .uri("/api/v1/inscripciones/count-por-recurso?recursoId={rid}&tipoRecurso={tipo}",
            recursoId, TipoRecurso.ASESORIA)
        .retrieve()
        .toEntity(Long.class);

    assertThat(response.getBody()).isEqualTo(2L);
  }

  private void crearInscripcion(UUID uid, UUID rid, TipoRecurso tipo) {
    authClient().post()
        .uri("/api/v1/inscripciones")
        .contentType(MediaType.APPLICATION_JSON)
        .body(InscripcionRequest.builder()
            .usuarioId(uid).recursoId(rid).tipoRecurso(tipo).build())
        .retrieve()
        .toBodilessEntity();
  }
}
