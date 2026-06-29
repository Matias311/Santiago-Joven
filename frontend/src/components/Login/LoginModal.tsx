import { useState, useEffect } from "react";
import { LoginForm } from "./LoginForm";
import { RegistroForm } from "./RegistroForm";
import { RecuperarForm } from "./RecuperarForm";
import { RestablecerForm } from "./RestablecerForm";
import type { ModoAuth, PropsLoginModal } from "../types/Auth";
import "./LoginModal.css";

/**
 * Títulos del encabezado del modal según el modo activo.
 * Ambos modos de recuperación comparten el mismo título "Recuperar Cuenta".
 */
const TITULOS: Record<ModoAuth, string> = {
  login: "Iniciar Sesión",
  registro: "Crear Cuenta",
  "recuperar-correo": "Recuperar Cuenta",
  "restablecer-correo": "Recuperar Cuenta",
};

/**
 * Modal de autenticación. Controla qué formulario se muestra según el modo activo.
 * No contiene lógica de validación ni llamadas a la API: esa responsabilidad
 * está delegada a cada formulario hijo (LoginForm, RegistroForm, etc).
 *
 * Modos disponibles:
 * - `login`: formulario de inicio de sesión.
 * - `registro`: formulario de creación de cuenta.
 * - `recuperar-correo`: formulario para ingresar el correo de recuperación.
 * - `restablecer-correo`: formulario para ingresar el OTP y la nueva contraseña.
 *
 * @component
 * @param props - Estado de apertura, cierre y callback de login exitoso.
 * @returns Modal de autenticación, o `null` si está cerrado.
 */
export const LoginModal = ({ isOpen, onClose, onLoginExitoso }: PropsLoginModal) => {
  const [modo, setModo] = useState<ModoAuth>("login");
  const [mensajeExito, setMensajeExito] = useState("");

  /**
   * Cada vez que el modal se abre, resetea el modo a "login" y limpia
   * cualquier mensaje de éxito que haya quedado de una sesión anterior.
   */
  useEffect(() => {
    if (isOpen) {
      setModo("login");
      setMensajeExito("");
    }
  }, [isOpen]);

  if (!isOpen) return null;

  /**
   * Se ejecuta cuando login o registro fueron exitosos.
   * Notifica al Navbar para que refresque el estado de sesión y cierra el modal.
   */
  const handleExito = () => {
    onLoginExitoso();
    onClose();
  };

  /**
   * Se ejecuta cuando el restablecimiento de contraseña fue exitoso.
   * Vuelve al modo login y muestra un mensaje de confirmación al usuario.
   */
  const handleRestablecerExito = () => {
    setMensajeExito("Contraseña restablecida, ya puedes iniciar sesión");
    setModo("login");
  };

  return (
    <div className="overlay">
      <div className="modal">
        <div className="modal-header">
          <h2>{TITULOS[modo]}</h2>
          <button className="close" onClick={onClose}>✕</button>
        </div>

        {modo === "login" && (
          <>
            {mensajeExito && <span className="field-success">{mensajeExito}</span>}
            <LoginForm
              onExito={handleExito}
              onIrARegistro={() => setModo("registro")}
            />
            <p className="link" onClick={() => setModo("recuperar-correo")}>
              Olvidé mi contraseña
            </p>
          </>
        )}

        {modo === "registro" && <RegistroForm onExito={handleExito} />}

        {modo === "recuperar-correo" && (
          <RecuperarForm onEnviar={() => setModo("restablecer-correo")} />
        )}

        {modo === "restablecer-correo" && (
          <RestablecerForm onExito={handleRestablecerExito} />
        )}
      </div>
    </div>
  );
};