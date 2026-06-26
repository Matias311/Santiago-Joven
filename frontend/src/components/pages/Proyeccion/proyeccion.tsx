// =============================================================
// ARCHIVO: proyeccion.tsx
// PROPOSITO: Pagina principal de Proyeccion y Accion Joven.
//            Orquesta las secciones: Hero, Cursos, Accion Joven y Contacto.
//
// ESTRUCTURA:
//   1. ProyeccionHero      — Hero con gradiente animado y tabs de navegacion
//   2. CursosSection       — Cursos destacados con ProyeccionCard1 (azul)
//   3. AccionJovenSection  — Voluntariado con ProyeccionCard2 (naranja)
//   4. ContactoSection     — Formulario + datos de ubicacion + mapa
//
// COMPONENTES DE CARTA:
//   - ProyeccionCard1 (./proyeccioncard1) — tema azul, grilla con bullets
//   - ProyeccionCard2 (./proyeccioncard2) — tema naranja, descripcion centrada
//
// PARA CONECTAR CON LA API:
//   Los arrays "cursosDestacados" y "accionJovenCards" son los puntos
//   de entrada de datos. Cuando la API este lista, reemplazar cada array
//   por un fetch/useEffect que llene el mismo tipo CartaItem[].
//   El handleSubmit del formulario tambien esta marcado con TODO.
// =============================================================

import { useState } from "react";
import "./proyeccion.css";
import ProyeccionCard1 from "./proyeccioncard1";
import ProyeccionCard2 from "./proyeccioncard2";
import ProyeccionCarrusel from "./proyeccioncarrusel";
import { Base } from "../../Base/Base.tsx";
import type { CartaItem } from "../../types/CartaItem";

// ─── DATOS: Cursos Destacados (tema azul) ────────────────────────────────────
// Cada objeto CartaItem alimenta un ProyeccionCard1.
//
// El campo "descripcion" usa " | " como separador de los 4 bullets
// que rodean el icono central de la grilla. Cuando la API este lista,
// este campo puede ser reemplazado por un array "bullets: string[]".
//
// TODO: Reemplazar este array por datos del endpoint de cursos.
const cursosDestacados: CartaItem[] = [
  {
    icono: "smart_toy",
    iconoColor: "#ffffff",
    iconoFondo: "linear-gradient(135deg, #1f75b3, #38d9a9)",
    iconoTamaño: "36px",
    tituloColor: "#1a1a2e",
    sliderSombra: "0 8px 32px rgba(31,117,179,0.18)",
    titulo: "Introducción a la Inteligencia Artificial",
    subtitulo: "¿Te interesa el uso de la IA?",
    descripcion:
      "Descubre los fundamentos de la IA, aprende sobre machine learning y cómo esta tecnología está cambiando el mundo. | Crea tu propia Inteligencia Artificial desde 0. Aplica lo aprendido y dale un avance tecnologico a tus proyectos. | Aprenderás sobre las redes neuronales y su increible capacidad de crear ideas o proyectos unicos. | Obtendras habilidades imprescindibles para el desarrollo tecnonologico futuro, no te quedes atrás avanza con el mundo!",
    boton: "Inscríbete aquí",
    botoncolor: "#1f75b3",
    clase: "proj-card-azul",
  },
  {
    icono: "code",
    iconoColor: "#ffffff",
    iconoFondo: "linear-gradient(135deg, #1f75b3, #4f8ef7)",
    iconoTamaño: "36px",
    tituloColor: "#1a1a2e",
    sliderSombra: "0 8px 32px rgba(31,117,179,0.18)",
    titulo: "Programación Web para Principiantes",
    subtitulo: "HTML, CSS y JavaScript desde cero",
    descripcion:
      "Aprende a crear sitios web modernos desde las bases del HTML. | Domina CSS para dar estilo y diseño a tus páginas web. | Incorpora JavaScript para agregar interactividad a tus proyectos. | Publica tu primera página en internet sin experiencia previa.",
    boton: "Inscríbete aquí",
    botoncolor: "#1f75b3",
    clase: "proj-card-azul",
  },
  {
    icono: "content_copy",
    iconoColor: "#ffffff",
    iconoFondo: "linear-gradient(135deg, #1f75b3, #38d9a9)",
    iconoTamaño: "36px",
    tituloColor: "#1a1a2e",
    sliderSombra: "0 8px 32px rgba(31,117,179,0.18)",
    titulo: "Excel: Básico e Intermedio",
    subtitulo: "La herramienta laboral más solicitada",
    descripcion:
      "Domina las fórmulas básicas de la planilla más usada del mundo. | Crea tablas dinámicas para analizar datos con facilidad. | Genera gráficos profesionales para presentar tu información. | Obtén habilidades imprescindibles para el mercado laboral actual.",
    boton: "Inscríbete aquí",
    botoncolor: "#1f75b3",
    clase: "proj-card-azul",
  },
];

