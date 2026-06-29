import { useState } from "react";
import { authService, ErrorAuth } from "../services/AuthService";
import { guardarSesion } from "../utils/sessionStorage";
import type { ErroresCampo } from "../types/Auth";

/**
 * Expresión regular para validar formato de correo electrónico.
 * Acepta: algo@algo.algo
 * Rechaza: espacios, más de un @, o dominios incompletos.
 */
const ES_CORREO_VALIDO = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

type Props = {
  /** Callback que se ejecuta cuando el registro fue exitoso y el auto-login completado. */
  onExito: () => void;
};

/**
 * Formulario de creación de cuenta.
 * Tras un registro exitoso, guarda la sesión automáticamente (auto-login)
 * y notifica al padre sin necesidad de que el usuario inicie sesión por separado.
 *
 * @remarks
 * El campo "teléfono" se removió intencionalmente. El endpoint
 * `POST /api/v1/auth/register` no lo acepta aún. Cuando el backend
 * lo soporte, se vuelve a agregar aquí.
 *
 * @component
 * @param props - Callback de éxito.
 * @returns Formulario de registro.
 */
export const RegistroForm = ({ onExito }: Props) => {
  const [nombre, setNombre] = useState("");
  const [apellido, setApellido] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [repetirPassword, setRepetirPassword] = useState("");
  const [errores, setErrores] = useState<ErroresCampo>({});
  const [cargando, setCargando] = useState(false);
  const [errorGeneral, setErrorGeneral] = useState("");

  /**
   * Valida todos los campos del formulario antes de llamar a la API.
   * Verifica que ningún campo esté vacío, que el correo tenga formato válido,
   * que la contraseña tenga al menos 8 caracteres y que ambas contraseñas coincidan.
   * @returns `true` si el formulario es válido, `false` si hay errores.
   */
  const validar = (): boolean => {
    const nuevosErrores: ErroresCampo = {};

    if (!nombre.trim()) nuevosErrores.nombre = "Este campo es obligatorio";
    if (!apellido.trim()) nuevosErrores.apellido = "Este campo es obligatorio";

    if (!email.trim()) {
      nuevosErrores.email = "Este campo es obligatorio";
    } else if (!ES_CORREO_VALIDO.test(email)) {
      nuevosErrores.email = "Ingresa un correo válido";
    }

    if (!password.trim()) {
      nuevosErrores.password = "Este campo es obligatorio";
    } else if (password.length < 8) {
      nuevosErrores.password = "La contraseña debe tener al menos 8 caracteres";
    }

    if (!repetirPassword.trim()) {
      nuevosErrores.repetirPassword = "Este campo es obligatorio";
    } else if (password !== repetirPassword) {
      nuevosErrores.repetirPassword = "Las contraseñas no son iguales";
    }

    setErrores(nuevosErrores);
    return Object.keys(nuevosErrores).length === 0;
  };

  /**
   * Maneja el envío del formulario.
   * Valida los campos, llama a la API de registro, guarda la sesión en localStorage
   * (auto-login) y notifica al padre que el proceso fue exitoso.
   * Si la API responde con error, muestra el mensaje correspondiente.
   */
  const handleSubmit = async () => {
    setErrorGeneral("");
    if (!validar()) return;

    setCargando(true);
    try {
      const sesion = await authService.register({ email, password, nombre, apellido });
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

      <label>Nombre</label>
      <input
        className={errores.nombre ? "input-error" : ""}
        placeholder="Ingresa tu nombre"
        value={nombre}
        disabled={cargando}
        onChange={(e) => setNombre(e.target.value)}
      />
      {errores.nombre && <span className="field-error">{errores.nombre}</span>}

      <label>Apellido</label>
      <input
        className={errores.apellido ? "input-error" : ""}
        placeholder="Ingresa tu apellido"
        value={apellido}
        disabled={cargando}
        onChange={(e) => setApellido(e.target.value)}
      />
      {errores.apellido && <span className="field-error">{errores.apellido}</span>}

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
        placeholder="Mínimo 8 caracteres"
        value={password}
        disabled={cargando}
        onChange={(e) => setPassword(e.target.value)}
      />
      {errores.password && <span className="field-error">{errores.password}</span>}

      <label>Repetir contraseña</label>
      <input
        type="password"
        className={errores.repetirPassword ? "input-error" : ""}
        placeholder="Ingresa nuevamente la contraseña"
        value={repetirPassword}
        disabled={cargando}
        onChange={(e) => setRepetirPassword(e.target.value)}
      />
      {errores.repetirPassword && <span className="field-error">{errores.repetirPassword}</span>}

      <button className="primary-btn" onClick={handleSubmit} disabled={cargando}>
        {cargando ? "..." : "Crear Cuenta"}
      </button>
    </>
  );
};