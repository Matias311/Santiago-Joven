import { useEffect, useState } from "react";
import type { CartaItem } from "./CartaItem";

// TODO: mover a variable de entorno (ej. import.meta.env.VITE_API_URL)
// cuando se defina la URL de producción del backend.
const API_BASE_URL = "http://localhost:8080";

export interface ContactoProyeccion {
  direccion: string;
  horario: string;
  email: string;
}

export interface DatosProyeccion {
  cursos: CartaItem[];
  proyectosAccion: CartaItem[];
  contacto: ContactoProyeccion;
  /** true mientras se está consultando la API */
  cargando: boolean;
}

/** Forma cruda que devuelve GET /api/v1/cursos-destacados/activos */
interface CursoDestacadoResponse {
  id: string;
  titulo: string;
  descripcion: string;
  eslogan: string;
  objetivo: string;
  categoriaId: string | null;
  imagen: string | null;
  activo: boolean;
  orden: number;
  enlaceInscripcion: string | null;
}

/** Forma cruda que devuelve GET /api/v1/acciones-joven/activas */
interface AccionJovenResponse {
  id: string;
  titulo: string;
  descripcion: string;
  imagen: string | null;
  activo: boolean;
}

/**
 * Mapea un CursoDestacadoResponse de la API al CartaItem que consume
 * ProyeccionCard / Slider.
 *
 * Decisiones tomadas (ver conversación con el equipo):
 * - Layout simple (un solo texto), no la grilla de 4: la API solo trae
 *   `eslogan`, `descripcion` y `objetivo`.
 * - `tema` fijo en "azul" hasta que `categoriaId` venga poblado con datos
 *   reales (hoy llega null).
 * - `iconoMedio` fijo: la API aún no tiene un campo `icono`, solo `imagen`.
 *   TODO: pedir al backend que agregue un campo `icono` (Material Symbols)
 *   en vez de (o además de) `imagen`.
 */
function mapCursoACartaItem(curso: CursoDestacadoResponse): CartaItem {
  const descripcionCompleta = [curso.descripcion, curso.objetivo]
    .filter(Boolean)
    .join(" ");

  return {
    tema: "azul",
    iconoMedio: "school",
    titulo: curso.titulo,
    subtitulo: curso.eslogan,
    descripcion: descripcionCompleta,
    boton: "Ver Detalles",
    // enlaceInscripcion es un link externo (sence.gob.cl, etc.), no una
    // ruta interna; ProyeccionCard ya detecta esto y usa <a target=_blank>.
    ruta: curso.enlaceInscripcion || undefined,
  };
}

/**
 * Mapea un AccionJovenResponse de la API al CartaItem de Acción Joven.
 *
 * - `tema` fijo en "naranja" (es el color que ya tenía esa sección).
 * - `icono`/`iconoMedio` fijos en "campaign": la API no trae ícono.
 * - `ruta` apunta a "#contacto" como destino por defecto del botón, ya que
 *   la API no entrega ningún link para Acción Joven. Ajustar si se define
 *   otro destino.
 */
function mapAccionACartaItem(accion: AccionJovenResponse): CartaItem {
  return {
    tema: "naranja",
    icono: "campaign",
    iconoMedio: "campaign",
    titulo: accion.titulo,
    descripcion: accion.descripcion,
    boton: "Ver Detalles",
    ruta: "#contacto",
  };
}

/**
 * Hook de datos para la página de Proyección.
 *
 * Trae `cursos` y `proyectosAccion` desde la API real (sin manejo de
 * errores ni fallback, por decisión explícita: si la API falla, el fetch
 * rechaza y queda en consola como cualquier error no capturado).
 *
 * `contacto` sigue estático: no existe en el Swagger un endpoint que
 * entregue dirección + horario + email juntos para esta sección.
 */
export function useProyeccion(): DatosProyeccion {
  const [cursos, setCursos] = useState<CartaItem[]>([]);
  const [proyectosAccion, setProyectosAccion] = useState<CartaItem[]>([]);
  const [cargando, setCargando] = useState(true);

  useEffect(() => {
    let vigente = true;

    async function cargarDatos() {
      const [resCursos, resAcciones] = await Promise.all([
        fetch(`${API_BASE_URL}/api/v1/cursos-destacados/activos`),
        fetch(`${API_BASE_URL}/api/v1/acciones-joven/activas`),
      ]);

      const cursosData: CursoDestacadoResponse[] = await resCursos.json();
      const accionesData: AccionJovenResponse[] = await resAcciones.json();

      if (!vigente) return;

      const cursosOrdenados = [...cursosData].sort((a, b) => a.orden - b.orden);

      setCursos(cursosOrdenados.map(mapCursoACartaItem));
      setProyectosAccion(accionesData.map(mapAccionACartaItem));
      setCargando(false);
    }

    cargarDatos();

    return () => {
      vigente = false;
    };
  }, []);

  const contacto: ContactoProyeccion = {
    direccion:
      "Herrera 360, Comuna de Santiago. (Centro Comunitario Santiago en Compañía)",
    horario: "Lunes a jueves [09:00 - 18:00 hrs] - Viernes [09:00 a 17:00 hrs]",
    email: "stgojoven@munistgo.cl",
  };

  return { cursos, proyectosAccion, contacto, cargando };
}
