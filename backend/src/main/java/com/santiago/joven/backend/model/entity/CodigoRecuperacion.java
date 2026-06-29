package com.santiago.joven.backend.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = "codigos_recuperacion",
    indexes = @Index(name = "idx_codigos_recuperacion_email_codigo", columnList = "email,codigo"))
public class CodigoRecuperacion {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false, length = 5)
  private String codigo;

  @Column(nullable = false)
  private LocalDateTime expiracion;

  @Column(nullable = false)
  private boolean usado;

  @Column(nullable = false)
  private LocalDateTime fechaCreacion;
}
