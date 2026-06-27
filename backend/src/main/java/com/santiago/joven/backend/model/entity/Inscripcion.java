package com.santiago.joven.backend.model.entity;

import com.santiago.joven.backend.model.enums.EstadoInscripcion;
import com.santiago.joven.backend.model.enums.TipoRecurso;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Inscripcion de un {@link Usuario} a un recurso (actividad, curso o asesoria).
 *
 * <p>La relacion es polimorfica: {@code recursoId} + {@code tipoRecurso} identifican el recurso
 * objetivo sin FK fisica.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "inscripciones")
public class Inscripcion extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

  @Column(nullable = false)
  private UUID recursoId;

  @Enumerated(EnumType.STRING)
  private TipoRecurso tipoRecurso;

  private LocalDateTime fechaInscripcion;

  @Enumerated(EnumType.STRING)
  private EstadoInscripcion estado;

  @Column(columnDefinition = "TEXT")
  private String notas;

  @PrePersist
  public void prePersistInscripcion() {
    if (fechaInscripcion == null) {
      fechaInscripcion = LocalDateTime.now();
    }
  }
}
