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

## ¿Por qué Cypress para E2E?

- **Contexto:** Frontend React sin tests; se necesitaban pruebas de navegación, renderizado e interacción del usuario.
- **Decisión:** Cypress 15 con Electron headless, integrado via Docker Compose (`cypress/included`) y GitHub Actions.
- **Alternativas:** Playwright (similar), Selenium (más lento), Testing Library (solo unit/component).
- **Consecuencia:** 7 tests E2E que verifican renderizado de secciones, navegación entre rutas y toggle de modo oscuro.

## ¿Por qué tests de integración con RestClient?

- **Contexto:** Verificar flujos completos autenticados (JWT + Spring Security + BD real) sin mockear capas.
- **Decisión:** `@SpringBootTest(webEnvironment=RANDOM_PORT)` + `RestClient` para llamar a la API embebida.
- **Alternativas:** `TestRestTemplate` (no disponible en Spring Boot 4), MockMvc (no prueba serialización HTTP real).
- **Consecuencia:** 42 tests de integración con autenticación real y BD H2.

## ¿Por qué asignar el rol USER en create() en vez de un paso separado?

- **Contexto:** El registro creaba el usuario, luego buscaba el rol USER y lo asignaba en una segunda transacción. Si la segunda fallaba, el usuario quedaba persistido sin rol y se retornaba 500.
- **Decisión:** Mover la asignación del rol USER dentro de `UsuarioServiceImpl.create()`, en la misma transacción.
- **Alternativas:** Dejarlo en dos pasos (create + asignarRoles), que expone una ventana donde el usuario existe sin rol.
- **Consecuencia:** El registro es atómico: o el usuario se crea con su rol o no se crea. El controller ya no necesita `RolRepository`.

## ¿Por qué exponer asignar roles como endpoint separado?

- **Contexto:** El admin necesitaba poder cambiar el rol de un usuario (ej: promover a MODERATOR). Ya existía `UsuarioService.asignarRoles()` pero sin endpoint REST.
- **Decisión:** Endpoint `PUT /api/v1/usuarios/{id}/roles` protegido con `PERMISSION_MANAGE_USERS` (el admin ya lo tiene). Recibe un `Set<UUID>` de IDs de roles y los asigna al usuario.
- **Alternativas:** Meterlo en el `PUT /usuarios/{id}` existente (rompe el contrato del DTO `UsuarioUpdate`), o en `RolController` (menos cohesivo).
- **Consecuencia:** El admin puede asignar cualquier rol desde el frontend sin tocar la BD directamente.

## ¿Por qué guardar OTP de recuperacion en BD?

- **Contexto:** El flujo de recuperar contrasena debe sobrevivir reinicios y funcionar igual en dev/prod/test.
- **Decisión:** Tabla `codigos_recuperacion` con email, codigo, expiracion de 5 minutos y flag `usado`.
- **Alternativas:** Cache en memoria (se pierde al reiniciar), Redis (dependencia extra).
- **Consecuencia:** El restablecimiento es transaccional y testeable con H2. El endpoint `/auth/recuperar` retorna 200 aunque el email no exista para evitar enumeracion.

## ¿Por qué no MapStruct?

- **Contexto:** MapStruct daba errores de compilación con Records
- **Decisión:** Mappers manuales @Component
- **Consecuencia:** Más código pero sin dependencias problemáticas
