import { useState } from "react";
import { authService, ErrorAuth } from "../services/AuthService";
import { guardarSesion } from "../utils/sessionStorage";
import type { ErroresCampo } from "../types/Auth";

/** Valida formato de correo simple: algo@algo.algo, sin espacios ni @ extra. */
const ES_CORREO_VALIDO = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

type Props = {
  /** Notifica al componente padre que el login fue exitoso (cierra el modal y refresca el Navbar). */
  onExito: () => void;
  /** Cambia al formulario de registro. */
  onIrARegistro: () => void;
};

/**
 * Formulario de inicio de sesión.
 * Responsabilidad única: pedir email/password, validar, y llamar a authService.login.
 *
 * @component
 * @param {Props} props - Callbacks de éxito y de navegación a registro.
 * @returns {JSX.Element} Formulario de login.
 */
export const LoginForm = ({ onExito, onIrARegistro }: Props) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errores, setErrores] = useState<ErroresCampo>({});
  const [cargando, setCargando] = useState(false);
  const [errorGeneral, setErrorGeneral] = useState("");

  /** Valida los campos del formulario antes de llamar a la API. */
  const validar = (): boolean => {
    const nuevosErrores: ErroresCampo = {};
    if (!email.trim()) {
      nuevosErrores.email = "Este campo es obligatorio";
    } else if (!ES_CORREO_VALIDO.test(email)) {
      nuevosErrores.email = "Ingresa un correo válido";
    }
    if (!password.trim()) {
      nuevosErrores.password = "Este campo es obligatorio";
    }
    setErrores(nuevosErrores);
    return Object.keys(nuevosErrores).length === 0;
  };

  /** Llama a la API, guarda la sesión y notifica éxito al padre. */
  const handleSubmit = async () => {
    setErrorGeneral("");
    if (!validar()) return;

    setCargando(true);
    try {
      const sesion = await authService.login({ email, password });
      guardarSesion(sesion);
      onExito();
    } catch (error) {
      setErrorGeneral(error instanceof ErrorAuth ? error.message : "Error inesperado");
    } finally {
      setCargando(false);
    }
  };

  return (
    <>
      {errorGeneral && <span className="field-error">{errorGeneral}</span>}

      <label>Correo</label>
      <input
        className={errores.email ? "input-error" : ""}
        placeholder="Ingresa tu correo"
        value={email}
        disabled={cargando}
        onChange={(e) => setEmail(e.target.value)}
      />
      {errores.email && <span className="field-error">{errores.email}</span>}

      <label>Contraseña</label>
      <input
        type="password"
        className={errores.password ? "input-error" : ""}
        placeholder="Contraseña"
        value={password}
        disabled={cargando}
        onChange={(e) => setPassword(e.target.value)}
      />
      {errores.password && <span className="field-error">{errores.password}</span>}

      <div className="button-group">
        <button className="primary-btn" onClick={handleSubmit} disabled={cargando}>
          {cargando ? "..." : "Iniciar Sesión"}
        </button>
        <button className="primary-btn" onClick={onIrARegistro} disabled={cargando}>
          Crear Cuenta
        </button>
      </div>
    </>
  );
};