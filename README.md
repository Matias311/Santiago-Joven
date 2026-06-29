# Aplicacion Web para Santiago Joven

## Requisitos tecnicos para uso
- Git 
- Docker

## Instalacion y configuracion de la aplicacion
- Clonar el repositorio para poder trabajar
```bash
    git clone url
```
- Ir al directorio creado
```bash
    cd santiago-joven
```
- Crear archivo `.env` y agregar las variables necesarias
```bash
OPENAI_API_KEY=
URL_DB=
DB_USER=
DB_PASSWORD=
```

- Ejecutar con docker

  **Desarrollo** (hot-reload al cambiar codigo — frontend en `localhost:3000`, backend en `localhost:8080/api/v1`):
  ```bash
  docker compose --profile dev up --build
  ```

  **Tests** (ejecutar por separado para evitar que un servicio aborte al otro):
  ```bash
  docker compose --profile test run --build backend-test   # Backend: 435 tests unitarios + integracion
  docker compose --profile test run frontend-test          # Frontend: ESLint
  docker compose --profile test run --rm frontend-e2e      # Frontend: 7 tests E2E con Cypress
  ```

  **Produccion:**
  ```bash
  docker compose --profile prod up --build -d
  ```

## Documentacion interactiva (Swagger)

Una vez ejecutando el backend en desarrollo:

| URL | Descripcion |
|-----|-------------|
| `http://localhost:8080/swagger-ui/index.html` | UI interactiva para probar endpoints |
| `http://localhost:8080/api-docs` | Esquema OpenAPI en JSON |

Desde Swagger UI puedes:
- Probar `POST /api/v1/auth/login` para obtener un token JWT
- Probar `POST /api/v1/auth/recuperar` y `/restablecer` para recuperar contrasena con OTP
- Usar el boton **Authorize** (🔒) para ingresar el token
- Probar cualquier endpoint protegido

## Documentacion adicional
- Pruebas: `docs/pruebas-unitarias.md`
- Arquitectura: `docs/arquitectura.md`
- Stack tecnologico: `docs/stack-tecnologico.md`
- Modelo de datos: `docs/modelo-datos.md`
- Decisiones tecnicas: `docs/decisiones-tecnicas.md`
- Consumir API desde frontend: `docs/consumir-api.md`

## Integrantes:
- Dario Jara: 
    - proyeccion joven
    - detalle del programa
- Luis Castro: 
    - Pagina Principal
    - Pop up logica de encuesta
    - chatbot
    - accesibilidad 
- Guillermo Farias: 
    - Login (logica + diseño)
    - asesorias
    - Ayuda al equipo
- Joshua Espinoza 
    - Pagina de administrador 
    - conexion comunitaria
- Hernan Sotelo: 
    - Nuestro programas
    - Pagina de detalle de programa
- Felipe Sanchez: 
    - Diseño de figma 
    - pagina principal css
    - ayuda general pal diseño responsive
- Matias Arias: 
    - Backend  
    - arquitectura del proyecto
    - DevOps (Test, despliegue, calidad de codigo, CI/CD, etc)
