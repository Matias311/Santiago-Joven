import { useState, useEffect } from "react";
import "./PopupEncuesta.css";

/**
 * Props del componente PopupEncuesta.
 */
interface Props {
  /**
   * Indica si hay encuestas activas disponibles.
   * Si es `true`, el popup se muestra automáticamente.
   * Si es `false`, el popup permanece oculto.
   */
  hayEncuestas: boolean;
}

/**
 * Popup de notificación que invita al usuario a participar en encuestas.
 *
 * Se muestra automáticamente cuando `hayEncuestas` es `true`.
 * El usuario puede cerrarlo con el botón ✕ o navegando a la sección
 * de encuestas mediante el botón "Ir a las encuestas", el cual redirige
 * a `#contribucion` y cierra el popup.
 *
 * @component
 * @example
 * // En App.tsx — mostrar el popup porque hay encuestas activas
 * <PopupEncuesta hayEncuestas={true} />
 *
 * @example
 * // En App.tsx — ocultar el popup cuando no hay encuestas
 * <PopupEncuesta hayEncuestas={false} />
 */
export default function PopupEncuesta({ hayEncuestas }: Props) {
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    if (hayEncuestas) {
      setVisible(true);
    }
  }, [hayEncuestas]);

  if (!visible) return null;

  return (
    <div className="popup-overlay">
      <div className="popup-contenedor">
        <button className="popup-cerrar" onClick={() => setVisible(false)}>✕</button>
        <span className="material-symbols-outlined popup-icono">content_paste</span>
        <h2>¡Tu opinión cuenta!</h2>
        <p>Opina con nosotros o sobre nosotros en los foros haciendo click aquí!</p>
        <a href="#contribucion" className="popup-btn" onClick={() => setVisible(false)}>
          Ir a las encuestas
        </a>
      </div>
    </div>
  );
}