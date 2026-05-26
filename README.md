# Aplicacion Web para Santiago Joven

## Requisitos tecnicos para uso
- NodeJS
- Git 
- Docker

> [!IMPORTANT]
> Para utilizar iconos tienen que copiarlos de aqui: <https://fontawesome.com/search?ic=free-collection> 

## Instalacion y configuracion de la aplicacion
- Clonar el repositorio para poder trabajar
```bash
    git clone url
```
- Ir al directorio creado
```bash
    cd santiago-joven
```
- Crear archivo `.env` y agregar la key de openai
```bash
OPENAI_API_KEY=
```
- Ejecutar con docker
```bash
docker compose up --build
```
Y listo, el frontend estara en `http://localhost:3000` y el backend en `localhost:8080/api/v1`  

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

 ## Documentacion 
- Arquitectura: `docs/arquitectura.md`
- Stack tecnologico: `docs/stack-tecnologico.md`
- Modelo de datos: `docs/modelo-datos.md`
- API: `docs/api.md`
- Decisiones tecnicas: `docs/decisiones-tecnicas.md`
- Bitacora: `docs/bitacora/`
