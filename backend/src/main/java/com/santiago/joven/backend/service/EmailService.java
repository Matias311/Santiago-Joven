package com.santiago.joven.backend.service;

public interface EmailService {

  void enviarCodigoRecuperacion(String destinatario, String codigo);
}
