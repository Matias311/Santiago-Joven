package com.santiago.joven.backend.model.entity;

import com.santiago.joven.backend.model.enums.TipoRecurso;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Estadisticas agregadas de un recurso (inscritos, asistentes, resenas, calificacion promedio).
 *
 * <p>Relacion polimorfica mediante {@code recursoId} + {@code tipoRecurso}. Se actualiza
 * periodicamente o bajo demanda.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "estadisticas")
public class Estadistica extends BaseEntity {

  @Enumerated(EnumType.STRING)
  private TipoRecurso tipoRecurso;

  @Column(nullable = false)
  private UUID recursoId;

  private Integer totalInscritos = 0;

  private Integer totalAsistentes = 0;

  private Integer totalResenas = 0;

  @Column(precision = 3, scale = 2)
  private BigDecimal promedioCalificacion;

  private LocalDateTime fechaActualizacion;

  @PrePersist
  public void prePersistEstadistica() {
    if (fechaActualizacion == null) {
      fechaActualizacion = LocalDateTime.now();
    }
  }

  public void actualizarFecha() {
    this.fechaActualizacion = LocalDateTime.now();
  }
}
