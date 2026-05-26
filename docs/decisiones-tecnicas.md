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
