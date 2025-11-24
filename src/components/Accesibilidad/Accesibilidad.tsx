import { useState } from "react";
import "./Accesibilidad.css";
import { faAccessibleIcon } from "@fortawesome/free-brands-svg-icons/faAccessibleIcon";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

type Props = {
  targetSelector?: string;
};

export default function AccessibilityWidget({
  targetSelector = "#site-content",
}: Props) {
  const [open, setOpen] = useState(false);

  const getTarget = (): Element | null => {
    return document.querySelector(targetSelector) || document.body;
  };

  const applyClass = (className: string) => {
    const target = getTarget();
    if (!target) return;

    // quitar clases anteriores
    target.classList.remove(
      "grayscale",
      "high-contrast",
      "negative-contrast",
      "light-background",
      "highlight-links",
      "readable-font",
    );

    // añadir nueva si viene
    if (className) target.classList.add(className);
  };

  const increaseText = () => {
    const target = getTarget();
    if (!target) return;
    const current = parseFloat(
      getComputedStyle(document.documentElement).fontSize,
    );
    document.documentElement.style.fontSize = `${current + 2}px`;
  };

  const decreaseText = () => {
    const current = parseFloat(
      getComputedStyle(document.documentElement).fontSize,
    );
    document.documentElement.style.fontSize = `${current - 2}px`;
  };

  const resetAll = () => {
    document.documentElement.style.fontSize = "16px";
    const target = getTarget();
    if (!target) return;
    applyClass("");
  };

  return (
    <div className="accessibility-container" aria-hidden={false}>
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
            <li onClick={increaseText}>🔍 Aumentar Texto</li>
            <li onClick={decreaseText}>🔍 Reducir Texto</li>
            <li onClick={() => applyClass("grayscale")}>▦ Escala de grises</li>
            <li onClick={() => applyClass("high-contrast")}>
              ⚫ Alto Contraste
            </li>
            <li onClick={() => applyClass("negative-contrast")}>
              👁 Contraste Negativo
            </li>
            <li onClick={() => applyClass("light-background")}>
              💡 Fondo Claro
            </li>
            <li onClick={() => applyClass("highlight-links")}>
              🔗 Links destacados
            </li>
            <li onClick={() => applyClass("readable-font")}>🅣 Texto Legible</li>

            <li onClick={resetAll}>🔄 Restablecer</li>
          </ul>
        </div>
      )}
    </div>
  );
}
