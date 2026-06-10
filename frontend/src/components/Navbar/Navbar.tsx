import { useState } from "react";
import { Link } from "react-router-dom";
import "../Navbar/Navbar.css";

export default function Navbar() {
  const [modoOscuro, setModoOscuro] = useState(false);

  return (
    <header className="navbar">
      <div className="fila-superior">
        <div className="logo">Santiago Joven</div>
        <div className="cuentas">
          <Link to="/cuenta">
            <span className="material-symbols-outlined seccion-icono" style={{ fontSize: "25px" }}>person</span>
            Mi cuenta
          </Link>
          <Link to="/contacto">Contacto</Link>
          <button
            type="button"
            className={`boton-modo-oscuro ${modoOscuro ? 'oscuro' : 'claro'}`}
            onClick={() => setModoOscuro(!modoOscuro)}
          >
            <span className="material-symbols-outlined">
              {modoOscuro ? 'dark_mode' : 'light_mode'}
            </span>
          </button>
        </div>
      </div>
      <hr className="linea-separadora" />
      <nav className="fila-inferior">
        <a href="/#inicio">Inicio</a>
        <a href="/#apoyo">Apoyo</a>
        <a href="/#proyeccion">Proyección</a>
        <a href="/#accion">Acción</a>
        <a href="/#programas">Programas</a>
        <a href="/#salud">Salud mental</a>
        <a href="/#conexion">Conexión</a>
        <a href="/#calendario">Calendario</a>
      </nav>
    </header>
  );
}