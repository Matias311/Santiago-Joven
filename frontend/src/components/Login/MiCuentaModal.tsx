import { useEffect, useState } from "react";
import { authService } from "../services/AuthService";
import { obtenerSesion, eliminarSesion } from "../utils/sessionStorage";
import type { UsuarioResponse, ErroresCampo } from "../types/Auth";
import "./LoginModal.css";

type Props = {
  /** Controla si el modal está visible. */
  isOpen: boolean;
  /** Callback para cerrar el modal sin cerrar sesión. */
  onClose: () => void;
  /** Callback que notifica al Navbar que la sesión terminó, para que refresque su estado. */
  onCerrarSesion: () => void;
};

/**
 * Modal de cuenta para usuarios autenticados.
 * Permite ver y editar nombre, apellido y correo, cerrar sesión y eliminar la cuenta.
 *
 * No verifica si el usuario está autenticado: esa responsabilidad es del Navbar,
 * que decide si abrir este modal o el de login según el estado de sesión.
 *
 * @component
 * @param props - Estado de apertura, cierre y callback de cierre de sesión.
 * @returns Modal de cuenta, o `null` si está cerrado.
 */
export const MiCuentaModal = ({ isOpen, onClose, onCerrarSesion }: Props) => {
  const [usuario, setUsuario] = useState<UsuarioResponse | null>(null);
  const [editando, setEditando] = useState<"nombre" | "apellido" | "email" | null>(null);
  const [valorEdicion, setValorEdicion] = useState("");
  const [errores, setErrores] = useState<ErroresCampo>({});
  const [cargando, setCargando] = useState(false);
  const [errorGeneral, setErrorGeneral] = useState("");

  /**
   * Cada vez que el modal se abre, inicia la carga de datos del usuario.
   * Usa AbortController para cancelar la petición si el modal se cierra
   * antes de que llegue la respuesta, evitando actualizar un componente desmontado.
   */
  useEffect(() => {
    if (!isOpen) return;

    const controller = new AbortController();
    cargarUsuario(controller.signal);

    return () => controller.abort();
  }, [isOpen]);

  /**
   * Obtiene los datos del usuario autenticado desde GET /api/v1/usuarios/{id}.
   * @param signal - Señal del AbortController para cancelar la petición si es necesario.
   */
  const cargarUsuario = async (signal: AbortSignal) => {
    const sesion = obtenerSesion();
    if (!sesion) return;

    setCargando(true);
    setErrorGeneral("");
    try {
      const respuesta = await fetch(`${authService.API_URL}/api/v1/usuarios/${sesion.userId}`, {
        headers: authService.headersAutenticados(),
        signal,
      });
      if (!respuesta.ok) throw new Error();
      setUsuario(await respuesta.json());
    } catch (error) {
      // Si la petición fue cancelada intencionalmente, no mostramos error.
      if (error instanceof DOMException && error.name === "AbortError") return;
      setErrorGeneral("No se pudieron cargar los datos de tu cuenta");
    } finally {
      setCargando(false);
    }
  };

  if (!isOpen) return null;

  /**
   * Activa el modo edición para un campo específico,
   * precargando el valor actual del usuario en el input.
   * @param campo - Campo a editar: "nombre", "apellido" o "email".
   */
  const handleEditar = (campo: "nombre" | "apellido" | "email") => {
    setEditando(campo);
    setValorEdicion(usuario?.[campo] ?? "");
    setErrores({});
  };

  /**
   * Envía el campo editado a PUT /api/v1/usuarios/{id}.
   * Si la API responde correctamente, actualiza los datos en pantalla y
   * sale del modo edición.
   */
  const handleGuardarEdicion = async () => {
    if (!editando || !usuario) return;

    if (!valorEdicion.trim()) {
      setErrores({ [editando]: "Este campo es obligatorio" });
      return;
    }

    setCargando(true);
    try {
      const respuesta = await fetch(`${authService.API_URL}/api/v1/usuarios/${usuario.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          ...authService.headersAutenticados(),
        },
        body: JSON.stringify({ [editando]: valorEdicion }),
      });
      if (!respuesta.ok) throw new Error();
      setUsuario(await respuesta.json());
      setEditando(null);
    } catch {
      setErrores({ [editando]: "No se pudo guardar el cambio" });
    } finally {
      setCargando(false);
    }
  };

  /**
   * Cierra la sesión local eliminando el token del localStorage
   * y notifica al Navbar para que actualice su estado.
   */
  const handleCerrarSesion = () => {
    authService.logout();
    onCerrarSesion();
    onClose();
  };

  /**
   * Solicita confirmación al usuario y luego elimina su cuenta
   * via DELETE /api/v1/usuarios/{id}.
   * Si tiene éxito, limpia la sesión local y cierra el modal.
   */
  const handleBorrarCuenta = async () => {
    if (!usuario) return;
    if (!window.confirm("¿Seguro que quieres eliminar tu cuenta? Esta acción no se puede deshacer.")) {
      return;
    }

    setCargando(true);
    try {
      const respuesta = await fetch(`${authService.API_URL}/api/v1/usuarios/${usuario.id}`, {
        method: "DELETE",
        headers: authService.headersAutenticados(),
      });
      if (!respuesta.ok) throw new Error();
      eliminarSesion();
      onCerrarSesion();
      onClose();
    } catch {
      setErrorGeneral("No se pudo eliminar la cuenta, intenta más tarde");
    } finally {
      setCargando(false);
    }
  };

  /**
   * Renderiza una fila editable con label, input y botón de editar/guardar.
   * El input está deshabilitado por defecto; se habilita solo cuando ese campo
   * está siendo editado. El botón alterna entre ícono de editar y de confirmar.
   * @param etiqueta - Texto del label visible al usuario.
   * @param campo - Campo del objeto usuario que representa esta fila.
   */
  const renderCampo = (etiqueta: string, campo: "nombre" | "apellido" | "email") => (
    <>
      <label>{etiqueta}</label>
      <div className="campo-con-accion">
        <input
          value={editando === campo ? valorEdicion : usuario?.[campo] ?? ""}
          disabled={editando !== campo || cargando}
          className={errores[campo] ? "input-error" : ""}
          onChange={(e) => setValorEdicion(e.target.value)}
        />
        <button
          type="button"
          className="boton-editar"
          aria-label={`Editar ${etiqueta.toLowerCase()}`}
          disabled={cargando}
          onClick={() => (editando === campo ? handleGuardarEdicion() : handleEditar(campo))}
        >
          <span className="material-symbols-outlined">
            {editando === campo ? "check" : "edit"}
          </span>
        </button>
      </div>
      {errores[campo] && <span className="field-error">{errores[campo]}</span>}
    </>
  );

  return (
    <div className="overlay">
      <div className="modal">
        <div className="modal-header">
          <h2>Mi Cuenta</h2>
          <button className="close" onClick={onClose}>✕</button>
        </div>

        {errorGeneral && <span className="field-error">{errorGeneral}</span>}

        {renderCampo("Nombre", "nombre")}
        {renderCampo("Apellido", "apellido")}
        {renderCampo("Correo", "email")}

        <div className="button-group">
          <button className="danger-btn" onClick={handleCerrarSesion} disabled={cargando}>
            Cerrar Sesión
          </button>
          <button className="danger-btn" onClick={handleBorrarCuenta} disabled={cargando}>
            Borrar Cuenta
          </button>
        </div>
      </div>
    </div>
  );
};