// ─── DATOS: Accion Joven – Voluntariado (tema naranja) ───────────────────────
// Cada objeto CartaItem alimenta un ProyeccionCard2.
//
// TODO: Reemplazar este array por datos del endpoint de actividades.
const accionJovenCards: CartaItem[] = [
  {
    icono: "volunteer_activism",
    iconoColor: "#ffffff",
    iconoFondo: "linear-gradient(135deg, #f5a623, #ff7043)",
    iconoTamaño: "36px",
    tituloColor: "#1a1a2e",
    sliderSombra: "0 8px 32px rgba(245,166,35,0.22)",
    titulo: "Proyectos de Impacto Social",
    descripcion:
      "Participa en campañas de reforestación, visitas a hogares de ancianos, colectas de alimentos y muchas otras actividades que generan un impacto positivo en la comunidad.",
    boton: "¡Quiero ser voluntario!",
    botoncolor: "#f5a623",
    clase: "proj-card-naranja",
  },
  {
    icono: "park",
    iconoColor: "#ffffff",
    iconoFondo: "linear-gradient(135deg, #f5a623, #ff7043)",
    iconoTamaño: "36px",
    tituloColor: "#1a1a2e",
    sliderSombra: "0 8px 32px rgba(245,166,35,0.22)",
    titulo: "Reforestación Comunitaria",
    descripcion:
      "Únete a nuestras brigadas verdes. Plantamos árboles en parques y espacios públicos de la comuna, recuperando espacios naturales para todos los vecinos.",
    boton: "Participar",
    botoncolor: "#f5a623",
    clase: "proj-card-naranja",
  },
  {
    icono: "groups",
    iconoColor: "#ffffff",
    iconoFondo: "linear-gradient(135deg, #f5a623, #ff7043)",
    iconoTamaño: "36px",
    tituloColor: "#1a1a2e",
    sliderSombra: "0 8px 32px rgba(245,166,35,0.22)",
    titulo: "Red de Jóvenes Líderes",
    descripcion:
      "Conecta con otros jóvenes que buscan marcar la diferencia. Comparte experiencias, aprende nuevas habilidades y construye un futuro mejor para tu comunidad.",
    boton: "Sumarme",
    botoncolor: "#f5a623",
    clase: "proj-card-naranja",
  },
];

// ─── SUBCOMPONENTE: ProyeccionHero ───────────────────────────────────────────
// Hero con gradiente animado, eyebrow, titulo, subtitulo y tabs de navegacion.
//
// INTERACCION: Al hacer clic en una tab se llama onTabChange(tabId),
//              el padre hace scroll suave a la seccion con id="section-{tabId}".
//
// Props:
//   activeTabId  — ID de la tab actualmente activa
//   onTabChange  — Callback al cambiar de tab
function ProyeccionHero({
  activeTabId,
  onTabChange,
}: {
  activeTabId: string;
  onTabChange: (id: string) => void;
}) {
  const tabs = [
    { id: "cursos", label: "Proyección", icono: "rocket_launch" },
    { id: "accion", label: "Acción Joven", icono: "campaign" },
  ];

  return (
    <section
      className="proj-hero"
      aria-label="Encabezado de la página Proyección"
    >
      <div className="proj-hero__blob proj-hero__blob--1" aria-hidden="true" />
      <div className="proj-hero__blob proj-hero__blob--2" aria-hidden="true" />

      <div className="proj-hero__content">
        <span className="proj-hero__eyebrow">
          <span
            className="material-symbols-outlined"
            style={{ fontSize: "16px" }}
          >
            stars
          </span>
          Municipalidad de Santiago
        </span>

        <h1 className="proj-hero__title">Proyección y Acción Joven</h1>

        <p className="proj-hero__subtitle">
          ¡Tu energía puede cambiar las cosas! Súmate a nuestras iniciativas
          sociales, cursos gratuitos y proyectos de voluntariado.
        </p>

        <nav
          className="proj-hero__tabs"
          role="tablist"
          aria-label="Navegación de secciones"
        >
          {tabs.map((tab) => (
            <button
              key={tab.id}
              role="tab"
              aria-selected={activeTabId === tab.id}
              aria-controls={`section-${tab.id}`}
              className={`proj-tab ${activeTabId === tab.id ? "proj-tab--active" : ""}`}
              onClick={() => onTabChange(tab.id)}
            >
              <span
                className="material-symbols-outlined proj-tab__icon"
                aria-hidden="true"
              >
                {tab.icono}
              </span>
              <span className="proj-tab__label">{tab.label}</span>
            </button>
          ))}
        </nav>
      </div>
    </section>
  );
}

