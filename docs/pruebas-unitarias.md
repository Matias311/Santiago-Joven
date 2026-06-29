# Pruebas

## Stack

| Herramienta | Propósito |
|---|---|
| JUnit 5 + Mockito 5 + AssertJ | Tests backend (Java) |
| Cypress 15 | Tests E2E frontend (React) |
| H2 (modo PostgreSQL) | Base de datos en memoria para `@DataJpaTest` |
| `@DataJpaTest` | Test de repositorios (solo capa JPA) |
| `@WebMvcTest` | Test de controladores (solo capa web) |
| `@SpringBootTest` + `RestClient` | Tests de integración (flujo completo) |

## Estructura

```
backend/src/test/java/com/santiago/joven/backend/
├── repository/       → @DataJpaTest (64 tests, 18 clases)
├── service/          → Mockito (106 tests, 14 clases)
├── controller/       → @WebMvcTest (188 tests, 18 clases)
├── security/         → Tests planos + Mockito (17 tests, 3 clases)
├── integration/      → @SpringBootTest (42 tests, 4 clases)
└── BackendApplicationTests.java  → Smoke test

frontend/
└── cypress/e2e/      → Cypress (7 tests, 2 archivos)

### Resumen de cobertura

| Capa         | Clases/archivos | Tests | Técnica              |
|--------------|----------------|-------|----------------------|
| Repository   | 18             | 64    | `@DataJpaTest`       |
| Service      | 14             | 106   | `@ExtendWith(MockitoExtension.class)` |
| Controller   | 18             | 188   | `@WebMvcTest`        |
| Security     | 3              | 17    | JUnit + Mockito      |
| Integration  | 4              | 43    | `@SpringBootTest` + `RestClient` |
| E2E (front)  | 2              | 7     | Cypress              |
| **Total**    | **59**         | **426** | —                    |

## Ejecución con Docker

```bash
# Backend (Maven)
docker compose --profile test run backend-test

