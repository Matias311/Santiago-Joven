import { Link } from "react-router-dom";
import SpotlightCard from "../DisenoCartas/DisenoCartas";
import "./ProyeccionCard.css";

type RgbaColor = `rgba(${number}, ${number}, ${number}, ${number})`;

export interface ProyeccionCardProps {
  /** Ícono Material Symbols pequeño junto al título (ej: "school", "work") */
  icono?: string;
  iconoFondo?: string;
  /** Ícono Material Symbols para el centro (ej: "smart_toy", "description", "draft") */
  iconoMedio?: string;
  titulo: string;
  subtitulo?: string;
  /** Hasta 4 textos para el layout en grilla, ubicados alrededor del ícono central */
  items?: string[];
  /** Texto único para el layout simple (ej: Acción Joven) */
  descripcion?: string;
  /** Texto del botón de acción. Por defecto "Ver detalles" */
  boton?: string;
  /** Tema visual de la tarjeta (define colores vía CSS, ej: "azul", "verde", "naranja") */
  tema?: string;
  ruta?: string;
  clase?: string;
  /** Color del efecto spotlight al pasar el mouse */
  spotlightColor?: RgbaColor;
}

function ProyeccionCard({
  icono,
  iconoFondo,
  iconoMedio,
  titulo,
  subtitulo,
  items,
  descripcion,
  boton = "Ver detalles",
  tema = "azul",
  ruta,
  clase,
  spotlightColor = "rgba(146, 231, 252, 0.37)",
}: ProyeccionCardProps) {
  const content = (
    <SpotlightCard
      className={`proyeccion-card tema-${tema} ${clase || ""}`}
      spotlightColor={spotlightColor}
    >
      {/* Encabezado: ícono pequeño + título + subtítulo */}
      <div className="proyeccion-card-top">
        {icono && (
          <span
            className="material-symbols-outlined proyeccion-card-icono-top"
            style={{ background: iconoFondo }}
          >
            {icono}
          </span>
        )}
        <div>
          <h3>{titulo}</h3>
          {subtitulo && (
            <p className="proyeccion-card-subtitulo">{subtitulo}</p>
          )}
        </div>
      </div>

      {items && items.length > 0 ? (
        /* Grid: 4 textos alrededor, ícono grande al centro (ej: cursos) */
        <div className="proyeccion-card-grid">
          {items.slice(0, 4).map((item, index) => (
            <div
              key={index}
              className={`proyeccion-card-grid-item item-${index + 1}`}
            >
              <p>{item}</p>
            </div>
          ))}

          <div className="proyeccion-card-center">
            {iconoMedio && (
              <span className="material-symbols-outlined proyeccion-card-icono-medio">
                {iconoMedio}
              </span>
            )}
          </div>
        </div>
      ) : (
        /* Layout simple: ícono central + un solo párrafo (ej: Acción Joven) */
        <div className="proyeccion-card-simple">
          {iconoMedio && (
            <span className="material-symbols-outlined proyeccion-card-icono-medio">
              {iconoMedio}
            </span>
          )}
          {descripcion && <p>{descripcion}</p>}
        </div>
      )}

      {boton && <button className="btn-proyeccion">{boton}</button>}
    </SpotlightCard>
  );

  if (ruta) {
    const esExterna = /^https?:\/\//.test(ruta);

    if (esExterna) {
      return (
        <a
          href={ruta}
          target="_blank"
          rel="noopener noreferrer"
          className="proyeccion-card-link"
        >
          {content}
        </a>
      );
    }

    return (
      <Link to={ruta} className="proyeccion-card-link">
        {content}
      </Link>
    );
  }

  return content;
}

export default ProyeccionCard;
