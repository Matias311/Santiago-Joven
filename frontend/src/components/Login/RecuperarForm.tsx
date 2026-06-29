import { useState } from "react";
import type { ErroresCampo } from "../types/Auth";

/**
 * Expresión regular para validar formato de correo electrónico.
 * Acepta: algo@algo.algo
 * Rechaza: espacios, más de un @, o dominios incompletos.
 */
const ES_CORREO_VALIDO = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

type Props = {
  /**
   * Callback que se ejecuta cuando el correo fue enviado exitosamente.
   * Recibe el correo ingresado para que el paso siguiente pueda usarlo si es necesario.
   */
  onEnviar: (correo: string) => void;
};

/**
 * Formulario del primer paso de recuperación de contraseña.
 * Pide el correo registrado y simula el envío de un código OTP.
 *
 * @todo Conectar con `authService.recoverByEmail` cuando el backend soporte este endpoint.
 *
 * @component
 * @param props - Callback que se ejecuta al enviar el correo exitosamente.
 * @returns Formulario de recuperación de correo.
 */
export const RecuperarForm = ({ onEnviar }: Props) => {
  const [correo, setCorreo] = useState("");
  const [errores, setErrores] = useState<ErroresCampo>({});
  const [cargando, setCargando] = useState(false);

  /**
   * Valida que el correo no esté vacío y tenga formato válido.
   * @returns `true` si el formulario es válido, `false` si hay errores.
   */
  const validar = (): boolean => {
    const nuevosErrores: ErroresCampo = {};
    if (!correo.trim()) {
      nuevosErrores.correo = "Este campo es obligatorio";
    } else if (!ES_CORREO_VALIDO.test(correo)) {
      nuevosErrores.correo = "Ingresa un correo válido";
    }
    setErrores(nuevosErrores);
    return Object.keys(nuevosErrores).length === 0;
  };

  /**
   * Maneja el envío del formulario.
   * Valida el correo, simula la llamada a la API con un delay de 800ms
   * y notifica al padre para avanzar al paso del OTP.
   */
  const handleSubmit = async () => {
    if (!validar()) return;
    setCargando(true);
    // TODO: reemplazar por authService.recoverByEmail({ correo }) cuando el backend lo soporte.
    await new Promise((r) => setTimeout(r, 800));
    setCargando(false);
    onEnviar(correo);
  };

  return (
    <>
      <label>Ingrese su correo</label>
      <input
        className={errores.correo ? "input-error" : ""}
        placeholder="Ingrese su correo registrado"
        value={correo}
        disabled={cargando}
        onChange={(e) => setCorreo(e.target.value)}
      />
      {errores.correo && <span className="field-error">{errores.correo}</span>}

      <button className="primary-btn" onClick={handleSubmit} disabled={cargando}>
        {cargando ? "..." : "Enviar Código"}
      </button>
    </>
  );
};