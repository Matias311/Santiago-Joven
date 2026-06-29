package com.santiago.joven.backend.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Seccion "Tu contribucion cuenta" con enlace a formulario externo. */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tu_contribucion_cuenta")
public class TuContribucionCuenta extends BaseEntity {

  @Column(nullable = false)
  private String titulo;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String descripcion;

  @Column(nullable = false)
  private String linkGoogleForms;

  @Column(nullable = false)
  private Boolean activo = true;

  private LocalDateTime fechaActualizacion;

  @PrePersist
  public void prePersistContribucion() {
    this.fechaActualizacion = LocalDateTime.now();
  }

  public void actualizarFecha() {
    this.fechaActualizacion = LocalDateTime.now();
  }
}
