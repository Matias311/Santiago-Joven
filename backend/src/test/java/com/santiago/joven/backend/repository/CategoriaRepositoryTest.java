package com.santiago.joven.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.model.entity.Categoria;
import com.santiago.joven.backend.model.enums.TipoCategoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class CategoriaRepositoryTest {

  @Autowired
  private CategoriaRepository repository;

  @Autowired
  private TestEntityManager em;

  @BeforeEach
  void setUp() {
    var cat1 = new Categoria();
    cat1.setNombre("Arte y Cultura");
    cat1.setTipo(TipoCategoria.ACTIVIDAD);
    em.persist(cat1);

    var cat2 = new Categoria();
    cat2.setNombre("Salud Mental");
    cat2.setTipo(TipoCategoria.ASESORIA);
    em.persist(cat2);

    var cat3 = new Categoria();
    cat3.setNombre("General");
    cat3.setTipo(TipoCategoria.GENERAL);
    em.persist(cat3);
  }

  @Test
  void findByNombre_debeRetornarCategoria() {
    var result = repository.findByNombre("Arte y Cultura");

    assertThat(result).isPresent();
    assertThat(result.get().getNombre()).isEqualTo("Arte y Cultura");
  }

  @Test
  void findByNombre_debeRetornarVacio_cuandoNoExiste() {
    var result = repository.findByNombre("Deportes");

    assertThat(result).isEmpty();
  }

  @Test
  void findByTipo_debeFiltrarPorTipo() {
    var resultados = repository.findByTipo(TipoCategoria.GENERAL);

    assertThat(resultados).hasSize(1).extracting(Categoria::getNombre).containsExactly("General");
  }

  @Test
  void findByTipo_debeRetornarVacio_sinCoincidencias() {
    var resultados = repository.findByTipo(TipoCategoria.CURSO);

    assertThat(resultados).isEmpty();
  }
}
