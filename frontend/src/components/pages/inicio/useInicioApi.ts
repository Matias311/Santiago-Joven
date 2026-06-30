import type { Dispatch, SetStateAction } from "react";
import type { CartaItem } from "../../types/CartaItem";
import type { ConexionItem } from "../../types/ConexionItem";
import type { CalendarEvent } from "../../cartas/CalendarCard";
import { obtenerToken } from "../../utils/sessionStorage";

const API_BASE =
  (import.meta.env.VITE_API_URL?.replace(/\/$/, "") ?? "") + "/api/v1";

const getAuthHeader = () => {
  const token = obtenerToken();

  if (!token) {
    throw new Error("Debes iniciar sesión primero.");
  }

  return {
    "Content-Type": "application/json",
    Authorization: `Bearer ${token}`,
  };
};

interface IdsAPI {
  asesorias: Record<string, string>;
  cursos: Record<string, string>;
  acciones: Record<string, string>;
  programas: Record<string, string>;
  salud: Record<string, string>;
  actividades: Record<string, string>;
}

interface ApiHelpersProps {
  idsAPI: IdsAPI;

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

  calendarioState: CalendarEvent[];
  setCalendarioState: Dispatch<SetStateAction<CalendarEvent[]>>;

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

export function inicioApi({
  idsAPI,

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

  calendarioState,
  setCalendarioState,

  updateItem,
  deleteItem,
}: ApiHelpersProps) {
  // ---------------- Asesorías ----------------

  const saveAsesoria = async (updated: CartaItem, index: number) => {
    const id = idsAPI.asesorias[asesoriasState[index]?.titulo ?? ""];
    if (!id) return;

    await fetch(`${API_BASE}/asesorias/${id}`, {
      method: "PUT",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: updated.titulo,
        definicion: updated.descripcion,
      }),
    });

    updateItem(asesoriasState, setAsesoriasState, index, updated);
  };

  const deleteAsesoria = async (index: number) => {
    const id = idsAPI.asesorias[asesoriasState[index]?.titulo ?? ""];
    if (!id) return;

    await fetch(`${API_BASE}/asesorias/${id}`, {
      method: "DELETE",
      headers: getAuthHeader(),
    });

    deleteItem(asesoriasState, setAsesoriasState, index);
  };

  // ---------------- Cursos ----------------

  const saveCurso = async (updated: CartaItem, index: number) => {
    const id = idsAPI.cursos[cursosState[index]?.titulo ?? ""];
    if (!id) return;

    await fetch(`${API_BASE}/cursos-destacados/${id}`, {
      method: "PUT",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: updated.titulo,
        descripcion: updated.descripcion,
      }),
    });

    updateItem(cursosState, setCursosState, index, updated);
  };

  const deleteCurso = async (index: number) => {
    const id = idsAPI.cursos[cursosState[index]?.titulo ?? ""];
    if (!id) return;

    await fetch(`${API_BASE}/cursos-destacados/${id}`, {
      method: "DELETE",
      headers: getAuthHeader(),
    });

    deleteItem(cursosState, setCursosState, index);
  };

  // ---------------- Acción ----------------

  const saveAccion = async (updated: CartaItem, index: number) => {
    const id = idsAPI.acciones[accionState[index]?.titulo ?? ""];
    if (!id) return;

    await fetch(`${API_BASE}/acciones-joven/${id}`, {
      method: "PUT",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: updated.titulo,
        descripcion: updated.descripcion,
      }),
    });

    updateItem(accionState, setAccionState, index, updated);
  };

  const deleteAccion = async (index: number) => {
    const id = idsAPI.acciones[accionState[index]?.titulo ?? ""];
    if (!id) return;

    await fetch(`${API_BASE}/acciones-joven/${id}`, {
      method: "DELETE",
      headers: getAuthHeader(),
    });

    deleteItem(accionState, setAccionState, index);
  };

  // ---------------- Programas ----------------

  const savePrograma = async (updated: CartaItem, index: number) => {
    const id = idsAPI.programas[programasState[index]?.titulo ?? ""];
    if (!id) return;

    await fetch(`${API_BASE}/programas/${id}`, {
      method: "PUT",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: updated.titulo,
        descripcion: updated.descripcion,
      }),
    });

    updateItem(programasState, setProgramasState, index, updated);
  };

  const deletePrograma = async (index: number) => {
    const id = idsAPI.programas[programasState[index]?.titulo ?? ""];
    if (!id) return;

    await fetch(`${API_BASE}/programas/${id}`, {
      method: "DELETE",
      headers: getAuthHeader(),
    });

    deleteItem(programasState, setProgramasState, index);
  };

  // ---------------- Salud ----------------

  const saveSalud = async (updated: CartaItem, index: number) => {
    const id = idsAPI.salud[saludState[index]?.titulo ?? ""];
    if (!id) return;

    await fetch(`${API_BASE}/salud-mental/${id}`, {
      method: "PUT",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: updated.titulo,
        descripcion: updated.descripcion,
      }),
    });

    updateItem(saludState, setSaludState, index, updated);
  };

  const deleteSalud = async (index: number) => {
    const id = idsAPI.salud[saludState[index]?.titulo ?? ""];
    if (!id) return;

    await fetch(`${API_BASE}/salud-mental/${id}`, {
      method: "DELETE",
      headers: getAuthHeader(),
    });

    deleteItem(saludState, setSaludState, index);
  };

  // ---------------- Actividades ----------------

  const saveActividad = async (updated: ConexionItem, titulo: string) => {
    const id = idsAPI.actividades[titulo];
    if (!id) return;

    await fetch(`${API_BASE}/actividades-talleres/${id}`, {
      method: "PUT",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: updated.texto,
      }),
    });
  };

  const deleteActividad = async (titulo: string) => {
    const id = idsAPI.actividades[titulo];
    if (!id) return;

    await fetch(`${API_BASE}/actividades-talleres/${id}`, {
      method: "DELETE",
      headers: getAuthHeader(),
    });
  };

  // ---------------- Calendario ----------------

  const saveCalendario = async (updated: CalendarEvent, index: number) => {
    const id = idsAPI.actividades[calendarioState[index]?.title ?? ""];
    if (!id) return;

    await fetch(`${API_BASE}/actividades-talleres/${id}`, {
      method: "PUT",
      headers: getAuthHeader(),
      body: JSON.stringify({
        titulo: updated.title,
        fechaHora: updated.date + "T00:00:00",
      }),
    });

    updateItem(calendarioState, setCalendarioState, index, updated);
  };

  const deleteCalendario = async (index: number) => {
    const id = idsAPI.actividades[calendarioState[index]?.title ?? ""];
    if (!id) return;

    await fetch(`${API_BASE}/actividades-talleres/${id}`, {
      method: "DELETE",
      headers: getAuthHeader(),
    });

    deleteItem(calendarioState, setCalendarioState, index);
  };

  return {
    saveAsesoria,
    deleteAsesoria,

    saveCurso,
    deleteCurso,

    saveAccion,
    deleteAccion,

    savePrograma,
    deletePrograma,

    saveSalud,
    deleteSalud,

    saveActividad,
    deleteActividad,

    saveCalendario,
    deleteCalendario,
  };
}
