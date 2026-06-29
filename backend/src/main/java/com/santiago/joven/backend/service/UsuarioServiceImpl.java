package com.santiago.joven.backend.service;

import com.santiago.joven.backend.dto.UsuarioRequest;
import com.santiago.joven.backend.dto.UsuarioResponse;
import com.santiago.joven.backend.dto.UsuarioUpdate;
import com.santiago.joven.backend.mapper.UsuarioMapper;
import com.santiago.joven.backend.model.entity.Usuario;
import com.santiago.joven.backend.model.enums.NombreRol;
import com.santiago.joven.backend.repository.RolRepository;
import com.santiago.joven.backend.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

  private final UsuarioRepository repository;
  private final RolRepository rolRepository;
  private final UsuarioMapper mapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  @Transactional(readOnly = true)
  public List<UsuarioResponse> findAll() {
    return repository.findAll().stream().map(mapper::toResponse).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public UsuarioResponse findById(UUID id) {
    return repository
        .findById(id)
        .map(mapper::toResponse)
        .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + id));
  }

  @Override
  @Transactional(readOnly = true)
  public UsuarioResponse findByEmail(String email) {
    return repository
        .findByEmail(email)
        .map(mapper::toResponse)
        .orElseThrow(
            () -> new EntityNotFoundException("Usuario no encontrado con email: " + email));
  }

  @Override
  @Transactional(readOnly = true)
  public boolean existsByEmail(String email) {
    return repository.existsByEmail(email);
  }

  @Override
  public UsuarioResponse create(UsuarioRequest request) {
    var entity = mapper.toEntity(request);
    if (request.password() != null && !request.password().startsWith("$2a$")) {
      entity.setPassword(passwordEncoder.encode(request.password()));
    }
    var rolUser =
        rolRepository
            .findByNombre(NombreRol.USER)
            .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));
    entity.getRoles().add(rolUser);
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public UsuarioResponse update(UUID id, UsuarioUpdate update) {
    var entity =
        repository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + id));
    if (update.password() != null) {
      entity.setPassword(passwordEncoder.encode(update.password()));
    }
    if (update.email() != null) entity.setEmail(update.email());
    if (update.nombre() != null) entity.setNombre(update.nombre());
    if (update.apellido() != null) entity.setApellido(update.apellido());
    if (update.activo() != null) entity.setActivo(update.activo());
    entity = repository.save(entity);
    return mapper.toResponse(entity);
  }

  @Override
  public void delete(UUID id) {
    if (!repository.existsById(id)) {
      throw new EntityNotFoundException("Usuario no encontrado con id: " + id);
    }
    repository.deleteById(id);
  }

  @Override
  public void asignarRoles(UUID usuarioId, Set<UUID> rolIds) {
    var usuario =
        repository
            .findById(usuarioId)
            .orElseThrow(
                () -> new EntityNotFoundException("Usuario no encontrado con id: " + usuarioId));
    var roles = rolRepository.findAllById(rolIds);
    usuario.getRoles().clear();
    usuario.getRoles().addAll(roles);
  }

  @Override
  @Transactional(readOnly = true)
  public List<NombreRol> obtenerRoles(UUID usuarioId) {
    var usuario =
        repository
            .findById(usuarioId)
            .orElseThrow(
                () -> new EntityNotFoundException("Usuario no encontrado con id: " + usuarioId));
    if (usuario.getRoles() == null) return List.of();
    return usuario.getRoles().stream().map(r -> r.getNombre()).toList();
  }
}
