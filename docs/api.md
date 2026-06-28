# API REST

Base URL: `/api/v1`

## Autenticacion

### POST /auth/login

```
Request:
  { "email": "usuario@ejemplo.cl", "password": "MiPassword123!" }

Response 200:
  { "token": "eyJ...", "userId": "uuid", "email": "usuario@ejemplo.cl", "roles": ["USER"] }
```

### POST /auth/register

Crea un usuario con rol `USER`. El password se hashea con BCrypt antes de persistir.

```
Request:
  { "email": "...", "password": "...", "nombre": "...", "apellido": "..." }

Response 201:
  { "token": "eyJ...", "userId": "uuid", "email": "...", "roles": ["USER"] }
```

### Authorization header

Todos los endpoints protegidos requieren:

```
Authorization: Bearer <token>
```

El token JWT expira en 24h por defecto.

## Rutas publicas

No requieren autenticacion:

| Metodo | Ruta |
|--------|------|
| POST | `/api/v1/auth/**` |
| GET | `/api/v1/salud-mental/**` |
| GET | `/api/v1/asesorias/**` |
| GET | `/api/v1/cursos-destacados/**` |
| GET | `/api/v1/actividades-talleres/**` |
| GET | `/api/v1/programas/**` |
| GET | `/api/v1/acciones-joven/**` |
| GET | `/api/v1/categorias/**` |
| GET | `/api/v1/galeria-fotos/**` |
| GET | `/api/v1/ubicaciones/**` |
| GET | `/api/v1/tu-contribucion-cuenta/**` |
| GET | `/api/v1/estadisticas/**` |
| POST | `/api/v1/contactos` |

## Endpoints CRUD por entidad

Cada entidad sigue el patron REST estandar (GET findAll, GET findById, POST create, PUT update, DELETE delete):

| Recurso | Path base |
|---------|-----------|
| Asesorias | `/asesorias` |
| Cursos Destacados | `/cursos-destacados` |
| Actividades / Talleres | `/actividades-talleres` |
| Programas | `/programas` |
| Accion Joven | `/acciones-joven` |
| Categorias | `/categorias` |
| Galeria de Fotos | `/galeria-fotos` |
| Ubicaciones | `/ubicaciones` |
| Salud Mental | `/salud-mental` |
| Tu Contribucion Cuenta | `/tu-contribucion-cuenta` |
| Inscripciones | `/inscripciones` |
| Resenas / Calificaciones | `/resenas-calificaciones` |
| Estadisticas | `/estadisticas` |
| Contactos | `/contactos` |
| Usuarios | `/usuarios` |
| Roles | `/roles` |
| Permisos | `/permisos` |
| Auditoria | `/auditoria` |

### Auditoria

Solo lectura (`GET`). Sin POST/PUT/DELETE.

## Endpoints custom

### Accion Joven

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/v1/acciones-joven/activas` | Lista acciones activas |

### Actividades / Talleres

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/v1/actividades-talleres/por-categoria/{categoriaId}` | Filtra por categoria |
| GET | `/api/v1/actividades-talleres/por-estado/{estado}` | Filtra por estado |
| GET | `/api/v1/actividades-talleres/entre-fechas?inicio=&fin=` | Rango de fechas |
| GET | `/api/v1/actividades-talleres/activas` | Solo activas |
| GET | `/api/v1/actividades-talleres/proximas` | Proximas a ocurrir |

### Asesorias

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/v1/asesorias/por-categoria/{categoriaId}` | Filtra por categoria |
| GET | `/api/v1/asesorias/activas` | Solo asesorias activas |

### Auditoria

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/v1/auditoria/por-entidad?entidadTipo=&entidadId=` | Historial por entidad |
| GET | `/api/v1/auditoria/por-usuario/{usuarioId}` | Acciones de un usuario |

### Categorias

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/v1/categorias/por-nombre/{nombre}` | Busca por nombre exacto |
| GET | `/api/v1/categorias/por-tipo/{tipo}` | Filtra por tipo |

### Contactos

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/v1/contactos/no-respondidos` | Contactos sin respuesta |
| GET | `/api/v1/contactos/por-email/{email}` | Busca por email del remitente |