# Frontend (lint)
docker compose --profile test run frontend-test
```

## CI (GitHub Actions)

El workflow `.github/workflows/pr.yml` se ejecuta en cada PR contra `main` con 3 jobs paralelos:

1. **Backend Tests**: JDK 21, `mvn test`
2. **Frontend Lint**: Node 22, `npm ci`, `npm run lint`
3. **Frontend E2E**: Node 22, `npm ci`, Cypress con Electron headless contra Vite dev server

## Ejecutar E2E con Docker

```bash
docker compose --profile test run --rm frontend-e2e
```

Esto levanta un contenedor con la imagen oficial `cypress/included:15.18.0` (Node, Xvfb, Electron incluidos), monta el proyecto frontend, instala dependencias, inicia Vite, y corre Cypress en modo headless.

## Estrategia por capa

### Repository (`@DataJpaTest`)
- Cada test crea datos con `TestEntityManager` en `@BeforeEach`
- Se prueba cada query custom (Spring Data derivadas y `@Query` manuales)
- Se verifican relaciones, constraints (unicidad, no nulos) y callbacks (`@PrePersist`)
- Perfil `test` → H2 en memoria con `MODE=PostgreSQL`
- Los datos reflejan contexto chileno (Santiago, nombres CL, +56)

### Service (unit test con Mockito)
- Se mockean `Repository`, `Mapper`, `PasswordEncoder`
- Se prueba la lógica de negocio: CRUD, validaciones, excepciones
- Un test por método público del service
- Casos de borde específicos implementados:

  **InscripcionServiceImpl** — validaciones en `create()`:
  - **Cupo máximo (`ACTIVIDAD`):**
    - `create_enActividadConCupoDisponible_debeIncrementarInscritos`: verifica que `inscritos` suba de 3→4
    - `create_enActividadSinCupo_exactamenteAlLimite_debeRechazar`: `inscritos >= cantidadMaximaParticipantes` → `IllegalStateException`
    - `create_enActividadConCupoIlimitado_debePermitir`: `cantidadMaximaParticipantes == null` permite inscripción
    - `create_enActividadQueNoExiste_debeLanzarExcepcion`: `EntityNotFoundException`
  - **Usuario activo:**
    - `create_debeLanzarExcepcion_cuandoUsuarioInactivo`: `usuario.activo == false` → `IllegalStateException`
  - **Existencia del usuario:**
    - `create_debeLanzarExcepcion_cuandoUsuarioNoExiste`: `EntityNotFoundException`

### Controller (`@WebMvcTest`)
- Se mockea el `Service` con `@MockitoBean`
- Se mockean `JwtTokenProvider` y `CustomUserDetailsService` (necesarios para cargar el contexto)
- Cada endpoint público se prueba con `@WithMockUser` (el context de `@WebMvcTest` no carga `SecurityConfig`)
- Endpoints protegidos usan `@WithMockUser(authorities = "PERMISSION_...")` con la autoridad requerida
- `InscripcionController.create()` tiene `@PreAuthorize("isAuthenticated()")` para exigir autenticación explícita
- Se prueban: status HTTP (200, 201+Location, 204, 400, 404), cuerpo de respuesta con `jsonPath`
- Para `AuthController` se mockean también `AuthenticationManager`, `PasswordEncoder`

### Security (test plano + Mockito)
- `JwtTokenProviderTest`: prueba unitaria sin Spring, crea el provider con clave fija
- Verifica generación, validación y extracción de claims del token
- `CustomUserDetailsTest`: prueba el mapeo de Usuario → UserDetails
- Verifica `isEnabled()` según `usuario.activo`, authorities desde roles y permisos
- `CustomUserDetailsServiceTest`: Mockito, prueba `loadUserByUsername` con repositorio mockeado

### Integration (`@SpringBootTest`)
- Usa `RestClient` para llamar a la API embebida en puerto aleatorio (`RANDOM_PORT`)
- `BaseIntegrationTest` configura `client()` (sin auth) y `authClient()` (con token JWT)
- **AuthIntegrationTest** (9 tests): registro+login, email+password incorrecto (401), email inexistente (401), email duplicado (409), email inválido (400), password vacío (400), login con usuario inactivo (401), token inválido en endpoint protegido (403), registro con verificación de rol USER y permisos en BD
- **InscripcionIntegrationTest** (15 tests): create (201), usuarioId inexistente (404), recursoId ACTIVIDAD inexistente (404), duplicado (409), sin token (403), actividad sin límite de cupo, actividad con cupo máximo, usuario inactivo (400), DELETE sin permiso (403), GET by ID inexistente (404), GET por-usuario, GET por-recurso, GET exists (true/false), GET count-por-recurso
- **AdminInscripcionIntegrationTest** (3 tests): DELETE con permiso ADMIN (204), UPDATE con permiso ADMIN (200), flujo completo con verificación de contador `inscritos`
- **UsuarioIntegrationTest** (16 tests): listar usuarios (200), sin token (403), con token USER (403), obtener por ID (200/404), obtener por email (200/404), exists email (true/false), crear (201), email duplicado (409), actualizar (200/404), eliminar (204/404), eliminar con USER (403)
- `BaseIntegrationTest.authClient(String token)` permite autenticarse con distintos usuarios en un mismo test
- Semilla de datos: `TestDataInitializer` con `@Profile("test")` inserta roles `USER`, `ADMIN`, `MODERATOR`, `VOLUNTEER`, permisos `MANAGE_USERS` y `CREATE_ACTIVITY` con asignación al rol `ADMIN` via `roles_permisos`
- Para tests admin se usa `JdbcTemplate` para asignar el rol `ADMIN` al usuario registrado y luego obtener un token actualizado vía `auth/login`

### E2E — Frontend (Cypress)
- Corre contra el servidor Vite de desarrollo en `http://localhost:5173`
- Navegador: Electron en modo headless (CI) o interactivo (`cy:open`)
- **`cypress/e2e/inicio.cy.ts`** (6 tests): logo y navbar, secciones del home, persistencia del modo oscuro vía `localStorage`, filtros de calendario, enlaces de navegación, widget de accesibilidad
- **`cypress/e2e/asesoria.cy.ts`** (1 test): carga directa de `/asesoria`

### Mapper (test plano)
- Convierte `Request` → `Entity` → `Response`
- Verifica que campos se mapeen correctamente (incluyendo nulos)

## Convenciones

- Nombres de test: `metodo_descripcion` en español
- Todo test debe ser determinista (no depender de estado global)
- Usar `assertThat()` de AssertJ (fluido, legible)
- `@ActiveProfiles("test")` en toda clase `@DataJpaTest`
- Evitar `@SpringBootTest` a menos que sea necesario (lento)
