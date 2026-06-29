import { Link } from "react-router-dom";
import "../Navbar/Navbar.css";

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
 * Contiene el logo, links de navegación interna, acceso a cuenta y botón de modo oscuro.
 *
 * @component
 * @param {NavbarProps} props - Props del componente.
 * @param {boolean} props.modoOscuro - Estado actual del modo oscuro.
 * @param {() => void} props.onToggleModo - Callback para alternar el modo oscuro.
 * @returns {JSX.Element} Header con navegación completa.
 *
 * @example
 * const { modoOscuro, toggleModo } = useTheme();
 * <Navbar modoOscuro={modoOscuro} onToggleModo={toggleModo} />
 */
export default function Navbar({ modoOscuro, onToggleModo }: NavbarProps) {
  return (
    <header className="navbar">
      <div className="fila-superior">
        <div className="logo">Santiago Joven</div>
        <div className="cuentas">
          {/* Enlace a la página de cuenta del usuario */}
          <Link to="/cuenta">
            <span
              className="material-symbols-outlined seccion-icono"
              style={{ fontSize: "25px" }}>
              person
            </span>
            Mi cuenta
          </Link>

          {/* Enlace a la página de contacto */}
          <Link to="/contacto">Contacto</Link>

          {/**
           * Botón para alternar entre modo claro y oscuro.
           * Cambia su ícono y clase según el estado actual.
           */}
          <button
            type="button"
            className={`boton-modo-oscuro ${modoOscuro ? "oscuro" : "claro"}`}
            onClick={onToggleModo}
            aria-label={modoOscuro ? "Cambiar a modo claro" : "Cambiar a modo oscuro"}
          >
            <span className="material-symbols-outlined">
              {modoOscuro ? "dark_mode" : "light_mode"}
            </span>
          </button>
        </div>
      </div>

      <hr className="linea-separadora" />

      {/* Navegación principal hacia las secciones de la página */}
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