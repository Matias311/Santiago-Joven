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
 * Asesoria ofrecida por la organizacion.
 *
 * <p>Incluye definicion, objetivos y metodologia como campos de texto largo. Pertenece a una {@link
 * Categoria} y es creada por un {@link Usuario}.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "asesorias")
public class Asesoria extends AuditableEntity {

  @Column(nullable = false)
  private String titulo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "categoria_id")
  private Categoria categoria;

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
