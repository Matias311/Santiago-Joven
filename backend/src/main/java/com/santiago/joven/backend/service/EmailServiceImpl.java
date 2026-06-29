package com.santiago.joven.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.mail.autoconfigure.MailProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;
  private final MailProperties mailProperties;

  @Value("${app.mail.from}")
  private String from;

  @Override
  public void enviarCodigoRecuperacion(String destinatario, String codigo) {
    var mensaje = new SimpleMailMessage();
    mensaje.setFrom(from);
    mensaje.setTo(destinatario);
    mensaje.setSubject("Recuperacion de contrasena - Santiago Joven");
    mensaje.setText(
        """
        Has solicitado restablecer tu contrasena.

        Tu codigo de verificacion es: %s

        Este codigo expira en 5 minutos.

        Si no solicitaste este cambio, ignora este mensaje.
        """
            .formatted(codigo));

    var host = mailProperties.getHost();
    if (host != null && !host.equals("localhost") && !host.isEmpty()) {
      mailSender.send(mensaje);
      log.info("OTP enviado a {}", destinatario);
    } else {
      log.info("=== OTP para {}: {} ===", destinatario, codigo);
    }
  }
}
