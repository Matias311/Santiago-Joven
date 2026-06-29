package com.santiago.joven.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.santiago.joven.backend.model.entity.CodigoRecuperacion;
import com.santiago.joven.backend.model.entity.Usuario;
import com.santiago.joven.backend.repository.CodigoRecuperacionRepository;
import com.santiago.joven.backend.repository.UsuarioRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class RecuperacionPasswordServiceImplTest {

  @Mock private CodigoRecuperacionRepository codigoRepository;
  @Mock private UsuarioRepository usuarioRepository;
  @Mock private PasswordEncoder passwordEncoder;
  @Mock private ApplicationEventPublisher eventPublisher;

  @InjectMocks private RecuperacionPasswordServiceImpl service;

  @Captor private ArgumentCaptor<CodigoRecuperacion> codigoCaptor;
  @Captor private ArgumentCaptor<CodigoRecuperacionGeneradoEvent> eventCaptor;

  private static final String EMAIL = "user@santiagojoven.org";

  @Test
  void solicitarCodigo_cuandoEmailExiste_invalidaCodigosPreviosYPublicaEvento() {
    when(usuarioRepository.existsByEmail(EMAIL)).thenReturn(true);

    service.solicitarCodigo(EMAIL);

    verify(codigoRepository).marcarCodigosActivosComoUsados(EMAIL);
    verify(codigoRepository).saveAndFlush(codigoCaptor.capture());
    var saved = codigoCaptor.getValue();
    assertThat(saved.getEmail()).isEqualTo(EMAIL);
    assertThat(saved.getCodigo()).hasSize(5);
    assertThat(saved.isUsado()).isFalse();
    assertThat(saved.getExpiracion()).isAfter(LocalDateTime.now());

    verify(eventPublisher).publishEvent(eventCaptor.capture());
    assertThat(eventCaptor.getValue().email()).isEqualTo(EMAIL);
    assertThat(eventCaptor.getValue().codigo()).isEqualTo(saved.getCodigo());
  }

  @Test
  void solicitarCodigo_cuandoEmailNoExiste_noHaceNada() {
    when(usuarioRepository.existsByEmail(EMAIL)).thenReturn(false);

    service.solicitarCodigo(EMAIL);

    verify(codigoRepository, never()).marcarCodigosActivosComoUsados(any());
    verify(codigoRepository, never()).saveAndFlush(any());
    verify(eventPublisher, never()).publishEvent(any());
  }

  @Test
  void restablecerPassword_conCodigoValido_actualizaPassword() {
    var entity = new CodigoRecuperacion();
    entity.setEmail(EMAIL);
    entity.setCodigo("12345");
    entity.setUsado(false);
    entity.setExpiracion(LocalDateTime.now().plusMinutes(5));

    var usuario = new Usuario();
    usuario.setEmail(EMAIL);
    usuario.setPassword("old-hash");

    when(codigoRepository.findByEmailAndCodigoAndUsadoFalseAndExpiracionAfter(
            eq(EMAIL), eq("12345"), any(LocalDateTime.class)))
        .thenReturn(Optional.of(entity));
    when(usuarioRepository.findByEmail(EMAIL)).thenReturn(Optional.of(usuario));
    when(passwordEncoder.encode("new-password")).thenReturn("new-hash");

    service.restablecerPassword(EMAIL, "12345", "new-password");

    assertThat(entity.isUsado()).isTrue();
    assertThat(usuario.getPassword()).isEqualTo("new-hash");
  }

  @Test
  void restablecerPassword_conCodigoInvalido_lanzaExcepcion() {
    when(codigoRepository.findByEmailAndCodigoAndUsadoFalseAndExpiracionAfter(
            eq(EMAIL), eq("00000"), any(LocalDateTime.class)))
        .thenReturn(Optional.empty());

    assertThatThrownBy(
            () -> service.restablecerPassword(EMAIL, "00000", "new-password"))
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("Codigo invalido");
  }
}
