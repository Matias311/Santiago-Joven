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
 * Foto asociada a una {@link ActividadTaller}.
 *
 * <p>Incluye URL de la imagen y metadatos (titulo, descripcion, orden).
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "galeria_fotos")
public class GaleriaFoto extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "actividad_id", nullable = false)
  private ActividadTaller actividad;

  @Column(nullable = false)
  private String urlImagen;

  private String titulo;

  @Column(columnDefinition = "TEXT")
  private String descripcion;

  private Integer orden;

  private LocalDateTime fechaCreacion;

  @PrePersist
  public void prePersistGaleria() {
    if (fechaCreacion == null) {
      fechaCreacion = LocalDateTime.now();
    }
  }
}
