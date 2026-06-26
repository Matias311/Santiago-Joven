// =============================================================
// ARCHIVO: proyeccioncard2.tsx
// PROPOSITO: Tarjeta de tema naranja para la seccion "Accion Joven".
//
// ESTRUCTURA VISUAL:
//   - Esquina superior izquierda: icono Material Symbol de la actividad
//   - Centro del header: titulo principal
//   - Cuerpo: texto descriptivo centrado
//   - Footer: boton de accion naranja
//
// PROPS (CartaItem):
//   icono          — nombre del Material Symbol (ej: "volunteer_activism")
//   iconoColor     — color del icono de esquina
//   iconoFondo     — gradiente/color del fondo del circulo del icono
//   titulo         — titulo principal de la carta
//   descripcion    — texto descriptivo del cuerpo de la carta
//   boton          — texto del boton CTA
//   botoncolor     — color de fondo del boton (acepta cualquier valor CSS)
//
// PARA CONECTAR CON LA API:
//   Cada CartaItem puede venir directamente del endpoint sin
//   transformaciones. El campo "descripcion" se muestra tal cual.
//   Si la API entrega iconos como URL de imagen en vez de Material
//   Symbols, agregar una prop "iconoUrl?: string" y renderizar
//   un <img> en lugar del <span> de Material Symbols.
// =============================================================

import "./proyeccioncard2.css";
import type { CartaItem } from "../../types/CartaItem";

type ProyeccionCard2Props = CartaItem & {
  onBoton?: () => void;
};

// ─── Componente principal ─────────────────────────────────────────────────────
export default function ProyeccionCard2({
  icono,
  iconoColor,
  iconoFondo,
  titulo,
  descripcion,
  boton,
  botoncolor,
  onBoton,
}: ProyeccionCard2Props) {
  return (
    <article className="pc2-card">
      {/* ── Header: icono de esquina + titulo centrado ── */}
      <header className="pc2-header">
        <div
          className="pc2-corner-icon"
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

        <h3 className="pc2-title">{titulo}</h3>
      </header>

      {/* ── Cuerpo: descripcion centrada ── */}
      {descripcion && <p className="pc2-body">{descripcion}</p>}

      {/* ── Footer: boton de accion ── */}
      {boton && (
        <div className="pc2-footer">
          <button
            className="pc2-btn"
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
