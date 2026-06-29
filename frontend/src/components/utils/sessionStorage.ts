import type { SesionUsuario } from "../types/Auth";

/**
 * Utilidades para persistir y leer la sesión del usuario autenticado.
 *
 * Se guarda un único objeto serializado (token, userId, email, roles) bajo
 * una sola clave, en lugar de múltiples claves sueltas, para que sea fácil
 * de limpiar al cerrar sesión y fácil de extender si se agregan más datos
 * a futuro (ej. fecha de expiración del token).
 *
 * Este archivo no depende de React: puede ser usado tanto desde componentes
 * como desde servicios (AuthService, futuros hooks, interceptores de fetch, etc).
 */

const CLAVE_SESION = "santiagojoven_sesion";

/**
 * Guarda la sesión completa del usuario en localStorage.
 * @param sesion - Datos de sesión devueltos por el backend tras login/registro.
 */
export const guardarSesion = (sesion: SesionUsuario): void => {
  localStorage.setItem(CLAVE_SESION, JSON.stringify(sesion));
};

/**
 * Obtiene la sesión guardada, o null si no hay ninguna o está corrupta.
 * @returns {SesionUsuario | null} Objeto de sesión o null.
 */
export const obtenerSesion = (): SesionUsuario | null => {
  const datos = localStorage.getItem(CLAVE_SESION);
  if (!datos) return null;

  try {
    return JSON.parse(datos) as SesionUsuario;
  } catch {
    localStorage.removeItem(CLAVE_SESION);
    return null;
  }
};

/**
 * Obtiene únicamente el token, para usarlo en el header Authorization.
 * @returns {string | null} Token JWT o null si no hay sesión.
 */
export const obtenerToken = (): string | null => {
  return obtenerSesion()?.token ?? null;
};

/**
 * Elimina la sesión guardada (logout).
 */
export const eliminarSesion = (): void => {
  localStorage.removeItem(CLAVE_SESION);
};

/**
 * Indica si hay una sesión guardada, sin verificar si el token sigue siendo válido
 * en el backend (eso lo determina la API cuando se usa el token en un request).
 * @returns {boolean} true si hay una sesión guardada.
 */
export const estaAutenticado = (): boolean => {
  return obtenerSesion() !== null;
};

/**
 * Indica si el usuario actual tiene un rol específico (ej. "ADMIN").
 * @param {string} rol - Nombre del rol a verificar (ej. "ADMIN", "MODERATOR").
 * @returns {boolean} true si el usuario tiene ese rol.
 */
export const tieneRol = (rol: string): boolean => {
  return obtenerSesion()?.roles.includes(rol) ?? false;
};