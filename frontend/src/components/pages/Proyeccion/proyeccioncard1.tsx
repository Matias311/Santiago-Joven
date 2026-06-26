// =============================================================
// ARCHIVO: proyeccioncard1.tsx
// PROPOSITO: Tarjeta de tema azul para la seccion "Cursos Destacados".
//
// ESTRUCTURA VISUAL:
//   - Esquina superior izquierda: icono Material Symbol del curso
//   - Centro del header: titulo principal + subtitulo
//   - Cuerpo: grilla 3 columnas [bullet | icono-central | bullet]
//             replicada en 2 filas = 4 bullets alrededor del icono
//   - Footer: boton de accion azul
//
// ICONO CENTRAL:
//   Usa FontAwesome "hexagon-nodes" (fas). Si el icono cambia
//   en el futuro, solo hay que editar la linea del FontAwesomeIcon.
//
// PROPS (CartaItem):
//   icono        — nombre del Material Symbol para el icono de esquina
//   iconoColor   — color del icono de esquina
//   iconoFondo   — gradiente/color del fondo del cuadrado del icono
//   titulo       — titulo principal de la carta
//   subtitulo    — linea secundaria debajo del titulo
//   descripcion  — texto largo separado por " | " para los 4 bullets
//   boton        — texto del boton CTA
//   botoncolor   — color de fondo del boton (valor CSS)
//
// PARA CONECTAR CON LA API:
//   Reemplazar "descripcion" por un array "bullets: string[]"
//   de 4 elementos que venga directamente del endpoint.
// =============================================================

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHexagonNodes } from "@fortawesome/free-solid-svg-icons";
import "./proyeccioncard1.css";
import type { CartaItem } from "../../types/CartaItem";

const SEPARADOR_BULLETS = " | ";

type ProyeccionCard1Props = CartaItem & {
  onBoton?: () => void;
};

// ─── Componente principal ─────────────────────────────────────────────────────
export default function ProyeccionCard1({
  icono,
  iconoColor,
  iconoFondo,
  titulo,
  subtitulo,
  descripcion,
  boton,
  botoncolor,
  onBoton,
}: ProyeccionCard1Props) {
  // Divide la descripcion en hasta 4 bullets por el separador " | ".
  const bullets = descripcion
    ? descripcion.split(SEPARADOR_BULLETS).slice(0, 4)
    : [];
  while (bullets.length < 4) bullets.push("");

  return (
    <article className="pc1-card">
      {/* ── Header: icono de esquina + titulo centrado ── */}
      <header className="pc1-header">
        <div
          className="pc1-corner-icon"
          style={{ background: iconoFondo }}
          aria-hidden="true"
        >
          <span
            className="material-symbols-outlined"
            style={{ color: iconoColor, fontSize: "22px" }}
          >
            {icono}
          </span>
        </div>

        <div className="pc1-titles">
          <h3 className="pc1-title">{titulo}</h3>
          {subtitulo && <p className="pc1-subtitle">{subtitulo}</p>}
        </div>
      </header>

      {/* ── Cuerpo: grilla con bullets a los lados e icono al centro ──
          LAYOUT:
            Col 1 (bullet)  |  Col 2 (icono)  |  Col 3 (bullet)
            Col 1 (bullet)  |  Col 2 (icono)  |  Col 3 (bullet)
          El icono ocupa col 2, filas 1 y 2 (row-span).
      */}
      <div className="pc1-grid" role="list">
        <p className="pc1-bullet pc1-bullet--tl" role="listitem">
          {bullets[0]}
        </p>
        <p className="pc1-bullet pc1-bullet--tr" role="listitem">
          {bullets[2]}
        </p>

        <div className="pc1-center" aria-hidden="true">
          <FontAwesomeIcon icon={faHexagonNodes} className="pc1-hex-icon" />
        </div>

        <p className="pc1-bullet pc1-bullet--bl" role="listitem">
          {bullets[1]}
        </p>
        <p className="pc1-bullet pc1-bullet--br" role="listitem">
          {bullets[3]}
        </p>
      </div>

      {/* ── Footer: boton de accion ──
          El color viene como prop inline; el CSS solo define forma y tipografia.
          No hay contenedor extra ni fondo gris — solo el boton.
      */}
      {boton && (
        <div className="pc1-footer">
          <button
            className="pc1-btn"
            style={{ background: botoncolor }}
            onClick={onBoton}
            type="button"
          >
            {boton}
          </button>
        </div>
      )}
    </article>
  );
}
