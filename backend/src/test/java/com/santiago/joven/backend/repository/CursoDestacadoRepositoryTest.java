package com.santiago.joven.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.model.entity.Categoria;
import com.santiago.joven.backend.model.entity.CursoDestacado;
import com.santiago.joven.backend.model.entity.Usuario;
import com.santiago.joven.backend.model.enums.TipoCategoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class CursoDestacadoRepositoryTest {

  @Autowired
  private CursoDestacadoRepository repository;

  @Autowired
  private TestEntityManager em;

  private CursoDestacado cursoActivo;
  private Categoria categoria;

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
    categoria.setNombre("Desarrollo Web");
    categoria.setTipo(TipoCategoria.CURSO);
    em.persist(categoria);

    var otraCategoria = new Categoria();
    otraCategoria.setNombre("Diseño Gráfico");
    otraCategoria.setTipo(TipoCategoria.CURSO);
    em.persist(otraCategoria);

    cursoActivo = new CursoDestacado();
    cursoActivo.setTitulo("Desarrollo Web Full-Stack");
    cursoActivo.setDescripcion("Aprende HTML, CSS, JavaScript y Django desde cero.");
    cursoActivo.setObjetivo("Formar desarrolladores web para el mercado laboral chileno.");
    cursoActivo.setActivo(true);
    cursoActivo.setOrden(1);
    cursoActivo.setCategoria(categoria);
    cursoActivo.setUsuarioCreador(usuario);
    em.persist(cursoActivo);

    var cursoInactivo = new CursoDestacado();
    cursoInactivo.setTitulo("Diseño UX/UI avanzado");
    cursoInactivo.setDescripcion("Curso de Figma y diseño de interfaces.");
    cursoInactivo.setObjetivo("Especializar diseñadores junior.");
    cursoInactivo.setActivo(false);
    cursoInactivo.setOrden(2);
    cursoInactivo.setCategoria(otraCategoria);
    cursoInactivo.setUsuarioCreador(usuario);
    em.persist(cursoInactivo);
  }

  @Test
  void findByCategoriaIdOrderByOrdenAsc_debeFiltrarPorCategoria() {
    var resultados = repository.findByCategoriaIdOrderByOrdenAsc(categoria.getId());

    assertThat(resultados).hasSize(1).extracting(CursoDestacado::getTitulo).containsExactly("Desarrollo Web Full-Stack");
  }

  @Test
  void findByActivoTrueOrderByOrdenAsc_debeRetornarActivos() {
    var resultados = repository.findByActivoTrueOrderByOrdenAsc();

    assertThat(resultados).hasSize(1).extracting(CursoDestacado::getTitulo).containsExactly("Desarrollo Web Full-Stack");
  }
}
