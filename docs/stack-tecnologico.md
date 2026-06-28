# Stack y tecnologias elegidas

## Frontend
- **React 19.2.0** — UI SPA escalable
- **TypeScript 5.9.3** — Tipado fuerte
- **React Router 7.9.5** — Navegación de rutas
- **Font Awesome 7.1.0** — Iconos
- **Vite 7.2.2** — Build rápido
- **CSS** — Estilos

## Backend
- **Java 21** — LTS estable
- **Spring Boot 4.0.6** — Framework web
- **Spring AI 2.0.0-M6** — Chatbot en backend
- **JPA / Hibernate** — ORM
- **Spring Validation** — Validación de DTOs con anotaciones
- **Spring Security** — Autenticación JWT stateless, autorización por permisos
- **JJWT 0.12.6** — Generación y validación de JWT
- **BCrypt** — Hasheo de passwords
- **Lombok** — Reducción de código boilerplate (getters, setters, constructors, builders)
- **springdoc-openapi 2.8.6** — Swagger/OpenAPI para documentación interactiva
- **@Component mappers** — Manuales, sin MapStruct
- **@ControllerAdvice** — Manejador global de excepciones

## Base de datos
- **PostgreSQL** — dev/prod, alojada en **Neon.tech**
- **H2** — tests, en modo PostgreSQL

## DevOps
- **Docker** + **Docker Compose** (3 perfiles: dev, test, prod)
- **Railway** — despliegue del backend
- **Vercel** — despliegue del frontend

## Documentación
- Swagger UI: `/swagger-ui/`
- OpenAPI JSON: `/api-docs`
- README.md
- docs/: stack-tecnologico.md, arquitectura.md, decisiones-tecnicas.md, modelo-datos.md
