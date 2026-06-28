# Arquitectura del proyecto

## Capas del backend

```text
controller/       → REST endpoints (api/v1/...)
    ↓
service/          → Logica de negocio (interfaz + implementacion)
    ↓
mapper/           → Conversion entidad ↔ DTO (@Component manual, sin MapStruct)
    ↓
repository/       → Acceso a datos (JpaRepository)
    ↓
model/entity/     → Entidades JPA
```

## Seguridad

```text
security/
├── SecurityConfig.java           ← Configuracion HTTP Security, CORS, rutas publicas
├── JwtTokenProvider.java         ← Generacion/validacion de tokens JWT (HMAC-SHA512)
├── JwtAuthenticationFilter.java  ← Filtro OncePerRequestFilter, extrae token del header Authorization
├── CustomUserDetailsService.java ← Carga usuario desde DB con JOIN FETCH de roles y permisos
└── CustomUserDetails.java        ← Wrapper que mapea roles → ROLE_* y permisos → PERMISSION_*

exception/
└── GlobalExceptionHandler.java   ← @ControllerAdvice para respuestas de error uniformes (ErrorResponse)

controller/AuthController.java    ← POST /api/v1/auth/login y /register (publicos)
```

### Detalle de componentes

- **JwtTokenProvider**: Usa `jjwt 0.12.6` con clave HMAC-SHA512. El token incluye subject (email), claims (userId, roles), issuedAt y expiration (24h por defecto).
- **JwtAuthenticationFilter**: Filtro que intercepta cada request, extrae el token del header `Authorization: Bearer ...`, lo valida y setea el `SecurityContext`.
- **CustomUserDetailsService**: Usa `UsuarioRepository.findByEmailWithRoles()` que hace `JOIN FETCH` de `roles` y `permisos` en una sola consulta para evitar `LazyInitializationException`.
- **CustomUserDetails**: Expone `getAuthorities()` que retorna `ROLE_ADMIN`, `PERMISSION_MANAGE_USERS`, etc. como `GrantedAuthority`.
- **PasswordEncoder**: `BCryptPasswordEncoder` para hashear passwords.

### Flujo de autenticacion

1. Cliente envia `POST /api/v1/auth/login` con `{ email, password }`
2. `AuthenticationManager` delega en `CustomUserDetailsService.loadUserByUsername()` que carga usuario con roles y permisos via `JOIN FETCH`
3. `BCryptPasswordEncoder` verifica el password
4. `JwtTokenProvider` genera JWT firmado con HMAC-SHA512
5. Cliente recibe `{ token, userId, email, roles }`
6. En adelante, cliente envia `Authorization: Bearer <token>` en cada request
7. `JwtAuthenticationFilter` extrae el token, valida firma, carga el usuario y setea el `SecurityContext`
8. `@PreAuthorize` en los controllers evalua los `GrantedAuthority` del contexto

### Endpoints publicos

- `POST /api/v1/auth/**` (login, register)
- `POST /api/v1/contactos` (crear contacto)
- Todos los `GET` de contenido: `/salud-mental`, `/asesorias`, `/cursos-destacados`, `/actividades-talleres`, `/programas`, `/acciones-joven`, `/categorias`, `/galeria-fotos`, `/ubicaciones`, `/tu-contribucion-cuenta`, `/estadisticas`

### Matriz de acceso

| Endpoint | Sin auth | USER | MODERADOR | ADMIN |
|---|---|---|---|---|
| GET /contenido/** | ✅ publico | ✅ | ✅ | ✅ |
| POST /api/v1/auth/** | ✅ publico | ✅ | ✅ | ✅ |
| POST /api/v1/contactos | ✅ publico | ✅ | ✅ | ✅ |
| POST /inscripciones, /resenas-calificaciones | ❌ | ✅ | ✅ | ✅ |
| POST/PUT/DELETE /contenido | ❌ | ❌ | ✅ permiso especifico | ✅ |
| CRUD /usuarios, /contactos (admin) | ❌ | ❌ | ❌ | ✅ MANAGE_USERS |
| CRUD /roles, /permisos | ❌ | ❌ | ❌ | ✅ MANAGE_ROLES |
| CRUD /auditoria | ❌ | ❌ | ❌ | ✅ VIEW_ANALYTICS |

## Estructura de paquetes

```
com.santiago.joven.backend
├── controller/       ← 18 REST controllers + AuthController
├── dto/              ← 45 DTO records (Request/Update/Response) + ErrorResponse + Login*
├── exception/        ← GlobalExceptionHandler (@ControllerAdvice)
├── mapper/           ← 18 @Component mappers manuales (toEntity, toResponse, updateEntity)
├── model/
│   ├── entity/       ← 18 JPA entities (BaseEntity + AuditableEntity)
│   └── enums/        ← Enumeraciones (NombreRol, EstadoInscripcion, TipoRecurso, etc.)
├── repository/       ← 18 @Repository interfaces (JpaRepository + @Query personalizadas)
├── security/         ← Spring Security + JWT
└── service/          ← 18 interfaces + 18 implementaciones (@Transactional)
```
