// src/components/Agenda.tsx
import AgendaImg from "../../../../public/agenda/agenda.png";
import Planta1Img from "../../../../public/agenda/planta1.png";
import Planta2Img from "../../../../public/agenda/planta2.png";
import "./agenda.css";
export default function Agenda() {
  return (
    <>
      <div className="agenda">
        <h2 className="titulo">Agenda</h2>
        <div className="evento">
          <div className="evento-icono">
            <img src={AgendaImg} alt="icono agenda" />
          </div>
          <div className="evento-info">
            <h2>Evento de prueba</h2>
            <p>
              <strong>Fecha:</strong> 15-10-2025
            </p>
            <p>
              <strong>Hora:</strong> 10:00
            </p>
            <p>
              <strong>Lugar:</strong> Cerrillos
            </p>
          </div>
          <div className="planta-derecha">
            <img src={Planta1Img} alt="planta derecha" />
          </div>
          <div className="planta-izquierda">
            <img src={Planta2Img} alt="planta izquierda" />
          </div>
        </div>
      </div>
    </>
  );
}
