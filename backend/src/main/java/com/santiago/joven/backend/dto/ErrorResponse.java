package com.santiago.joven.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO para respuestas de error uniformes.
 *
 * @param timestamp momento en que ocurrio el error
 * @param status codigo HTTP
 * @param error titulo del error
 * @param message mensaje descriptivo
 * @param path ruta de la peticion
 * @param details lista de errores detallados (validacion de campos, etc.)
 */
public record ErrorResponse(
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    String path,
    List<String> details) {

  /**
   * Crea una respuesta de error sin detalles adicionales.
   *
   * @param status codigo HTTP
   * @param error titulo del error
   * @param message mensaje descriptivo
   * @param path ruta de la peticion
   */
  public ErrorResponse(int status, String error, String message, String path) {
    this(LocalDateTime.now(), status, error, message, path, List.of());
  }

  /**
   * Crea una respuesta de error con detalles adicionales.
   *
   * @param status codigo HTTP
   * @param error titulo del error
   * @param message mensaje descriptivo
   * @param path ruta de la peticion
   * @param details lista de errores detallados
   */
  public ErrorResponse(
      int status, String error, String message, String path, List<String> details) {
    this(LocalDateTime.now(), status, error, message, path, details);
  }
}
