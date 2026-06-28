package com.santiago.joven.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/api/v1/auth/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/v1/contactos")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/salud-mental/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/asesorias/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/cursos-destacados/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/actividades-talleres/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/programas/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/acciones-joven/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/categorias/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/galeria-fotos/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/ubicaciones/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/tu-contribucion-cuenta/**")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/estadisticas/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    var config = new CorsConfiguration();
    config.addAllowedOrigin("*");
    config.addAllowedMethod("*");
    config.addAllowedHeader("*");
    var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}
