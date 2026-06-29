import { useEffect, useState } from "react";
import { authService } from "../services/AuthService";
import { obtenerSesion, eliminarSesion } from "../utils/sessionStorage";
import type { UsuarioResponse, ErroresCampo } from "../types/Auth";
import "./LoginModal.css";

type Props = {
  isOpen: boolean;
  onClose: () => void;
  /** Notifica al padre (Navbar) que la sesión terminó, para refrescar su estado. */
  onCerrarSesion: () => void;
};

/**
 * Modal de cuenta para usuarios autenticados.
 *
 * Este componente no verifica si el usuario está autenticado: esa
 * responsabilidad es del Navbar, que decide si abrir este modal o el
 * de login según el estado de sesión (ver Navbar.tsx).
 *
 * @component
 * @param {Props} props - Estado de apertura, cierre y callback de cierre de sesión.
 * @returns {JSX.Element | null} Modal de cuenta, o null si está cerrado.
 */
export const MiCuentaModal = ({ isOpen, onClose, onCerrarSesion }: Props) => {
  const [usuario, setUsuario] = useState<UsuarioResponse | null>(null);
  const [editando, setEditando] = useState<"nombre" | "apellido" | "email" | null>(null);
  const [valorEdicion, setValorEdicion] = useState("");
  const [errores, setErrores] = useState<ErroresCampo>({});
  const [cargando, setCargando] = useState(false);
  const [errorGeneral, setErrorGeneral] = useState("");

  // Carga los datos reales del usuario cada vez que se abre el modal
  useEffect(() => {
    if (!isOpen) return;
    cargarUsuario();
  }, [isOpen]);

  /** Pide los datos del usuario actual a GET /api/v1/usuarios/{id}. */
  const cargarUsuario = async () => {
    const sesion = obtenerSesion();
    if (!sesion) return;

    setCargando(true);
    setErrorGeneral("");
    try {
      const respuesta = await fetch(`${authService.API_URL}/api/v1/usuarios/${sesion.userId}`, {
        headers: authService.headersAutenticados(),
      });
      if (!respuesta.ok) throw new Error();
      setUsuario(await respuesta.json());
    } catch {
      setErrorGeneral("No se pudieron cargar los datos de tu cuenta");
    } finally {
      setCargando(false);
    }
  };

  if (!isOpen) return null;

  /** Abre el modo de edición para un campo específico, precargando su valor actual. */
  const handleEditar = (campo: "nombre" | "apellido" | "email") => {
    setEditando(campo);
    setValorEdicion(usuario?.[campo] ?? "");
    setErrores({});
  };

  /** Envía el campo editado a PUT /api/v1/usuarios/{id}. */
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

  /** Elimina la sesión local y notifica al padre para que actualice el estado de auth. */
  const handleCerrarSesion = () => {
    authService.logout();
    onCerrarSesion();
    onClose();
  };

  /** Elimina la cuenta del usuario actual vía DELETE /api/v1/usuarios/{id}. */
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

  /** Renderiza una fila de campo: vista normal con botón de editar, o input editable. */
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
        <div className="brand">Santiago Joven</div>
        <h2>Mi Cuenta</h2>

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

        <button className="close" onClick={onClose}>✕</button>
      </div>
    </div>
  );
};