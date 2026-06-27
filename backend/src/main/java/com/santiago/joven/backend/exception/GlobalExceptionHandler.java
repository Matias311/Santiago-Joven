package com.santiago.joven.backend.exception;

import com.santiago.joven.backend.dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/** Manejador global de excepciones usando {@link ControllerAdvice}. */
@ControllerAdvice
public class GlobalExceptionHandler {

  private ErrorResponse buildResponse(HttpStatus status, String message, WebRequest request) {
    return new ErrorResponse(
        status.value(), status.getReasonPhrase(), message, request.getDescription(false));
  }

  private ErrorResponse buildResponse(
      HttpStatus status, String message, WebRequest request, List<String> details) {
    return new ErrorResponse(
        status.value(), status.getReasonPhrase(), message, request.getDescription(false), details);
  }

  /**
   * Maneja errores de validacion de {@code @Valid} en request bodies.
   *
   * @param ex excepcion lanzada por validacion fallida
   * @param request peticion actual
   * @return 400 Bad Request con detalles de cada campo invalido
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidation(
      MethodArgumentNotValidException ex, WebRequest request) {
    var details =
        ex.getBindingResult().getFieldErrors().stream()
            .map(f -> f.getField() + ": " + f.getDefaultMessage())
            .collect(Collectors.toList());
    var response = buildResponse(HttpStatus.BAD_REQUEST, "Error de validacion", request, details);
    return ResponseEntity.badRequest().body(response);
  }

  /**
   * Maneja errores de validacion en parametros de ruta/consulta ({@code @PathVariable},
   * {@code @RequestParam}).
   *
   * @param ex excepcion lanzada por validacion fallida
   * @param request peticion actual
   * @return 400 Bad Request con detalles
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolation(
      ConstraintViolationException ex, WebRequest request) {
    var details =
        ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.toList());
    var response = buildResponse(HttpStatus.BAD_REQUEST, "Error de validacion", request, details);
    return ResponseEntity.badRequest().body(response);
  }

  /**
   * Maneja errores de cuerpo de peticion malformado (JSON invalido, tipos incorrectos).
   *
   * @param ex excepcion lanzada
   * @param request peticion actual
   * @return 400 Bad Request
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex, WebRequest request) {
    var response = buildResponse(HttpStatus.BAD_REQUEST, "Cuerpo de peticion invalido", request);
    return ResponseEntity.badRequest().body(response);
  }

  /**
   * Maneja errores de conversion de tipos en parametros de ruta/consulta (ej. UUID invalido).
   *
   * @param ex excepcion lanzada
   * @param request peticion actual
   * @return 400 Bad Request
   */
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex, WebRequest request) {
    var response =
        buildResponse(
            HttpStatus.BAD_REQUEST,
            "Parametro invalido: "
                + ex.getName()
                + " debe ser "
                + ex.getRequiredType().getSimpleName(),
            request);
    return ResponseEntity.badRequest().body(response);
  }

  /**
   * Maneja entidades no encontradas lanzadas desde servicios.
   *
   * @param ex excepcion lanzada
   * @param request peticion actual
   * @return 404 Not Found
   */
  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleEntityNotFound(
      EntityNotFoundException ex, WebRequest request) {
    var response = buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  /**
   * Maneja violaciones de integridad referencial (duplicados, FK, etc.).
   *
   * @param ex excepcion lanzada
   * @param request peticion actual
   * @return 409 Conflict
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
      DataIntegrityViolationException ex, WebRequest request) {
    var response = buildResponse(HttpStatus.CONFLICT, "Conflicto de integridad de datos", request);
    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }

  /**
   * Captura cualquier otra excepcion no manejada especificamente.
   *
   * @param ex excepcion lanzada
   * @param request peticion actual
   * @return 500 Internal Server Error
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleAllUncaught(Exception ex, WebRequest request) {
    var response =
        buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", request);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}
