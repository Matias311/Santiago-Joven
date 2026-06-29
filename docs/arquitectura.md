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

controller/AuthController.java      ← POST /auth/login, /register, /recuperar y /restablecer (publicos)
```

## Flujo de autenticacion

### Registro (`POST /api/v1/auth/register`)
1. Valida email unico (409 si existe)
2. BCrypt encodea el password
3. `UsuarioService.create()` persiste el usuario + asigna rol `USER` en una sola transacción
4. JwtTokenProvider genera JWT y retorna `201 Created` con `LoginResponse`

### Login (`POST /api/v1/auth/login`)
1. AuthenticationManager chequea credenciales
   - CustomUserDetailsService carga usuario con JOIN FETCH de roles y permisos
   - BCryptPasswordEncoder verifica el password
2. JwtTokenProvider genera JWT HMAC-SHA512 con subject=email, userId, roles
3. Cliente recibe token y lo envia en header `Authorization: Bearer <token>`
4. JwtAuthenticationFilter valida token en cada request y setea SecurityContext
5. @PreAuthorize evalua los permisos del contexto

### Recuperacion de contrasena
1. `POST /api/v1/auth/recuperar` recibe `{ email }` y siempre retorna 200 para evitar enumeracion de correos
2. Si el email existe, genera OTP numerico de 5 digitos con expiracion de 5 minutos
3. Persiste el OTP en `codigos_recuperacion` y lo envia por email via SMTP usando `SMTP_FROM` como remitente; si no hay SMTP local, lo loggea
4. `POST /api/v1/auth/restablecer` recibe `{ email, codigo, nuevaPassword }`, valida OTP vigente/no usado y actualiza password con BCrypt

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
controller/       → controladores REST (incl. Auth)
dto/              → DTO records de requests/responses + ErrorResponse
exception/        → GlobalExceptionHandler
mapper/           → 18 mappers manuales
model/entity/     → entidades JPA
model/enums/      → enumeraciones
repository/       → repositorios Spring Data JPA
security/         → Spring Security + JWT + OpenApi config
service/          → interfaces + implementaciones transaccionales
```
