package com.santiago.joven.backend.model.entity;

import com.santiago.joven.backend.model.enums.EstadoActividad;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Actividad o taller presencial/virtual.
 *
 * <p>Tiene fecha/hora, ubicacion opcional, capacidad maxima e inscripciones. El estado indica si
 * esta confirmado, pendiente o cancelado. Puede contener una {@link GaleriaFoto} asociada.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "actividades_talleres")
public class ActividadTaller extends AuditableEntity {

  @Column(nullable = false)
  private String titulo;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String descripcion;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "categoria_id")
  private Categoria categoria;

  @Column(nullable = false)
  private LocalDateTime fechaHora;

  @Column(nullable = false)
  private Boolean activo = true;

  private Integer cantidadMaximaParticipantes;

  private String imagen;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ubicacion_id")
  private Ubicacion ubicacion;

  private String enlaceInscripcion;

  private Integer inscritos = 0;

  @Enumerated(EnumType.STRING)
  private EstadoActividad estado;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "usuario_creador_id")
  private Usuario usuarioCreador;

  @OneToMany(mappedBy = "actividad")
  private Set<GaleriaFoto> galeriaFotos;
}
