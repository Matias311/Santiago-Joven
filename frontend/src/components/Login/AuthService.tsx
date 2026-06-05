// Tipos 
export type PayloadLogin = {
  usuario: string;
  password: string;
};

export type PayloadRegistro = {
  correo: string;
  telefono: string;
  password: string;
};

export type PayloadRecuperacion = {
  correo?: string;
  telefono?: string;
};

export type PayloadRestablecimiento = {
  codigo: string;
  nuevaPassword: string;
};

// Servicio 
// Hay que remplazar las variables cuando la base de datos este lista y hacer las comprobaciones.
export const authService = {
  login: async (datos: PayloadLogin): Promise<void> => {
    console.log("LOGIN →", datos);
  },

  register: async (datos: PayloadRegistro): Promise<void> => {
    console.log("REGISTER →", datos);
  },

  recoverByEmail: async (datos: Pick<PayloadRecuperacion, "correo">): Promise<void> => {
    console.log("RECOVER EMAIL →", datos);
  },

  recoverByPhone: async (datos: Pick<PayloadRecuperacion, "telefono">): Promise<void> => {
    console.log("RECOVER PHONE →", datos);
  },

  resetPassword: async (datos: PayloadRestablecimiento): Promise<void> => {
    console.log("RESET →", datos);
  },
};