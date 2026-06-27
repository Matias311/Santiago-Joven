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
