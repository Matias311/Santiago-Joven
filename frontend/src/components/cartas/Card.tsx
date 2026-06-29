import { Link } from "react-router-dom";
import "./Card.css";
import type { CartaItem } from "../types/CartaItem";

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
  link,
}: CartaItem) {
  return (
    <div
      className={`carta-universal ${clase || ""}`}
      style={{ boxShadow: sliderSombra }}
    >
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
      <h3 style={{ color: tituloColor }}>{titulo}</h3>
      {subtitulo && <p className="subtitulo-carta">{subtitulo}</p>}
      {descripcion && <p>{descripcion}</p>}
      {boton &&
        (link ? (
          <Link to={link}>
            <button
              className="btn-inscribase"
              style={{ background: botoncolor }}
            >
              {boton}
            </button>
          </Link>
        ) : (
          <button className="btn-inscribase" style={{ background: botoncolor }}>
            {boton}
          </button>
        ))}
    </div>
  );
}

export default Card;
