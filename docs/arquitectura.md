# Arquitectura del proyecto

## Despliegue

| Componente | Plataforma |
|------------|-----------|
| Backend | Railway |
| Frontend | Vercel |
| Base de datos | Neon.tech (PostgreSQL) |

## Capas del backend

```text
controller/       → REST endpoints (api/v1/)
    ↓
service/          → Interfaces + implementaciones (@Transactional)
    ↓
mapper/           → Component mappers manuales
    ↓
repository/       → JpaRepository + @Query
    ↓
model/entity/     → JPA entities (BaseEntity, AuditableEntity)
```

## Seguridad

```text
security/
├── OpenApiConfig.java              ← Schema de seguridad JWT para Swagger
├── SecurityConfig.java             ← HTTP Security, CORS, rutas publicas
├── JwtTokenProvider.java           ← Generar/validar JWT (HMAC-SHA512)
├── JwtAuthenticationFilter.java    ← Filtro OncePerRequest, extrae Bearer token
├── CustomUserDetailsService.java   ← Carga usuario con JOIN FETCH de roles/permisos
└── CustomUserDetails.java          ← GrantedAuthority: ROLE_, PERMISSION_

exception/
└── GlobalExceptionHandler.java     ← @ControllerAdvice, respuestas ErrorResponse

controller/AuthController.java      ← POST /auth/login y /register (publicos)
```

## Flujo de autenticacion

1. Login en `POST /api/v1/auth/login`
2. AuthenticationManager chequea credenciales
   - CustomUserDetailsService carga usuario con JOIN FETCH de roles y permisos
   - BCryptPasswordEncoder verifica el password
3. JwtTokenProvider genera JWT HMAC-SHA512 con subject=email, userId, roles
4. Cliente recibe token y lo envia en header `Authorization: Bearer <token>`
5. JwtAuthenticationFilter valida token en cada request y setea SecurityContext
6. @PreAuthorize evalua los permisos del contexto

## Endpoints publicos

- `POST /api/v1/auth/**`
- `POST /api/v1/contactos`
- GET de contenido: salud-mental, asesorias, cursos, actividades, programas, acciones, categorias, galeria, ubicaciones, tu-contribucion, estadisticas
- Swagger UI: `/swagger-ui/`

## Endpoints protegidos

- POST/PUT/DELETE de contenido: requieren permiso específico (CREATE_PROGRAM, EDIT_ACTIVITY, etc.)
- CRUD usuarios, contactos (admin), inscripciones, resenas: require MANAGE_USERS
- CRUD roles, permisos: require MANAGE_ROLES
- CRUD auditoria (solo GET), estadisticas: require VIEW_ANALYTICS

## Documentacion interactiva

Swagger UI en `/swagger-ui/index.html`:
- Boton Authorize para ingresar token
- Candados en endpoints protegidos
- Schemas de todos los DTOs
- Tags agrupando cada recurso

## Estructura de paquetes

```
controller/       → 19 controladores (incl. Auth)
dto/              → 45 DTO records + ErrorResponse + Login*
exception/        → GlobalExceptionHandler
mapper/           → 18 mappers manuales
model/entity/     → 18 entidades
model/enums/      → enumeraciones
repository/       → 18 repositorios
security/         → Spring Security + JWT + OpenApi config
service/          → 18 interfaces + 18 implementaciones
```
