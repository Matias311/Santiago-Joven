package com.santiago.joven.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.model.entity.ActividadTaller;
import com.santiago.joven.backend.model.entity.Categoria;
import com.santiago.joven.backend.model.entity.Ubicacion;
import com.santiago.joven.backend.model.entity.Usuario;
import com.santiago.joven.backend.model.enums.EstadoActividad;
import com.santiago.joven.backend.model.enums.TipoCategoria;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class ActividadTallerRepositoryTest {

  @Autowired
  private ActividadTallerRepository repository;

  @Autowired
  private TestEntityManager em;

  private ActividadTaller actividad1;
  private Categoria categoria;
  private Ubicacion ubicacion;

  @BeforeEach
  void setUp() {
    var usuario = new Usuario();
    usuario.setEmail("coordinador@santiagojoven.org");
    usuario.setPassword("pass1234");
    usuario.setNombre("Carolina");
    usuario.setApellido("Rojas");
    usuario.setActivo(true);
    em.persist(usuario);

    categoria = new Categoria();
    categoria.setNombre("Talleres");
    categoria.setTipo(TipoCategoria.ACTIVIDAD);
    em.persist(categoria);

    ubicacion = new Ubicacion();
    ubicacion.setNombre("Centro Comunitario La Moneda");
    ubicacion.setCiudad("Santiago");
    em.persist(ubicacion);

    actividad1 = new ActividadTaller();
    actividad1.setTitulo("Taller de guitarra popular");
    actividad1.setDescripcion("Aprende guitarra con músicos locales en el centro de Santiago.");
    actividad1.setFechaHora(LocalDateTime.now().plusDays(1));
    actividad1.setActivo(true);
    actividad1.setEstado(EstadoActividad.CONFIRMADO);
    actividad1.setCategoria(categoria);
    actividad1.setUbicacion(ubicacion);
    actividad1.setUsuarioCreador(usuario);
    actividad1.setCantidadMaximaParticipantes(20);
    em.persist(actividad1);

    var actividadPasada = new ActividadTaller();
    actividadPasada.setTitulo("Taller de cueca tradicional");
    actividadPasada.setDescripcion("Taller de baile nacional ya realizado.");
    actividadPasada.setFechaHora(LocalDateTime.now().minusDays(5));
    actividadPasada.setActivo(true);
    actividadPasada.setEstado(EstadoActividad.CONFIRMADO);
    actividadPasada.setCategoria(categoria);
    actividadPasada.setUbicacion(ubicacion);
    actividadPasada.setUsuarioCreador(usuario);
    em.persist(actividadPasada);

    var actividadInactiva = new ActividadTaller();
    actividadInactiva.setTitulo("Taller de cocina internacional");
    actividadInactiva.setDescripcion("Cancelado por falta de inscritos.");
    actividadInactiva.setFechaHora(LocalDateTime.now().plusDays(3));
    actividadInactiva.setActivo(false);
    actividadInactiva.setEstado(EstadoActividad.PENDIENTE);
    actividadInactiva.setCategoria(categoria);
    actividadInactiva.setUbicacion(ubicacion);
    actividadInactiva.setUsuarioCreador(usuario);
    em.persist(actividadInactiva);
  }

  @Test
  void findByCategoriaId_debeFiltrarPorCategoria() {
    var resultados = repository.findByCategoriaId(categoria.getId());

    assertThat(resultados).hasSize(3);
  }

  @Test
  void findByEstado_debeFiltrarPorEstado() {
    var resultados = repository.findByEstado(EstadoActividad.CONFIRMADO);

    assertThat(resultados).hasSize(2);
  }

  @Test
  void findByFechaHoraBetween_debeFiltrarPorRango() {
    var inicio = LocalDateTime.now().minusDays(1);
    var fin = LocalDateTime.now().plusDays(2);

    var resultados = repository.findByFechaHoraBetween(inicio, fin);

    assertThat(resultados).hasSize(1).extracting(ActividadTaller::getTitulo).containsExactly("Taller de guitarra popular");
  }

  @Test
  void findByActivoTrueOrderByFechaHoraAsc_debeRetornarActivasOrdenadas() {
    var resultados = repository.findByActivoTrueOrderByFechaHoraAsc();

    assertThat(resultados).hasSize(2);
    assertThat(resultados.get(0).getTitulo()).isEqualTo("Taller de cueca tradicional");
    assertThat(resultados.get(1).getTitulo()).isEqualTo("Taller de guitarra popular");
  }

  @Test
  void findTop10ByOrderByFechaHoraAsc_debeLimitarResultados() {
    var resultados = repository.findTop10ByOrderByFechaHoraAsc();

    assertThat(resultados).hasSize(3);
    assertThat(resultados.get(0).getTitulo()).isEqualTo("Taller de cueca tradicional");
  }

  @Test
  void save_debePersistirActividad() {
    var nueva = new ActividadTaller();
    nueva.setTitulo("Taller de muralismo en Bellavista");
    nueva.setDescripcion("Pinta murales en el barrio Bellavista con artistas locales.");
    nueva.setFechaHora(LocalDateTime.now());
    nueva.setActivo(true);
    nueva.setEstado(EstadoActividad.PENDIENTE);

    var guardada = repository.save(nueva);

    assertThat(guardada.getId()).isNotNull();
    assertThat(guardada.getTitulo()).isEqualTo("Taller de muralismo en Bellavista");
  }
}
