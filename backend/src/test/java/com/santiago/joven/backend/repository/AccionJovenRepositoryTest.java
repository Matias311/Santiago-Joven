package com.santiago.joven.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.model.entity.AccionJoven;
import com.santiago.joven.backend.model.entity.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class AccionJovenRepositoryTest {

  @Autowired
  private AccionJovenRepository repository;

  @Autowired
  private TestEntityManager em;

  private AccionJoven accionActiva;

  @BeforeEach
  void setUp() {
    var usuario = new Usuario();
    usuario.setEmail("coordinador@santiagojoven.org");
    usuario.setPassword("pass1234");
    usuario.setNombre("Carolina");
    usuario.setApellido("Rojas");
    usuario.setActivo(true);
    em.persist(usuario);

    accionActiva = new AccionJoven();
    accionActiva.setTitulo("Reciclatón en Parque O'Higgins");
    accionActiva.setDescripcion("Jornada de reciclaje comunitario abierta a toda la comuna de Santiago.");
    accionActiva.setActivo(true);
    accionActiva.setUsuarioCreador(usuario);
    em.persist(accionActiva);

    var accionInactiva = new AccionJoven();
    accionInactiva.setTitulo("Feria emprendedora 2025");
    accionInactiva.setDescripcion("Evento finalizado para emprendedores juveniles.");
    accionInactiva.setActivo(false);
    accionInactiva.setUsuarioCreador(usuario);
    em.persist(accionInactiva);
  }

  @Test
  void findByActivoTrue_debeRetornarSoloActivas() {
    var resultados = repository.findByActivoTrue();

    assertThat(resultados).hasSize(1).extracting(AccionJoven::getTitulo).containsExactly("Reciclatón en Parque O'Higgins");
  }

  @Test
  void save_debePersistirAccionJoven() {
    var nueva = new AccionJoven();
    nueva.setTitulo("Limpieza del Mapocho");
    nueva.setDescripcion("Voluntariado para limpiar las riberas del río Mapocho.");
    nueva.setActivo(true);

    var guardada = repository.save(nueva);

    assertThat(guardada.getId()).isNotNull();
    assertThat(guardada.getTitulo()).isEqualTo("Limpieza del Mapocho");
  }
}