### Cursos Destacados

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/v1/cursos-destacados/por-categoria/{categoriaId}` | Filtra por categoria |
| GET | `/api/v1/cursos-destacados/activos` | Solo cursos activos |

### Estadisticas

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/v1/estadisticas/por-recurso?recursoId=&tipoRecurso=` | Estadisticas de un recurso |

### Galeria Fotos

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/v1/galeria-fotos/por-actividad/{actividadId}` | Fotos de una actividad |

### Inscripciones

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/v1/inscripciones/por-usuario/{usuarioId}` | Inscripciones de un usuario |
| GET | `/api/v1/inscripciones/por-recurso?recursoId=&tipoRecurso=` | Inscripciones de un recurso |
| GET | `/api/v1/inscripciones/por-estado/{estado}` | Filtra por estado |
| GET | `/api/v1/inscripciones/count-por-recurso?recursoId=&tipoRecurso=` | Cantidad de inscripciones |
| GET | `/api/v1/inscripciones/exists?usuarioId=&recursoId=&tipoRecurso=` | Verifica si existe inscripcion |

### Permisos

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/v1/permisos/por-nombre/{nombre}` | Busca por nombre |
| GET | `/api/v1/permisos/por-modulo/{modulo}` | Filtra por modulo |

### Programas

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/v1/programas/activos` | Solo programas activos |

### Resenas / Calificaciones

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/v1/resenas-calificaciones/por-recurso?recursoId=&tipoRecurso=` | Resenas de un recurso |
| GET | `/api/v1/resenas-calificaciones/por-usuario/{usuarioId}` | Resenas de un usuario |
| GET | `/api/v1/resenas-calificaciones/por-calificacion-minima/{calificacion}` | Calificacion minima |

### Roles

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/v1/roles/por-nombre/{nombre}` | Busca por nombre |

### Salud Mental

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/v1/salud-mental/ordenados` | Lista ordenada (orden + nombre) |

### Tu Contribucion Cuenta

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/v1/tu-contribucion-cuenta/activo` | Registro activo actual |

### Ubicaciones

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/v1/ubicaciones/por-ciudad/{ciudad}` | Filtra por ciudad |

### Usuarios

| Metodo | Ruta | Descripcion |
|--------|------|-------------|
| GET | `/api/v1/usuarios/por-email/{email}` | Busca por email exacto |
| GET | `/api/v1/usuarios/exists-email/{email}` | Verifica si el email existe |

## Permisos por endpoint

Los metodos POST/PUT/DELETE estan protegidos con `@PreAuthorize` y permisos especificos:

| Permiso | Endpoints protegidos |
|---------|---------------------|
| `MANAGE_USERS` | CRUD `/usuarios`, `/contactos`, `/inscripciones`, `/resenas-calificaciones`, `/tu-contribucion-cuenta` |
| `MANAGE_ROLES` | CRUD `/roles`, `/permisos` |
| `VIEW_ANALYTICS` | CRUD `/auditoria`, `/estadisticas` |
| `CREATE_PROGRAM` | POST `/programas`, `/acciones-joven` |
| `EDIT_PROGRAM` | PUT `/programas`, `/acciones-joven` |
| `DELETE_PROGRAM` | DELETE `/programas`, `/acciones-joven` |
| `CREATE_ACTIVITY` | POST `/actividades-talleres` |
| `EDIT_ACTIVITY` | PUT `/actividades-talleres` |
| `DELETE_ACTIVITY` | DELETE `/actividades-talleres` |
| `CREATE_ASESORIA` | POST `/asesorias` |
| `EDIT_ASESORIA` | PUT `/asesorias` |
| `DELETE_ASESORIA` | DELETE `/asesorias` |
| `CREATE_COURSE` | POST `/cursos-destacados` |
| `EDIT_COURSE` | PUT `/cursos-destacados` |
| `DELETE_COURSE` | DELETE `/cursos-destacados` |
| `MANAGE_GALLERY` | CRUD `/categorias`, `/galeria-fotos` |
| `EDIT_HEALTH` | CRUD `/salud-mental` |
| `MANAGE_LOCATIONS` | CRUD `/ubicaciones` |
