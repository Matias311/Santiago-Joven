# Como consumir la API desde el frontend

La URL base ya viene definida en `import.meta.env.VITE_API_URL`. No necesitas tocarla ni crear `.env`.

## Funcion base

```typescript
const API_URL = import.meta.env.VITE_API_URL;

async function request<T>(path: string, options: RequestInit = {}): Promise<T> {
  const token = localStorage.getItem('token');

  const res = await fetch(`${API_URL}/api/v1${path}`, {
    ...options,
    headers: {
      'Content-Type': 'application/json',
      ...(token ? { Authorization: `Bearer ${token}` } : {}),
      ...options.headers,
    },
  });

  if (!res.ok) {
    const errorBody = await res.json().catch(() => null);
    throw new ApiError(
      errorBody?.message || `Error ${res.status}`,
      res.status,
      errorBody?.details || [],
    );
  }

  if (res.status === 204) return undefined as T;
  return res.json();
}

class ApiError extends Error {
  status: number;
  details: string[];

  constructor(message: string, status: number, details: string[] = []) {
    super(message);
    this.status = status;
    this.details = details;
  }
}
```

## Ejemplo 1 — Sin token (endpoint publico)

```typescript
import { useState, useEffect } from 'react';

interface SaludMentalResponse {
  id: string;
  titulo: string;
  descripcion: string;
  contenido: string;
  recurso: object;
  activo: boolean;
}

function ListaSaludMental() {
  const [items, setItems] = useState<SaludMentalResponse[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    request<SaludMentalResponse[]>('/salud-mental')
      .then(setItems)
      .catch((e: ApiError) => setError(e.message))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <p>Cargando...</p>;
  if (error) return <p className="error">Error: {error}</p>;

  return (
    <ul>
      {items.map(item => (
        <li key={item.id}>{item.titulo}</li>
      ))}
    </ul>
  );
}
```

## Ejemplo 2 — Con token (login)

```typescript
interface LoginResponse {
  token: string;
  userId: string;
  email: string;
  roles: string[];
}

async function iniciarSesion(email: string, password: string) {
  try {
    const data = await request<LoginResponse>('/auth/login', {
      method: 'POST',
      body: JSON.stringify({ email, password }),
    });

    localStorage.setItem('token', data.token);
    localStorage.setItem('userId', data.userId);
    localStorage.setItem('roles', JSON.stringify(data.roles));

    return data;
  } catch (e) {
    if (e instanceof ApiError) {
      if (e.status === 401) {
        throw new Error('Credenciales incorrectas');
      }
      if (e.status === 400) {
        throw new Error(`Datos invalidos: ${e.details.join(', ')}`);
      }
    }
    throw new Error('Error al conectar con el servidor');
  }
}
```

## Ejemplo 3 — Con token (crear contenido)

```typescript
interface CursoRequest {
  titulo: string;
  descripcion: string;
  url: string;
  activo: boolean;
}

interface CursoResponse {
  id: string;
  titulo: string;
  descripcion: string;
  url: string;
  activo: boolean;
}

async function crearCurso(data: CursoRequest) {
  const token = localStorage.getItem('token');
  if (!token) throw new Error('Debes iniciar sesion primero');

  try {
    return await request<CursoResponse>('/cursos-destacados', {
      method: 'POST',
      body: JSON.stringify(data),
    });
  } catch (e) {
    if (e instanceof ApiError) {
      if (e.status === 403) {
        throw new Error('No tienes permisos para crear cursos');
      }
    }
    throw e;
  }
}
```

## Ejemplo 4 — Con token (eliminar, 204)

```typescript
async function eliminarCurso(id: string) {
  const token = localStorage.getItem('token');
  if (!token) throw new Error('Debes iniciar sesion primero');

  try {
    await request(`/cursos-destacados/${id}`, { method: 'DELETE' });
  } catch (e) {
    if (e instanceof ApiError) {
      if (e.status === 404) throw new Error('Curso no encontrado');
      if (e.status === 403) throw new Error('No tienes permisos para eliminar');
    }
    throw e;
  }
}
```

## Codigos HTTP

| Codigo | Significado |
|--------|-------------|
| 200 | OK |
| 201 | Creado |
| 204 | Eliminado (sin contenido) |
| 400 | Datos invalidos (revisar details) |
| 401 | No autenticado (token faltante o invalido) |
| 403 | No autorizado (permiso insuficiente) |
| 404 | Recurso no encontrado |
| 409 | Conflicto (duplicado) |
| 500 | Error interno del servidor |
