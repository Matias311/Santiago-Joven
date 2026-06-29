import { useState, useEffect } from "react";
import { LoginForm } from "./LoginForm";
import { RegistroForm } from "./RegistroForm";
import type { ModoAuth, PropsLoginModal } from "../types/Auth";
import "./LoginModal.css";

const TITULOS: Record<ModoAuth, string> = {
  login: "Iniciar Sesión",
  registro: "Crear Cuenta",
};

/**
 * Modal de autenticación. Ya no contiene la lógica de los formularios:
 * solo decide cuál mostrar (login o registro) y delega toda la
 * validación/llamadas a la API a LoginForm y RegistroForm respectivamente.
 *
 * El flujo de "recuperar contraseña" se removió temporalmente: no existe
 * el endpoint en el backend todavía (ver feature request enviado).
 *
 * @component
 * @param {PropsLoginModal} props - Estado de apertura, cierre y callback de login exitoso.
 * @returns {JSX.Element | null} Modal de autenticación, o null si está cerrado.
 */
export const LoginModal = ({ isOpen, onClose, onLoginExitoso }: PropsLoginModal) => {
  const [modo, setModo] = useState<ModoAuth>("login");

  // Siempre vuelve a "login" cada vez que se abre el modal
  useEffect(() => {
    if (isOpen) setModo("login");
  }, [isOpen]);

  if (!isOpen) return null;

  /** Cierra el modal y notifica al padre (Navbar) que ya hay sesión activa. */
  const handleExito = () => {
    onLoginExitoso();
    onClose();
  };

  return (
    <div className="overlay">
      <div className="modal">
        <div className="brand">Santiago Joven</div>
        <h2>{TITULOS[modo]}</h2>

        {modo === "login" && (
          <LoginForm onExito={handleExito} onIrARegistro={() => setModo("registro")} />
        )}

        {modo === "registro" && <RegistroForm onExito={handleExito} />}

        <button className="close" onClick={onClose}>✕</button>
      </div>
    </div>
  );
};