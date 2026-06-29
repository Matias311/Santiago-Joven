import type { SesionUsuario } from "../types/Auth";

/**
 * Utilidades para persistir y leer la sesión del usuario autenticado.
 *
 * Se guarda un único objeto serializado (token, userId, email, roles) bajo
 * una sola clave en lugar de múltiples claves sueltas, para que sea fácil
 * de limpiar al cerrar sesión y fácil de extender si se agregan más datos
 * a futuro (ej. fecha de expiración del token).
 *
 * @remarks
 * Este archivo no depende de React: puede ser usado desde componentes,
 * servicios, hooks o interceptores de fetch.
 */

/** Clave bajo la que se guarda la sesión en localStorage. */
const CLAVE_SESION = "santiagojoven_sesion";

/**
 * Guarda la sesión completa del usuario en localStorage.
 * @param sesion - Datos de sesión devueltos por el backend tras login/registro.
 */
export const guardarSesion = (sesion: SesionUsuario): void => {
  localStorage.setItem(CLAVE_SESION, JSON.stringify(sesion));
};

/**
 * Obtiene la sesión guardada en localStorage.
 * Si los datos están corruptos (JSON inválido), los elimina automáticamente.
 * @returns Objeto de sesión, o `null` si no hay sesión o está corrupta.
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
 * Obtiene únicamente el token JWT de la sesión activa.
 * Útil para construir el header `Authorization: Bearer <token>`.
 * @returns Token JWT, o `null` si no hay sesión activa.
 */
export const obtenerToken = (): string | null => {
  return obtenerSesion()?.token ?? null;
};

/**
 * Elimina la sesión guardada en localStorage.
 * Se llama al cerrar sesión para que el usuario quede desautenticado localmente.
 */
export const eliminarSesion = (): void => {
  localStorage.removeItem(CLAVE_SESION);
};

/**
 * Indica si hay una sesión guardada localmente.
 * No verifica si el token sigue siendo válido en el backend:
 * eso se determina cuando la API rechaza el token con un 401.
 * @returns `true` si hay una sesión guardada, `false` si no.
 */
export const estaAutenticado = (): boolean => {
  return obtenerSesion() !== null;
};

/**
 * Indica si el usuario autenticado tiene un rol específico.
 * @param rol - Nombre del rol a verificar (ej. "ADMIN", "MODERATOR").
 * @returns `true` si el usuario tiene ese rol, `false` si no o si no hay sesión.
 */
export const tieneRol = (rol: string): boolean => {
  return obtenerSesion()?.roles.includes(rol) ?? false;
};