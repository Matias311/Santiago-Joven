import "./proyeccion.css";
import Card from "../../cartas/Card";
import Slider from "../../sliders/slider";
import type { CartaItem } from "../../types/CartaItem";

/**
 * Datos de los cursos destacados mostrados en el slider de Proyección.
 * Cada item representa un curso con su ícono, colores y descripción.
 */
const cursos: CartaItem[] = [
  {
    icono: "smart_toy",
    iconoMedio: "smart_toy",
    tema: "azul",
    titulo: "Introducción a la Inteligencia Artificial",
    subtitulo: "¿Te interesa el uso de la IA?",
    boton: "Ver Detalles",
    gridItems: [
      "1.-Descubre los fundamentos de la IA, aprende sobre machine learning y cómo esta tecnología está cambiando el mundo.",
      "2.-Crea tu propia Inteligencia Artificial desde 0. Aplica lo aprendido y dale un avance tecnológico a tus proyectos.",
      "3.-Aprenderás sobre las redes neuronales y su increíble capacidad de crear ideas o proyectos únicos.",
      "4.-Obtendrás habilidades imprescindibles para el desarrollo tecnológico futuro; no te quedes atrás y avanza con el mundo.",
    ],
  },
  {
    icono: "description",
    iconoMedio: "description",
    tema: "verde",
    titulo: "Excel: Básico e Intermedio",
    subtitulo: "¿Muchos datos, poca organización?",
    boton: "Ver Detalles",
    gridItems: [
      "Domina la planilla de cálculo más usada. Desde fórmulas básicas hasta tablas dinámicas. Esencial para cualquier trabajo.",
      "Aprende a automatizar procesos; muchas veces agregar datos a mano no da abasto, aquí te enseñaremos a ser un experto en Excel.",
      "Organiza de mejor manera los datos, nada es mejor que un buen orden y coherencia, haz tus proyectos intuitivos y útiles para mejorar el flujo de trabajo.",
      "Prepárate para el mundo laboral, hoy en día los empleadores solicitan diferentes tipos de capacitaciones, prepárate desde antes aquí y vuélvete un experto para el futuro.",
    ],
  },
];

/**
 * Datos de los proyectos de voluntariado mostrados en el slider de Acción Joven.
 */
const proyectosAccion: CartaItem[] = [
  {
    icono: "campaign",
    iconoMedio: "campaign",
    tema: "naranja",
    titulo: "Proyectos de Impacto Social",
    descripcion:
      "Participa en campañas de reforestación, visitas a hogares de ancianos, colectas de alimentos y muchas otras actividades pensadas para generar un impacto positivo en la comunidad. Comparte experiencias, aprende nuevas habilidades y conecta con otros jóvenes que, al igual que tú, buscan aportar con pequeñas acciones que pueden marcar una gran diferencia.",
    boton: "Ver Detalles",
  },
];

/** Datos de contacto y ubicación de la oficina. */
const contacto = {
  direccion:
    "Herrera 360, Comuna de Santiago. (Centro Comunitario Santiago en Compañía)",
  horario: "Lunes a jueves [09:00 - 18:00 hrs] - Viernes [09:00 a 17:00 hrs]",
  email: "stgojoven@munistgo.cl",
};

/**
 * Página de Proyección Joven.
 *
 * Muestra tres secciones principales:
 * - Hero con accesos rápidos a Proyección y Acción Joven.
 * - Slider de cursos destacados y slider de proyectos de voluntariado.
 * - Sección de contacto y ubicación.
 */
export default function Proyeccion() {
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
          <Slider cartas={cursos} />
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
            <Slider cartas={proyectosAccion} />
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
