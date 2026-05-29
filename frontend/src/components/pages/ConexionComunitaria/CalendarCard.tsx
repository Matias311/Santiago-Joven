export interface CalendarEvent {
  id: number;
  categoria: "Ferias" | "Talleres" | "Cursos" | "Campañas";
  tagClass: "ferias" | "talleres" | "cursos" | "campañas";
  title: string;
  lugar: string;
  date: string;
  btnStatus: "proximamente" | "expirado";
}

export default function CalendarCard({ event }: { event: CalendarEvent }) {
  return (
    <div className="calendar-item">
      <span className={`item-tag ${event.tagClass}`}>{event.categoria}</span>
      <h3>{event.title}</h3>
      <p className="item-location">{event.lugar}</p>
      <span className="item-date">{event.date}</span>
      <span className={`event-status ${event.btnStatus}`}>
        {event.btnStatus === "proximamente" ? "Proximamente" : "Expirado"}
      </span>
    </div>
  );
}
