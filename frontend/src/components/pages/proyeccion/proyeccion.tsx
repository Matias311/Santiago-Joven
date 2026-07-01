import "./proyeccion.css";
import Card from "../../cartas/Card";
import Slider from "../../sliders/slider";
import { useProyeccion } from "../../types/useProyeccion";

/**
 * Página de Proyección Joven.
 *
 * Muestra tres secciones principales:
 * - Hero con accesos rápidos a Proyección y Acción Joven.
 * - Slider de cursos destacados y slider de proyectos de voluntariado.
 * - Sección de contacto y ubicación.
 *
 * Los datos (cursos, proyectos de Acción Joven, contacto) vienen del hook
 * `useProyeccion`, que hoy los devuelve estáticos pero ya con la forma que
 * tendrá la respuesta de la API. Este componente no maneja datos, solo los
 * pinta.
 */
export default function Proyeccion() {
  const { cursos, proyectosAccion, contacto, cargando } = useProyeccion();

  return (
    <>
      {/* Hero: Proyección y Acción Joven */}
      <header id="proyeccion-hero" className="proyeccion-section">
        <div className="proyeccion-texto">
          <h1>Proyección y Acción Joven</h1>
          <p>
            ¡Tu energía puede cambiar las cosas e impulsar tu futuro! Súmate a
            nuestras iniciativas sociales y proyectos de voluntariado mientras
            accedes a cursos y herramientas para tu crecimiento personal y
            profesional.
          </p>
        </div>
        <div className="proyeccion-accesos">
          <Card
            icono="rocket"
            iconoColor="#E3E3E3"
            iconoTamaño="2.5rem"
            titulo="Proyección"
            ruta="#cursos-destacados"
          />
          <Card
            icono="campaign"
            iconoColor="#E3E3E3"
            iconoTamaño="2.5rem"
            titulo="Acción Joven"
            ruta="#accion-joven"
          />
        </div>
      </header>

      <main className="contenido-principal">
        {/* Cursos Destacados */}
        <section id="cursos-destacados" className="seccion-informativa">
          <div className="seccion-encabezado">
            <h2>Cursos Destacados</h2>
            <p>
              Impulsa tu futuro. Aquí encontrarás cursos y herramientas para tu
              crecimiento personal y profesional.
            </p>
          </div>
          {cargando ? (
            <p className="proyeccion-cargando">Cargando cursos...</p>
          ) : (
            <Slider cartas={cursos} />
          )}
        </section>

        {/* Acción Joven */}
        <div className="fondo-naranja-suave">
          <section
            id="accion-joven"
            className="accion-joven seccion-informativa"
          >
            <div className="seccion-encabezado">
              <span
                className="material-symbols-outlined seccion-icono"
                style={{ color: "#f59e0b", fontSize: "70px" }}
              >
                campaign
              </span>
              <h2>Acción Joven</h2>
              <p>
                ¡Tu energía puede cambiar las cosas! Súmate a nuestras
                iniciativas sociales y proyectos de voluntariado.
              </p>
            </div>
            {cargando ? (
              <p className="proyeccion-cargando">Cargando...</p>
            ) : (
              <Slider cartas={proyectosAccion} />
            )}
          </section>
        </div>

        {/* Contáctanos y Ubícanos */}
        <section
          id="contacto"
          className="contacto-ubicacion seccion-informativa"
        >
          <div className="seccion-encabezado">
            <span
              className="material-symbols-outlined seccion-icono"
              style={{ color: "#0077FF", fontSize: "70px" }}
            >
              location_on
            </span>
            <h2>Contáctanos y Ubícanos</h2>
            <p>
              ¿Tienes preguntas? Escríbenos. También puedes visitarnos en
              nuestra oficina.
            </p>
          </div>
          <div className="contacto-layout">
            <div className="formulario-contacto">
              <h3>Envíanos un mensaje</h3>
              <label>Nombre</label>
              <input type="text" />
              <label>Correo Electrónico</label>
              <input type="email" />
              <label>Mensaje</label>
              <textarea></textarea>
              <button className="btn-enviar-contacto">Enviar Mensaje</button>
            </div>
            <div className="info-ubicacion">
              <h3>Nuestra Ubicación</h3>
              <div className="ubicacion-item">
                <span className="material-symbols-outlined seccion-icono">
                  location_on
                </span>
                <p>{contacto.direccion}</p>
              </div>
              <div className="ubicacion-item">
                <span className="material-symbols-outlined seccion-icono">
                  schedule
                </span>
                <p>{contacto.horario}</p>
              </div>
              <div className="ubicacion-item">
                <span className="material-symbols-outlined seccion-icono">
                  email
                </span>
                <p>{contacto.email}</p>
              </div>
            </div>
          </div>
          <div className="mapa-ubicacion">
            <iframe
              title="Ubicación de Santiago Joven"
              src="https://www.google.com/maps?q=Herrera%20360,%20Santiago,%20Chile&output=embed"
              loading="lazy"
              referrerPolicy="no-referrer-when-downgrade"
            ></iframe>
          </div>
        </section>
      </main>
    </>
  );
}
