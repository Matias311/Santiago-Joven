package com.santiago.joven.backend.model.entity;

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
 * Resena y calificacion (1-5) de un usuario a un recurso.
 *
 * <p>Relacion polimorfica mediante {@code recursoId} + {@code tipoRecurso}.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "resenas_calificaciones")
public class ResenaCalificacion extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

  @Column(nullable = false)
  private UUID recursoId;

  @Enumerated(EnumType.STRING)
  private TipoRecurso tipoRecurso;

  private Integer calificacion;

  @Column(columnDefinition = "TEXT")
  private String comentario;

  private LocalDateTime fechaCreacion;

  @PrePersist
  public void prePersistResena() {
    if (fechaCreacion == null) {
      fechaCreacion = LocalDateTime.now();
    }
  }
}
