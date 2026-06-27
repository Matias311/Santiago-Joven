package com.santiago.joven.backend.repository;

import com.santiago.joven.backend.model.entity.Contacto;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/** Repositorio para la entidad {@link Contacto}. */
public interface ContactoRepository extends JpaRepository<Contacto, UUID> {

  /**
   * Lista contactos no respondidos, ordenados del mas reciente al mas antiguo.
   *
   * @return lista de contactos pendientes
   */
  List<Contacto> findByRespondidoFalseOrderByFechaContactoDesc();

  /**
   * Busca contactos por email del remitente.
   *
   * @param email email a buscar
   * @return lista de contactos con ese email
   */
  List<Contacto> findByEmail(String email);
}
