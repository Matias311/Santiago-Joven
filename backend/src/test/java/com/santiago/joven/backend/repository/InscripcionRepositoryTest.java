package com.santiago.joven.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.model.entity.Inscripcion;
import com.santiago.joven.backend.model.entity.Usuario;
import com.santiago.joven.backend.model.enums.EstadoInscripcion;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class InscripcionRepositoryTest {

  @Autowired
  private InscripcionRepository repository;

  @Autowired
  private TestEntityManager em;

  private Usuario usuario;
  private UUID recursoId;

  @BeforeEach
  void setUp() {
    usuario = new Usuario();
    usuario.setEmail("emilio.torres@santiagojoven.org");
    usuario.setPassword("pass1234");
    usuario.setNombre("Emilio");
    usuario.setApellido("Torres");
    usuario.setActivo(true);
    em.persist(usuario);

    recursoId = UUID.randomUUID();

    var inscripcion1 = new Inscripcion();
    inscripcion1.setUsuario(usuario);
    inscripcion1.setRecursoId(recursoId);
    inscripcion1.setTipoRecurso(TipoRecurso.CURSO);
    inscripcion1.setEstado(EstadoInscripcion.INSCRITO);
    em.persist(inscripcion1);

    var inscripcion2 = new Inscripcion();
    inscripcion2.setUsuario(usuario);
    inscripcion2.setRecursoId(UUID.randomUUID());
    inscripcion2.setTipoRecurso(TipoRecurso.ACTIVIDAD);
    inscripcion2.setEstado(EstadoInscripcion.INSCRITO);
    em.persist(inscripcion2);

    var inscripcion3 = new Inscripcion();
    inscripcion3.setUsuario(usuario);
    inscripcion3.setRecursoId(UUID.randomUUID());
    inscripcion3.setTipoRecurso(TipoRecurso.ACTIVIDAD);
    inscripcion3.setEstado(EstadoInscripcion.CANCELADO);
    em.persist(inscripcion3);
  }

  @Test
  void findByUsuarioId_debeFiltrarPorUsuario() {
    var resultados = repository.findByUsuarioId(usuario.getId());

    assertThat(resultados).hasSize(3);
  }

  @Test
  void findByRecursoIdAndTipoRecurso_debeFiltrarPorRecurso() {
    var resultados = repository.findByRecursoIdAndTipoRecurso(recursoId, TipoRecurso.CURSO);

    assertThat(resultados).hasSize(1);
  }

  @Test
  void findByEstado_debeFiltrarPorEstado() {
    var resultados = repository.findByEstado(EstadoInscripcion.INSCRITO);

    assertThat(resultados).hasSize(2);
  }

  @Test
  void countByRecursoIdAndTipoRecurso_debeContar() {
    var count = repository.countByRecursoIdAndTipoRecurso(recursoId, TipoRecurso.CURSO);

    assertThat(count).isEqualTo(1);
  }

  @Test
  void existsByUsuarioIdAndRecursoIdAndTipoRecurso_debeVerificarExistencia() {
    var exists = repository.existsByUsuarioIdAndRecursoIdAndTipoRecurso(
        usuario.getId(), recursoId, TipoRecurso.CURSO);

    assertThat(exists).isTrue();
  }

  @Test
  void existsByUsuarioIdAndRecursoIdAndTipoRecurso_debeRetornarFalse_siNoExiste() {
    var exists = repository.existsByUsuarioIdAndRecursoIdAndTipoRecurso(
        usuario.getId(), UUID.randomUUID(), TipoRecurso.CURSO);

    assertThat(exists).isFalse();
  }
}
