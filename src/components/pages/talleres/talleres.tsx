import TallerImg from "../../../assets/talleres/taller.png";
import Engranajes1Img from "../../../assets/talleres/engranajes1.png";
import Engranajes2Img from "../../../assets/talleres/engranajes2.png";
import "./talleres.css";

export default function Talleres() {
  return (
    <>
      <div className="talleres">
        <h2 className="titulo">Talleres</h2>

        <div className="evento">
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
          <div className="evento-icono">
            <img src={TallerImg} alt="taller" />
          </div>
          <div className="engranajes-izquierda">
            <img src={Engranajes2Img} alt="engranajes derecha" />
          </div>
          <div className="engranajes-abajo">
            <img src={Engranajes1Img} alt="engranajes izquierda" />
          </div>
        </div>
      </div>
    </>
  );
}

