package com.santiago.joven.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.model.entity.Asesoria;
import com.santiago.joven.backend.model.entity.Categoria;
import com.santiago.joven.backend.model.entity.Usuario;
import com.santiago.joven.backend.model.enums.TipoCategoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class AsesoriaRepositoryTest {

  @Autowired
  private AsesoriaRepository repository;

  @Autowired
  private TestEntityManager em;

  private Asesoria asesoria1;
  private Categoria categoria;

  @BeforeEach
  void setUp() {
    var usuario = new Usuario();
    usuario.setEmail("psicologo@santiagojoven.org");
    usuario.setPassword("pass1234");
    usuario.setNombre("Catalina");
    usuario.setApellido("Valenzuela");
    usuario.setActivo(true);
    em.persist(usuario);

    categoria = new Categoria();
    categoria.setNombre("Asesoría Vocacional");
    categoria.setTipo(TipoCategoria.ASESORIA);
    em.persist(categoria);

    var otraCategoria = new Categoria();
    otraCategoria.setNombre("Acompañamiento");
    otraCategoria.setTipo(TipoCategoria.GENERAL);
    em.persist(otraCategoria);

    asesoria1 = new Asesoria();
    asesoria1.setTitulo("Orientación vocacional para jóvenes");
    asesoria1.setDefinicion("Apoyo en la elección de carreras técnicas y universitarias.");
    asesoria1.setObjetivos("Ayudar a jóvenes de la RM a descubrir su vocación.");
    asesoria1.setMetodologia("Sesiones presenciales en Providencia y online vía Zoom.");
    asesoria1.setActivo(true);
    asesoria1.setOrden(1);
    asesoria1.setCategoria(categoria);
    asesoria1.setUsuarioCreador(usuario);
    em.persist(asesoria1);

    var asesoriaInactiva = new Asesoria();
    asesoriaInactiva.setTitulo("Asesoría legal cerrada");
    asesoriaInactiva.setDefinicion("Programa finalizado.");
    asesoriaInactiva.setObjetivos("N/A");
    asesoriaInactiva.setMetodologia("N/A");
    asesoriaInactiva.setActivo(false);
    asesoriaInactiva.setOrden(2);
    asesoriaInactiva.setCategoria(otraCategoria);
    asesoriaInactiva.setUsuarioCreador(usuario);
    em.persist(asesoriaInactiva);
  }

  @Test
  void findByCategoriaIdOrderByOrdenAsc_debeFiltrarPorCategoria() {
    var resultados = repository.findByCategoriaIdOrderByOrdenAsc(categoria.getId());

    assertThat(resultados).hasSize(1).extracting(Asesoria::getTitulo).containsExactly("Orientación vocacional para jóvenes");
  }

  @Test
  void findByActivoTrueOrderByOrdenAsc_debeRetornarActivasOrdenadas() {
    var resultados = repository.findByActivoTrueOrderByOrdenAsc();

    assertThat(resultados).hasSize(1).extracting(Asesoria::getTitulo).containsExactly("Orientación vocacional para jóvenes");
  }

  @Test
  void findByCategoriaIdOrderByOrdenAsc_debeRetornarVacio_siNoCoincide() {
    var resultados = repository.findByCategoriaIdOrderByOrdenAsc(java.util.UUID.randomUUID());

    assertThat(resultados).isEmpty();
  }
}
