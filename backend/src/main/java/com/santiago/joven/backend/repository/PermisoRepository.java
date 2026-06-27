package com.santiago.joven.backend.repository;

import com.santiago.joven.backend.model.entity.Permiso;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio para la entidad {@link Permiso}. */
public interface PermisoRepository extends JpaRepository<Permiso, UUID> {

  /**
   * Busca un permiso por su nombre exacto.
   *
   * @param nombre nombre del permiso
   * @return Optional con el permiso si existe
   */
  Optional<Permiso> findByNombre(String nombre);

  /**
   * Lista todos los permisos de un modulo especifico.
   *
   * @param modulo nombre del modulo (COURSES, ACTIVITIES, USERS, etc.)
   * @return lista de permisos del modulo
   */
  List<Permiso> findByModulo(String modulo);
}
