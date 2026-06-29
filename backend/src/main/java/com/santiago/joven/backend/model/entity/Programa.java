package com.santiago.joven.backend.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Programa educativo de la organizacion.
 *
 * <p>Similar a {@link Asesoria} pero con un alcance mayor. Incluye definicion, objetivos y
 * metodologia.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "programas")
public class Programa extends AuditableEntity {

  @Column(nullable = false)
  private String titulo;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String descripcion;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String definicion;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String objetivos;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String metodologia;

  private String imagen;

  @Column(nullable = false)
  private Boolean activo = true;

  private Integer orden;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_creador_id")
  private Usuario usuarioCreador;
}
