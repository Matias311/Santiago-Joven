# Decisiones Técnicas

A continuación, las decisiones principales tomadas durante el desarrollo, explicando contexto, decisión, consecuencias y alternativas descartadas.

## ¿Por qué Spring Boot y Java?

- **Contexto:** Backend estable, escalable, con soporte a largo plazo.
- **Decisión:** Java 21 (LTS) + Spring Boot.
- **Alternativas:** Node.js (menos estructura), Python (menor rendimiento), Go (ecosistema reducido).

## ¿Por qué pasar el chatbot al backend?

- **Contexto:** La API key de OpenAI no debe exponerse en frontend.
- **Decisión:** Ejecutar chatbot en backend con Spring AI.
- **Alternativas:** Chatbot en frontend (expone claves).

## ¿Por qué H2 y PostgreSQL?

- **Contexto:** rapidez en desarrollo sin perder compatibilidad.
- **Decisión:** dev/prod con PostgreSQL (Neon), test con H2 modo PostgreSQL.
- **Alternativas:** PostgreSQL en todos los entornos.

## ¿Por qué Lombok?

- **Contexto:** Reducir boilerplate de getters, setters, constructores.
- **Decisión:** @Getter, @Setter, @NoArgsConstructor, @Builder.
- **Riesgo:** Dependencia de procesador de anotaciones.

## ¿Por qué Docker y Docker Compose?

- **Contexto:** consistencia entre entornos.
- **Decisión:** 3 profiles (dev, test, prod).
- **Alternativas:** Deployment manual.

## ¿Por qué spring-boot-starter-validation?

- **Contexto:** Validar datos de entrada en la API.
- **Decisión:** @Valid en controladores, anotaciones jakarta.validation.
- **Alternativas:** Validación manual en servicios.

## ¿Por qué Records + @Builder?

- **Contexto:** DTOs inmutables, menos código.
- **Decisión:** Records Java 21 anotados con @Builder.
- **Alternativas:** Clases con @Data (mutables), clases manuales.

## ¿Por qué perfiles en application.yml?

- **Contexto:** Configuración de BD y JPA según entorno.
- **Decisión:** Único multi-documento: base (JWT)/dev (PostgreSQL)/test (H2)/prod (validate).
- **Alternativas:** Archivos por perfil.

## ¿Por qué @ControllerAdvice?

- **Contexto:** Respuestas HTTP uniformes para errores.
- **Decisión:** GlobalExceptionHandler con ErrorResponse.

## ¿Por qué Spring Security + JWT?

- **Tipo:** Stateless auth para SPA
- **Decisión:** JWT HMAC-SHA512, expira 24h
- **Alternativas:** Sesiones (stateful), OAuth2 (complejidad)
- **Consecuencia:** Escalable, token auto-contenido

## ¿Por qué @PreAuthorize y permisos?

- **Decisión:** `hasAuthority('PERMISSION_...')` a nivel método
- **Consecuencia:** Control granular solo cambiando asignaciones en BD

## ¿Por qué JOIN FETCH en auth?

- **Decisión:** Query única en `UsuarioRepository.findByEmailWithRoles()` para traer roles y permisos y evitar LazyInitializationException
- **Alternativas:** EAGER loading (innecesario en otros contextos), OpenEntityManagerInView

## ¿Por qué Swagger / OpenAPI?

- **Contexto:** Documentación interactiva, viva, que se actualiza con los cambios
- **Decisión:** springdoc-openapi con @Tag, @Operation, @SecurityRequirement
- **Consecuencia:** swagger-ui/index.html disponible, canaddos para endpoints protegidos

## ¿Por qué no MapStruct?

- **Contexto:** MapStruct daba errores de compilación con Records
- **Decisión:** Mappers manuales @Component
- **Consecuencia:** Más código pero sin dependencias problemáticas
