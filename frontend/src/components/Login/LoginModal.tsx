// Imports
import { useEffect, useRef, useState } from "react";
import { authService, guardarToken } from "./AuthService";
import type { ModoAuth, DatosFormulario, ErroresCampo, PropsLoginModal } from "../types/Auth";
import "./LoginModal.css";

// Estado vacío para resetear el formulario al abrir/cambiar de modo
const FORMULARIO_VACIO: DatosFormulario = {
  usuario: "",
  password: "",
  correo: "",
  telefono: "",
  codigo: Array(5).fill(""),
  nuevaPassword: "",
  repetirPassword: "",
};

// Títulos para los modos que tienen encabezado fijo; el resto usa "Recuperar Cuenta"
const TITULOS: Partial<Record<ModoAuth, string>> = {
  login: "Iniciar Sesión",
  registro: "Crear Cuenta",
};

// Valida formato correo: algo@algo.algo sin espacios ni @ extra
const esCorreoValido = (valor: string) =>
  /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(valor);

// Valida que el string contenga solo dígitos 0-9
const esSoloNumeros = (valor: string) => /^\d+$/.test(valor);

/**
 * Modal de autenticación. Maneja cuatro modos en una sola vista:
 * login, registro, recuperar contraseña por correo, y restablecer con OTP.
 */
