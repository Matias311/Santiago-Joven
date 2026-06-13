export interface Survey {
  id: number;
  title: string;
  desde: string;
  hasta: string;
  url: string;
}

const statusTexts = {
  proximamente: "Próximamente",
  "en-proceso": "En proceso",
  expirado: "Ya expiro",
};

/* para tranformar de formato date a texto */
const formatDatetoText = (dateString: string): string => {
  const parts = dateString.split("-");
  let date: Date;

  if (parts[0].length === 4) {
    // YYYY-MM-DD
    date = new Date(`${dateString}T00:00:00`);
  } else {
    // DD-MM-YYYY
    date = new Date(`${parts[2]}-${parts[1]}-${parts[0]}T00:00:00`);
  }

  if (isNaN(date.getTime())) return dateString;

  // Transforma fecha a español
  const formatter = new Intl.DateTimeFormat("es-ES", {
    day: "numeric",
    month: "long",
    year: "numeric",
  });

  return formatter.format(date);
};

export default function SurveyCard({ event }: { event: Survey }) {
  // CAMBIO DE ESTADO DE EVENTO DINAMICO
  // convierte la fecha final e inicial para poder compararla con la actual
  const partsHasta = event.hasta.split("-");
  const fechaHasta =
    partsHasta[0].length === 4
      ? new Date(`${event.hasta}T23:59:59`)
      : new Date(`${partsHasta[2]}-${partsHasta[1]}-${partsHasta[0]}T23:59:59`);

  const partsDesde = event.desde.split("-");
  const fechaDesde =
    partsDesde[0].length === 4
      ? new Date(`${event.desde}T00:00:00`)
      : new Date(`${partsDesde[2]}-${partsDesde[1]}-${partsDesde[0]}T00:00:00`);

  // consigue la fecha actual y luego la compara
  const fechaActual = new Date();
  let estadoFinal: "proximamente" | "en-proceso" | "expirado";
  if (fechaActual > fechaHasta) {
    estadoFinal = "expirado";
  } else if (fechaActual >= fechaDesde) {
    estadoFinal = "en-proceso";
  } else {
    estadoFinal = "proximamente";
  }

  fechaActual.setHours(0, 0, 0, 0);

  return (
    <div className="calendar-item">
      <h3>{event.title}</h3>
      <a href={event.url} className="item-location">
        Participar en la encuesta
      </a>
      <span className="item-date">Desde: {formatDatetoText(event.desde)}</span>
      <span className="item-date">Hasta: {formatDatetoText(event.hasta)}</span>
      <span className={`eventProps-status ${estadoFinal}`}>
        {statusTexts[estadoFinal] || estadoFinal}
      </span>
    </div>
  );
}
