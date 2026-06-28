package com.santiago.joven.backend.security;

import com.santiago.joven.backend.model.entity.Permiso;
import com.santiago.joven.backend.model.entity.Usuario;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record CustomUserDetails(Usuario usuario) implements UserDetails {

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    var authorities = new HashSet<GrantedAuthority>();
    if (usuario.getRoles() != null) {
      for (var rol : usuario.getRoles()) {
        authorities.add(new SimpleGrantedAuthority("ROLE_" + rol.getNombre().name()));
        if (rol.getPermisos() != null) {
          for (Permiso permiso : rol.getPermisos()) {
            authorities.add(new SimpleGrantedAuthority("PERMISSION_" + permiso.getNombre()));
          }
        }
      }
    }
    return authorities;
  }

  @Override
  public String getPassword() {
    return usuario.getPassword();
  }

  @Override
  public String getUsername() {
    return usuario.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return usuario.getActivo();
  }
}
