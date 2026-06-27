package com.santiago.joven.backend.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Ubicacion geografica asociada a actividades y talleres. */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ubicaciones")
public class Ubicacion extends BaseEntity {

  @Column(nullable = false)
  private String nombre;

  private String direccion;

  private String ciudad;

  private BigDecimal latitud;

  private BigDecimal longitud;

  private LocalDateTime fechaCreacion;

  @OneToMany(mappedBy = "ubicacion")
  private Set<ActividadTaller> actividadesTalleres;

  @PrePersist
  public void prePersistUbicacion() {
    if (fechaCreacion == null) {
      fechaCreacion = LocalDateTime.now();
    }
  }
}
