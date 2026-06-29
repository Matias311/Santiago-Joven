package com.santiago.joven.backend.service;

public interface RecuperacionPasswordService {

  void solicitarCodigo(String email);

  void restablecerPassword(String email, String codigo, String nuevaPassword);
}
