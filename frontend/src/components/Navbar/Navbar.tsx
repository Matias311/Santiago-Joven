import { useState } from "react";
import { Link } from "react-router-dom";
import { LoginModal } from "../Login/LoginModal";
import { MiCuentaModal } from "../Login/MiCuentaModal";
import { estaAutenticado } from "../utils/sessionStorage";
import "./Navbar.css";
/**
 * Props del componente Navbar.
 * @typedef {Object} NavbarProps
 * @property {boolean} modoOscuro - Indica si el modo oscuro está activo.
 * @property {() => void} onToggleModo - Función para alternar entre modo claro y oscuro.
 */
type NavbarProps = {
  modoOscuro: boolean;
  onToggleModo: () => void;
};

/**
 * Barra de navegación principal de la aplicación Santiago Joven.
 * Decide si el botón "Mi cuenta" abre el LoginModal o el MiCuentaModal,
 * según si hay una sesión guardada. Ninguno de los dos modales hace esa
 * verificación por sí mismo: la responsabilidad vive aquí.
 *
 * @component
 * @param {NavbarProps} props - Props del componente.
 * @returns {JSX.Element} Header con navegación completa.
 */
export default function Navbar({ modoOscuro, onToggleModo }: NavbarProps) {
  const [loginAbierto, setLoginAbierto] = useState(false);
  const [cuentaAbierta, setCuentaAbierta] = useState(false);
  const [sesionActiva, setSesionActiva] = useState(estaAutenticado());

  /** Abre MiCuentaModal si ya hay sesión activa, o LoginModal si no. */
  const handleClickMiCuenta = () => {
    if (sesionActiva) {
      setCuentaAbierta(true);
    } else {
      setLoginAbierto(true);
    }
  };

  /** Se llama cuando LoginModal confirma un login/registro exitoso. */
  const handleLoginExitoso = () => {
    setSesionActiva(true);
  };

  /** Se llama cuando MiCuentaModal cierra la sesión o borra la cuenta. */
  const handleCerrarSesion = () => {
    setSesionActiva(false);
    setCuentaAbierta(false);
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
        onLoginExitoso={handleLoginExitoso}
      />
      <MiCuentaModal
        isOpen={cuentaAbierta}
        onClose={() => setCuentaAbierta(false)}
        onCerrarSesion={handleCerrarSesion}
      />
    </>
  );
}