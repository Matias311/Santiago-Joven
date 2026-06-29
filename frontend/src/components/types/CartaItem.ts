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
  /** Ruta de navegación interna al hacer clic en el botón. */
  link?: string;
}
