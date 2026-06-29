package com.santiago.joven.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import org.springframework.boot.mail.autoconfigure.MailProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender mailSender;
  private final MailProperties mailProperties;

  @Value("${app.mail.from}")
  private String from;

  @Value("${app.mail.brevo-api-key:}")
  private String brevoApiKey;

  @Value("${app.mail.brevo-url:https://api.brevo.com/v3/smtp/email}")
  private String brevoUrl;

  @Value("${app.mail.brevo-timeout-ms:5000}")
  private int brevoTimeoutMs;

  @Override
  public void enviarCodigoRecuperacion(String destinatario, String codigo) {
    var asunto = "Recuperacion de contrasena - Santiago Joven";
    var texto =
        """
        Has solicitado restablecer tu contrasena.

        Tu codigo de verificacion es: %s

        Este codigo expira en 5 minutos.

        Si no solicitaste este cambio, ignora este mensaje.
        """
            .formatted(codigo);

    if (StringUtils.hasText(brevoApiKey)) {
      enviarPorBrevoApi(destinatario, asunto, texto);
      return;
    }

    var mensaje = new SimpleMailMessage();
    mensaje.setFrom(from);
    mensaje.setTo(destinatario);
    mensaje.setSubject(asunto);
    mensaje.setText(texto);

    var host = mailProperties.getHost();
    if (host != null && !host.equals("localhost") && !host.isEmpty()) {
      mailSender.send(mensaje);
      log.info("OTP enviado a {}", destinatario);
    } else {
      log.info("=== OTP para {}: {} ===", destinatario, codigo);
    }
  }

  private void enviarPorBrevoApi(String destinatario, String asunto, String texto) {
    var requestFactory = new SimpleClientHttpRequestFactory();
    var timeout = Duration.ofMillis(brevoTimeoutMs);
    requestFactory.setConnectTimeout(timeout);
    requestFactory.setReadTimeout(timeout);

    RestClient.builder()
        .requestFactory(requestFactory)
        .build()
        .post()
        .uri(brevoUrl)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .header("api-key", brevoApiKey)
        .body(
            Map.of(
                "sender", Map.of("email", from, "name", "Santiago Joven"),
                "to", List.of(Map.of("email", destinatario)),
                "subject", asunto,
                "textContent", texto))
        .retrieve()
        .toBodilessEntity();

    log.info("OTP enviado por API Brevo a {}", destinatario);
  }
}
