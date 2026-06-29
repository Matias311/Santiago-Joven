package com.santiago.joven.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.model.entity.ResenaCalificacion;
import com.santiago.joven.backend.model.entity.Usuario;
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
class ResenaCalificacionRepositoryTest {

  @Autowired
  private ResenaCalificacionRepository repository;

  @Autowired
  private TestEntityManager em;

  private Usuario usuario;
  private UUID recursoId;

  @BeforeEach
  void setUp() {
    usuario = new Usuario();
    usuario.setEmail("camila.parra@santiagojoven.org");
    usuario.setPassword("pass1234");
    usuario.setNombre("Camila");
    usuario.setApellido("Parra");
    usuario.setActivo(true);
    em.persist(usuario);

    recursoId = UUID.randomUUID();

    var resena1 = new ResenaCalificacion();
    resena1.setUsuario(usuario);
    resena1.setRecursoId(recursoId);
    resena1.setTipoRecurso(TipoRecurso.CURSO);
    resena1.setCalificacion(5);
    resena1.setComentario("Excelente taller, muy bien organizado en Providencia.");
    em.persist(resena1);

    var resena2 = new ResenaCalificacion();
    resena2.setUsuario(usuario);
    resena2.setRecursoId(recursoId);
    resena2.setTipoRecurso(TipoRecurso.ACTIVIDAD);
    resena2.setCalificacion(3);
    resena2.setComentario("Regular, la sala quedó chica para los asistentes.");
    em.persist(resena2);

    var resena3 = new ResenaCalificacion();
    resena3.setUsuario(usuario);
    resena3.setRecursoId(UUID.randomUUID());
    resena3.setTipoRecurso(TipoRecurso.ACTIVIDAD);
    resena3.setCalificacion(1);
    resena3.setComentario("No se presentó el instructor.");
    em.persist(resena3);
  }

  @Test
  void findByRecursoIdAndTipoRecurso_debeFiltrarPorRecurso() {
    var resultados = repository.findByRecursoIdAndTipoRecurso(recursoId, TipoRecurso.CURSO);

    assertThat(resultados).hasSize(1).extracting(ResenaCalificacion::getCalificacion).containsExactly(5);
  }

  @Test
  void findByUsuarioId_debeFiltrarPorUsuario() {
    var resultados = repository.findByUsuarioId(usuario.getId());

    assertThat(resultados).hasSize(3);
  }

  @Test
  void findByCalificacionGreaterThanEqual_debeFiltrarPorCalificacionMinima() {
    var resultados = repository.findByCalificacionGreaterThanEqual(4);

    assertThat(resultados).hasSize(1).extracting(ResenaCalificacion::getCalificacion).containsExactly(5);
  }

  @Test
  void findByCalificacionGreaterThanEqual_debeRetornarVacio_siNingunaCumple() {
    var resultados = repository.findByCalificacionGreaterThanEqual(5);

    assertThat(resultados).hasSize(1);
  }
}
