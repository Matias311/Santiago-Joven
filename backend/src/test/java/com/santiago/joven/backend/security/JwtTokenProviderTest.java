package com.santiago.joven.backend.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtTokenProviderTest {

  private static final String SECRET = "mySuperSecretKeyForJwtTokenTesting1234567890!";
  private static final long EXPIRATION_MS = 3600000;

  private JwtTokenProvider provider;
  private UUID userId;
  private String email;

  @BeforeEach
  void setUp() {
    provider = new JwtTokenProvider(SECRET, EXPIRATION_MS);
    userId = UUID.randomUUID();
    email = "test@santiagojoven.org";
  }

  @Test
  void generateToken_debeCrearTokenValido() {
    var token = provider.generateToken(userId, email, List.of("USER", "ADMIN"));

    assertThat(token).isNotBlank();
    assertThat(provider.validateToken(token)).isTrue();
  }

  @Test
  void validateToken_conTokenValido_debeRetornarTrue() {
    var token = provider.generateToken(userId, email, List.of("USER"));

    assertThat(provider.validateToken(token)).isTrue();
  }

  @Test
  void validateToken_conTokenInvalido_debeRetornarFalse() {
    assertThat(provider.validateToken("token-invalido")).isFalse();
  }

  @Test
  void getEmailFromToken_debeExtraerEmail() {
    var token = provider.generateToken(userId, email, List.of("USER"));

    assertThat(provider.getEmailFromToken(token)).isEqualTo(email);
  }

  @Test
  void getUserIdFromToken_debeExtraerUserId() {
    var token = provider.generateToken(userId, email, List.of("USER"));

    assertThat(provider.getUserIdFromToken(token)).isEqualTo(userId.toString());
  }

  @Test
  void generateToken_conDistintosRoles_debeContenerRoles() {
    var token = provider.generateToken(userId, email, List.of("MODERATOR", "VOLUNTEER"));

    assertThat(provider.validateToken(token)).isTrue();
    assertThat(provider.getEmailFromToken(token)).isEqualTo(email);
  }
}
