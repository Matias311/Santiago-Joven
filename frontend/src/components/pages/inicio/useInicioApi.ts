import type { Dispatch, SetStateAction } from "react";
import type { CartaItem } from "../../types/CartaItem";
import type { ConexionItem } from "../../types/ConexionItem";
import type { CalendarEvent } from "../../cartas/CalendarCard";
import type { Survey } from "../../cartas/SurveyCard";
import { obtenerToken } from "../../utils/sessionStorage";

const API_BASE =
  (import.meta.env.VITE_API_URL?.replace(/\/$/, "") ?? "") + "/api/v1";

// Retorna headers con JWT. Lanza error si no hay sesión activa.
const getAuthHeader = () => {
  const token = obtenerToken();
  if (!token) throw new Error("Debes iniciar sesión primero.");
  return {
    "Content-Type": "application/json",
    Authorization: `Bearer ${token}`,
  };
};

// IDs de la BD mapeados por título, para poder editar/eliminar por ID.
interface IdsAPI {
  asesorias: Record<string, string>;
  cursos: Record<string, string>;
  acciones: Record<string, string>;
  programas: Record<string, string>;
  salud: Record<string, string>;
  actividades: Record<string, string>;
  encuestas: Record<string, string>;
}

interface ApiHelpersProps {
  idsAPI: IdsAPI;
  updateIdsAPI: (seccion: keyof IdsAPI, titulo: string, id: string) => void;

  // Mapas de categorías para endpoints que exigen categoriaId
  categoriasPorTipo: Record<string, string>; // tipo (CURSO, ACTIVIDAD…) -> id
  categoriasPorNombre: Record<string, string>; // nombre ("Cursos"…) -> id

  asesoriasState: CartaItem[];
  setAsesoriasState: Dispatch<SetStateAction<CartaItem[]>>;
  cursosState: CartaItem[];
  setCursosState: Dispatch<SetStateAction<CartaItem[]>>;
  accionState: CartaItem[];
  setAccionState: Dispatch<SetStateAction<CartaItem[]>>;
  programasState: CartaItem[];
  setProgramasState: Dispatch<SetStateAction<CartaItem[]>>;
  saludState: CartaItem[];
  setSaludState: Dispatch<SetStateAction<CartaItem[]>>;
  actividadesState: ConexionItem[];
  setActividadesState: Dispatch<SetStateAction<ConexionItem[]>>;
  talleresState: ConexionItem[];
  setTalleresState: Dispatch<SetStateAction<ConexionItem[]>>;
  calendarioState: CalendarEvent[];
  setCalendarioState: Dispatch<SetStateAction<CalendarEvent[]>>;
  encuestaState: Survey[];
  setEncuestaState: Dispatch<SetStateAction<Survey[]>>;

  updateItem: <T>(
    list: T[],
    setList: Dispatch<SetStateAction<T[]>>,
    index: number,
    updated: T,
  ) => void;
  deleteItem: <T>(
    list: T[],
    setList: Dispatch<SetStateAction<T[]>>,
    index: number,
  ) => void;
}

// Lanza error legible si la API responde con código de error
async function checkResponse(res: Response, contexto: string) {
  if (!res.ok) {
    const texto = await res.text();
    throw new Error(`${contexto} falló (${res.status}): ${texto}`);
  }
}

