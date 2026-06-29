package com.santiago.joven.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.model.entity.Permiso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class PermisoRepositoryTest {

  @Autowired
  private PermisoRepository repository;

  @Autowired
  private TestEntityManager em;

  @BeforeEach
  void setUp() {
    var permiso1 = new Permiso();
    permiso1.setNombre("PERMISSION_MANAGE_USERS");
    permiso1.setModulo("USERS");
    em.persist(permiso1);

    var permiso2 = new Permiso();
    permiso2.setNombre("PERMISSION_EDIT_CONTENT");
    permiso2.setModulo("CONTENT");
    em.persist(permiso2);

    var permiso3 = new Permiso();
    permiso3.setNombre("PERMISSION_VIEW_REPORTS");
    permiso3.setModulo("REPORTS");
    em.persist(permiso3);
  }

  @Test
  void findByNombre_debeRetornarPermiso() {
    var result = repository.findByNombre("PERMISSION_MANAGE_USERS");

    assertThat(result).isPresent();
    assertThat(result.get().getModulo()).isEqualTo("USERS");
  }

  @Test
  void findByNombre_debeRetornarVacio_cuandoNoExiste() {
    var result = repository.findByNombre("INEXISTENTE");

    assertThat(result).isEmpty();
  }

  @Test
  void findByModulo_debeFiltrarPorModulo() {
    var resultados = repository.findByModulo("CONTENT");

    assertThat(resultados).hasSize(1).extracting(Permiso::getNombre).containsExactly("PERMISSION_EDIT_CONTENT");
  }

  @Test
  void findByModulo_debeRetornarVacio_sinCoincidencias() {
    var resultados = repository.findByModulo("SECURITY");

    assertThat(resultados).isEmpty();
  }
}
