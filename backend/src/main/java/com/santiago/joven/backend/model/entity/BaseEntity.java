package com.santiago.joven.backend.model.entity;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

/**
 * Base con identificador UUID autogenerado via {@code @PrePersist}.
 *
 * <p>Todas las entidades del dominio heredan de esta clase para garantizar un PK uniforme generado
 * del lado de la aplicacion.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

  @Id private UUID id;

  @PrePersist
  public void prePersist() {
    if (id == null) {
      id = UUID.randomUUID();
    }
  }
}
