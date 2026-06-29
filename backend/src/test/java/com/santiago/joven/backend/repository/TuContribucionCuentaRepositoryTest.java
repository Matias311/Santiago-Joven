package com.santiago.joven.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.model.entity.TuContribucionCuenta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class TuContribucionCuentaRepositoryTest {

  @Autowired
  private TuContribucionCuentaRepository repository;

  @Autowired
  private TestEntityManager em;

  @BeforeEach
  void setUp() {
    var activo = new TuContribucionCuenta();
    activo.setTitulo("Tu contribución cuenta");
    activo.setDescripcion("Ayúdanos a seguir creando programas para jóvenes de Santiago. Cada aporte, por pequeño que sea, genera un gran cambio.");
    activo.setLinkGoogleForms("https://forms.gle/ejemplo-santiago-joven");
    activo.setActivo(true);
    em.persist(activo);

    var inactivo = new TuContribucionCuenta();
    inactivo.setTitulo("Campaña antigua 2024");
    inactivo.setDescripcion("Formulario de la campaña anterior.");
    inactivo.setLinkGoogleForms("https://forms.gle/antiguo-santiago-joven");
    inactivo.setActivo(false);
    em.persist(inactivo);
  }

  @Test
  void findByActivoTrue_debeRetornarElActivo() {
    var result = repository.findByActivoTrue();

    assertThat(result).isPresent();
    assertThat(result.get().getTitulo()).isEqualTo("Tu contribución cuenta");
    assertThat(result.get().getLinkGoogleForms()).isEqualTo("https://forms.gle/ejemplo-santiago-joven");
  }

  @Test
  void save_debePersistirYAsignarId() {
    var nueva = new TuContribucionCuenta();
    nueva.setTitulo("Donación voluntarios 2026");
    nueva.setDescripcion("Formulario para donantes del programa de nivelación escolar.");
    nueva.setLinkGoogleForms("https://forms.gle/donaciones-santiago-joven");
    nueva.setActivo(true);

    var guardada = repository.save(nueva);

    assertThat(guardada.getId()).isNotNull();
    assertThat(guardada.getTitulo()).isEqualTo("Donación voluntarios 2026");
  }
}
