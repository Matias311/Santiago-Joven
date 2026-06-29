import React from "react";
import "./InfoCard.css";

// Define qué datos puede recibir la tarjeta. El "?" indica que son opcionales.
interface PropsTarjeta {
  etiquetaSuperior?: string;
  colorAcento?:      string;
  iconoSuperiorDerecho?: React.ReactNode;
  iconoGrande?:          React.ReactNode;
  textDefinicion?:   string;
  textObjetivos?:    string;
  textMetodologia?:  string;
  iconoDefinicion?:  React.ReactNode;
  iconoObjetivos?:   React.ReactNode;
  iconoMetodologia?: React.ReactNode;
  etiquetaCTA?:      string;
  textBoton?:        string;
  alClickBoton?:     () => void;      // Función que se ejecuta al hacer clic en el botón
}

// Datos que necesita cada sección interna (ícono, título y texto)
interface PropsSeccion {
  icono:  React.ReactNode;
  titulo: string;
  texto:  string;
}

// Íconos SVG por defecto, usados si no se proporcionan íconos personalizados
const IconoInfo: React.FC = () => (
  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor"
    strokeWidth={2} strokeLinecap="round" strokeLinejoin="round">
    <circle cx="12" cy="12" r="10" />
    <line x1="12" y1="8"  x2="12" y2="8.5" strokeWidth={2.5} />
    <line x1="12" y1="12" x2="12" y2="16" />
  </svg>
);

const IconoLista: React.FC = () => (
  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor"
    strokeWidth={2} strokeLinecap="round" strokeLinejoin="round">
    <line x1="8"    y1="6"  x2="21"   y2="6" />
    <line x1="8"    y1="12" x2="21"   y2="12" />
    <line x1="8"    y1="18" x2="21"   y2="18" />
    <line x1="3"    y1="6"  x2="3.01" y2="6"  strokeWidth={2.5} />
    <line x1="3"    y1="12" x2="3.01" y2="12" strokeWidth={2.5} />
    <line x1="3"    y1="18" x2="3.01" y2="18" strokeWidth={2.5} />
  </svg>
);

const IconoFlujo: React.FC = () => (
  <svg viewBox="0 0 24 24" fill="none" stroke="currentColor"
    strokeWidth={2} strokeLinecap="round" strokeLinejoin="round">
    <circle cx="18" cy="5"  r="3" />
    <circle cx="6"  cy="12" r="3" />
    <circle cx="18" cy="19" r="3" />
    <line x1="8.59"  y1="13.51" x2="15.42" y2="17.49" />
    <line x1="15.41" y1="6.51"  x2="8.59"  y2="10.49" />
  </svg>
);

// Bloque reutilizable que muestra un ícono, título y texto. Se usa tres veces.
const Seccion: React.FC<PropsSeccion> = ({ icono, titulo, texto }) => (
  <div className="ic-section">
    <div className="ic-section-header">
      <span className="ic-section-icon">{icono}</span>
      <h3 className="ic-section-title">{titulo}</h3>
    </div>
    <p className="ic-section-text">{texto}</p>
  </div>
);

// Componente principal. Si no se pasan datos, usa los valores por defecto indicados con "=".
const InfoCard: React.FC<PropsTarjeta> = ({
  etiquetaSuperior        = "Título de la carta",
  colorAcento             = "#4caf6e",
  iconoSuperiorDerecho,
  iconoGrande,
  textDefinicion          = "",
  textObjetivos           = "",
  textMetodologia         = "",
  iconoDefinicion,
  iconoObjetivos,
  iconoMetodologia,
  etiquetaCTA             = "¿Interesado en la asesoría?",
  textBoton               = "¡Contáctanos!",
  alClickBoton,
}) => {

  // El color de acento se pasa al CSS como variable para usarlo dinámicamente en los estilos
  const variablesCSS = {
    "--accent":      colorAcento,
    "--accent-fade": colorAcento + "40", // Versión semitransparente del color
  } as React.CSSProperties;

  return (
    <div className="ic-wrapper" style={variablesCSS}>

      <p className="ic-top-label">{etiquetaSuperior}</p>

      <div className="ic-card">

        <div className="ic-fade-right" />

        {/* Solo se renderiza si fue proporcionado */}
        {iconoSuperiorDerecho && (
          <div className="ic-top-right-icon">{iconoSuperiorDerecho}</div>
        )}

        {iconoGrande && (
          <div className="ic-big-icon">{iconoGrande}</div>
        )}

        {/* Si no se pasa un ícono personalizado, se usa el ícono por defecto con ?? */}
        <div className="ic-sections">
          <Seccion
            icono={iconoDefinicion  ?? <IconoInfo />}
            titulo="Definición y Contexto"
            texto={textDefinicion}
          />
          <Seccion
            icono={iconoObjetivos   ?? <IconoLista />}
            titulo="Objetivos"
            texto={textObjetivos}
          />
          <Seccion
            icono={iconoMetodologia ?? <IconoFlujo />}
            titulo="Metodología"
            texto={textMetodologia}
          />
        </div>

        <div className="ic-cta">
          <p className="ic-cta-label">{etiquetaCTA}</p>
          <button className="ic-button" onClick={alClickBoton}>
            {textBoton}
          </button>
        </div>

      </div>
    </div>
  );
};

export default InfoCard;