import { useEffect, useState } from "react";
import "./Accesibilidad.css";
import { faAccessibleIcon } from "@fortawesome/free-brands-svg-icons/faAccessibleIcon";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

/**
 * Props del componente AccessibilityWidget.
 */
type Props = {
  /**
   * Selector CSS del elemento al que se le aplicarán las clases de accesibilidad.
   * Si no se especifica, se aplica a `#site-content` por defecto.
   * Si el selector no encuentra ningún elemento, se aplica a `document.body`.
   */
  targetSelector?: string;
};

/**
 * Widget flotante de opciones de accesibilidad.
 *
 * Muestra un botón fijo en pantalla que al hacer clic despliega un panel
 * con opciones para ajustar la visualización del sitio: tamaño de texto,
 * contraste, escala de grises, fuente legible, entre otros.
 *
 * Las clases de accesibilidad se aplican al elemento indicado por `targetSelector`.
 * Solo puede estar activa una clase de visual a la vez; al elegir una nueva se
 * elimina la anterior automáticamente.
 *
 * @component
 * @example
 * // En App.tsx — apunta al contenido principal del sitio
 * <AccessibilityWidget targetSelector="#site-content" />
 */
export default function AccessibilityWidget({
  targetSelector = "#site-content",
}: Props) {
  const [open, setOpen] = useState(false);
  const [topOffset, setTopOffset] = useState(160);

  useEffect(() => {
    const updateTopOffset = () => {
      const navbar = document.querySelector(".navbar");
      const defaultOffset = 160;
      const navHeight = navbar?.getBoundingClientRect().height ?? 128;
      setTopOffset(navHeight + 16);
    };

    updateTopOffset();
    window.addEventListener("resize", updateTopOffset);
    return () => window.removeEventListener("resize", updateTopOffset);
  }, []);

  /** Obtiene el elemento objetivo o `document.body` como fallback. */
  const getTarget = (): Element | null => {
    return document.querySelector(targetSelector) || document.body;
  };

  /**
   * Elimina cualquier clase de accesibilidad activa y aplica la nueva.
   * Si `className` está vacío, solo elimina sin agregar nada (usado por `resetAll`).
   */
  const applyClass = (className: string) => {
    const target = getTarget();
    if (!target) return;

    target.classList.remove(
      "grayscale",
      "high-contrast",
      "negative-contrast",
      "light-background",
      "highlight-links",
      "readable-font",
    );

    if (className) target.classList.add(className);
  };

  /** Aumenta el tamaño de fuente global en 2px. */
  const increaseText = () => {
    const current = parseFloat(
      getComputedStyle(document.documentElement).fontSize,
    );
    document.documentElement.style.fontSize = `${current + 2}px`;
  };

  /** Reduce el tamaño de fuente global en 2px. */
  const decreaseText = () => {
    const current = parseFloat(
      getComputedStyle(document.documentElement).fontSize,
    );
    document.documentElement.style.fontSize = `${current - 2}px`;
  };

  /** Restablece el tamaño de fuente a 16px y elimina todas las clases de accesibilidad. */
  const resetAll = () => {
    document.documentElement.style.fontSize = "16px";
    applyClass("");
  };

  return (
    <div className="accessibility-container" aria-hidden={false} style={{ top: `${topOffset}px` }}>
      <button
        className="accessibility-btn"
        aria-label="Abrir opciones de accesibilidad"
        onClick={() => setOpen(!open)}
      >
        <FontAwesomeIcon icon={faAccessibleIcon} />
      </button>

      {open && (
        <div
          className="accessibility-panel"
          role="dialog"
          aria-modal="false"
          aria-label="Opciones de accesibilidad"
        >
          <h2>Accesibilidad</h2>
          <ul>
            <li onClick={increaseText}>
              <span className="material-symbols-outlined">zoom_in</span> Aumentar Texto
            </li>
            <li onClick={decreaseText}>
              <span className="material-symbols-outlined">zoom_out</span> Reducir Texto
            </li>
            <li onClick={() => applyClass("grayscale")}>
              <span className="material-symbols-outlined">format_color_reset</span> Escala de grises
            </li>
            <li onClick={() => applyClass("high-contrast")}>
              <span className="material-symbols-outlined">contrast</span> Alto Contraste
            </li>
            <li onClick={() => applyClass("negative-contrast")}>
              <span className="material-symbols-outlined">invert_colors</span> Contraste Negativo
            </li>
            <li onClick={() => applyClass("light-background")}>
              <span className="material-symbols-outlined">light_mode</span> Fondo Claro
            </li>
            <li onClick={() => applyClass("highlight-links")}>
              <span className="material-symbols-outlined">link</span> Links destacados
            </li>
            <li onClick={() => applyClass("readable-font")}>
              <span className="material-symbols-outlined">text_fields</span> Texto Legible
            </li>
            <li onClick={resetAll}>
              <span className="material-symbols-outlined">restart_alt</span> Restablecer
            </li>
          </ul>
        </div>
      )}
    </div>
  );
}