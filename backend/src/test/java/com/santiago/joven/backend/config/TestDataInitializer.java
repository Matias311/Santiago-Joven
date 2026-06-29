package com.santiago.joven.backend.config;

import com.santiago.joven.backend.model.entity.Rol;
import com.santiago.joven.backend.model.enums.NombreRol;
import com.santiago.joven.backend.repository.RolRepository;
import java.util.Set;
import java.util.UUID;
import javax.sql.DataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@Profile("test")
public class TestDataInitializer {

  @Bean
  CommandLineRunner seedData(
      RolRepository rolRepository, DataSource dataSource) {
    return args -> {
      if (rolRepository.count() > 0) return;

      var roles = Set.of(
          new Rol(NombreRol.USER),
          new Rol(NombreRol.ADMIN),
          new Rol(NombreRol.MODERATOR),
          new Rol(NombreRol.VOLUNTEER));

      rolRepository.saveAll(roles);
      rolRepository.flush();

      var jdbc = new JdbcTemplate(dataSource);

      var permManageUsers = UUID.randomUUID();
      jdbc.update(
          "INSERT INTO permisos (id, nombre, descripcion, modulo) VALUES (?, ?, ?, ?)",
          permManageUsers, "MANAGE_USERS", "Gestionar usuarios", "USERS");

      var permCreateActivity = UUID.randomUUID();
      jdbc.update(
          "INSERT INTO permisos (id, nombre, descripcion, modulo) VALUES (?, ?, ?, ?)",
          permCreateActivity, "CREATE_ACTIVITY", "Crear actividades", "ACTIVITIES");

      var permManageRoles = UUID.randomUUID();
      jdbc.update(
          "INSERT INTO permisos (id, nombre, descripcion, modulo) VALUES (?, ?, ?, ?)",
          permManageRoles, "MANAGE_ROLES", "Gestionar roles y permisos", "ROLES");

      var adminRolId = jdbc.queryForObject(
          "SELECT id FROM roles WHERE nombre = ?", UUID.class, "ADMIN");
      jdbc.update(
          "INSERT INTO roles_permisos (rol_id, permiso_id) VALUES (?, ?)",
          adminRolId, permManageUsers);
      jdbc.update(
          "INSERT INTO roles_permisos (rol_id, permiso_id) VALUES (?, ?)",
          adminRolId, permCreateActivity);
      jdbc.update(
          "INSERT INTO roles_permisos (rol_id, permiso_id) VALUES (?, ?)",
          adminRolId, permManageRoles);
    };
  }
}
