package com.santiago.joven.backend.security;

import static org.assertj.core.api.Assertions.assertThat;

import com.santiago.joven.backend.model.entity.Permiso;
import com.santiago.joven.backend.model.entity.Rol;
import com.santiago.joven.backend.model.entity.Usuario;
import com.santiago.joven.backend.model.enums.NombreRol;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CustomUserDetailsTest {

  @Test
  void isEnabled_cuandoActivo_debeRetornarTrue() {
    var usuario = new Usuario();
    usuario.setActivo(true);
    var details = new CustomUserDetails(usuario);
    assertThat(details.isEnabled()).isTrue();
  }

  @Test
  void isEnabled_cuandoInactivo_debeRetornarFalse() {
    var usuario = new Usuario();
    usuario.setActivo(false);
    var details = new CustomUserDetails(usuario);
    assertThat(details.isEnabled()).isFalse();
  }

  @Test
  void getUsername_debeRetornarEmail() {
    var usuario = new Usuario();
    usuario.setEmail("test@santiagojoven.org");
    var details = new CustomUserDetails(usuario);
    assertThat(details.getUsername()).isEqualTo("test@santiagojoven.org");
  }

  @Test
  void getPassword_debeRetornarPassword() {
    var usuario = new Usuario();
    usuario.setPassword("encodedPassword123");
    var details = new CustomUserDetails(usuario);
    assertThat(details.getPassword()).isEqualTo("encodedPassword123");
  }

  @Test
  void isAccountNonExpired_debeRetornarTrue() {
    var details = new CustomUserDetails(new Usuario());
    assertThat(details.isAccountNonExpired()).isTrue();
  }

  @Test
  void isAccountNonLocked_debeRetornarTrue() {
    var details = new CustomUserDetails(new Usuario());
    assertThat(details.isAccountNonLocked()).isTrue();
  }

  @Test
  void isCredentialsNonExpired_debeRetornarTrue() {
    var details = new CustomUserDetails(new Usuario());
    assertThat(details.isCredentialsNonExpired()).isTrue();
  }

  @Test
  void getAuthorities_conRolesYPermisos_debeIncluirRolesYPermisos() {
    var permiso = new Permiso();
    permiso.setNombre("MANAGE_USERS");
    var rol = new Rol();
    rol.setNombre(NombreRol.ADMIN);
    rol.setPermisos(Set.of(permiso));
    var usuario = new Usuario();
    usuario.setRoles(Set.of(rol));

    var details = new CustomUserDetails(usuario);
    var authorities = details.getAuthorities();

    assertThat(authorities)
        .hasSize(2)
        .extracting(Object::toString)
        .containsExactlyInAnyOrder("ROLE_ADMIN", "PERMISSION_MANAGE_USERS");
  }

  @Test
  void getAuthorities_sinRoles_debeRetornarVacio() {
    var usuario = new Usuario();
    var details = new CustomUserDetails(usuario);
    assertThat(details.getAuthorities()).isEmpty();
  }
}
