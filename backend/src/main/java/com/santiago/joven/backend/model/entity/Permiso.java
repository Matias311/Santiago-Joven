package com.santiago.joven.backend.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Permiso granular (CREATE_COURSE, EDIT_ACTIVITY, etc.).
 *
 * <p>Asociado a {@link Rol} mediante la tabla roles_permisos.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "permisos")
public class Permiso extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String nombre;

  @Column(columnDefinition = "TEXT")
  private String descripcion;

  private String modulo;

  private LocalDateTime fechaCreacion;

  @PrePersist
  public void prePersistPermiso() {
    if (fechaCreacion == null) {
      fechaCreacion = LocalDateTime.now();
    }
  }
}
