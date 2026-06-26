import { useState } from "react";
import { Link } from "react-router-dom";
import { LoginModal } from "../Login/LoginModal";
import { MiCuentaModal } from "../Login/MiCuentaModal";
import { estaAutenticado } from "../Login/AuthService";
import "../Navbar/Navbar.css";

type NavbarProps = {
  modoOscuro: boolean;
  onToggleModo: () => void;
};

export default function Navbar({ modoOscuro, onToggleModo }: NavbarProps) {
  const [loginAbierto, setLoginAbierto] = useState(false);
  const [cuentaAbierta, setCuentaAbierta] = useState(false);

  // Abre MiCuentaModal si ya hay sesión activa, LoginModal si no
  const handleClickMiCuenta = () => {
    if (estaAutenticado()) {
      setCuentaAbierta(true);
    } else {
      setLoginAbierto(true);
    }
  };

  return (
    <>
      <header className="navbar">
        <div className="fila-superior">
          <div className="logo">Santiago Joven</div>
          <div className="cuentas">
            <button
              type="button"
              className="boton-mi-cuenta"
              onClick={handleClickMiCuenta}
            >
              <span className="material-symbols-outlined seccion-icono" style={{ fontSize: "25px" }}>person</span>
              Mi cuenta
            </button>
            <Link to="/contacto">Contacto</Link>
            <button
              type="button"
              className={`boton-modo-oscuro ${modoOscuro ? 'oscuro' : 'claro'}`}
              onClick={onToggleModo}
              aria-label={modoOscuro ? "Cambiar a modo claro" : "Cambiar a modo oscuro"}
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

      <LoginModal
        isOpen={loginAbierto}
        onClose={() => setLoginAbierto(false)}
      />
      <MiCuentaModal
        isOpen={cuentaAbierta}
        onClose={() => setCuentaAbierta(false)}
        onCerrarSesion={() => setCuentaAbierta(false)}
      />
    </>
  );
}