import "./programas.css";
import InfoCard from "../../cartas/InfoCard";
import Card from "../../cartas/Card";

// ──────────────────────────────────────────
// Iconos — Programa Lazos
// ──────────────────────────────────────────

const IconoLazosSmall = (
  <span className="material-symbols-outlined" style={{ fontSize: "32px" }}>
    favorite
  </span>
);

const IconoLazosGrande = (
  <span className="material-symbols-outlined" style={{ fontSize: "130px" }}>
    diversity_3
  </span>
);

// ──────────────────────────────────────────
// Iconos — Programa Senda
// ──────────────────────────────────────────

const IconoSendaSmall = (
  <span className="material-symbols-outlined" style={{ fontSize: "32px" }}>
    route
  </span>
);

const IconoSendaGrande = (
  <span className="material-symbols-outlined" style={{ fontSize: "130px" }}>
    directions_walk
  </span>
);

// ──────────────────────────────────────────
// Textos — Programa Lazos
// ──────────────────────────────────────────

const definicionLazos =
  "Lazos es un programa de acompañamiento socioemocional para jóvenes que enfrentan " +
  "situaciones de vulnerabilidad. A través del vínculo con adultos referentes, se busca " +
  "fortalecer la autoestima, la pertenencia y la capacidad de proyectarse hacia el futuro.";

const objetivosLazos =
  "Construir relaciones de confianza entre jóvenes y adultos de la comunidad que " +
  "sirvan como red de apoyo real, promoviendo el desarrollo personal, la resiliencia " +
  "y la participación activa en el entorno social.";

const metodologiaLazos =
  "Sesiones grupales e individuales facilitadas por profesionales capacitados, con " +
  "actividades de expresión, diálogo y construcción de proyectos de vida. El programa " +
  "opera en ciclos de tres meses con seguimiento personalizado.";

// ──────────────────────────────────────────
// Textos — Programa Senda
// ──────────────────────────────────────────

const definicionSenda =
  "Senda acompaña a jóvenes en situación de calle o riesgo de habitabilidad precaria, " +
  "ofreciendo un recorrido estructurado hacia la reinserción social, laboral y familiar " +
  "mediante el acceso a herramientas concretas y redes de apoyo.";

const objetivosSenda =
  "Guiar a los jóvenes en la construcción de un proyecto de vida sostenible, " +
  "facilitando el acceso a vivienda transitoria, capacitación laboral y vínculos " +
  "institucionales que permitan una reinserción digna y duradera.";

const metodologiaSenda =
  "Trabajo de calle con duplas psicosociales, derivación a casas de acogida y talleres " +
  "de formación en habilidades para la vida. Se trabaja con enfoque de derechos y " +
  "acompañamiento continuo durante todo el proceso de reinserción.";

// ──────────────────────────────────────────
// Página
// ──────────────────────────────────────────

export default function Programas() {
  return (
    <>
      <header className="programas-header">
        <div className="programas-header-texto">
          <h1>Nuestros Programas</h1>
          <p>
            Conoce los programas insertos en la Oficina de la Juventud para
            apoyarte.
          </p>
        </div>
        <div
          className="carta-seccion"
          style={{ gridTemplateColumns: "repeat(2, 1fr)" }}
        >
          <a href="#lazos" style={{ textDecoration: "none" }}>
            <Card
              icono="diversity_3"
              iconoColor="#E3E3E3"
              iconoTamaño="2.5rem"
              titulo="Lazos"
            />
          </a>
          <a href="#senda" style={{ textDecoration: "none" }}>
            <Card
              icono="route"
              iconoColor="#E3E3E3"
              iconoTamaño="2.5rem"
              titulo="Senda"
            />
          </a>
        </div>
      </header>

      <main className="contenido-principal">
        <section className="seccion-informativa" id="lazos">
          <div className="seccion-encabezado">
            <span
              className="material-symbols-outlined seccion-icono"
              style={{ color: "#4caf6e", fontSize: "70px" }}
            >
              favorite
            </span>
            <h2 style={{ color: "#3f7d44" }}>Programa Lazos</h2>
            <p>
              Acompañamiento socioemocional para jóvenes en situación de
              vulnerabilidad.
            </p>
          </div>
          <InfoCard
            colorAcento="#4caf6e"
            etiquetaSuperior="Programa Lazos"
            iconoSuperiorDerecho={IconoLazosSmall}
            iconoGrande={IconoLazosGrande}
            textDefinicion={definicionLazos}
            textObjetivos={objetivosLazos}
            textMetodologia={metodologiaLazos}
            etiquetaCTA="¿Quieres ser parte de Lazos?"
            textBoton="¡Contáctanos!"
            alClickBoton={() => {}}
          />
        </section>

        <div className="fondo-gris">
          <section className="seccion-informativa" id="senda">
            <div className="seccion-encabezado">
              <span
                className="material-symbols-outlined seccion-icono"
                style={{ color: "#3b82f6", fontSize: "70px" }}
              >
                route
              </span>
              <h2 style={{ color: "#1d4ed8" }}>Programa Senda</h2>
              <p>
                Reinserción social, laboral y familiar para jóvenes en situación
                de calle.
              </p>
            </div>
            <InfoCard
              colorAcento="#3b82f6"
              etiquetaSuperior="Programa Senda"
              iconoSuperiorDerecho={IconoSendaSmall}
              iconoGrande={IconoSendaGrande}
              textDefinicion={definicionSenda}
              textObjetivos={objetivosSenda}
              textMetodologia={metodologiaSenda}
              etiquetaCTA="¿Quieres ser parte de Senda?"
              textBoton="¡Contáctanos!"
              alClickBoton={() => {}}
            />
          </section>
        </div>
      </main>
    </>
  );
}
