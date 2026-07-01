export interface CardData {
  img: string;
  icon: string;
  iconColor: string;
  title: string;
  description: string;
  date: string;
  lugar: string;
  ciudad: string;
  cupos_disponibles: number;
  cupos: number;
  btn: string;
  btnClass: "participar" | "cerrado" | "cancelado" | "lleno";
  enlaceInscripcion: string; // URL del formulario de inscripción
}

// ModalProps sin onLoginRequest ya que el botón redirige directamente al enlace
interface ModalProps {
  card: CardData;
  onClose: () => void;
}

export function Modal({ card, onClose }: ModalProps) {
  const iscancelado =
    card.btnClass === "cancelado" ||
    card.btnClass === "lleno" ||
    card.btnClass === "cerrado";

  return (
    <div
      className="modal-overlay"
      onClick={(e) => e.target === e.currentTarget && onClose()}
    >
      <div className="modal">
        <img src={card.img} className="modal-img" alt={card.title} />
        <button className="modal-close" onClick={onClose}>
          ✕
        </button>

        <div className="modal-body">
          <h3 className="modal-title">{card.title}</h3>

          <div className="modal-subtitle-info">
            <p>{card.description}</p>
          </div>

          <div className="modal-section-title">Fecha:</div>
          <ul className="modal-list">
            <li>{card.date}</li>
          </ul>

          <div className="modal-section-title">
            Ubicación:{" "}
            <span>
              {card.lugar}, {card.ciudad}
            </span>
            <br />
            Cupos disponibles para este evento:
            <span>
              {card.cupos_disponibles}/{card.cupos}
            </span>
          </div>

          {iscancelado ? (
            <div className="modal-footer-action">
              <div className="event-status expirado">
                Ya no es posible registrarse.
              </div>
            </div>
          ) : (
            <>
              <div className="modal-form">
                <div>
                  <label>Nombre completo</label>
                  <input type="text" placeholder="Nombres y Apellidos" />
                </div>
                <div>
                  <label>Rut</label>
                  <input type="text" placeholder="Rut con guion y puntos" />
                </div>
              </div>
              <div className="modal-footer-action">
                {/* Abre el enlace de inscripción en una nueva pestaña */}
                <button
                  className={`modal-btn-main ${card.btnClass}`}
                  onClick={() => {
                    if (card.enlaceInscripcion) {
                      window.open(card.enlaceInscripcion, "_blank");
                    }
                  }}
                >
                  Registrarse
                </button>
              </div>
            </>
          )}
        </div>
      </div>
    </div>
  );
}

export default function Card({
  data,
  onClick,
}: {
  data: CardData;
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
          <span className="card_date">{data.date}</span>
          <button
            className={`card_btn ${data.btnClass}`}
            onClick={(e) => {
              e.stopPropagation();
              onClick();
            }}
          >
            {data.btn}
          </button>
        </div>
      </div>
    </div>
  );
}
