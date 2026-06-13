export interface CalendarEvent {
  id: number;
  categoria: "Ferias" | "Talleres" | "Cursos" | "Campañas";
  tagClass: "ferias" | "talleres" | "cursos" | "campañas";
  title: string;
  detail: string;
  date: string;
}

const statusTexts = {
  proximamente: "Próximamente",
  "en-proceso": "En proceso",
  expirado: "Ya expiró",
};

interface CalendarCardProps {
  eventProps: CalendarEvent;
}

/* Convierte YYYY-MM-DD o DD-MM-YYYY a texto */

const formatDatetoText = (dateString: string): string => {
  const parts = dateString.split("-");
  let date: Date;

  if (parts[0].length === 4) {
    date = new Date(`${dateString}T00:00:00`);
  } else {
    date = new Date(`${parts[2]}-${parts[1]}-${parts[0]}T00:00:00`);
  }

  if (isNaN(date.getTime())) return dateString;

  return new Intl.DateTimeFormat("es-ES", {
    day: "numeric",
    month: "long",
    year: "numeric",
  }).format(date);
};

export default function CalendarCard({ eventProps }: CalendarCardProps) {
  // fecha del evento
  const parts = eventProps.date.split("-");

  const fechaEvento =
    parts[0].length === 4
      ? new Date(`${eventProps.date}T00:00:00`)
      : new Date(`${parts[2]}-${parts[1]}-${parts[0]}T00:00:00`);

  // fecha actual
  const fechaActual = new Date();

  fechaActual.setHours(0, 0, 0, 0);
  fechaEvento.setHours(0, 0, 0, 0);

  let estadoFinal: "proximamente" | "en-proceso" | "expirado";

  if (fechaEvento.getTime() < fechaActual.getTime()) {
    estadoFinal = "expirado";
  } else if (fechaEvento.getTime() === fechaActual.getTime()) {
    estadoFinal = "en-proceso";
  } else {
    estadoFinal = "proximamente";
  }

  return (
    <div className="calendar-item">
      <span className={`item-tag ${eventProps.tagClass}`}>
        {eventProps.categoria}
      </span>

      <h3>{eventProps.title}</h3>

      <p className="item-location">{eventProps.detail}</p>

      <span className="item-date">{formatDatetoText(eventProps.date)}</span>

      <span className={`eventProps-status ${estadoFinal}`}>
        {statusTexts[estadoFinal]}
      </span>
    </div>
  );
}
