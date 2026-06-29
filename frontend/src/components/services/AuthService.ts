import type {
  PayloadLogin,
  PayloadRegistro,
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
 * No debe distinguir entre "usuario no existe" y "cuenta desactivada",
 * para no filtrarle información útil a un atacante.
 */
const ERROR_CREDENCIALES = "Usuario o contraseña incorrectos";

/**
 * Error tipado que lanzan las funciones de este servicio,
 * para que los componentes puedan mostrar el mensaje correcto.
 */
export class ErrorAuth extends Error {}

/**
 * Autentica al usuario contra POST /api/v1/auth/login.
 * @param {PayloadLogin} datos - Email y contraseña.
 * @returns {Promise<RespuestaAuth>} Token, userId, email y roles.
 * @throws {ErrorAuth} Si las credenciales son inválidas o el servidor falla.
 */
const login = async (datos: PayloadLogin): Promise<RespuestaAuth> => {
  const respuesta = await fetch(`${API_URL}/api/v1/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(datos),
  });

  if (respuesta.status === 401) {
    throw new ErrorAuth(ERROR_CREDENCIALES);
  }
  if (!respuesta.ok) {
    throw new ErrorAuth("No se pudo iniciar sesión, intenta más tarde");
  }

  return respuesta.json();
};

/**
 * Registra un nuevo usuario contra POST /api/v1/auth/register.
 * @param {PayloadRegistro} datos - Email, password, nombre y apellido.
 * @returns {Promise<RespuestaAuth>} Token, userId, email y roles (auto-login tras registro).
 * @throws {ErrorAuth} Si el correo ya existe (409) o los datos son inválidos (400).
 */
const register = async (datos: PayloadRegistro): Promise<RespuestaAuth> => {
  const respuesta = await fetch(`${API_URL}/api/v1/auth/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(datos),
  });

  if (respuesta.status === 409) {
    throw new ErrorAuth("Ese correo ya está registrado");
  }
  if (!respuesta.ok) {
    throw new ErrorAuth("No se pudo crear la cuenta, revisa los datos");
  }

  return respuesta.json();
};

/**
 * Cierra la sesión local. No requiere llamar a la API: los JWT son stateless,
 * así que basta con eliminar el token guardado en el navegador.
 */
const logout = (): void => {
  eliminarSesion();
};

/**
 * Construye el header Authorization para requests protegidos.
 * Centralizado aquí para que MiCuentaModal y futuros componentes no repitan esta lógica.
 * @returns {HeadersInit} Headers con Bearer token, o {} si no hay sesión.
 */
const headersAutenticados = (): HeadersInit => {
  const token = obtenerToken();
  return token ? { Authorization: `Bearer ${token}` } : {};
};

export const authService = {
  login,
  register,
  logout,
  headersAutenticados,
  API_URL,
};