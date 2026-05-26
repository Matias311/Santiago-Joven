# Stack y tecnologias elegidas:

## Front-end:
    - **React 19.2.0** - UI escalable y aprovechar SPA (Single Page Application)
    - **TypeScript 5.9.3** - Tipado fuerte para prevenir errores de runtime y mejor manejo de tipos
    - **React Router 7.9.5** - Navegacion de rutas en SPA (Single Page Application)
    - **Font Awesome 7.1.0** - Para tener iconos consistentes en toda la pagina
    - **Vite 7.2.2** - Build rapido
    - **CSS** - Estilos de la pagina

## Backend:
    - **Java 21** - LTS version estable
    - **Spring Boot 4.0.6** - Ultima version estable de Spring Boot para Java 21
    - **SpringAI 2.0.0-M6** - Para poder trabajar con modelos de IA y poder centralizar y agregar seguridad extra al chatbot
    - **Lombok** - Para eliminar el codigo repetido y poder trabajar de manera eficiente en la creacion de las clases
    - **JPA - Hibernate** - ORM 

## Base de datos:
    - **H2 (dev/test)** - SetUp rapido para el desarrollo local (no estar configurando al principio del desarrollo una base de datos)
    - **Postgresql (produccion)** - Robustez y soporte SQL avanzado (utilizar bytea para imagenes e informacion avanzada)

## DevOps:
    - **Docker** - Empaquetar de manera consistente la aplicacion
    - **Docker Compose** - Organizacion local de los servicios del backend y del frontend
    - **Vercel** - Despliegue rapido del frontend
