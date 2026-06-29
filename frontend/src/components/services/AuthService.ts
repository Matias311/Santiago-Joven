import type {
  PayloadLogin,
  PayloadRegistro,
  PayloadRecuperar,
  PayloadRestablecer,
  RespuestaAuth,
} from "../types/Auth";
import { obtenerToken, eliminarSesion } from "../utils/sessionStorage";

/**
 * URL base de la API. Se puede sobrescribir con una variable de entorno
 * (VITE_API_URL) para apuntar a staging/producción sin tocar código.
 */
const API_URL = import.meta.env.VITE_API_URL ?? "http://localhost:8080";

/**
 * Mensaje genérico que se muestra ante credenciales inválidas.
 * No distingue entre "usuario no existe" y "cuenta desactivada"
 * para no filtrarle información útil a un atacante.
 */
const ERROR_CREDENCIALES = "Usuario o contraseña incorrectos";

/**
 * Error tipado que lanzan las funciones de este servicio.
 * Permite que los componentes distingan errores de autenticación
 * de errores inesperados del sistema.
 */
export class ErrorAuth extends Error {}

/**
 * Autentica al usuario contra POST /api/v1/auth/login.
 * @param datos - Email y contraseña del usuario.
 * @returns Token JWT, userId, email y roles del usuario autenticado.
 * @throws {ErrorAuth} Si las credenciales son inválidas (401) o el servidor falla.
 */
const login = async (datos: PayloadLogin): Promise<RespuestaAuth> => {
  const respuesta = await fetch(`${API_URL}api/v1/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(datos),
  });

  if (respuesta.status === 401) throw new ErrorAuth(ERROR_CREDENCIALES);
  if (!respuesta.ok) throw new ErrorAuth("No se pudo iniciar sesión, intenta más tarde");

  return respuesta.json();
};

/**
 * Registra un nuevo usuario contra POST /api/v1/auth/register.
 * Tras el registro exitoso, la API devuelve un token (auto-login).
 * @param datos - Email, password, nombre y apellido del nuevo usuario.
 * @returns Token JWT, userId, email y roles del usuario recién creado.
 * @throws {ErrorAuth} Si el correo ya existe (409) o los datos son inválidos (400).
 */
const register = async (datos: PayloadRegistro): Promise<RespuestaAuth> => {
  const respuesta = await fetch(`${API_URL}api/v1/auth/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(datos),
  });

  if (respuesta.status === 409) throw new ErrorAuth("Ese correo ya está registrado");
  if (!respuesta.ok) throw new ErrorAuth("No se pudo crear la cuenta, revisa los datos");

  return respuesta.json();
};

/**
 * Solicita el envío de un código OTP de 5 dígitos al correo del usuario.
 * Llama a POST /api/v1/auth/recuperar.
 * Siempre retorna 200 aunque el correo no exista, para evitar enumeración de usuarios.
 * @param datos - Correo del usuario que quiere recuperar su contraseña.
 * @throws {ErrorAuth} Si el servidor falla.
 */
const recuperar = async (datos: PayloadRecuperar): Promise<void> => {
  const respuesta = await fetch(`${API_URL}api/v1/auth/recuperar`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(datos),
  });
  if (!respuesta.ok) throw new ErrorAuth("No se pudo enviar el código, intenta más tarde");
};

/**
 * Restablece la contraseña del usuario usando el código OTP recibido por correo.
 * Llama a POST /api/v1/auth/restablecer.
 * @param datos - Correo, código OTP de 5 dígitos y nueva contraseña.
 * @throws {ErrorAuth} Si el código es inválido o expiró.
 */
const restablecer = async (datos: PayloadRestablecer): Promise<void> => {
  const respuesta = await fetch(`${API_URL}api/v1/auth/restablecer`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(datos),
  });
  if (!respuesta.ok) throw new ErrorAuth("Código inválido o expirado");
};

/**
 * Cierra la sesión local eliminando el token del localStorage.
 * No requiere llamar a la API porque los JWT son stateless:
 * basta con que el cliente deje de enviar el token.
 */
const logout = (): void => {
  eliminarSesion();
};

/**
 * Construye el header `Authorization: Bearer <token>` para requests protegidos.
 * Centralizado aquí para que los componentes no repitan esta lógica.
 * @returns Headers con el token, o un objeto vacío si no hay sesión activa.
 */
const headersAutenticados = (): HeadersInit => {
  const token = obtenerToken();
  return token ? { Authorization: `Bearer ${token}` } : {};
};

export const authService = {
  login,
  register,
  recuperar,
  restablecer,
  logout,
  headersAutenticados,
  API_URL,
};