// ─── SUBCOMPONENTE: CursosSection ────────────────────────────────────────────
// Seccion de cursos con fondo azul radial.
// Muestra una ProyeccionCard1 a la vez dentro de un ProyeccionCarrusel.
//
// ESTADO: indiceCurso controla que carta se muestra.
// NAVEGACION: swipe horizontal y dots de paginacion (sin flechas).
function CursosSection() {
  const [indiceCurso, setIndiceCurso] = useState(0);

  return (
    <section
      id="section-cursos"
      className="proj-section proj-section--blue"
      aria-labelledby="cursos-heading"
    >
      <div className="proj-section__inner">
        <div className="proj-section__header">
          <span
            className="material-symbols-outlined proj-section__icon"
            style={{ color: "#1f75b3" }}
            aria-hidden="true"
          >
            school
          </span>
          <h2 id="cursos-heading" className="proj-section__title">
            Cursos Destacados
          </h2>
          <p className="proj-section__subtitle proj-section__subtitle--link">
            Impulsa tu futuro. Cursos gratuitos y herramientas para tu
            crecimiento.
          </p>
        </div>

        {/* Carrusel: una carta azul a la vez, swipe + dots */}
        <ProyeccionCarrusel
          tema="blue"
          total={cursosDestacados.length}
          indice={indiceCurso}
          onChange={setIndiceCurso}
        >
          {cursosDestacados.map((curso) => (
            <ProyeccionCard1
              key={curso.titulo}
              {...curso}
              onBoton={() => {
                // TODO: Conectar con flujo de inscripcion o modal de la API
                console.log("Inscripcion:", curso.titulo);
              }}
            />
          ))}
        </ProyeccionCarrusel>
      </div>
    </section>
  );
}

// ─── SUBCOMPONENTE: AccionJovenSection ───────────────────────────────────────
// Seccion de voluntariado con fondo naranja radial.
// Muestra una ProyeccionCard2 a la vez dentro de un ProyeccionCarrusel.
//
// ESTADO: indiceAccion controla que carta se muestra.
// NAVEGACION: swipe horizontal y dots de paginacion (sin flechas).
function AccionJovenSection() {
  const [indiceAccion, setIndiceAccion] = useState(0);

  return (
    <section
      id="section-accion"
      className="proj-section proj-section--orange"
      aria-labelledby="accion-heading"
    >
      <div className="proj-section__inner">
        <div className="proj-section__header">
          <span
            className="material-symbols-outlined proj-section__icon proj-section__icon--pulse"
            style={{ color: "#f5a623" }}
            aria-hidden="true"
          >
            campaign
          </span>
          <h2 id="accion-heading" className="proj-section__title">
            Acción Joven
          </h2>
          <p className="proj-section__subtitle">
            ¡Tu energía puede cambiar las cosas! Súmate al voluntariado
            comunitario.
          </p>
        </div>

        {/* Carrusel: una carta naranja a la vez, swipe + dots */}
        <ProyeccionCarrusel
          tema="orange"
          total={accionJovenCards.length}
          indice={indiceAccion}
          onChange={setIndiceAccion}
        >
          {accionJovenCards.map((accion) => (
            <ProyeccionCard2
              key={accion.titulo}
              {...accion}
              onBoton={() => {
                // TODO: Conectar con flujo de inscripcion al voluntariado
                console.log("Voluntariado:", accion.titulo);
              }}
            />
          ))}
        </ProyeccionCarrusel>
      </div>
    </section>
  );
}

