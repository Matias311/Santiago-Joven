package com.santiago.joven.backend.model.entity;

import com.santiago.joven.backend.model.enums.NombreRol;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Rol de seguridad (ADMIN, MODERATOR, VOLUNTEER, USER).
 *
 * <p>Se relaciona N:N con {@link Usuario} (via usuarios_roles) y N:N con {@link Permiso} (via
 * roles_permisos).
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Rol extends BaseEntity {

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, unique = true)
  private NombreRol nombre;

  @Column(columnDefinition = "TEXT")
  private String descripcion;

  private LocalDateTime fechaCreacion;

  @ManyToMany(mappedBy = "roles")
  private Set<Usuario> usuarios;

  @ManyToMany
  @JoinTable(
      name = "roles_permisos",
      joinColumns = @JoinColumn(name = "rol_id"),
      inverseJoinColumns = @JoinColumn(name = "permiso_id"))
  private Set<Permiso> permisos;

  public Rol(NombreRol nombre) {
    this.nombre = nombre;
  }

  @PrePersist
  public void prePersistRol() {
    if (fechaCreacion == null) {
      fechaCreacion = LocalDateTime.now();
    }
  }
}
