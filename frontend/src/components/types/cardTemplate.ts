import type { CartaItem } from "../types/CartaItem";
import type { ConexionItem } from "../types/ConexionItem";
import type { CalendarEvent } from "../cartas/CalendarCard";
import type { Survey } from "../cartas/SurveyCard";

export const templates = {
  asesorias: {
    icono: "service_toolbox",
    iconoColor: "#78A75A",
    iconoTamaño: "42px",
    titulo: "Insertar datos",
    descripcion: "Insertar datos",
  } as CartaItem,

  preuniversitario: {
    icono: "book_ribbon",
    iconoColor: "#DA954B",
    iconoTamaño: "40px",
    titulo: "Insertar datos",
    descripcion: "Insertar datos",
  } as CartaItem,

  cursos: {
    icono: "smart_toy",
    iconoColor: "#789DE5",
    iconoFondo: "rgba(56,189,248,0.15)",
    botoncolor: "#0ea5e9",
    tituloColor: "#0ea5e9",
    sliderSombra: "0 16px 40px rgba(56,189,248,0.2)",
    titulo: "Insertar datos",
    descripcion: "Insertar datos",
  } as CartaItem,

  accion: {
    titulo: "Insertar datos",
    descripcion: "Insertar datos",
    boton: "Insertar datos",
    botoncolor: "",
    icono: "",
    iconoFondo: "",
    tituloColor: "inherit",
  } as CartaItem,

  programas: {
    icono: "family_group",
    iconoColor: "#789DE5",
    iconoFondo: "rgba(56,189,248,0.15)",
    botoncolor: "#0ea5e9",
    tituloColor: "#0ea5e9",
    sliderSombra: "0 16px 40px rgba(56,189,248,0.2)",
    titulo: "Insertar datos",
    descripcion: "Insertar datos",
    boton: "Insertar datos",
    clase: "programa-lazos",
  } as CartaItem,

  salud: {
    icono: "call",
    titulo: "Insertar datos",
    subtitulo: "Insertar datos",
    descripcion: "Insertar datos",
  } as CartaItem,

  actividades: {
    icono: "event_available",
    texto: "Insertar datos",
  } as ConexionItem,

  talleres: {
    icono: "mic",
    texto: "Insertar datos",
  } as ConexionItem,

  encabezado: {
    icono: "handshake",
    iconoColor: "#E3E3E3",
    iconoTamaño: "2.5rem",
    titulo: "Insertar datos",
  } as CartaItem,

  calendario: {
    id: 0,
    categoria: "Ferias",
    tagClass: "ferias",
    title: "Insertar datos",
    detail: "Insertar datos",
    date: "",
  } as CalendarEvent,

  encuesta: {
    id: 0,
    title: "Insertar datos",
    desde: "",
    hasta: "",
    url: "https://",
  } as Survey,
};