export const LoginModal = ({ isOpen, onClose }: PropsLoginModal) => {
  const [modoAuth, setModoAuth] = useState<ModoAuth>("login");
  const [datosForm, setDatosForm] = useState<DatosFormulario>(FORMULARIO_VACIO);
  const [cargando, setCargando] = useState(false);
  const [erroresCampo, setErroresCampo] = useState<ErroresCampo>({});
  const [mensajeExito, setMensajeExito] = useState("");

  // Refs para mover el foco automáticamente entre los inputs del OTP
  const refsOtp = useRef<(HTMLInputElement | null)[]>([]);

  // Resetea el formulario cada vez que el modal se abre
  useEffect(() => {
    if (isOpen) {
      setModoAuth("login");
      setDatosForm(FORMULARIO_VACIO);
      setErroresCampo({});
      setMensajeExito("");
    }
  }, [isOpen]);

  if (!isOpen) return null;

  // Cambia de modo limpiando el formulario y los errores
  const irA = (modo: ModoAuth) => {
    setDatosForm(FORMULARIO_VACIO);
    setErroresCampo({});
    setMensajeExito("");
    setModoAuth(modo);
  };

  const obtenerTitulo = () => TITULOS[modoAuth] ?? "Recuperar Cuenta";

  // Actualiza un campo del formulario y limpia su error si lo había
  const handleCambio = (campo: keyof DatosFormulario, valor: string | string[]) => {
    setDatosForm((prev) => ({ ...prev, [campo]: valor }));
    if (erroresCampo[campo]) {
      setErroresCampo((prev) => ({ ...prev, [campo]: undefined }));
    }
  };

  // Actualiza un dígito del OTP y mueve el foco al siguiente input
  const handleCambioOtp = (indice: number, valor: string) => {
    if (valor && !/^\d$/.test(valor)) return;
    const nuevoCodigo = [...datosForm.codigo];
    nuevoCodigo[indice] = valor;
    setDatosForm((prev) => ({ ...prev, codigo: nuevoCodigo }));
    if (valor && indice < 4) {
      refsOtp.current[indice + 1]?.focus();
    }
  };

  // Retrocede el foco al input anterior al borrar con Backspace en un input vacío
  const handleTeclaOtp = (indice: number, e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Backspace" && !datosForm.codigo[indice] && indice > 0) {
      refsOtp.current[indice - 1]?.focus();
    }
  };

  // --- Validaciones ---

  const validarLogin = (): boolean => {
    const errores: ErroresCampo = {};
    if (!datosForm.usuario.trim())  errores.usuario  = "Este campo es obligatorio";
    if (!datosForm.password.trim()) errores.password = "Este campo es obligatorio";
    setErroresCampo(errores);
    return Object.keys(errores).length === 0;
  };

  const validarRegistro = (): boolean => {
    const errores: ErroresCampo = {};

    if (!datosForm.correo.trim())
      errores.correo = "Este campo es obligatorio";
    else if (!esCorreoValido(datosForm.correo))
      errores.correo = "Ingrese un correo válido (ej: nombre@dominio.com)";

    if (!datosForm.telefono.trim())
      errores.telefono = "Este campo es obligatorio";
    else if (!esSoloNumeros(datosForm.telefono))
      errores.telefono = "Solo acepta números";

    if (!datosForm.nuevaPassword.trim())
      errores.nuevaPassword = "Este campo es obligatorio";
    else if (datosForm.nuevaPassword.length < 8)
      errores.nuevaPassword = "La contraseña debe tener al menos 8 caracteres";

    if (!datosForm.repetirPassword.trim())
      errores.repetirPassword = "Este campo es obligatorio";
    else if (datosForm.nuevaPassword !== datosForm.repetirPassword)
      errores.repetirPassword = "Las contraseñas no son iguales";

    setErroresCampo(errores);
    return Object.keys(errores).length === 0;
  };

  const validarRecuperarCorreo = (): boolean => {
    const errores: ErroresCampo = {};
    if (!datosForm.correo.trim())
      errores.correo = "Este campo es obligatorio";
    else if (!esCorreoValido(datosForm.correo))
      errores.correo = "Ingrese un correo válido (ej: nombre@dominio.com)";
    setErroresCampo(errores);
    return Object.keys(errores).length === 0;
  };

  const validarRestablecerCorreo = (): boolean => {
    const errores: ErroresCampo = {};
    if (datosForm.codigo.join("").length < 5)
      errores.codigo = "Ingrese los 5 dígitos";
    if (!datosForm.nuevaPassword.trim())
      errores.nuevaPassword = "Este campo es obligatorio";
    else if (datosForm.nuevaPassword.length < 8)
      errores.nuevaPassword = "La contraseña debe tener al menos 8 caracteres";
    setErroresCampo(errores);
    return Object.keys(errores).length === 0;
  };

  // --- Handlers de submit ---

  // Llama al servicio de login, guarda el token y cierra el modal
  const handleLogin = async () => {
    if (!validarLogin()) return;
    setCargando(true);
    try {
      const { token } = await authService.login({ usuario: datosForm.usuario, password: datosForm.password });
      guardarToken(token);
      onClose();
    } finally {
      setCargando(false);
    }
  };

  // Registra al usuario y redirige al login con mensaje de confirmación
  const handleRegistro = async () => {
    if (!validarRegistro()) return;
    setCargando(true);
    try {
      await authService.register({
        correo: datosForm.correo,
        telefono: datosForm.telefono,
        password: datosForm.nuevaPassword,
      });
      irA("login");
      setMensajeExito("Cuenta creada, ya puedes iniciar sesión");
    } finally {
      setCargando(false);
    }
  };

  // Envía el correo con el código OTP y avanza al paso de restablecimiento
  const handleRecuperarCorreo = async () => {
    if (!validarRecuperarCorreo()) return;
    setCargando(true);
    try {
      await authService.recoverByEmail({ correo: datosForm.correo });
      setModoAuth("restablecer-correo");
    } finally {
      setCargando(false);
    }
  };

  // Verifica el OTP y actualiza la contraseña; redirige al login si tiene éxito
  const handleRestablecerCorreo = async () => {
    if (!validarRestablecerCorreo()) return;
    setCargando(true);
    try {
      await authService.resetPassword({
        codigo: datosForm.codigo.join(""),
        nuevaPassword: datosForm.nuevaPassword,
      });
      irA("login");
      setMensajeExito("Contraseña restablecida, inicie sesión");
    } finally {
      setCargando(false);
    }
  };

  // --- Render ---
  // Renderiza condicionalmente el formulario según modoAuth
  return (
    <div className="overlay">
      <div className="modal">
        <div className="brand">Santiago Joven</div>
        <h2>{obtenerTitulo()}</h2>

        {/* LOGIN */}
        {modoAuth === "login" && (
          <>
            {mensajeExito && <span className="field-success">{mensajeExito}</span>}

            <label>Usuario</label>
            <input
              className={erroresCampo.usuario ? "input-error" : ""}
              placeholder="Nombre de usuario o correo"
              value={datosForm.usuario}
              disabled={cargando}
              onChange={(e) => handleCambio("usuario", e.target.value)}
            />
            {erroresCampo.usuario && <span className="field-error">{erroresCampo.usuario}</span>}

            <label>Contraseña</label>
            <input
              type="password"
              className={erroresCampo.password ? "input-error" : ""}
              placeholder="Contraseña"
              value={datosForm.password}
              disabled={cargando}
              onChange={(e) => handleCambio("password", e.target.value)}
            />
            {erroresCampo.password && <span className="field-error">{erroresCampo.password}</span>}

            <div className="button-group">
              <button className="primary-btn" onClick={handleLogin} disabled={cargando}>
                {cargando ? "..." : "Iniciar Sesión"}
              </button>
              <button className="primary-btn" onClick={() => irA("registro")} disabled={cargando}>
                Crear Cuenta
              </button>
            </div>

            <p className="link" onClick={() => irA("recuperar-correo")}>
              Olvidé mi contraseña
            </p>
          </>
        )}

        {/* REGISTRO */}
        {modoAuth === "registro" && (
          <>
            <label>Ingrese su correo</label>
            <input
              className={erroresCampo.correo ? "input-error" : ""}
              placeholder="Ingrese su correo"
              value={datosForm.correo}
              disabled={cargando}
              onChange={(e) => handleCambio("correo", e.target.value)}
            />
            {erroresCampo.correo && <span className="field-error">{erroresCampo.correo}</span>}

            <label>Ingrese su N° de Teléfono</label>
            <input
              className={erroresCampo.telefono ? "input-error" : ""}
              placeholder="9 35346806"
              inputMode="numeric"
              value={datosForm.telefono}
              disabled={cargando}
              onChange={(e) => handleCambio("telefono", e.target.value)}
            />
            {erroresCampo.telefono && <span className="field-error">{erroresCampo.telefono}</span>}

            <label>Ingrese una contraseña</label>
            <input
              type="password"
              className={erroresCampo.nuevaPassword ? "input-error" : ""}
              placeholder="Mínimo 8 caracteres"
              value={datosForm.nuevaPassword}
              disabled={cargando}
              onChange={(e) => handleCambio("nuevaPassword", e.target.value)}
            />
            {erroresCampo.nuevaPassword && <span className="field-error">{erroresCampo.nuevaPassword}</span>}

            <label>Ingrese nuevamente la contraseña</label>
            <input
              type="password"
              className={erroresCampo.repetirPassword ? "input-error" : ""}
              placeholder="Ingrese nuevamente la contraseña"
              value={datosForm.repetirPassword}
              disabled={cargando}
              onChange={(e) => handleCambio("repetirPassword", e.target.value)}
            />
            {erroresCampo.repetirPassword && <span className="field-error">{erroresCampo.repetirPassword}</span>}

            <button className="primary-btn" onClick={handleRegistro} disabled={cargando}>
              {cargando ? "..." : "Crear Sesión"}
            </button>
          </>
        )}

        {/* RECUPERAR POR CORREO */}
        {modoAuth === "recuperar-correo" && (
          <>
            <label>Ingrese su correo</label>
            <input
              className={erroresCampo.correo ? "input-error" : ""}
              placeholder="Ingrese su correo registrado"
              value={datosForm.correo}
              disabled={cargando}
              onChange={(e) => handleCambio("correo", e.target.value)}
            />
            {erroresCampo.correo && <span className="field-error">{erroresCampo.correo}</span>}

            <button className="primary-btn" onClick={handleRecuperarCorreo} disabled={cargando}>
              {cargando ? "..." : "Enviar Codigo"}
            </button>
          </>
        )}

        {/* RESTABLECER POR CORREO — ingresa el OTP recibido y la nueva contraseña */}
        {modoAuth === "restablecer-correo" && (
          <>
            <label>Ingrese los 5 dígitos enviados a su correo</label>
            <div className={`otp ${erroresCampo.codigo ? "otp-error" : ""}`}>
              {datosForm.codigo.map((digito, i) => (
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
            {erroresCampo.codigo && <span className="field-error">{erroresCampo.codigo}</span>}

            <label>Ingrese nueva clave</label>
            <input
              type="password"
              className={erroresCampo.nuevaPassword ? "input-error" : ""}
              placeholder="Ingrese nueva contraseña"
              value={datosForm.nuevaPassword}
              disabled={cargando}
              onChange={(e) => handleCambio("nuevaPassword", e.target.value)}
            />
            {erroresCampo.nuevaPassword && <span className="field-error">{erroresCampo.nuevaPassword}</span>}

            <button className="primary-btn" onClick={handleRestablecerCorreo} disabled={cargando}>
              {cargando ? "..." : "Reestablecer"}
            </button>
          </>
        )}

        <button className="close" onClick={onClose}>✕</button>
      </div>
    </div>
  );
};