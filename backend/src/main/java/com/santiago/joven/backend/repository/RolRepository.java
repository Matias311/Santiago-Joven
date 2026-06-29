package com.santiago.joven.backend.repository;

import com.santiago.joven.backend.model.entity.Rol;
import com.santiago.joven.backend.model.enums.NombreRol;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repositorio para la entidad {@link Rol}. */
@Repository
public interface RolRepository extends JpaRepository<Rol, UUID> {

  /**
   * Busca un rol por su nombre del enum.
   *
   * @param nombre valor del enum {@link NombreRol}
   * @return Optional con el rol si existe
   */
  Optional<Rol> findByNombre(NombreRol nombre);
}
