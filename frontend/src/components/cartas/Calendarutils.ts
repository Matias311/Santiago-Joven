import type { CategoriaCalendario, TagClass } from "./CalendarCard";

export const estadoToCategoria: Record<string, CategoriaCalendario> = {
  CONFIRMADO: "Ferias",
  PENDIENTE: "Talleres",
  CANCELADO: "Campañas",
};

export const categoriaToTagClass: Record<CategoriaCalendario, TagClass> = {
  Ferias: "ferias",
  Talleres: "talleres",
  Cursos: "cursos",
  Campañas: "campañas",
};
