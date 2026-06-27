package com.santiago.joven.backend.repository;

import com.santiago.joven.backend.model.entity.Usuario;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio para la entidad {@link Usuario}. */
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

  /**
   * Busca un usuario por su email.
   *
   * @param email email exacto
   * @return Optional con el usuario si existe
   */
  Optional<Usuario> findByEmail(String email);

  /**
   * Verifica si existe un usuario con el email dado.
   *
   * @param email email a verificar
   * @return true si ya existe
   */
  boolean existsByEmail(String email);
}
