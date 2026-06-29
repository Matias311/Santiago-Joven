package com.santiago.joven.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.model.entity.Programa;
import com.santiago.joven.backend.model.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class ProgramaRepositoryTest {

  @Autowired
  private ProgramaRepository repository;

  @Autowired
  private TestEntityManager em;

  @BeforeEach
  void setUp() {
    var usuario = new Usuario();
    usuario.setEmail("coordinador@santiagojoven.org");
    usuario.setPassword("pass1234");
    usuario.setNombre("Carolina");
    usuario.setApellido("Rojas");
    usuario.setActivo(true);
    em.persist(usuario);

    var programaActivo = new Programa();
    programaActivo.setTitulo("Nivelación Escolar RM");
    programaActivo.setDescripcion("Programa de apoyo escolar para jóvenes de la Región Metropolitana.");
    programaActivo.setDefinicion("Clases gratuitas de matemáticas, lenguaje e inglés para estudiantes de enseñanza media.");
    programaActivo.setObjetivos("Reducir la brecha educativa en comunas como Puente Alto, La Pintana y Cerro Navia.");
    programaActivo.setMetodologia("Sesiones semanales presenciales en sedes comunitarias y tutoring online.");
    programaActivo.setActivo(true);
    programaActivo.setOrden(1);
    programaActivo.setUsuarioCreador(usuario);
    em.persist(programaActivo);

    var programaInactivo = new Programa();
    programaInactivo.setTitulo("Programa de intercambio juvenil");
    programaInactivo.setDescripcion("Finalizado en 2025.");
    programaInactivo.setDefinicion("Intercambio con organizaciones juveniles de Latinoamérica.");
    programaInactivo.setObjetivos("Fomentar el intercambio cultural.");
    programaInactivo.setMetodologia("Presencial.");
    programaInactivo.setActivo(false);
    programaInactivo.setOrden(2);
    programaInactivo.setUsuarioCreador(usuario);
    em.persist(programaInactivo);
  }

  @Test
  void findByActivoTrueOrderByOrdenAsc_debeRetornarActivosOrdenados() {
    var resultados = repository.findByActivoTrueOrderByOrdenAsc();

    assertThat(resultados).hasSize(1).extracting(Programa::getTitulo).containsExactly("Nivelación Escolar RM");
    assertThat(resultados.get(0).getOrden()).isEqualTo(1);
  }
}
