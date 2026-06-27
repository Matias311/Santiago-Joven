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
 * Curso destacado visible en la landing page.
 *
 * <p>Contiene informacion promocional (eslogan, objetivo, enlace de inscripcion) y pertenece a una
 * {@link Categoria}.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cursos_destacados")
public class CursoDestacado extends AuditableEntity {

  @Column(nullable = false)
  private String titulo;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String descripcion;

  private String eslogan;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String objetivo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "categoria_id")
  private Categoria categoria;

  private String imagen;

  @Column(nullable = false)
  private Boolean activo = true;

  private Integer orden;

  private String enlaceInscripcion;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_creador_id")
  private Usuario usuarioCreador;
}
