package com.santiago.joven.backend.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Solicitud de contacto del formulario publico.
 *
 * <p>Incluye datos del remitente, mensaje y trazabilidad de respuesta por parte de un {@link
 * Usuario} del sistema.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "contactos")
public class Contacto extends BaseEntity {

  @Column(nullable = false)
  private String nombre;

  @Column(nullable = false)
  private String email;

  private String telefono;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String mensaje;

  private String programaInteres;

  private LocalDateTime fechaContacto;

  private Boolean respondido = false;

  @Column(columnDefinition = "TEXT")
  private String respuesta;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_respondio_id")
  private Usuario usuarioRespondio;

  private LocalDateTime fechaRespuesta;

  @PrePersist
  public void prePersistContacto() {
    if (fechaContacto == null) {
      fechaContacto = LocalDateTime.now();
    }
  }
}
