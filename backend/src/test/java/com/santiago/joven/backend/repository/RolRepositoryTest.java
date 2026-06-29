package com.santiago.joven.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.model.entity.Rol;
import com.santiago.joven.backend.model.enums.NombreRol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class RolRepositoryTest {

  @Autowired
  private RolRepository repository;

  @Autowired
  private TestEntityManager em;

  @BeforeEach
  void setUp() {
    em.persist(new Rol(NombreRol.ADMIN));
    em.persist(new Rol(NombreRol.MODERATOR));
    em.persist(new Rol(NombreRol.USER));
  }

  @Test
  void findByNombre_debeRetornarRol() {
    var result = repository.findByNombre(NombreRol.ADMIN);

    assertThat(result).isPresent();
    assertThat(result.get().getNombre()).isEqualTo(NombreRol.ADMIN);
  }

  @Test
  void findByNombre_debeRetornarVacio_cuandoNoExiste() {
    var result = repository.findByNombre(NombreRol.VOLUNTEER);

    assertThat(result).isEmpty();
  }

  @Test
  void save_debePersistirRol() {
    var nuevo = new Rol(NombreRol.VOLUNTEER);
    var guardado = repository.save(nuevo);

    assertThat(guardado.getId()).isNotNull();
    assertThat(guardado.getNombre()).isEqualTo(NombreRol.VOLUNTEER);
  }
}
