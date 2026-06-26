import type {
  PayloadLogin,
  PayloadRegistro,
  PayloadRecuperacion,
  PayloadRestablecimiento,
  RespuestaAuth,
} from "../types/Auth";

/**
 * Funciones para manejar el JWT 
 * Clave compartida entre todas las funciones para evitar typos
 */
const CLAVE_TOKEN = "santiagojoven_token";

export const guardarToken = (token: string) => {
  localStorage.setItem(CLAVE_TOKEN, token);
};

export const obtenerToken = (): string | null => {
  return localStorage.getItem(CLAVE_TOKEN);
};

export const eliminarToken = () => {
  localStorage.removeItem(CLAVE_TOKEN);
};

// Devuelve true si hay un token guardado, sin verificar su validez
export const estaAutenticado = (): boolean => {
  return !!obtenerToken();
};

/**
 * Servicio de autenticación (placeholder)
 * Todas las funciones simulan un si
 * Reemplazar con el backend cuando este listo
 */
export const authService = {
  login: async (datos: PayloadLogin): Promise<RespuestaAuth> => {
    console.log("LOGIN →", datos);
    return { token: "token-simulado-123" };
  },

  register: async (datos: PayloadRegistro): Promise<RespuestaAuth> => {
    console.log("REGISTER →", datos);
    return { token: "token-simulado-123" };
  },

  recoverByEmail: async (datos: PayloadRecuperacion): Promise<void> => {
    console.log("RECOVER EMAIL →", datos);
  },

  resetPassword: async (datos: PayloadRestablecimiento): Promise<void> => {
    console.log("RESET →", datos);
  },

  logout: () => {
    eliminarToken();
  },
};