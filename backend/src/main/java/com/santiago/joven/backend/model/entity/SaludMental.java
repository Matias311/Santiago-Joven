package com.santiago.joven.backend.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Recurso de salud mental (lineas de ayuda, contactos).
 *
 * <p>Entidad simple sin fechas de auditoria.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "salud_mental")
public class SaludMental extends BaseEntity {

  @Column(nullable = false)
  private String titulo;

  @Column(columnDefinition = "TEXT")
  private String descripcion;

  private String icono;

  private String telefono;

  private String enlace;

  private Integer orden;
}