export function inicioApi({
  idsAPI,
  updateIdsAPI,
  categoriasPorTipo,
  categoriasPorNombre,
  asesoriasState,
  setAsesoriasState,
  cursosState,
  setCursosState,
  accionState,
  setAccionState,
  programasState,
  setProgramasState,
  saludState,
  setSaludState,
  setActividadesState,
  setTalleresState,
  calendarioState,
  setCalendarioState,
  encuestaState,
  setEncuestaState,
  updateItem,
  deleteItem,
}: ApiHelpersProps) {
  // ── Asesorías ──────────────────────────────────────────────────────────────
  // Requiere categoriaId de tipo ASESORIA

  const createAsesoria = async (newItem: CartaItem) => {
    const categoriaId = categoriasPorTipo["ASESORIA"];
    if (!categoriaId) throw new Error("No hay categoría de tipo ASESORIA.");

    const res = await fetch(`${API_BASE}/asesorias`, {
      method: "POST",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: newItem.titulo,
        definicion: newItem.descripcion,
        objetivos: "Insertar datos",
        metodologia: "Insertar datos",
        categoriaId,
      }),
    });
    await checkResponse(res, "Crear asesoría");
    const data = await res.json();
    if (data?.id) updateIdsAPI("asesorias", newItem.titulo, data.id);
    setAsesoriasState((prev) => [...prev, newItem]);
  };

  const saveAsesoria = async (updated: CartaItem, index: number) => {
    const tituloAnterior = asesoriasState[index]?.titulo ?? "";
    const id = idsAPI.asesorias[tituloAnterior];
    if (!id) return;

    const res = await fetch(`${API_BASE}/asesorias/${id}`, {
      method: "PUT",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: updated.titulo,
        definicion: updated.descripcion,
      }),
    });
    await checkResponse(res, "Actualizar asesoría");
    if (updated.titulo !== tituloAnterior)
      updateIdsAPI("asesorias", updated.titulo, id);
    updateItem(asesoriasState, setAsesoriasState, index, updated);
  };

  const deleteAsesoria = async (index: number) => {
    const id = idsAPI.asesorias[asesoriasState[index]?.titulo ?? ""];
    if (!id) return;
    const res = await fetch(`${API_BASE}/asesorias/${id}`, {
      method: "DELETE",
      headers: getAuthHeader(),
    });
    await checkResponse(res, "Eliminar asesoría");
    deleteItem(asesoriasState, setAsesoriasState, index);
  };

  // ── Cursos ─────────────────────────────────────────────────────────────────
  // Busca categoriaId por nombre "Cursos" (más flexible que buscar por tipo)

  const createCurso = async (newItem: CartaItem) => {
    const res = await fetch(`${API_BASE}/cursos-destacados`, {
      method: "POST",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: newItem.titulo,
        descripcion: newItem.descripcion,
        objetivo: "Insertar datos",
        eslogan: "Insertar datos",
        categoriaId: categoriasPorNombre["Cursos"] ?? null,
      }),
    });
    await checkResponse(res, "Crear curso");
    const data = await res.json();
    if (data?.id) updateIdsAPI("cursos", newItem.titulo, data.id);
    setCursosState((prev) => [...prev, newItem]);
  };

  const saveCurso = async (updated: CartaItem, index: number) => {
    const tituloAnterior = cursosState[index]?.titulo ?? "";
    const id = idsAPI.cursos[tituloAnterior];
    if (!id) return;
    const res = await fetch(`${API_BASE}/cursos-destacados/${id}`, {
      method: "PUT",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: updated.titulo,
        descripcion: updated.descripcion,
      }),
    });
    await checkResponse(res, "Actualizar curso");
    if (updated.titulo !== tituloAnterior)
      updateIdsAPI("cursos", updated.titulo, id);
    updateItem(cursosState, setCursosState, index, updated);
  };

  const deleteCurso = async (index: number) => {
    const id = idsAPI.cursos[cursosState[index]?.titulo ?? ""];
    if (!id) return;
    const res = await fetch(`${API_BASE}/cursos-destacados/${id}`, {
      method: "DELETE",
      headers: getAuthHeader(),
    });
    await checkResponse(res, "Eliminar curso");
    deleteItem(cursosState, setCursosState, index);
  };

  // ── Acción Joven ───────────────────────────────────────────────────────────
  // No requiere categoriaId

  const createAccion = async (newItem: CartaItem) => {
    const res = await fetch(`${API_BASE}/acciones-joven`, {
      method: "POST",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: newItem.titulo,
        descripcion: newItem.descripcion,
      }),
    });
    await checkResponse(res, "Crear acción");
    const data = await res.json();
    if (data?.id) updateIdsAPI("acciones", newItem.titulo, data.id);
    setAccionState((prev) => [...prev, newItem]);
  };

  const saveAccion = async (updated: CartaItem, index: number) => {
    const tituloAnterior = accionState[index]?.titulo ?? "";
    const id = idsAPI.acciones[tituloAnterior];
    if (!id) return;
    const res = await fetch(`${API_BASE}/acciones-joven/${id}`, {
      method: "PUT",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: updated.titulo,
        descripcion: updated.descripcion,
      }),
    });
    await checkResponse(res, "Actualizar acción");
    if (updated.titulo !== tituloAnterior)
      updateIdsAPI("acciones", updated.titulo, id);
    updateItem(accionState, setAccionState, index, updated);
  };

  const deleteAccion = async (index: number) => {
    const id = idsAPI.acciones[accionState[index]?.titulo ?? ""];
    if (!id) return;
    const res = await fetch(`${API_BASE}/acciones-joven/${id}`, {
      method: "DELETE",
      headers: getAuthHeader(),
    });
    await checkResponse(res, "Eliminar acción");
    deleteItem(accionState, setAccionState, index);
  };

  // ── Programas ──────────────────────────────────────────────────────────────
  // Requiere definicion, objetivos, metodologia

  const createPrograma = async (newItem: CartaItem) => {
    const res = await fetch(`${API_BASE}/programas`, {
      method: "POST",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: newItem.titulo,
        descripcion: newItem.descripcion,
        definicion: "Insertar datos",
        objetivos: "Insertar datos",
        metodologia: "Insertar datos",
      }),
    });
    await checkResponse(res, "Crear programa");
    const data = await res.json();
    if (data?.id) updateIdsAPI("programas", newItem.titulo, data.id);
    setProgramasState((prev) => [...prev, newItem]);
  };

  const savePrograma = async (updated: CartaItem, index: number) => {
    const tituloAnterior = programasState[index]?.titulo ?? "";
    const id = idsAPI.programas[tituloAnterior];
    if (!id) return;
    const res = await fetch(`${API_BASE}/programas/${id}`, {
      method: "PUT",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: updated.titulo,
        descripcion: updated.descripcion,
      }),
    });
    await checkResponse(res, "Actualizar programa");
    if (updated.titulo !== tituloAnterior)
      updateIdsAPI("programas", updated.titulo, id);
    updateItem(programasState, setProgramasState, index, updated);
  };

  const deletePrograma = async (index: number) => {
    const id = idsAPI.programas[programasState[index]?.titulo ?? ""];
    if (!id) return;
    const res = await fetch(`${API_BASE}/programas/${id}`, {
      method: "DELETE",
      headers: getAuthHeader(),
    });
    await checkResponse(res, "Eliminar programa");
    deleteItem(programasState, setProgramasState, index);
  };

  // ── Salud Mental ───────────────────────────────────────────────────────────
  // No requiere categoriaId

  const createSalud = async (newItem: CartaItem) => {
    const res = await fetch(`${API_BASE}/salud-mental`, {
      method: "POST",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: newItem.titulo,
        descripcion: newItem.descripcion,
      }),
    });
    await checkResponse(res, "Crear recurso de salud");
    const data = await res.json();
    if (data?.id) updateIdsAPI("salud", newItem.titulo, data.id);
    setSaludState((prev) => [...prev, newItem]);
  };

  const saveSalud = async (updated: CartaItem, index: number) => {
    const tituloAnterior = saludState[index]?.titulo ?? "";
    const id = idsAPI.salud[tituloAnterior];
    if (!id) return;
    const res = await fetch(`${API_BASE}/salud-mental/${id}`, {
      method: "PUT",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: updated.titulo,
        descripcion: updated.descripcion,
      }),
    });
    await checkResponse(res, "Actualizar recurso de salud");
    if (updated.titulo !== tituloAnterior)
      updateIdsAPI("salud", updated.titulo, id);
    updateItem(saludState, setSaludState, index, updated);
  };

  const deleteSalud = async (index: number) => {
    const id = idsAPI.salud[saludState[index]?.titulo ?? ""];
    if (!id) return;
    const res = await fetch(`${API_BASE}/salud-mental/${id}`, {
      method: "DELETE",
      headers: getAuthHeader(),
    });
    await checkResponse(res, "Eliminar recurso de salud");
    deleteItem(saludState, setSaludState, index);
  };

  // ── Actividades y Talleres ─────────────────────────────────────────────────
  // Requiere categoriaId de tipo ACTIVIDAD y fechaHora.
  // El parámetro "destino" decide si la carta va a actividades o talleres.

  const createActividad = async (
    newItem: ConexionItem,
    destino: "actividades" | "talleres" = "actividades",
  ) => {
    const categoriaId = categoriasPorTipo["ACTIVIDAD"];
    if (!categoriaId) throw new Error("No hay categoría de tipo ACTIVIDAD.");

    const res = await fetch(`${API_BASE}/actividades-talleres`, {
      method: "POST",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: newItem.texto,
        descripcion: "Insertar datos",
        categoriaId,
        fechaHora: new Date().toISOString(),
      }),
    });
    await checkResponse(res, "Crear actividad");
    const data = await res.json();
    if (data?.id) updateIdsAPI("actividades", newItem.texto, data.id);

    if (destino === "talleres") {
      setTalleresState((prev) => [...prev, newItem]);
    } else {
      setActividadesState((prev) => [...prev, newItem]);
    }
  };

  const saveActividad = async (
    updated: ConexionItem,
    titulo: string,
    index: number,
    isTaller: boolean = false,
  ) => {
    const id = idsAPI.actividades[titulo];
    if (!id) return;

    const res = await fetch(`${API_BASE}/actividades-talleres/${id}`, {
      method: "PUT",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: updated.texto,
        descripcion: updated.descripcion ?? "Insertar datos",
        fechaHora: updated.date
          ? updated.date + "T00:00:00"
          : new Date().toISOString(),
        imagen: updated.imagen ?? "",
        enlaceInscripcion: updated.url ?? "",
        cantidadMaximaParticipantes: updated.cupos ?? 0,
      }),
    });
    await checkResponse(res, "Actualizar actividad");
    if (updated.texto !== titulo)
      updateIdsAPI("actividades", updated.texto, id);

    if (isTaller) {
      setTalleresState((prev) => {
        const l = [...prev];
        l[index] = updated;
        return l;
      });
    } else {
      setActividadesState((prev) => {
        const l = [...prev];
        l[index] = updated;
        return l;
      });
    }
  };

  const deleteActividad = async (titulo: string) => {
    const id = idsAPI.actividades[titulo];
    if (!id) return;
    const res = await fetch(`${API_BASE}/actividades-talleres/${id}`, {
      method: "DELETE",
      headers: getAuthHeader(),
    });
    await checkResponse(res, "Eliminar actividad");
  };

  // ── Calendario ─────────────────────────────────────────────────────────────
  // Usa el mismo endpoint que actividades; requiere categoriaId y fechaHora

  const createCalendario = async (newItem: CalendarEvent) => {
    const categoriaId = categoriasPorTipo["ACTIVIDAD"];
    if (!categoriaId) throw new Error("No hay categoría de tipo ACTIVIDAD.");

    const res = await fetch(`${API_BASE}/actividades-talleres`, {
      method: "POST",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: newItem.title,
        fechaHora: newItem.date + "T00:00:00",
        descripcion: "Insertar datos",
        categoriaId,
      }),
    });
    await checkResponse(res, "Crear evento de calendario");
    const data = await res.json();
    if (data?.id) updateIdsAPI("actividades", newItem.title, data.id);
    setCalendarioState((prev) => [...prev, newItem]);
  };

  const saveCalendario = async (updated: CalendarEvent, index: number) => {
    const tituloAnterior = calendarioState[index]?.title ?? "";
    const id = idsAPI.actividades[tituloAnterior];
    if (!id) return;
    const res = await fetch(`${API_BASE}/actividades-talleres/${id}`, {
      method: "PUT",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: updated.title,
        fechaHora: updated.date + "T00:00:00",
      }),
    });
    await checkResponse(res, "Actualizar evento de calendario");
    if (updated.title !== tituloAnterior)
      updateIdsAPI("actividades", updated.title, id);
    updateItem(calendarioState, setCalendarioState, index, updated);
  };

  const deleteCalendario = async (index: number) => {
    const id = idsAPI.actividades[calendarioState[index]?.title ?? ""];
    if (!id) return;
    const res = await fetch(`${API_BASE}/actividades-talleres/${id}`, {
      method: "DELETE",
      headers: getAuthHeader(),
    });
    await checkResponse(res, "Eliminar evento de calendario");
    deleteItem(calendarioState, setCalendarioState, index);
  };

  // ── Encuestas (Tu Contribución Cuenta) ────────────────────────────────────
  // Requiere titulo, descripcion y linkGoogleForms

  const createEncuesta = async (newItem: Survey) => {
    const res = await fetch(`${API_BASE}/tu-contribucion-cuenta`, {
      method: "POST",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: newItem.title,
        descripcion: "Insertar datos",
        linkGoogleForms: newItem.url || "Insertar datos",
      }),
    });
    await checkResponse(res, "Crear encuesta");
    const data = await res.json();
    if (data?.id) updateIdsAPI("encuestas", newItem.title, data.id);
    setEncuestaState((prev) => [
      ...prev,
      { ...newItem, id: data?.id ? prev.length + 1 : Date.now() },
    ]);
  };

  const saveEncuesta = async (updated: Survey, index: number) => {
    const tituloAnterior = encuestaState[index]?.title ?? "";
    const id = idsAPI.encuestas?.[tituloAnterior];
    if (!id) return;
    const res = await fetch(`${API_BASE}/tu-contribucion-cuenta/${id}`, {
      method: "PUT",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: updated.title,
        linkGoogleForms: updated.url,
      }),
    });
    await checkResponse(res, "Actualizar encuesta");
    if (updated.title !== tituloAnterior)
      updateIdsAPI("encuestas", updated.title, id);
    updateItem(encuestaState, setEncuestaState, index, updated);
  };

  const deleteEncuesta = async (index: number) => {
    const id = idsAPI.encuestas?.[encuestaState[index]?.title ?? ""];
    if (!id) return;
    const res = await fetch(`${API_BASE}/tu-contribucion-cuenta/${id}`, {
      method: "DELETE",
      headers: getAuthHeader(),
    });
    await checkResponse(res, "Eliminar encuesta");
    deleteItem(encuestaState, setEncuestaState, index);
  };

  return {
    createAsesoria,
    saveAsesoria,
    deleteAsesoria,
    createCurso,
    saveCurso,
    deleteCurso,
    createAccion,
    saveAccion,
    deleteAccion,
    createPrograma,
    savePrograma,
    deletePrograma,
    createSalud,
    saveSalud,
    deleteSalud,
    createActividad,
    saveActividad,
    deleteActividad,
    createCalendario,
    saveCalendario,
    deleteCalendario,
    createEncuesta,
    saveEncuesta,
    deleteEncuesta,
  };
}
