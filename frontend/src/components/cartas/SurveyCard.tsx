export interface Survey {
  id: number;
  title: string;
  url: string;
}

export default function SurveyCard({ event }: { event: Survey }) {
  return (
    <div className="calendar-item">
      <h3>{event.title}</h3>
      <a href={event.url} className="item-location">
        Participar en la encuesta
      </a>
      <span className={`eventProps-status en-proceso`}>En proceso</span>
    </div>
  );
}
