/**
 * Tipos relacionados a autenticación, alineados 1 a 1 con los schemas
 * reales de la API (LoginRequest, UsuarioRequest, LoginResponse, UsuarioResponse).
 *
 * Importante: estos tipos deben actualizarse si el equipo de backend cambia
 * los schemas en el OpenAPI. No inventar campos que no existan en la API.
 */

/** Modos de autenticación soportados por el LoginModal (controlador). */
export type ModoAuth = "login" | "registro";

/** Body esperado por POST /api/v1/auth/login. */
export type PayloadLogin = {
  email: string;
  password: string;
};

/** Body esperado por POST /api/v1/auth/register. No incluye teléfono: la API no lo soporta aún (ver feature request). */
export type PayloadRegistro = {
  email: string;
  password: string;
  nombre: string;
  apellido: string;
};

/** Body esperado por PUT /api/v1/usuarios/{id}. Todos los campos son opcionales (actualización parcial). */
export type PayloadActualizarUsuario = {
  email?: string;
  password?: string;
  nombre?: string;
  apellido?: string;
  activo?: boolean;
};

/** Respuesta devuelta por login y register (LoginResponse en la API). */
export type RespuestaAuth = {
  token: string;
  userId: string;
  email: string;
  roles: string[];
};

/** Sesión persistida en localStorage; mismo shape que RespuestaAuth. */
export type SesionUsuario = RespuestaAuth;

/** Respuesta devuelta por GET /api/v1/usuarios/{id} (UsuarioResponse en la API). */
export type UsuarioResponse = {
  id: string;
  email: string;
  nombre: string;
  apellido: string;
  activo: boolean;
};

/** Errores de validación por campo, usados para mostrar mensajes bajo cada input. */
export type ErroresCampo = Partial<Record<string, string>>;

export type PropsLoginModal = {
  isOpen: boolean;
  onClose: () => void;
  /** Notifica al padre (Navbar) que el login fue exitoso, para refrescar el estado de sesión. */
  onLoginExitoso: () => void;
};