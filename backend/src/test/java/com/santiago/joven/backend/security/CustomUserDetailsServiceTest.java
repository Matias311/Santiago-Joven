package com.santiago.joven.backend.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.santiago.joven.backend.model.entity.Usuario;
import com.santiago.joven.backend.repository.UsuarioRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

  @Mock private UsuarioRepository usuarioRepository;
  @InjectMocks private CustomUserDetailsService service;

  @Test
  void loadUserByUsername_cuandoExiste_debeRetornarCustomUserDetails() {
    var usuario = new Usuario();
    usuario.setId(UUID.randomUUID());
    usuario.setEmail("test@santiagojoven.org");
    usuario.setPassword("encoded");

    when(usuarioRepository.findByEmailWithRoles("test@santiagojoven.org"))
        .thenReturn(Optional.of(usuario));

    var details = service.loadUserByUsername("test@santiagojoven.org");

    assertThat(details).isInstanceOf(CustomUserDetails.class);
    assertThat(details.getUsername()).isEqualTo("test@santiagojoven.org");
    assertThat(details.getPassword()).isEqualTo("encoded");
  }

  @Test
  void loadUserByUsername_cuandoNoExiste_debeLanzarExcepcion() {
    when(usuarioRepository.findByEmailWithRoles("no-existe@test.cl"))
        .thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.loadUserByUsername("no-existe@test.cl"))
        .isInstanceOf(UsernameNotFoundException.class)
        .hasMessageContaining("no-existe@test.cl");
  }
}
