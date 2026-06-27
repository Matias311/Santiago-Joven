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
 * Iniciativa o accion destacada de la organizacion.
 *
 * <p>Entidad simple con titulo, descripcion e imagen opcional.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "accion_joven")
public class AccionJoven extends AuditableEntity {

  @Column(nullable = false)
  private String titulo;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String descripcion;

  private String imagen;

  @Column(nullable = false)
  private Boolean activo = true;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_creador_id")
  private Usuario usuarioCreador;
}