// ─── SUBCOMPONENTE: ContactoSection ──────────────────────────────────────────
// Formulario de contacto + informacion de ubicacion + mapa embebido.
//
// ESTADO: Maneja el formulario con useState local.
// TODO: Reemplazar handleSubmit con llamada real a la API de contacto.
function ContactoSection() {
  const [form, setForm] = useState({ nombre: "", correo: "", mensaje: "" });

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  };

  // TODO: Conectar con la API de contacto cuando este disponible
  const handleSubmit = () => {
    alert("Mensaje enviado (mock). Pronto conectaremos con la API real.");
    setForm({ nombre: "", correo: "", mensaje: "" });
  };

  return (
    <section
      id="section-contacto"
      className="proj-section proj-contacto"
      aria-labelledby="contacto-heading"
    >
      <div className="proj-contacto__pin" aria-hidden="true">
        <span
          className="material-symbols-outlined"
          style={{ fontSize: "48px", color: "#1f75b3" }}
        >
          location_on
        </span>
      </div>

      <div className="proj-section__inner">
        <div className="proj-section__header">
          <h2 id="contacto-heading" className="proj-section__title">
            Contáctanos y Ubícanos
          </h2>
          <p className="proj-section__subtitle">
            ¿Tienes preguntas? Escríbenos o visítanos en nuestra sede.
          </p>
        </div>

        <div className="proj-contacto__grid">
          {/* Columna izquierda: formulario */}
          <div className="proj-contacto__form">
            <h3 className="proj-contacto__col-title">Envíanos un mensaje</h3>

            <label className="proj-label" htmlFor="proj-nombre">
              Nombre
            </label>
            <input
              id="proj-nombre"
              className="proj-input"
              name="nombre"
              type="text"
              value={form.nombre}
              onChange={handleChange}
              placeholder="Tu nombre completo"
              autoComplete="name"
            />

            <label className="proj-label" htmlFor="proj-correo">
              Correo Electrónico
            </label>
            <input
              id="proj-correo"
              className="proj-input"
              name="correo"
              type="email"
              value={form.correo}
              onChange={handleChange}
              placeholder="tucorreo@ejemplo.com"
              autoComplete="email"
            />

            <label className="proj-label" htmlFor="proj-mensaje">
              Mensaje
            </label>
            <textarea
              id="proj-mensaje"
              className="proj-input proj-textarea"
              name="mensaje"
              value={form.mensaje}
              onChange={handleChange}
              placeholder="¿En qué podemos ayudarte?"
            />

            <button
              className="proj-btn proj-btn--blue"
              onClick={handleSubmit}
              type="button"
            >
              <span
                className="material-symbols-outlined"
                style={{ fontSize: "18px" }}
              >
                send
              </span>
              Enviar Mensaje
            </button>
          </div>

          {/* Columna derecha: informacion de ubicacion */}
          <div className="proj-contacto__info">
            <h3 className="proj-contacto__col-title">Nuestra Ubicación</h3>

            <div className="proj-contacto__info-item">
              <span
                className="material-symbols-outlined proj-contacto__info-icon"
                style={{ color: "#1f75b3" }}
                aria-hidden="true"
              >
                location_on
              </span>
              <p>
                Herrera 360, Comuna de Santiago.
                <br />
                (Centro Comunitario Santiago en Compañía)
              </p>
            </div>

            <div className="proj-contacto__info-item">
              <span
                className="material-symbols-outlined proj-contacto__info-icon"
                style={{ color: "#1f75b3" }}
                aria-hidden="true"
              >
                schedule
              </span>
              <p>
                Lunes a jueves: 09:00 – 18:00 hrs
                <br />
                Viernes: 09:00 – 17:00 hrs
              </p>
            </div>

            <div className="proj-contacto__info-item">
              <span
                className="material-symbols-outlined proj-contacto__info-icon"
                style={{ color: "#1f75b3" }}
                aria-hidden="true"
              >
                email
              </span>
              <a
                className="proj-contacto__email"
                href="mailto:stgojoven@munistgo.cl"
              >
                stgojoven@munistgo.cl
              </a>
            </div>
          </div>
        </div>

        <div className="proj-contacto__map-row">
          <iframe
            title="Ubicación Centro Comunitario Santiago en Compañía"
            src="https://maps.google.com/maps?q=Herrera+360+Santiago&output=embed"
            allowFullScreen
            loading="lazy"
            className="proj-contacto__map"
          />
        </div>
      </div>
    </section>
  );
}

// ─── COMPONENTE PRINCIPAL: ProyeccionPage ────────────────────────────────────
// Orquestador de la pagina. Maneja el estado de la tab activa
// y el scroll suave entre secciones.
function ProyeccionPage() {
  const [activeTabId, setActiveTabId] = useState("cursos");

  const handleTabChange = (tabId: string) => {
    setActiveTabId(tabId);
    const target = document.getElementById(`section-${tabId}`);
    if (target) {
      target.scrollIntoView({ behavior: "smooth", block: "start" });
    }
  };

  const content = (
    <main className="proj-page" id="proyeccion-main">
      <ProyeccionHero activeTabId={activeTabId} onTabChange={handleTabChange} />
      <CursosSection />
      <div className="proj-section-divider" aria-hidden="true" />
      <AccionJovenSection />
      <ContactoSection />
    </main>
  );

  return <Base content={content} />;
}

export default ProyeccionPage;
