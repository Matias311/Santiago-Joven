import { useState } from "react";
import { createPortal } from "react-dom";
import "./SurveyCard.css";

export interface Survey {
  id: number;
  title: string;
  descripcion: string;
  url: string;
}

export default function SurveyCard({ event }: { event: Survey }) {
  const [mostrarDetalle, setMostrarDetalle] = useState(false);

  return (
    <>
      <div className="calendar-item">
        <h3>{event.title}</h3>
        <button
          className="item-location"
          onClick={() => setMostrarDetalle(true)}
        >
          Ver más detalles
        </button>
        <span className={`eventProps-status en-proceso`}>En proceso</span>
      </div>

      {mostrarDetalle &&
        createPortal(
          <div className="encuesta-popup-fondo">
            <div className="encuesta-popup-caja">
              <h2>{event.title}</h2>
              <p>{event.descripcion}</p>

              <a
                href={event.url}
                target="_blank"
                rel="noopener noreferrer"
                className="encuesta-popup-boton"
                id="encuesta-popup-ir"
              >
                Ir al formulario
              </a>

              <button
                className="encuesta-popup-boton"
                id="encuesta-popup-cerrar"
                onClick={() => setMostrarDetalle(false)}
              >
                Cerrar
              </button>
            </div>
          </div>,
          document.body,
        )}
    </>
  );
}
