package com.santiago.joven.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class CodigoRecuperacionEmailListener {

  private final EmailService emailService;

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void enviarCodigo(CodigoRecuperacionGeneradoEvent event) {
    try {
      emailService.enviarCodigoRecuperacion(event.email(), event.codigo());
    } catch (RuntimeException ex) {
      log.error("No se pudo enviar OTP de recuperacion a {}", event.email(), ex);
    }
  }
}
