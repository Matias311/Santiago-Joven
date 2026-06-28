package com.santiago.joven.backend.repository;

import com.santiago.joven.backend.model.entity.Usuario;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/** Repositorio para la entidad {@link Usuario}. */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

  /**
   * Busca un usuario por su email.
   *
   * @param email email exacto
   * @return Optional con el usuario si existe
   */
  @Query("""
      SELECT DISTINCT u FROM Usuario u
      JOIN FETCH u.roles r
      LEFT JOIN FETCH r.permisos
      WHERE u.email = :email""")
  Optional<Usuario> findByEmailWithRoles(String email);

  Optional<Usuario> findByEmail(String email);

  boolean existsByEmail(String email);
}
