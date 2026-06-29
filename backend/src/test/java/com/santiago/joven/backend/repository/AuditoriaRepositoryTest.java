package com.santiago.joven.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.model.entity.Auditoria;
import com.santiago.joven.backend.model.entity.Usuario;
import com.santiago.joven.backend.model.enums.TipoCambio;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class AuditoriaRepositoryTest {

  @Autowired
  private AuditoriaRepository repository;

  @Autowired
  private TestEntityManager em;

  private Auditoria auditoria;
  private Usuario usuario;

  @BeforeEach
  void setUp() {
    usuario = new Usuario();
    usuario.setEmail("admin@santiagojoven.org");
    usuario.setPassword("pass1234");
    usuario.setNombre("Felipe");
    usuario.setApellido("Echeverría");
    usuario.setActivo(true);
    em.persist(usuario);

    auditoria = new Auditoria();
    auditoria.setEntidadTipo("Usuario");
    auditoria.setEntidadId(UUID.randomUUID());
    auditoria.setUsuario(usuario);
    auditoria.setTipoCambio(TipoCambio.CREAR);
    auditoria.setCambiosAnteriores(null);
    auditoria.setCambiosNuevos("{\"nombre\":\"Benjamín Muñoz\",\"email\":\"benjamin.munoz@santiagojoven.org\"}");
    em.persist(auditoria);
  }

  @Test
  void findByEntidadTipoAndEntidadId_debeFiltrarPorEntidad() {
    var resultados = repository.findByEntidadTipoAndEntidadId("Usuario", auditoria.getEntidadId());

    assertThat(resultados).hasSize(1);
    assertThat(resultados.get(0).getTipoCambio()).isEqualTo(TipoCambio.CREAR);
  }

  @Test
  void findByEntidadTipoAndEntidadId_debeRetornarVacio_sinCoincidencias() {
    var resultados = repository.findByEntidadTipoAndEntidadId("Usuario", UUID.randomUUID());

    assertThat(resultados).isEmpty();
  }

  @Test
  void findByUsuarioIdOrderByFechaDesc_debeFiltrarPorUsuario() {
    var resultados = repository.findByUsuarioIdOrderByFechaDesc(usuario.getId());

    assertThat(resultados).hasSize(1);
  }

  @Test
  void findByUsuarioIdOrderByFechaDesc_debeRetornarVacio_sinCoincidencias() {
    var resultados = repository.findByUsuarioIdOrderByFechaDesc(UUID.randomUUID());

    assertThat(resultados).isEmpty();
  }
}
