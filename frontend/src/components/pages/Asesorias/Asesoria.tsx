import "./asesoria.css";
import Card from "../../cartas/Card";
import InfoCard from "../../cartas/InfoCard";
import type { CartaItem } from "../../types/CartaItem";
import { useState, useEffect, useRef } from "react";

// Íconos y títulos que aparecen en el encabezado como accesos directos
const headerIconos: CartaItem[] = [
  { icono: "handshake", iconoColor: "#E3E3E3", iconoTamaño: "2.5rem", titulo: "Asesorías" },
  { icono: "school",    iconoColor: "#E3E3E3", iconoTamaño: "2.5rem", titulo: "Pre Universitario" },
];
// Anclas a las que redirige cada acceso directo del encabezado
const anclas = ["#asesorias", "#preuniversitario"];

// Íconos de Material Symbols usados en las tarjetas (pequeño = esquina, grande = decorativo)
const IcoNetworkSmall = <span className="material-symbols-outlined" style={{ fontSize: "32px" }}>work</span>;
const IcoNetworkGrande = <span className="material-symbols-outlined" style={{ fontSize: "130px" }}>network_intel_node</span>;
const IcoWorkSmall = <span className="material-symbols-outlined" style={{ fontSize: "32px" }}>work</span>;
const IcoWorkGrande = <span className="material-symbols-outlined" style={{ fontVariationSettings: "'FILL' 1", fontSize: "130px" }}>work</span>;
const IcoBookSmall = <span className="material-symbols-outlined" style={{ fontSize: "32px" }}>auto_stories</span>;
const IcoBookGrande = <span className="material-symbols-outlined" style={{ fontSize: "130px" }}>book_5</span>;
const IcoCalcSmall = <span className="material-symbols-outlined" style={{ fontSize: "32px" }}>auto_stories</span>;
const IcoCalcGrande = <span className="material-symbols-outlined" style={{ fontSize: "130px" }}>calculate</span>;
const IcoCsSmall = <span className="material-symbols-outlined" style={{ fontSize: "32px" }}>auto_stories</span>;
const IcoCsGrande = <span className="material-symbols-outlined" style={{ fontVariationSettings: "'FILL' 1", fontSize: "130px" }}>auto_stories</span>;

// Contenido de las tarjetas de la sección "Asesorías"
const cartasCapacitacion = [
  {
    colorAcento: "#78A75A",
    etiquetaSuperior: "Capacitación Adulto",
    iconoSuperiorDerecho: IcoNetworkSmall,
    iconoGrande: IcoNetworkGrande,
    textDefinicion: "La vida adulta actual requiere habilidades digitales más allá del uso básico del celular. Este programa busca reducir la brecha digital, ayudando a convertir la tecnología en una herramienta útil para lograr mayor independencia.",
    textObjetivos: "Entregar a los jóvenes habilidades digitales para manejar de forma autónoma su vida personal, académica y laboral, enfrentando con seguridad las exigencias del mundo actual.",
    textMetodologia: "Metodología práctica basada en casos reales, mentorías personalizadas y simulaciones seguras para desarrollar habilidades digitales, productividad y protección frente a fraudes en línea.",
    etiquetaCTA: "¿Interesado en la Asesoría?",
    textBoton: "¡Contáctanos!",
  },
  {
    colorAcento: "#EA580C",
    etiquetaSuperior: "Asesoría a Jóvenes",
    iconoSuperiorDerecho: IcoWorkSmall,
    iconoGrande: IcoWorkGrande,
    textDefinicion: "La asesoría integral brinda acompañamiento profesional y orientación a jóvenes para ayudarles a tomar decisiones importantes. Ofrece un espacio de escucha y apoyo neutral para aclarar dudas sobre su futuro, identidad y bienestar en un contexto de sobreinformación y presión social.",
    textObjetivos: "Acompañar a cada joven de forma personalizada para reconocer sus fortalezas, afrontar desafíos emocionales y académicos, y tomar decisiones informadas que orienten su proyecto de vida.",
    textMetodologia: "Ofrecemos sesiones de escucha activa en un entorno seguro y de confianza, orientación basada en fortalezas para identificar talentos e intereses, y planes de acción personalizados con metas claras y seguimiento continuo para promover avances concretos.",
    etiquetaCTA: "¿Interesado en la Asesoría?",
    textBoton: "¡Contáctanos!",
  },
];

