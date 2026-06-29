package com.santiago.joven.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.model.entity.ActividadTaller;
import com.santiago.joven.backend.model.entity.GaleriaFoto;
import com.santiago.joven.backend.model.entity.Usuario;
import com.santiago.joven.backend.model.enums.EstadoActividad;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class GaleriaFotoRepositoryTest {

  @Autowired
  private GaleriaFotoRepository repository;

  @Autowired
  private TestEntityManager em;

  private ActividadTaller actividad;

  @BeforeEach
  void setUp() {
    var usuario = new Usuario();
    usuario.setEmail("coordinador@santiagojoven.org");
    usuario.setPassword("pass1234");
    usuario.setNombre("Carolina");
    usuario.setApellido("Rojas");
    usuario.setActivo(true);
    em.persist(usuario);

    actividad = new ActividadTaller();
    actividad.setTitulo("Taller de muralismo en Bellavista");
    actividad.setDescripcion("Registro fotográfico del taller de murales en el barrio Bellavista, Santiago.");
    actividad.setFechaHora(LocalDateTime.now().plusDays(1));
    actividad.setActivo(true);
    actividad.setEstado(EstadoActividad.CONFIRMADO);
    actividad.setUsuarioCreador(usuario);
    em.persist(actividad);

    var foto1 = new GaleriaFoto();
    foto1.setActividad(actividad);
    foto1.setUrlImagen("https://i.santiagojoven.org/galeria/mural-bellavista-01.jpg");
    foto1.setTitulo("Mural colectivo en Av. Bellavista");
    foto1.setDescripcion("Jóvenes pintando un mural colaborativo.");
    foto1.setOrden(1);
    em.persist(foto1);

    var foto2 = new GaleriaFoto();
    foto2.setActividad(actividad);
    foto2.setUrlImagen("https://i.santiagojoven.org/galeria/mural-bellavista-02.jpg");
    foto2.setTitulo("Grupo de participantes");
    foto2.setDescripcion("Foto grupal al finalizar la jornada.");
    foto2.setOrden(2);
    em.persist(foto2);
  }

  @Test
  void findByActividadIdOrderByOrdenAsc_debeRetornarFotosOrdenadas() {
    var resultados = repository.findByActividadIdOrderByOrdenAsc(actividad.getId());

    assertThat(resultados).hasSize(2);
    assertThat(resultados.get(0).getTitulo()).isEqualTo("Mural colectivo en Av. Bellavista");
    assertThat(resultados.get(1).getTitulo()).isEqualTo("Grupo de participantes");
  }

  @Test
  void findByActividadIdOrderByOrdenAsc_debeRetornarVacio_sinFotos() {
    var resultados = repository.findByActividadIdOrderByOrdenAsc(java.util.UUID.randomUUID());

    assertThat(resultados).isEmpty();
  }
}
