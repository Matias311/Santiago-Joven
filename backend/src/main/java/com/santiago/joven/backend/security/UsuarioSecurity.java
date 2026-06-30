package com.santiago.joven.backend.security;

import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class UsuarioSecurity {

  public boolean isSelf(UUID usuarioId, Authentication authentication) {
    if (usuarioId == null
        || authentication == null
        || !(authentication.getPrincipal() instanceof CustomUserDetails currentUser)) {
      return false;
    }
    return usuarioId.equals(currentUser.usuario().getId());
  }

  public boolean isAdmin(Authentication authentication) {
    return hasRole(authentication, "ROLE_ADMIN");
  }

  public boolean isModerator(Authentication authentication) {
    return hasRole(authentication, "ROLE_MODERATOR");
  }

  /**
   * Admin y Moderador pueden ver informacion de usuarios (read-only los datos personales).
   * Admin NO puede modificar nada. Moderador puede gestionar permisos/roles.
   */
  public boolean canViewUsers(Authentication authentication) {
    return isAdmin(authentication) || isModerator(authentication);
  }

  /**
   * Solo Moderador puede gestionar roles/permisos de usuarios
   * (asignar y eliminar permisos).
   */
  public boolean canManageRoles(Authentication authentication) {
    return isModerator(authentication);
  }

  private boolean hasRole(Authentication authentication, String role) {
    if (authentication == null) return false;
    return authentication.getAuthorities().stream()
        .anyMatch(authority -> role.equals(authority.getAuthority()));
  }
}
