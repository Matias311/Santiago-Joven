import { Link } from "react-router-dom";
import "./ProyeccionCard.css";

export interface ProyeccionCardProps {
  icono?: string;
  iconoFondo?: string;
  /** Ícono Material Symbols para el centro (ej: "smart_toy", "description", "draft") */
  iconoMedio?: string;
  titulo: string;
  subtitulo?: string;
  /** Hasta 4 textos para el layout en grilla (cursos) */
  items?: string[];
  /** Texto único para el layout simple (ej: Acción Joven) */
  descripcion?: string;
  boton?: string;
  /** Tema visual de la tarjeta (define colores vía CSS, ej: "azul", "verde") */
  tema?: string;
  ruta?: string;
  clase?: string;
}

function ProyeccionCard({
  icono,
  iconoMedio,
  titulo,
  subtitulo,
  items,
  descripcion,
  boton,
  tema = "azul",
  ruta,
  clase,
}: ProyeccionCardProps) {
  const content = (
    <article className={`proyeccion-card tema-${tema} ${clase || ""}`}>
      {/* Encabezado: ícono pequeño + título + subtítulo */}
      <div className="proyeccion-card-top">
        {icono && (
          <span className="material-symbols-outlined proyeccion-card-icono-top">
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
        /* Grid: textos a los lados, ícono al centro (ej: cursos) */
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
    </article>
  );

  if (ruta) {
    return (
      <Link to={ruta} className="proyeccion-card-link">
        {content}
      </Link>
    );
  }

  return content;
}

export default ProyeccionCard;
