package com.santiago.joven.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.model.entity.Contacto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class ContactoRepositoryTest {

  @Autowired
  private ContactoRepository repository;

  @Autowired
  private TestEntityManager em;

  @BeforeEach
  void setUp() {
    var contactoSinRespuesta = new Contacto();
    contactoSinRespuesta.setNombre("Sofía");
    contactoSinRespuesta.setEmail("sofia.contreras@gmail.com");
    contactoSinRespuesta.setTelefono("+56 9 8765 4321");
    contactoSinRespuesta.setMensaje("Hola, quiero inscribirme en los talleres de arte en Santiago Centro.");
    contactoSinRespuesta.setProgramaInteres("Talleres de Arte");
    contactoSinRespuesta.setRespondido(false);
    em.persist(contactoSinRespuesta);

    var contactoRespondido = new Contacto();
    contactoRespondido.setNombre("Matías");
    contactoRespondido.setEmail("matias.ibanez@yahoo.cl");
    contactoRespondido.setTelefono("+56 9 9123 4567");
    contactoRespondido.setMensaje("Consulta sobre el programa de nivelación escolar.");
    contactoRespondido.setProgramaInteres("Nivelación Escolar");
    contactoRespondido.setRespondido(true);
    contactoRespondido.setRespuesta("Hola Matías, te contactaremos a la brevedad. Saludos, equipo Santiago Joven.");
    em.persist(contactoRespondido);
  }

  @Test
  void findByRespondidoFalseOrderByFechaContactoDesc_debeRetornarNoRespondidos() {
    var resultados = repository.findByRespondidoFalseOrderByFechaContactoDesc();

    assertThat(resultados).hasSize(1);
    assertThat(resultados.get(0).getEmail()).isEqualTo("sofia.contreras@gmail.com");
  }

  @Test
  void findByEmail_debeFiltrarPorEmail() {
    var resultados = repository.findByEmail("matias.ibanez@yahoo.cl");

    assertThat(resultados).hasSize(1).extracting(Contacto::getNombre).containsExactly("Matías");
  }

  @Test
  void findByEmail_debeRetornarVacio_sinCoincidencias() {
    var resultados = repository.findByEmail("noexiste@example.com");

    assertThat(resultados).isEmpty();
  }
}
