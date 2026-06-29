package com.santiago.joven.backend.model.entity;

import com.santiago.joven.backend.model.enums.TipoCategoria;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Categoria que clasifica asesorias, cursos y actividades.
 *
 * <p>Incluye datos de presentacion (icono, color) y un {@code tipo} que restringe su uso (ASESORIA,
 * CURSO, ACTIVIDAD o GENERAL).
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categorias")
public class Categoria extends BaseEntity {

  @Column(nullable = false, unique = true)
  private String nombre;

  @Column(columnDefinition = "TEXT")
  private String descripcion;

  private String icono;

  private String color;

  @Enumerated(EnumType.STRING)
  private TipoCategoria tipo;

  private LocalDateTime fechaCreacion;

  @OneToMany(mappedBy = "categoria")
  private Set<Asesoria> asesorias;

  @OneToMany(mappedBy = "categoria")
  private Set<CursoDestacado> cursosDestacados;

  @OneToMany(mappedBy = "categoria")
  private Set<ActividadTaller> actividadesTalleres;

  @PrePersist
  public void prePersistCategoria() {
    if (fechaCreacion == null) {
      fechaCreacion = LocalDateTime.now();
    }
  }
}
