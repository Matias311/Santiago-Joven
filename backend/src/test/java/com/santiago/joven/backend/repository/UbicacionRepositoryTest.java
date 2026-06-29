package com.santiago.joven.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.model.entity.Ubicacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class UbicacionRepositoryTest {

  @Autowired
  private UbicacionRepository repository;

  @Autowired
  private TestEntityManager em;

  @BeforeEach
  void setUp() {
    var ubicacion1 = new Ubicacion();
    ubicacion1.setNombre("Centro Comunitario La Moneda");
    ubicacion1.setDireccion("Av. La Moneda 1234, Santiago Centro");
    ubicacion1.setCiudad("Santiago");
    em.persist(ubicacion1);

    var ubicacion2 = new Ubicacion();
    ubicacion2.setNombre("Biblioteca de Providencia");
    ubicacion2.setDireccion("Av. Providencia 1500, Providencia");
    ubicacion2.setCiudad("Santiago");
    em.persist(ubicacion2);

    var ubicacion3 = new Ubicacion();
    ubicacion3.setNombre("Centro Cultural San Martín");
    ubicacion3.setCiudad("Valparaíso");
    em.persist(ubicacion3);
  }

  @Test
  void findByCiudad_debeFiltrarPorCiudad() {
    var resultados = repository.findByCiudad("Santiago");

    assertThat(resultados).hasSize(2).extracting(Ubicacion::getNombre)
        .containsExactlyInAnyOrder("Centro Comunitario La Moneda", "Biblioteca de Providencia");
  }

  @Test
  void findByCiudad_debeRetornarVacio_sinCoincidencias() {
    var resultados = repository.findByCiudad("Concepción");

    assertThat(resultados).isEmpty();
  }
}
