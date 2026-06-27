package com.santiago.joven.backend.model.entity;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * Extension de {@link BaseEntity} que agrega marcas de tiempo {@code fechaCreacion} y {@code
 * fechaActualizacion}.
 *
 * <p>Los valores se asignan automaticamente con {@link LocalDateTime#now()} en los callbacks
 * {@code @PrePersist} y {@code @PreUpdate}.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class AuditableEntity extends BaseEntity {

  private LocalDateTime fechaCreacion;

  private LocalDateTime fechaActualizacion;

  @PrePersist
  public void prePersistAudit() {
    this.fechaCreacion = LocalDateTime.now();
    this.fechaActualizacion = LocalDateTime.now();
  }

  @PreUpdate
  public void preUpdateAudit() {
    this.fechaActualizacion = LocalDateTime.now();
  }
}
