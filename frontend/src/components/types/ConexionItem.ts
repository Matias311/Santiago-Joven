/**
 * Representa un ítem de la sección de conexión (actividades o talleres).
 * Usada en `Inicio.tsx`.
 */
export interface ConexionItem {
  /** Nombre del ícono de Material Symbols. */
  icono: string;
  /** Texto descriptivo del ítem. */
  texto: string;
  /** Descripcion extensa */
  descripcion?: string;
  /** URL formulario. */
  url?: string;
  /** Imagen de la carta. */
  imagen?: string;
  /** Fecha */
  date?: string;
  /** Direccion */
  lugar?: string;
  /** Ciudad */
  ciudad?: string;
  /** Cupos disponibles. */
  cupos_disponibles?: number;
  /** Cupos totales. */
  cupos?: number;
}
