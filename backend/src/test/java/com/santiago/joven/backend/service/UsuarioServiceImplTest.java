package com.santiago.joven.backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.santiago.joven.backend.dto.UsuarioRequest;
import com.santiago.joven.backend.dto.UsuarioResponse;
import com.santiago.joven.backend.dto.UsuarioUpdate;
import com.santiago.joven.backend.mapper.UsuarioMapper;
import com.santiago.joven.backend.model.entity.Rol;
import com.santiago.joven.backend.model.entity.Usuario;
import com.santiago.joven.backend.model.enums.NombreRol;
import com.santiago.joven.backend.repository.RolRepository;
import com.santiago.joven.backend.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

  @Mock
  private UsuarioRepository repository;

  @Mock
  private RolRepository rolRepository;

  @Mock
  private UsuarioMapper mapper;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UsuarioServiceImpl service;

  private UUID id;
  private Usuario usuario;
  private UsuarioResponse response;

  @BeforeEach
  void setUp() {
    id = UUID.randomUUID();
    usuario = new Usuario();
    usuario.setId(id);
    usuario.setEmail("benjamin.munoz@santiagojoven.org");
    usuario.setNombre("Benjamín");
    usuario.setApellido("Muñoz");
    usuario.setActivo(true);

    response = new UsuarioResponse(id, "benjamin.munoz@santiagojoven.org", "Benjamín", "Muñoz", true);
  }

  @Test
  void findAll_debeRetornarTodos() {
    when(repository.findAll()).thenReturn(List.of(usuario));
    when(mapper.toResponse(usuario)).thenReturn(response);

    var result = service.findAll();

    assertThat(result).hasSize(1).containsExactly(response);
  }

  @Test
  void findById_debeRetornarUsuario_cuandoExiste() {
    when(repository.findById(id)).thenReturn(Optional.of(usuario));
    when(mapper.toResponse(usuario)).thenReturn(response);

    var result = service.findById(id);

    assertThat(result).isEqualTo(response);
  }

  @Test
  void findById_debeLanzarExcepcion_cuandoNoExiste() {
    when(repository.findById(id)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.findById(id))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("Usuario no encontrado con id: " + id);
  }

  @Test
  void findByEmail_debeRetornarUsuario_cuandoExiste() {
    var email = "benjamin.munoz@santiagojoven.org";
    when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
    when(mapper.toResponse(usuario)).thenReturn(response);

    var result = service.findByEmail(email);

    assertThat(result).isEqualTo(response);
  }

  @Test
  void findByEmail_debeLanzarExcepcion_cuandoNoExiste() {
    when(repository.findByEmail("noexiste@santiagojoven.org")).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.findByEmail("noexiste@santiagojoven.org"))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("noexiste@santiagojoven.org");
  }

  @Test
  void existsByEmail_debeDelegarEnRepositorio() {
    when(repository.existsByEmail("benjamin.munoz@santiagojoven.org")).thenReturn(true);

    assertThat(service.existsByEmail("benjamin.munoz@santiagojoven.org")).isTrue();
  }

  @Test
  void create_debeEncriptarPasswordYGuardar() {
    var request = UsuarioRequest.builder()
        .email("nuevo@santiagojoven.org")
        .password("plain123")
        .nombre("Isidora")
        .apellido("Mardones")
        .build();

    var entity = new Usuario();
    var rolUser = new Rol(NombreRol.USER);
    when(mapper.toEntity(request)).thenReturn(entity);
    when(passwordEncoder.encode("plain123")).thenReturn("$2a$10$encoded");
    when(rolRepository.findByNombre(NombreRol.USER)).thenReturn(Optional.of(rolUser));
    when(repository.save(entity)).thenReturn(usuario);
    when(mapper.toResponse(usuario)).thenReturn(response);

    var result = service.create(request);

    assertThat(result).isEqualTo(response);
    verify(passwordEncoder).encode("plain123");
  }

  @Test
  void create_noDebeReEncriptarSiYaEstaHasheado() {
    var request = UsuarioRequest.builder()
        .email("existente@santiagojoven.org")
        .password("$2a$10$existinghash")
        .nombre("Mateo")
        .apellido("Vásquez")
        .build();

    var entity = new Usuario();
    entity.setPassword("$2a$10$existinghash");
    var rolUser = new Rol(NombreRol.USER);
    when(mapper.toEntity(request)).thenReturn(entity);
    when(rolRepository.findByNombre(NombreRol.USER)).thenReturn(Optional.of(rolUser));
    when(repository.save(entity)).thenReturn(usuario);
    when(mapper.toResponse(usuario)).thenReturn(response);

    service.create(request);

    verify(passwordEncoder, org.mockito.Mockito.never()).encode(any());
  }

  @Test
  void update_debeActualizarCampos() {
    var update = UsuarioUpdate.builder()
        .nombre("Benjamín Andrés")
        .apellido("Muñoz Soto")
        .build();

    when(repository.findById(id)).thenReturn(Optional.of(usuario));
    when(repository.save(usuario)).thenReturn(usuario);

    var updatedResponse = new UsuarioResponse(id, "benjamin.munoz@santiagojoven.org", "Benjamín Andrés", "Muñoz Soto", true);
    when(mapper.toResponse(usuario)).thenReturn(updatedResponse);

    var result = service.update(id, update);

    assertThat(result.nombre()).isEqualTo("Benjamín Andrés");
    assertThat(result.apellido()).isEqualTo("Muñoz Soto");
  }

  @Test
  void update_debeEncriptarPassword_cuandoSeActualiza() {
    var update = UsuarioUpdate.builder().password("nuevapass").build();

    when(repository.findById(id)).thenReturn(Optional.of(usuario));
    when(passwordEncoder.encode("nuevapass")).thenReturn("$2a$10$newhash");
    when(repository.save(usuario)).thenReturn(usuario);
    when(mapper.toResponse(usuario)).thenReturn(response);

    service.update(id, update);

    verify(passwordEncoder).encode("nuevapass");
  }

  @Test
  void update_debeLanzarExcepcion_cuandoNoExiste() {
    var update = UsuarioUpdate.builder().nombre("Nuevo").build();

    when(repository.findById(id)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.update(id, update))
        .isInstanceOf(EntityNotFoundException.class);
  }

  @Test
  void delete_debeEliminar_cuandoExiste() {
    when(repository.existsById(id)).thenReturn(true);

    service.delete(id);

    verify(repository).deleteById(id);
  }

  @Test
  void delete_debeLanzarExcepcion_cuandoNoExiste() {
    when(repository.existsById(id)).thenReturn(false);

    assertThatThrownBy(() -> service.delete(id))
        .isInstanceOf(EntityNotFoundException.class);
  }

  @Test
  void asignarRoles_debeAsignarRoles() {
    var rolId = UUID.randomUUID();
    var rol = new Rol(NombreRol.USER);

    when(repository.findById(id)).thenReturn(Optional.of(usuario));
    when(rolRepository.findAllById(Set.of(rolId))).thenReturn(List.of(rol));

    service.asignarRoles(id, Set.of(rolId));

    assertThat(usuario.getRoles()).hasSize(1);
  }

  @Test
  void asignarRoles_debeLanzarExcepcion_cuandoUsuarioNoExiste() {
    when(repository.findById(id)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.asignarRoles(id, Set.of(UUID.randomUUID())))
        .isInstanceOf(EntityNotFoundException.class);
  }

  @Test
  void obtenerRoles_debeRetornarLista() {
    usuario.setRoles(Set.of(new Rol(NombreRol.ADMIN), new Rol(NombreRol.USER)));

    when(repository.findById(id)).thenReturn(Optional.of(usuario));

    var roles = service.obtenerRoles(id);

    assertThat(roles).hasSize(2).contains(NombreRol.ADMIN, NombreRol.USER);
  }

  @Test
  void obtenerRoles_debeLanzarExcepcion_cuandoUsuarioNoExiste() {
    when(repository.findById(id)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.obtenerRoles(id))
        .isInstanceOf(EntityNotFoundException.class);
  }
}
