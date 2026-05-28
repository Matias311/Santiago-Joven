import "./Card.css";
import type { CartaItem } from '../types/typeCard';

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
}: CartaItem) {
  return (
    <div className={`carta-universal ${clase || ''}`} style={{ boxShadow: sliderSombra }}>
      <span
        className="material-symbols-outlined icono"
        style={{ color: iconoColor, fontSize: iconoTamaño, background: iconoFondo }}
      >
        {icono}
      </span>
      <h3 style={{ color: tituloColor }}>{titulo}</h3>
      {subtitulo && <p className="subtitulo-carta">{subtitulo}</p>}
      {descripcion && <p>{descripcion}</p>}
      {boton && (
        <button className="btn-inscribase" style={{ background: botoncolor }}>
          {boton}
        </button>
      )}
    </div>
  );
}

export default Card;