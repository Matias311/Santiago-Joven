import "./centrosComunitarios.css";
import CentroComunitarioCarol from "../../../assets/CentroComunitarioCarol.png";
import CentroComunitarioMatta from "../../../assets/CentroComunitarioMatta.png";

export default function CentrosComunitarios() {
  return (
    <div className="centros-container">
      <div className="centro-card">
        <img
          src={CentroComunitarioCarol}
          alt="Centro Comunitario Carol Urzúa"
          className="centro-img"
        />
        <h3 className="centro-title">Centro Comunitario Carol Urzúa</h3>
        <a href="/carol" className="centro-link">
          Ver más &gt;&gt;
        </a>
      </div>

      <div className="centro-card">
        <img
          src={CentroComunitarioMatta}
          alt="Centro Comunitario Matta"
          className="centro-img"
        />
        <h3 className="centro-title">Centro Comunitario Matta</h3>
        <a href="/matta" className="CCMatta-link">
          Ver más &gt;&gt;
        </a>
      </div>
    </div>
  );
}
