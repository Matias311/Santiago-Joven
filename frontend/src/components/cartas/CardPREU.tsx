// ──────────────────────────────────────────
// TIPOS
// ──────────────────────────────────────────

export interface CardPreuData {
  img: string;
  icon: string;
  iconColor: string;
  title: string;
  description: string;
  horario: string;
  profesor: string;
  url: string; // link de inscripción (Google Classroom, etc.)
  btnClass: "participar" | "cerrado" | "cancelado" | "lleno";
}

// ──────────────────────────────────────────
// COMPONENTE CARD PREU
// ──────────────────────────────────────────

export default function CardPREU({
  data,
  onClick,
}: {
  data: CardPreuData;
  onClick: () => void;
}) {
  return (
    <div className="card" onClick={onClick}>
      <img src={data.img} className="card_img" alt={data.title} />
      <div className="card_body">
        <div className="card_header">
          <span className="card_icon">
            <span
              style={{ color: data.iconColor }}
              className="card-header-icon material-symbols-outlined"
            >
              {data.icon}
            </span>
          </span>
          <span className="card_title">{data.title}</span>
        </div>
        <p className="card_description">{data.description}</p>
        <div className="card_footer">
          <button
            className={`card_btn ${data.btnClass}`}
            onClick={(e) => {
              e.stopPropagation();
              onClick();
            }}
          >
            Ver más
          </button>
        </div>
      </div>
    </div>
  );
}

// ──────────────────────────────────────────
// COMPONENTE MODAL PREU
// ──────────────────────────────────────────

interface ModalPreuProps {
  card: CardPreuData;
  onClose: () => void;
}

export function ModalPREU({ card, onClose }: ModalPreuProps) {
  const iscerrado =
    card.btnClass === "cancelado" ||
    card.btnClass === "lleno" ||
    card.btnClass === "cerrado";

  return (
    <div
      className="modal-overlay"
      onClick={(e) => e.target === e.currentTarget && onClose()}
    >
      <div className="modal">
        {/* Imagen */}
        <img src={card.img} className="modal-img" alt={card.title} />

        {/* Botón cerrar */}
        <button className="modal-close" onClick={onClose}>
          ✕
        </button>

        <div className="modal-body">
          {/* Título */}
          <h3 className="modal-title">{card.title}</h3>

          {/* Descripción */}
          <div className="modal-subtitle-info">
            <p>{card.description}</p>
          </div>

          {/* Horario */}
          <div className="modal-section-title">Horario:</div>
          <ul className="modal-list">
            <li>{card.horario}</li>
          </ul>

          {/* Profesor */}
          <div className="modal-section-title">
            Profesor: <span>{card.profesor}</span>
          </div>

          {/* Botón inscripción */}
          <div className="modal-footer-action">
            {iscerrado ? (
              <div className="event-status expirado">
                Las inscripciones están cerradas.
              </div>
            ) : (
              <a
                href={card.url}
                target="_blank"
                rel="noopener noreferrer"
                className={`modal-btn-main ${card.btnClass}`}
              >
                Inscribirse
              </a>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}
