package com.santiago.joven.backend.model.entity;

import com.santiago.joven.backend.model.enums.TipoCambio;
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
 * Registro de auditoria para trazabilidad de cambios.
 *
 * <p>Almacena el tipo de entidad modificada, el usuario responsable, el tipo de cambio y los
 * valores anteriores/nuevos como JSON en formato TEXT (portable entre H2 y PostgreSQL).
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "auditoria")
public class Auditoria extends BaseEntity {

  private String entidadTipo;

  @Column(nullable = false)
  private UUID entidadId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_id", nullable = false)
  private Usuario usuario;

  @Enumerated(EnumType.STRING)
  private TipoCambio tipoCambio;

  @Column(columnDefinition = "TEXT")
  private String cambiosAnteriores;

  @Column(columnDefinition = "TEXT")
  private String cambiosNuevos;

  private LocalDateTime fecha;

  @PrePersist
  public void prePersistAuditoria() {
    if (fecha == null) {
      fecha = LocalDateTime.now();
    }
  }
}
