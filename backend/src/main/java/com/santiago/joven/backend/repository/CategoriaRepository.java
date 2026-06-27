package com.santiago.joven.backend.repository;

import com.santiago.joven.backend.model.entity.Categoria;
import com.santiago.joven.backend.model.enums.TipoCategoria;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio para la entidad {@link Categoria}. */
public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {

  /**
   * Busca una categoria por su nombre exacto.
   *
   * @param nombre nombre de la categoria
   * @return Optional con la categoria si existe
   */
  Optional<Categoria> findByNombre(String nombre);

  /**
   * Lista todas las categorias de un tipo especifico.
   *
   * @param tipo valor del enum {@link TipoCategoria}
   * @return lista de categorias del tipo indicado
   */
  List<Categoria> findByTipo(TipoCategoria tipo);
}