// Contenido de las tarjetas de la sección "Pre Universitario"
const cartasPreuniversitario = [
  {
    colorAcento: "#D2954C",
    etiquetaSuperior: "Competencia Lectora",
    iconoSuperiorDerecho: IcoBookSmall,
    iconoGrande: IcoBookGrande,
    textDefinicion: "La sección de Preuniversitario brinda nivelación académica gratuita a jóvenes que no pueden acceder a centros privados, ayudando a reducir desigualdades y preparando mejor a los estudiantes para las pruebas de admisión universitaria.",
    textObjetivos: "Fortalecer los conocimientos y habilidades de razonamiento de los estudiantes para que puedan obtener el puntaje necesario e ingresar a la carrera y universidad que desean.",
    textMetodologia: "Mejorar los conocimientos y habilidades de los estudiantes para ayudarlos a ingresar a la carrera y universidad que desean.",
    etiquetaCTA: "¿Interesado en el Preuniversitario?",
    textBoton: "¡Contáctanos!",
  },
  {
    colorAcento: "#129AFE",
    etiquetaSuperior: "M1 (Matemáticas)",
    iconoSuperiorDerecho: IcoCalcSmall,
    iconoGrande: IcoCalcGrande,
    textDefinicion: "El programa busca que los jóvenes desarrollen pensamiento lógico y resolución de problemas, convirtiendo las matemáticas en una herramienta útil y accesible para todos.",
    textObjetivos: "Lograr que los estudiantes dominen los contenidos fundamentales y desarrollen habilidades para resolver problemas, obteniendo el puntaje necesario para ingresar a la carrera universitaria que deseen.",
    textMetodologia: "Fortalecer los conocimientos y habilidades matemáticas de los estudiantes para ayudarlos a alcanzar el puntaje necesario e ingresar a la carrera que desean.",
    etiquetaCTA: "¿Interesado en el Preuniversitario?",
    textBoton: "¡Contáctanos!",
  },
  {
    colorAcento: "#62C550",
    etiquetaSuperior: "Ciencias Sociales",
    iconoSuperiorDerecho: IcoCsSmall,
    iconoGrande: IcoCsGrande,
    textDefinicion: "El programa busca que los jóvenes desarrollen pensamiento crítico sobre historia, economía y ciudadanía, fomentando una participación activa en la sociedad y la democracia.",
    textObjetivos: "Brindar herramientas de análisis histórico, geográfico y social para comprender la realidad, rendir con éxito la prueba electiva y desarrollar una visión integral de la sociedad.",
    textMetodologia: "Entregar herramientas de análisis social, histórico y geográfico para comprender la realidad y enfrentar con éxito la prueba electiva.",
    etiquetaCTA: "¿Interesado en el Preuniversitario?",
    textBoton: "¡Contáctanos!",
  },
];

