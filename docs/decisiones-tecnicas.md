# Decisiones Tecnicas
Estructura para responder preguntas.
Ejemplo:
```text
**Contexto:** Que problema o necesidad existe
**Decision:** Que elegiste exactamente
**Consecuencias:** Que ganas y que sacrificas
**Alternativas descartadas:** Que otra cosa evaluaste y por que no se eligio
```


## ¿Por qué utilizar Spring Boot y Java para el backend?
**Contexto:** Se requiere un backend estable, escalable y con soporte a largo plazo.  
**Decisión:** Usar Java 21 (LTS) con Spring Boot para construir la API.  
**Consecuencias:** Mayor robustez y ecosistema empresarial, pero mayor verbosidad.  
**Alternativas descartadas:** Node.js (menos tipado y estructura), Python (menor rendimiento), Go (ecosistema enterprise más reducido).

## ¿Por qué pasar el chatbot al backend utilizando Spring AI y no dejarlo en el frontend?
**Contexto:** El chatbot necesita acceso seguro a claves y control de prompts.  
**Decisión:** Ejecutarlo en el backend usando Spring AI.  
**Consecuencias:** Mayor seguridad y control del flujo, pero más carga en el backend.  
**Alternativas descartadas:** Chatbot en frontend (expone claves y reduce control).

## ¿Por qué utilizar H2 con JPA y PostgreSQL para el desarrollo del backend?
**Contexto:** Se necesita un entorno rápido para desarrollo sin perder compatibilidad con producción.  
**Decisión:** Usar H2 en dev/test y PostgreSQL en producción, con JPA como capa ORM.  
**Consecuencias:** Arranque rápido en local, pero se debe validar compatibilidad SQL.  
**Alternativas descartadas:** Usar PostgreSQL también en local (más pesado), o usar solo H2 (no representa producción).

## ¿Qué es Lombok y por qué se utiliza en el desarrollo?
**Contexto:** El boilerplate en Java reduce velocidad y claridad.  
**Decisión:** Usar Lombok para generar getters, setters y constructores.  
**Consecuencias:** Menos código repetido y más productividad, pero dependencia en anotaciones.  
**Alternativas descartadas:** Escribir manualmente el boilerplate (más propenso a errores).

## ¿Por qué utilizamos Docker y Docker Compose con el despliegue en Vercel?
**Contexto:** Se necesita consistencia entre entornos y despliegue rápido del frontend.  
**Decisión:** Dockerizar el backend y usar Docker Compose para orquestación local. Vercel para frontend.  
**Consecuencias:** Setup reproducible y despliegue ágil, pero requiere conocimientos de contenedores.  
**Alternativas descartadas:** Deploy manual sin contenedores (más frágil).

## ¿Por qué usar spring-boot-starter-validation en los DTOs?
**Contexto:** Los datos de entrada deben validarse antes de llegar a la capa de servicio para evitar errores y malas prácticas.
**Decisión:** Agregar `spring-boot-starter-validation` y anotar los DTOs con `@NotBlank`, `@Email`, `@Size`, `@Min`/`@Max`, etc.
**Consecuencias:** Validacion declarativa y centralizada en los controladores con `@Valid`. Mensajes de error automaticos. Dependencia adicional.
**Alternativas descartadas:** Validacion manual en servicios (codigo repetitivo y disperso), o no validar (riesgo de datos corruptos).

## ¿Por qué usar Java Records para los DTOs?
**Contexto:** Un DTO es un contenedor de datos inmutable que viaja entre capas; requiere constructor, getters, equals, hashCode y toString.
**Decisión:** Usar `record` de Java 21 en lugar de clases con Lombok.
**Consecuencias:** Menos codigo (todo viene implicito), inmutabilidad garantizada, y serializacion JSON nativa con Jackson. No se puede extender ni agregar setters.
**Alternativas descartadas:** Clases con `@Data` de Lombok (mas verboso, permite mutacion accidental), clases manuales (excesivo boilerplate).

## ¿Por qué agregar @Builder de Lombok a los Records?
**Contexto:** Construir un record con muchos campos usando el constructor canónico es ilegible y propenso a errores de orden.
**Decisión:** Anotar cada record DTO con `@Builder` de Lombok para generar un builder fluido.
**Consecuencias:** Sintaxis clara al construir DTOs (`.field(value).build()`), sin perder la inmutabilidad del record. Dependencia en Lombok.
**Alternativas descartadas:** Constructor tradicional (ilegible con muchos parametros), patrón Builder manual (codigo repetitivo).

## ¿Por qué usar profiles de Spring (dev/test/prod) en application.yml?
**Contexto:** Los entornos de desarrollo, test y producción requieren configuraciones de base de datos y JPA diferentes.
**Decisión:** Usar un único `application.yml` con documentos separados por `---` y `spring.config.activate.on-profile`. Dev apunta a PostgreSQL con ddl-auto=update y SQL visible. Test usa H2 en memoria con ddl-auto=create-drop. Prod apunta a PostgreSQL con ddl-auto=validate.
**Consecuencias:** Configuración centralizada, cambios de entorno via `SPRING_PROFILES_ACTIVE` en docker-compose, sin archivos repetidos. El perfil `test` permite ejecutar `mvn test` sin depender de Docker ni PostgreSQL.
**Alternativas descartadas:** Archivos separados `application-{profile}.properties` (más archivos, misma funcionalidad), una sola config para todos los entornos (inseguro e incompatible).

