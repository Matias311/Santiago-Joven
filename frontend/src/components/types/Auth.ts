/**
 * Tipos relacionados a autenticación, alineados 1 a 1 con los schemas
 * reales de la API (LoginRequest, UsuarioRequest, LoginResponse, UsuarioResponse).
 *
 * @remarks
 * Estos tipos deben actualizarse si el equipo de backend cambia los schemas
 * en el OpenAPI. No agregar campos que no existan en la API.
 */

/**
 * Modos de autenticación disponibles en el LoginModal.
 * Controla qué formulario se muestra al usuario.
 */
export type ModoAuth = "login" | "registro" | "recuperar-correo" | "restablecer-correo";

/**
 * Body esperado por POST /api/v1/auth/login.
 */
export type PayloadLogin = {
  email: string;
  password: string;
};

/**
 * Body esperado por POST /api/v1/auth/register.
 * @remarks El campo "teléfono" no está incluido porque la API no lo soporta aún.
 * Cuando el backend lo agregue, se debe añadir aquí también.
 */
export type PayloadRegistro = {
  email: string;
  password: string;
  nombre: string;
  apellido: string;
};

/**
 * Body esperado por PUT /api/v1/usuarios/{id}.
 * Todos los campos son opcionales porque la API permite actualización parcial:
 * solo se envían los campos que el usuario quiere modificar.
 */
export type PayloadActualizarUsuario = {
  email?: string;
  password?: string;
  nombre?: string;
  apellido?: string;
  activo?: boolean;
};

/**
 * Body esperado por POST /api/v1/auth/recuperar.
 * Solicita el envío de un código OTP de 5 dígitos al correo del usuario.
 */
export type PayloadRecuperar = {
  email: string;
};

/**
 * Body esperado por POST /api/v1/auth/restablecer.
 * Valida el código OTP y actualiza la contraseña del usuario.
 */
export type PayloadRestablecer = {
  email: string;
  codigo: string;
  nuevaPassword: string;
};

/**
 * Respuesta devuelta por POST /api/v1/auth/login y POST /api/v1/auth/register.
 * Equivale al schema `LoginResponse` en el OpenAPI.
 */
export type RespuestaAuth = {
  /** Token JWT para autenticar requests posteriores. */
  token: string;
  /** ID único del usuario en la base de datos. */
  userId: string;
  email: string;
  /** Lista de roles asignados al usuario (ej: ["USER"], ["ADMIN"]). */
  roles: string[];
};

/**
 * Sesión del usuario persistida en localStorage.
 * Tiene el mismo shape que RespuestaAuth porque se guarda
 * directamente la respuesta del login/registro.
 */
export type SesionUsuario = RespuestaAuth;

/**
 * Respuesta devuelta por GET /api/v1/usuarios/{id}.
 * Equivale al schema `UsuarioResponse` en el OpenAPI.
 */
export type UsuarioResponse = {
  id: string;
  email: string;
  nombre: string;
  apellido: string;
  /** Indica si la cuenta está activa. Una cuenta inactiva no puede iniciar sesión. */
  activo: boolean;
};

/**
 * Mapa de errores de validación por campo.
 * La clave es el nombre del campo (ej: "email") y el valor es el mensaje de error.
 * Usa `Partial` porque no todos los campos tienen error al mismo tiempo.
 */
export type ErroresCampo = Partial<Record<string, string>>;

/**
 * Props del componente LoginModal.
 */
export type PropsLoginModal = {
  /** Controla si el modal está visible. */
  isOpen: boolean;
  /** Callback para cerrar el modal. */
  onClose: () => void;
  /** Callback que notifica al Navbar que el login fue exitoso, para refrescar el estado de sesión. */
  onLoginExitoso: () => void;
};