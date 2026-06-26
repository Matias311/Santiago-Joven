import { authService } from "./AuthService";
import "./LoginModal.css";

type Props = {
  isOpen: boolean;
  onClose: () => void;
  onCerrarSesion: () => void;
};

/**
 * Modal de cuenta para usuarios autenticados.
 * Muestra datos del usuario (placeholders hasta conectar backend) y acciones de cuenta.
 */
export const MiCuentaModal = ({ isOpen, onClose, onCerrarSesion }: Props) => {
  if (!isOpen) return null;

  // Elimina el token y notifica al padre para actualizar el estado de autenticación
  const handleCerrarSesion = () => {
    authService.logout();
    onCerrarSesion();
    onClose();
  };

  // Placeholder: cuando el backend esté listo, aquí va la confirmación + llamada real
  const handleBorrarCuenta = () => {
    console.log("BORRAR CUENTA");
  };

  return (
    <div className="overlay">
      <div className="modal">
        <div className="brand">Santiago Joven</div>
        <h2>Mi Cuenta</h2>

        {/* Campos deshabilitados hasta que el backend entregue los datos reales del usuario */}
        <label>Usuario</label>
        <div className="campo-con-accion">
          <input placeholder="Nombre de usuario o correo" disabled />
          <button type="button" className="boton-editar" aria-label="Editar usuario">
            <span className="material-symbols-outlined">edit</span>
          </button>
        </div>

        <label>Correo</label>
        <div className="campo-con-accion">
          <input placeholder="Correo" disabled />
          <button type="button" className="boton-editar" aria-label="Editar correo">
            <span className="material-symbols-outlined">edit</span>
          </button>
        </div>

        <div className="button-group">
          <button className="danger-btn" onClick={handleCerrarSesion}>
            Cerrar Sesión
          </button>
          <button className="danger-btn" onClick={handleBorrarCuenta}>
            Borrar Cuenta
          </button>
        </div>

        <p className="link">Cambiar Contraseña</p>

        <button className="close" onClick={onClose}>✕</button>
      </div>
    </div>
  );
};