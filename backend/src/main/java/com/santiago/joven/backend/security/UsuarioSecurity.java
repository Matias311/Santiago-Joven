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

  public boolean canManageUsers(Authentication authentication) {
    if (authentication == null) return false;
    return authentication.getAuthorities().stream()
        .anyMatch(authority -> "PERMISSION_MANAGE_USERS".equals(authority.getAuthority()));
  }
}
