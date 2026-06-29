package com.santiago.joven.backend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.model.entity.Permiso;
import com.santiago.joven.backend.model.entity.Rol;
import com.santiago.joven.backend.model.entity.Usuario;
import com.santiago.joven.backend.model.enums.NombreRol;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class UsuarioRepositoryTest {

  @Autowired
  private UsuarioRepository repository;

  @Autowired
  private TestEntityManager em;

  private Usuario usuario;

  @BeforeEach
  void setUp() {
    usuario = new Usuario();
    usuario.setEmail("benjamin.munoz@santiagojoven.org");
    usuario.setPassword("password123");
    usuario.setNombre("Benjamín");
    usuario.setApellido("Muñoz");
    usuario.setActivo(true);
    em.persist(usuario);
  }

  @Test
  void findByEmail_debeRetornarUsuario_cuandoExiste() {
    var result = repository.findByEmail("benjamin.munoz@santiagojoven.org");

    assertThat(result).isPresent();
    assertThat(result.get().getEmail()).isEqualTo("benjamin.munoz@santiagojoven.org");
    assertThat(result.get().getNombre()).isEqualTo("Benjamín");
  }

  @Test
  void findByEmail_debeRetornarVacio_cuandoNoExiste() {
    var result = repository.findByEmail("noexiste@santiagojoven.org");

    assertThat(result).isEmpty();
  }

  @Test
  void existsByEmail_debeRetornarTrue_cuandoExiste() {
    assertThat(repository.existsByEmail("benjamin.munoz@santiagojoven.org")).isTrue();
  }

  @Test
  void existsByEmail_debeRetornarFalse_cuandoNoExiste() {
    assertThat(repository.existsByEmail("noexiste@santiagojoven.org")).isFalse();
  }

  @Test
  void findByEmailWithRoles_debeRetornarUsuarioConRoles() {
    var permiso = new Permiso();
    permiso.setNombre("PERMISSION_MANAGE_USERS");
    permiso.setModulo("USERS");
    em.persist(permiso);

    var rol = new Rol(NombreRol.ADMIN);
    rol.setPermisos(Set.of(permiso));
    em.persist(rol);

    usuario.setRoles(Set.of(rol));
    em.persist(usuario);
    em.flush();
    em.clear();

    var result = repository.findByEmailWithRoles("benjamin.munoz@santiagojoven.org");

    assertThat(result).isPresent();
    assertThat(result.get().getEmail()).isEqualTo("benjamin.munoz@santiagojoven.org");
    assertThat(result.get().getRoles())
        .hasSize(1)
        .extracting(r -> r.getNombre().name())
        .containsExactly("ADMIN");
  }

  @Test
  void findByEmailWithRoles_debeRetornarVacio_cuandoNoExiste() {
    var result = repository.findByEmailWithRoles("noexiste@santiagojoven.org");

    assertThat(result).isEmpty();
  }

  @Test
  void save_debePersistirUsuario() {
    var nuevo = new Usuario();
    nuevo.setEmail("isidora.mardones@santiagojoven.org");
    nuevo.setPassword("pass1234");
    nuevo.setNombre("Isidora");
    nuevo.setApellido("Mardones");
    nuevo.setActivo(true);

    var guardado = repository.save(nuevo);

    assertThat(guardado.getId()).isNotNull();
    assertThat(guardado.getEmail()).isEqualTo("isidora.mardones@santiagojoven.org");
  }

  @Test
  void delete_debeEliminarUsuario() {
    var id = usuario.getId();
    repository.deleteById(id);
    em.flush();

    assertThat(repository.findById(id)).isEmpty();
  }

  @Test
  void findAll_debeRetornarTodosLosUsuarios() {
    var otro = new Usuario();
    otro.setEmail("mateo.vasquez@santiagojoven.org");
    otro.setPassword("pass1234");
    otro.setNombre("Mateo");
    otro.setApellido("Vásquez");
    otro.setActivo(true);
    em.persist(otro);

    var usuarios = repository.findAll();

    assertThat(usuarios).hasSize(2);
  }

  @Test
  void findByEmail_debeSerCaseSensitive() {
    var result = repository.findByEmail("BENJAMIN.MUNOZ@SANTIAGOJOVEN.ORG");
    assertThat(result).isEmpty();
  }

  @Test
  void emailUnico_debeLanzarExcepcion_alDuplicar() {
    var duplicado = new Usuario();
    duplicado.setEmail("benjamin.munoz@santiagojoven.org");
    duplicado.setPassword("pass1234");
    duplicado.setNombre("Otro");
    duplicado.setApellido("User");
    duplicado.setActivo(true);

    assertThat(org.junit.jupiter.api.Assertions.assertThrows(
        jakarta.persistence.PersistenceException.class,
        () -> {
          em.persist(duplicado);
          em.flush();
        })).isNotNull();
  }

  @Test
  void prePersist_debeAsignarId() {
    assertThat(usuario.getId()).isNotNull();
  }

  @Test
  void prePersist_debeAsignarFechasAuditoria() {
    assertThat(usuario.getFechaCreacion()).isNotNull();
    assertThat(usuario.getFechaActualizacion()).isNotNull();
  }
}