// Componente slider: muestra una tarjeta a la vez y permite navegar entre ellas.
// Avanza automáticamente cada 15 segundos.
function SliderInfoCard({ cartas }: { cartas: typeof cartasCapacitacion }) {
  const [actual, setActual]       = useState(0);                          // Índice de la carta visible
  const [direccion, setDireccion] = useState<"right" | "left">("right");  // Dirección de la animación
  const [animando, setAnimando]   = useState(false);
  const timerRef = useRef<ReturnType<typeof setTimeout> | null>(null);

  // Cambia a la carta indicada, activa la animación y reinicia el temporizador
  const cambiar = (index: number, dir: "right" | "left") => {
    setDireccion(dir);
    setAnimando(false);
    setTimeout(() => setAnimando(true), 10); // Pequeño delay para que la animación se reinicie
    setActual(index);
    if (timerRef.current) clearTimeout(timerRef.current);
    timerRef.current = setTimeout(() => {
      cambiar((index + 1) % cartas.length, "right");
    }, 15000);
  };

  // Al montar el componente, inicia el avance automático
  useEffect(() => {
    setAnimando(true);
    timerRef.current = setTimeout(() => {
      cambiar(1 % cartas.length, "right");
    }, 15000);
    return () => { if (timerRef.current) clearTimeout(timerRef.current); }; // Limpia el timer al desmontar
  }, []);

  const anterior  = () => cambiar((actual - 1 + cartas.length) % cartas.length, "left");
  const siguiente = () => cambiar((actual + 1) % cartas.length, "right");

  const carta = cartas[actual];

  return (
    <div className="slider-infocard">
      <div className="slider-fila">
        <button className="slider-flecha" onClick={anterior}>
          <span className="material-symbols-outlined">chevron_left</span>
        </button>

        {/* Aplica la clase de animación según la dirección de navegación */}
        <div className={animando ? `slide-enter-${direccion}` : ""}>
          <InfoCard
            colorAcento={carta.colorAcento}
            etiquetaSuperior={carta.etiquetaSuperior}
            iconoSuperiorDerecho={carta.iconoSuperiorDerecho}
            iconoGrande={carta.iconoGrande}
            textDefinicion={carta.textDefinicion}
            textObjetivos={carta.textObjetivos}
            textMetodologia={carta.textMetodologia}
            etiquetaCTA={carta.etiquetaCTA}
            textBoton={carta.textBoton}
            alClickBoton={() => {}} //TODO: Agregar funcionalidad:3
          />
        </div>

        <button className="slider-flecha" onClick={siguiente}>
          <span className="material-symbols-outlined">chevron_right</span>
        </button>
      </div>

      {/* Puntos de navegación: el activo toma el color de acento de la carta actual */}
      <div className="slider-dots">
        {cartas.map((_, i) => (
          <button
            key={i}
            className={`slider-dot ${i === actual ? "activo" : ""}`}
            style={i === actual ? { background: carta.colorAcento } : {}}
            onClick={() => cambiar(i, i > actual ? "right" : "left")}
          />
        ))}
      </div>
    </div>
  );
}

export default function Asesoria() {
  return (
    <>
      <header id="asesoria-inicio" className="asesoria-header">
        <div className="asesoria-header-texto">
          <h1>Asesorías y Preuniversitarios</h1>
          <p>Te acompañamos en tus trámites, estudios y beneficios. Encuentra aquí toda la asesoría que necesitas.</p>
        </div>

        {/* Accesos directos a cada sección, renderizados como tarjetas clicables */}
        <div className="carta-seccion" style={{ gridTemplateColumns: "repeat(2, 1fr)" }}>
          {headerIconos.map((carta, index) => (
            <a key={carta.titulo} href={anclas[index]} className="asesoria-header-link">
              <Card
                icono={carta.icono}
                iconoColor={carta.iconoColor}
                iconoTamaño={carta.iconoTamaño}
                titulo={carta.titulo}
              />
            </a>
          ))}
        </div>
      </header>

      <main className="contenido-principal">

        <section id="asesorias" className="seccion-informativa">
          <div className="seccion-encabezado">
            <span className="material-symbols-outlined seccion-icono" style={{ color: "#78A75A", fontSize: "70px" }}>
              handshake
            </span>
            <h2 style={{ color: "#3f7d44" }}>Asesorías</h2>
            <p>Programas de capacitación y asesoría para adultos y jóvenes.</p>
          </div>
          <SliderInfoCard cartas={cartasCapacitacion} />
        </section>

        <div className="fondo-gris">
          <section id="preuniversitario" className="seccion-informativa">
            <div className="seccion-encabezado">
              <span className="material-symbols-outlined seccion-icono" style={{ color: "#DA954B", fontSize: "70px" }}>
                school
              </span>
              <h2 style={{ color: "#b97a3a" }}>Pre Universitario</h2>
              <p>Nivelación académica gratuita para rendir la PAES y alcanzar tu carrera.</p>
            </div>
            <SliderInfoCard cartas={cartasPreuniversitario} />
          </section>
        </div>

      </main>
    </>
  );
}