import "./Card.css";
import { Link } from "react-router-dom";
import type { CartaItem } from "../types/CartaItem";
import SpotlightCard from "../DisenoCartas/DisenoCartas";

/**
 * Componente de tarjeta universal reutilizable.
 *
 * Muestra un ícono, título, subtítulo opcional, descripción opcional y un botón de acción opcional.
 * Se puede personalizar visualmente a través de sus props de estilo.
 *
 * @component
 * @example
 * // Uso básico con título, descripción y botón
 * <Card
 *   titulo="Curso de React"
 *   descripcion="Aprende React desde cero con proyectos reales."
 *   boton="Inscríbete"
 * />
 *
 * @example
 * // Uso completo con ícono y personalización de colores
 * <Card
 *   icono="school"
 *   iconoColor="#ffffff"
 *   iconoTamaño="48px"
 *   iconoFondo="#4f46e5"
 *   titulo="Curso de React"
 *   tituloColor="#1e293b"
 *   subtitulo="Nivel intermedio"
 *   descripcion="Aprende React desde cero con proyectos reales."
 *   boton="Inscríbete"
 *   botoncolor="#4f46e5"
 *   sliderSombra="0 4px 24px rgba(0,0,0,0.12)"
 *   clase="carta-destacada"
 * />
 */
function Card({
  icono,
  iconoColor,
  iconoTamaño,
  botoncolor,
  iconoFondo,
  tituloColor,
  titulo,
  sliderSombra,
  subtitulo,
  descripcion,
  boton,
  clase,
  ruta,
  gridItems,
}: CartaItem) {
  const hasGridItems = Array.isArray(gridItems) && gridItems.length === 4;

  const iconElement = (
    <span
      className="material-symbols-outlined icono"
      style={{
        color: iconoColor,
        fontSize: iconoTamaño,
        background: iconoFondo,
      }}
    >
      {icono}
    </span>
  );

  const content = (
    <SpotlightCard
      className={`carta-universal ${clase || ""}`}
      spotlightColor="rgba(146, 231, 252, 0.37)"
      style={{ boxShadow: sliderSombra }}
    >
      <h3 style={{ color: tituloColor }}>{titulo}</h3>
      {subtitulo && <p className="subtitulo-carta">{subtitulo}</p>}
      {hasGridItems ? (
        <div className="card-grid">
          {gridItems!.map((item, index) => (
            <p key={index} className={`card-grid-item item-${index + 1}`}>
              {item}
            </p>
          ))}
          <div className="grid-center">{iconElement}</div>
        </div>
      ) : (
        <>
          {iconElement}
          {descripcion && <p>{descripcion}</p>}
        </>
      )}
      {boton && (
        <button className="btn-inscribase" style={{ background: botoncolor }}>
          {boton}
        </button>
      )}
    </SpotlightCard>
  );

  if (ruta) {
    return (
      <Link to={ruta} className="card-link">
        {content}
      </Link>
    );
  }

  return content;
}

export default Card;
