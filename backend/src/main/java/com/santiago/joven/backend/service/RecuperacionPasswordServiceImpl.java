package com.santiago.joven.backend.service;

import com.santiago.joven.backend.model.entity.CodigoRecuperacion;
import com.santiago.joven.backend.repository.CodigoRecuperacionRepository;
import com.santiago.joven.backend.repository.UsuarioRepository;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RecuperacionPasswordServiceImpl implements RecuperacionPasswordService {

  private static final int EXPIRACION_MINUTOS = 5;

  private final CodigoRecuperacionRepository codigoRepository;
  private final UsuarioRepository usuarioRepository;
  private final EmailService emailService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void solicitarCodigo(String email) {
    if (!usuarioRepository.existsByEmail(email)) {
      return;
    }

    var codigo = String.format("%05d", ThreadLocalRandom.current().nextInt(10000, 100000));
    var now = LocalDateTime.now();

    var entity = new CodigoRecuperacion();
    entity.setEmail(email);
    entity.setCodigo(codigo);
    entity.setExpiracion(now.plusMinutes(EXPIRACION_MINUTOS));
    entity.setUsado(false);
    entity.setFechaCreacion(now);
    codigoRepository.save(entity);

    emailService.enviarCodigoRecuperacion(email, codigo);
  }

  @Override
  public void restablecerPassword(String email, String codigo, String nuevaPassword) {
    var now = LocalDateTime.now();
    var opt =
        codigoRepository.findByEmailAndCodigoAndUsadoFalseAndExpiracionAfter(email, codigo, now);

    var entity =
        opt.orElseThrow(
            () -> new IllegalStateException("Codigo invalido o expirado"));

    entity.setUsado(true);

    var usuario =
        usuarioRepository
            .findByEmail(email)
            .orElseThrow(
                () -> new IllegalStateException("Usuario no encontrado"));

    usuario.setPassword(passwordEncoder.encode(nuevaPassword));
  }
}
