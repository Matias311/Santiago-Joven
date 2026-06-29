import { useRef, useState } from "react";
import { authService, ErrorAuth } from "../services/AuthService";
import type { ErroresCampo } from "../types/Auth";

type Props = {
  /** Correo del usuario, necesario para el endpoint de restablecimiento. */
  correo: string;
  /** Callback que se ejecuta cuando la contraseña fue restablecida exitosamente. */
  onExito: () => void;
};

/**
 * Formulario del segundo paso de recuperación de contraseña.
 * Permite ingresar el código OTP de 5 dígitos recibido por correo
 * y definir una nueva contraseña. Llama a POST /api/v1/auth/restablecer.
 *
 * @component
 * @param props - Correo del usuario y callback que se ejecuta al restablecer exitosamente.
 * @returns Formulario de restablecimiento de contraseña.
 */
export const RestablecerForm = ({ correo, onExito }: Props) => {
  const [codigo, setCodigo] = useState(Array(5).fill(""));
  const [nuevaPassword, setNuevaPassword] = useState("");
  const [errores, setErrores] = useState<ErroresCampo>({});
  const [cargando, setCargando] = useState(false);

  /**
   * Referencias a cada input del OTP para mover el foco automáticamente
   * entre ellos al escribir o borrar dígitos.
   */
  const refsOtp = useRef<(HTMLInputElement | null)[]>([]);

  /**
   * Maneja el cambio de valor en un input del OTP.
   * Solo acepta dígitos del 0 al 9. Al escribir un dígito válido,
   * mueve el foco automáticamente al siguiente input.
   * @param indice - Posición del input en el array (0 a 4).
   * @param valor - Valor ingresado por el usuario.
   */
  const handleCambioOtp = (indice: number, valor: string) => {
    // Expresión regular que acepta solo un dígito del 0 al 9.
    if (valor && !/^\d$/.test(valor)) return;
    const nuevo = [...codigo];
    nuevo[indice] = valor;
    setCodigo(nuevo);
    if (valor && indice < 4) refsOtp.current[indice + 1]?.focus();
  };

  /**
   * Maneja la tecla Backspace en un input del OTP.
   * Si el input actual está vacío y no es el primero,
   * mueve el foco al input anterior para facilitar la corrección.
   * @param indice - Posición del input en el array (0 a 4).
   * @param e - Evento de teclado.
   */
  const handleTeclaOtp = (indice: number, e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Backspace" && !codigo[indice] && indice > 0) {
      refsOtp.current[indice - 1]?.focus();
    }
  };

  /**
   * Valida que el código OTP tenga los 5 dígitos completos
   * y que la nueva contraseña tenga al menos 8 caracteres.
   * @returns `true` si el formulario es válido, `false` si hay errores.
   */
  const validar = (): boolean => {
    const nuevosErrores: ErroresCampo = {};
    if (codigo.join("").length < 5) nuevosErrores.codigo = "Ingresa los 5 dígitos";
    if (!nuevaPassword.trim()) {
      nuevosErrores.nuevaPassword = "Este campo es obligatorio";
    } else if (nuevaPassword.length < 8) {
      nuevosErrores.nuevaPassword = "Mínimo 8 caracteres";
    }
    setErrores(nuevosErrores);
    return Object.keys(nuevosErrores).length === 0;
  };

  /**
   * Maneja el envío del formulario.
   * Valida los campos, llama a la API con el correo, código y nueva contraseña.
   * Notifica al padre cuando el restablecimiento fue exitoso.
   */
  const handleSubmit = async () => {
    if (!validar()) return;
    setCargando(true);
    try {
      await authService.restablecer({
        email: correo,
        codigo: codigo.join(""),
        nuevaPassword,
      });
      onExito();
    } catch (error) {
      setErrores({ codigo: error instanceof ErrorAuth ? error.message : "Error inesperado" });
    } finally {
      setCargando(false);
    }
  };

  return (
    <>
      <label>Ingresa los 5 dígitos enviados a tu correo</label>
      <div className={`otp ${errores.codigo ? "otp-error" : ""}`}>
        {codigo.map((digito, i) => (
          <input
            key={i}
            ref={(el) => { refsOtp.current[i] = el; }}
            maxLength={1}
            inputMode="numeric"
            value={digito}
            disabled={cargando}
            onChange={(e) => handleCambioOtp(i, e.target.value)}
            onKeyDown={(e) => handleTeclaOtp(i, e)}
          />
        ))}
      </div>
      {errores.codigo && <span className="field-error">{errores.codigo}</span>}

      <label>Nueva contraseña</label>
      <input
        type="password"
        className={errores.nuevaPassword ? "input-error" : ""}
        placeholder="Mínimo 8 caracteres"
        value={nuevaPassword}
        disabled={cargando}
        onChange={(e) => setNuevaPassword(e.target.value)}
      />
      {errores.nuevaPassword && <span className="field-error">{errores.nuevaPassword}</span>}

      <button className="primary-btn" onClick={handleSubmit} disabled={cargando}>
        {cargando ? "..." : "Restablecer"}
      </button>
    </>
  );
};