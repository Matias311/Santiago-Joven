/**
 * Tipos e interfaces compartidas entre componentes.
 * Centraliza las definiciones para evitar duplicación de código.
 * @module types
 */

// ─── Interfaces de inicio.tsx ───────────────────────────────────────────────

/**
 * Representa una tarjeta de contenido reutilizable.
 * Usada en `Card.tsx`, `Slider.tsx` e `Inicio.tsx`.
 */
export interface CartaItem {
  /** Nombre del ícono de Material Symbols (e.g. `"school"`, `"rocket"`). */
  icono?: string;
  /** Color CSS del ícono. */
  iconoColor?: string;
  /** Tamaño CSS del ícono (e.g. `"48px"`). */
  iconoTamaño?: string;
  /** Color o gradiente CSS del fondo del contenedor del ícono. */
  iconoFondo?: string;
  /** Color o gradiente CSS del fondo del botón. */
  botoncolor?: string;
  /** Color CSS del título. */
  tituloColor?: string;
  /** Valor CSS de `box-shadow` para la tarjeta. */
  sliderSombra?: string;
  /** Texto principal de la tarjeta. Requerido. */
  titulo: string;
  /** Descripción o cuerpo de texto de la tarjeta. */
  descripcion?: string;
  /** Texto secundario debajo del título. */
  subtitulo?: string;
  /** Texto del botón de acción. Si no se pasa, el botón no se renderiza. */
  boton?: string;
  /** Clase CSS adicional para el contenedor raíz de la tarjeta. */
  clase?: string;
}

/**
 * Representa un ítem de la sección de conexión (actividades o talleres).
 * Usada en `Inicio.tsx`.
 */
export interface ConexionItem {
  /** Nombre del ícono de Material Symbols. */
  icono: string;
  /** Texto descriptivo del ítem. */
  texto: string;
}

/**
 * Estructura principal de datos de la página de inicio.
 * Agrupa toda la información que se renderiza en `Inicio.tsx`.
 */
export interface Info {
  /** Sección de apoyo, dividida en asesorías y preuniversitario. */
  apoyo: {
    asesorias: CartaItem[];
    preuniversitario: CartaItem[];
  };
  /** Listado de tarjetas de cursos disponibles. */
  cursos: CartaItem[];
  /** Listado de tarjetas de acción joven. */
  accion: CartaItem[];
  /** Listado de tarjetas de programas. */
  programas: CartaItem[];
  /** Listado de tarjetas de salud. */
  salud: CartaItem[];
  /** Sección de conexión, dividida en actividades y talleres. */
  conexion: {
    actividades: ConexionItem[];
    talleres: ConexionItem[];
  };
  /** Información de contacto de la organización. */
  contacto: {
    /** Dirección física. */
    direccion: string;
    /** Horario de atención. */
    horario: string;
    /** Correo electrónico de contacto. */
    email: string;
  };
}