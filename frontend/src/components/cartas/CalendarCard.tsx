/* =========================================================
   TIPOS
========================================================= */
export type CategoriaCalendario = "Ferias" | "Talleres" | "Cursos" | "Campañas";
export type TagClass = "ferias" | "talleres" | "cursos" | "campañas";

export interface CalendarEvent {
  id: number;
  categoria: CategoriaCalendario;
  tagClass: TagClass;
  title: string;
  detail: string;
  date: string; // "YYYY-MM-DD"
}

/* =========================================================
   HELPERS EXPORTADOS
========================================================= */

/* =========================================================
   INTERNOS
========================================================= */
const statusTexts = {
  proximamente: "Próximamente",
  "en-proceso": "En proceso",
  expirado: "Ya expiró",
} as const;

interface CalendarCardProps {
  eventProps: CalendarEvent;
  onClick: () => void; // 👈 agregado
}

const formatDateToText = (dateString: string): string => {
  const parts = dateString.split("-");
  const date =
    parts[0].length === 4
      ? new Date(`${dateString}T00:00:00`)
      : new Date(`${parts[2]}-${parts[1]}-${parts[0]}T00:00:00`);

  if (isNaN(date.getTime())) return dateString;

  return new Intl.DateTimeFormat("es-ES", {
    day: "numeric",
    month: "long",
    year: "numeric",
  }).format(date);
};

const calcularEstado = (dateString: string): keyof typeof statusTexts => {
  const parts = dateString.split("-");
  const fechaEvento =
    parts[0].length === 4
      ? new Date(`${dateString}T00:00:00`)
      : new Date(`${parts[2]}-${parts[1]}-${parts[0]}T00:00:00`);

  const fechaActual = new Date();
  fechaActual.setHours(0, 0, 0, 0);
  fechaEvento.setHours(0, 0, 0, 0);

  if (fechaEvento.getTime() < fechaActual.getTime()) return "expirado";
  if (fechaEvento.getTime() === fechaActual.getTime()) return "en-proceso";
  return "proximamente";
};

/* =========================================================
   COMPONENTE
========================================================= */
export default function CalendarCard({
  eventProps,
  onClick,
}: CalendarCardProps) {
  const estadoFinal = calcularEstado(eventProps.date);

  return (
    <div className="calendar-item">
      <span className={`item-tag ${eventProps.tagClass}`}>
        {eventProps.categoria}
      </span>

      <h3>{eventProps.title}</h3>

      <button className="item-detail-btn" onClick={onClick}>
        Ver más detalles
      </button>

      <span className="item-date">{formatDateToText(eventProps.date)}</span>

      <span className={`eventProps-status ${estadoFinal}`}>
        {statusTexts[estadoFinal]}
      </span>
    </div>
  );
}
