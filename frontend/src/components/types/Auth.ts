// Tipos relacionados al login, registro y recuperación de cuenta

// Define los cuatro modos posibles del LoginModal
export type ModoAuth =
  | "login"
  | "registro"
  | "recuperar-correo"
  | "restablecer-correo";

export type DatosFormulario = {
  usuario: string;
  password: string;
  correo: string;
  telefono: string;
  codigo: string[]; // Array de 5 dígitos para el OTP
  nuevaPassword: string;
  repetirPassword: string;
};

// Cada campo es opcional: solo existen los campos que tienen error activo
export type ErroresCampo = Partial<Record<keyof DatosFormulario, string>>;

export type PropsLoginModal = {
  isOpen: boolean;
  onClose: () => void;
};

// Payloads del servicio de autenticación
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
  correo: string;
};

export type PayloadRestablecimiento = {
  codigo: string;
  nuevaPassword: string;
};

export type RespuestaAuth = {
  token: string;
};