## ¿Por qué usar @ControllerAdvice para el manejo global de excepciones?
**Contexto:** Los controladores pueden lanzar diversas excepciones (EntityNotFoundException, MethodArgumentNotValidException, etc.) y se necesita una respuesta HTTP uniforme en toda la API.
**Decisión:** Crear `GlobalExceptionHandler` con `@ControllerAdvice` que captura excepciones específicas y devuelve un `ErrorResponse` (record en `dto/`) con timestamp, status, error, message, path y detalles.
**Consecuencias:** Centraliza la lógica de errores, evita try-catch repetitivos en los controladores, respuestas JSON consistentes. El catch-all `Exception.class` asegura que ningún error quede sin manejar.
**Alternativas descartadas:** Try-catch en cada controlador (código repetitivo e inconsistente), `ResponseStatusException` (acopla el error al controlador).

## ¿Por qué usar H2 con modo PostgreSQL en el perfil test?
**Contexto:** Los tests usan el mismo schema JPA que producción (PostgreSQL), pero no deben depender de una base de datos externa.
**Decisión:** Configurar H2 con `MODE=PostgreSQL` para emular la sintaxis y comportamiento de PostgreSQL en memoria.
**Consecuencias:** Tests rápidos, auto-contenidos y portables. La mayoría del SQL es compatible, aunque ciertas funciones propias de PostgreSQL pueden no funcionar. ddl-auto=create-drop asegura esquema limpio en cada ejecución.
**Alternativas descartadas:** Testcontainers con PostgreSQL real (más lento, requiere Docker), H2 sin modo PostgreSQL (incompatibilidades SQL).

## ¿Por qué usar Spring Security con JWT en lugar de sesiones?
**Contexto:** La aplicacion es una SPA que se comunica con el backend via REST. Las sesiones basadas en cookies requieren estado en el servidor y no funcionan bien con despliegues stateless.
**Decisión:** Usar Spring Security con autenticacion stateless mediante tokens JWT. El cliente envia el token en el header `Authorization: Bearer <token>`. El servidor valida la firma con una clave HMAC-SHA256 sin almacenar sesion.
**Consecuencias:** Sin estado en el servidor (escalable horizontalmente), token auto-contenido con datos del usuario. El token expira en 24h por defecto. Si se compromete la clave secreta, todos los tokens son vulnerables.
**Alternativas descartadas:** Sesiones HTTP (requieren afinidad de servidor o Redis compartido), OAuth2 (complejidad innecesaria para una app monolitica), API Keys (sin granularidad de permisos).

## ¿Por qué usar @PreAuthorize con permisos en lugar de roles?
**Contexto:** El modelo de seguridad incluye roles (ADMIN, MODERATOR, USER) y permisos granulares (CREATE_COURSE, VIEW_ANALYTICS, etc.). Un rol puede tener multiples permisos.
**Decisión:** Usar `@PreAuthorize("hasAuthority('PERMISSION_...')")` en los metodos de los controladores en lugar de `@PreAuthorize("hasRole('...')")`. Los permisos se asignan a roles via la tabla `roles_permisos` y se cargan como GrantedAuthority en el JWT.
**Consecuencias:** Control granular sin modificar codigo — solo basta cambiar las asignaciones en `roles_permisos`. La lógica de autorización esta descentralizada en cada controller, visible directamente en los endpoints.
**Alternativas descartadas:** `hasRole()` (solo verifica prefijo ROLE_, menos granular), SecurityConfig con matchers (un solo archivo gigante, dificil de mantener), anotaciones personalizadas (sobreingenieria).

## ¿Por qué usar JOIN FETCH en la consulta del usuario para evitar LazyInitializationException?
**Contexto:** Al cargar un usuario desde la DB para autenticacion JWT, el filtro `JwtAuthenticationFilter` necesita acceder a `usuario.roles.permisos` para construir las autoridades (`getAuthorities()`). Con el fetch LAZY por defecto, Hibernate lanza `LazyInitializationException` porque la sesion ya se cerro al salir del `@Transactional` del servicio.
**Decisión:** Agregar una query `@Query("SELECT DISTINCT u FROM Usuario u JOIN FETCH u.roles r LEFT JOIN FETCH r.permisos WHERE u.email = :email")` en el repositorio que trae todo en una sola consulta (eager fetch). El `DISTINCT` evita filas duplicadas del JOIN y el `LEFT JOIN` asegura que roles sin permisos no filtren el resultado.
**Consecuencias:** Una sola consulta SQL, sin lazy loading, sin sesion necesaria. El rendimiento es mejor que N+1 queries. La desventaja es que siempre se cargan roles y permisos aunque no se necesiten (no relevante para auth). Se mapeo a un metodo dedicado `findByEmailWithRoles()` en lugar de modificar el `findByEmail()` existente, manteniendo compatibilidad con otros usos.
**Alternativas descartadas:** `@Transactional` en el filtro (acopla transacciones a la capa web), `FetchType.EAGER` en la entidad (carga siempre, aunque no se necesite), `OpenEntityManagerInViewFilter` (anti-patron, mantiene sesion abierta en toda la request).

## ¿Por qué los permisos del seed no usan prefijo PERMISSION_ en la DB?
**Contexto:** Los `@PreAuthorize` verifican `hasAuthority('PERMISSION_CREATE_COURSE')`, pero en la tabla `permisos` el nombre es `CREATE_COURSE` (sin prefijo).
**Decisión:** Almacenar el nombre limpio en la DB y agregar el prefijo `PERMISSION_` en `CustomUserDetails.getAuthorities()` al construir el `SimpleGrantedAuthority`. Esto mantiene la DB normalizada y evita redundancia.
**Consecuencias:** El prefijo es transparente para el seed y la DB. Si en el futuro se cambia el esquema de autoridades, solo se modifica el metodo `getAuthorities()`. El `@PreAuthorize` siempre debe usar el formato `PERMISSION_<nombre>` que es el `GrantedAuthority` final.

