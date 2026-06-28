package com.santiago.joven.backend.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    var securitySchemeName = "bearerAuth";
    return new OpenAPI()
        .info(
            new Info()
                .title("Santiago Joven API")
                .description(
                    "API REST del programa Santiago Joven. "
                        + "Autenticacion via JWT. "
                        + "Los endpoints publicos no requieren token. "
                        + "Los endpoints protegidos requieren header Authorization: Bearer <token>.")
                .version("1.0.0")
                .contact(
                    new Contact()
                        .name("Santiago Joven")
                        .url("https://santiagojoven.cl")
                        .email("contacto@santiagojoven.cl"))
                .license(
                    new License().name("MIT").url("https://opensource.org/licenses/MIT")))
        .components(
            new Components()
                .addSecuritySchemes(
                    securitySchemeName,
                    new SecurityScheme()
                        .name(securitySchemeName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
  }
}
