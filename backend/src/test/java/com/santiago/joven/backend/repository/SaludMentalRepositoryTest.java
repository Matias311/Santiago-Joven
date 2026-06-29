package com.santiago.joven.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.model.entity.SaludMental;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class SaludMentalRepositoryTest {

  @Autowired
  private SaludMentalRepository repository;

  @Autowired
  private TestEntityManager em;

  @BeforeEach
  void setUp() {
    var item1 = new SaludMental();
    item1.setTitulo("Línea *4141 (Prevención del Suicidio)");
    item1.setTelefono("*4141");
    item1.setDescripcion("Línea gratuita de prevención del suicidio del Ministerio de Salud.");
    item1.setOrden(1);
    em.persist(item1);

    var item2 = new SaludMental();
    item2.setTitulo("Fundación Todo Mejora");
    item2.setEnlace("https://todomejora.org");
    item2.setDescripcion("Apoyo a jóvenes LGBTQ+ en Chile.");
    item2.setOrden(2);
    em.persist(item2);

    var item3 = new SaludMental();
    item3.setTitulo("Línea Joven Santiago");
    item3.setTelefono("+56 2 2720 5000");
    item3.setDescripcion("Atención psicológica gratuita para jóvenes de la RM.");
    item3.setOrden(3);
    em.persist(item3);
  }

  @Test
  void findAllByOrderByOrdenAsc_debeRetornarOrdenados() {
    var resultados = repository.findAllByOrderByOrdenAsc();

    assertThat(resultados).hasSize(3);
    assertThat(resultados.get(0).getTitulo()).isEqualTo("Línea *4141 (Prevención del Suicidio)");
    assertThat(resultados.get(1).getTitulo()).isEqualTo("Fundación Todo Mejora");
    assertThat(resultados.get(2).getTitulo()).isEqualTo("Línea Joven Santiago");
  }
}
