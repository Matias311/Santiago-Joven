package com.santiago.joven.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.model.entity.Estadistica;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class EstadisticaRepositoryTest {

  @Autowired
  private EstadisticaRepository repository;

  @Autowired
  private TestEntityManager em;

  private UUID recursoId;
  private Estadistica estadistica;

  @BeforeEach
  void setUp() {
    recursoId = UUID.randomUUID();

    estadistica = new Estadistica();
    estadistica.setRecursoId(recursoId);
    estadistica.setTipoRecurso(TipoRecurso.CURSO);
    estadistica.setTotalInscritos(120);
    estadistica.setTotalAsistentes(98);
    estadistica.setTotalResenas(45);
    estadistica.setPromedioCalificacion(new BigDecimal("4.70"));
    em.persist(estadistica);
  }

  @Test
  void findByRecursoIdAndTipoRecurso_debeRetornarEstadistica() {
    var result = repository.findByRecursoIdAndTipoRecurso(recursoId, TipoRecurso.CURSO);

    assertThat(result).isPresent();
    assertThat(result.get().getTotalInscritos()).isEqualTo(120);
    assertThat(result.get().getPromedioCalificacion()).isEqualByComparingTo(new BigDecimal("4.70"));
  }

  @Test
  void findByRecursoIdAndTipoRecurso_debeRetornarVacio_sinCoincidencias() {
    var result = repository.findByRecursoIdAndTipoRecurso(UUID.randomUUID(), TipoRecurso.ACTIVIDAD);

    assertThat(result).isEmpty();
  }
}
