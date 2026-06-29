package com.santiago.joven.backend.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Usuario del sistema.
 *
 * <p>Contiene credenciales (email/password hasheada con BCrypt), datos personales y relaciones con
 * roles, contenidos creados, inscripciones, resenas, contactos y auditoria.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario extends AuditableEntity {

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String nombre;

  @Column(nullable = false)
  private String apellido;

  @Column(nullable = false)
  private Boolean activo = true;

  @ManyToMany
  @JoinTable(
      name = "usuarios_roles",
      joinColumns = @JoinColumn(name = "usuario_id"),
      inverseJoinColumns = @JoinColumn(name = "rol_id"))
  private Set<Rol> roles = new java.util.HashSet<>();

  @OneToMany(mappedBy = "usuarioCreador")
  private Set<Asesoria> asesorias;

  @OneToMany(mappedBy = "usuarioCreador")
  private Set<CursoDestacado> cursosDestacados;

  @OneToMany(mappedBy = "usuarioCreador")
  private Set<AccionJoven> accionesJoven;

  @OneToMany(mappedBy = "usuarioCreador")
  private Set<Programa> programas;

  @OneToMany(mappedBy = "usuarioCreador")
  private Set<ActividadTaller> actividadesTalleres;

  @OneToMany(mappedBy = "usuario")
  private Set<Inscripcion> inscripciones;

  @OneToMany(mappedBy = "usuario")
  private Set<ResenaCalificacion> resenas;

  @OneToMany(mappedBy = "usuarioRespondio")
  private Set<Contacto> contactosRespondidos;

  @OneToMany(mappedBy = "usuario")
  private Set<Auditoria> auditorias;
}
