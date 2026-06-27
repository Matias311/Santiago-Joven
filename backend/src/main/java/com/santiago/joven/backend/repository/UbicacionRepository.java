package com.santiago.joven.backend.repository;

import com.santiago.joven.backend.model.entity.Ubicacion;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio para la entidad {@link Ubicacion}. */
public interface UbicacionRepository extends JpaRepository<Ubicacion, UUID> {

  /**
   * Lista ubicaciones filtradas por ciudad.
   *
   * @param ciudad nombre de la ciudad
   * @return lista de ubicaciones en esa ciudad
   */
  List<Ubicacion> findByCiudad(String ciudad);
}
