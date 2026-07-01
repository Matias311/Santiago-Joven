import { useState } from "react";
import "./CardPREU.css";

export interface CardData {
  img?: string;
  icon: string;
  iconColor: string;
  title: string;
  description: string;
  date: string;
  lugar: string;
  cupos_disponibles: number;
  cupos: number;
  btn: string;
  btnClass: "participar" | "cerrado" | "cancelado" | "lleno";
}

export interface CardPreuData extends CardData {
  taller?: string;
  profesor: string;
  horarios: string[];
  modalidad: string;
  fechaLimite: string;
  cuposDisponibles: number;
  cuposTotales: number;
  urlEncuesta: string;
}

function Modal({
  card,
  isLoggedIn,
  onClose,
  onLoginRequest,
}: {
  card: CardData & Partial<CardPreuData>;
  isLoggedIn: boolean;
  onClose: () => void;
  onLoginRequest: () => void;
}) {
  const iscancelado = ["cancelado", "lleno", "cerrado"].includes(card.btnClass);
  const esPreu = Boolean(
    card.profesor || card.horarios?.length || card.modalidad,
  );

  // ASIGNACIÓN AUTOMÁTICA ULTRA COMPACTA
  const t = card.title.toLowerCase();
  const rutaImg =
    t.includes("lector") || t.includes("lenguaje")
      ? "/preuLenguaje.png"
      : t.includes("social") || t.includes("ciencia")
        ? "/preuCienciaSo.png"
        : t.includes("m1") || t.includes("mate")
          ? "/preuMate.png"
          : card.img;

  return (
    <div
      className="modal-overlay"
      onClick={(e) => e.target === e.currentTarget && onClose()}
    >
      <div className={`modal ${esPreu ? "modal-light" : ""}`}>
        <button className="modal-close" onClick={onClose}>
          ✕
        </button>

        <div className="modal-body">
          <h3 className="modal-title">{card.title}</h3>

          {esPreu ? (
            <>
              {card.taller && <p className="modal-taller">{card.taller}</p>}

              {/* Muestra tu imagen correspondiente de forma automática */}
              {rutaImg && (
                <div
                  style={{
                    margin: "1rem 0",
                    borderRadius: "12px",
                    overflow: "hidden",
                  }}
                >
                  <img
                    src={rutaImg}
                    alt={card.title}
                    style={{
                      width: "100%",
                      height: "150px",
                      objectFit: "cover",
                      display: "block",
                    }}
                  />
                </div>
              )}

              <p className="modal-descripcion-preu">{card.description}</p>
              {card.profesor && (
                <p className="modal-profesor">
                  <strong>Profesor:</strong> {card.profesor}
                </p>
              )}

              {card.horarios && card.horarios.length > 0 && (
                <>
                  <p className="modal-preu-heading">Inicio de Actividades:</p>
                  <ul className="modal-list-preu">
                    {card.horarios.map((h, i) => (
                      <li key={i}>{h}</li>
                    ))}
                  </ul>
                </>
              )}

              {card.modalidad && (
                <p className="modal-modalidad">
                  <strong>Modalidad:</strong> {card.modalidad}
                </p>
              )}

              {iscancelado ? (
                <div className="modal-footer-action">
                  <div className="event-status expirado">
                    Ya no es posible registrarse.
                  </div>
                </div>
              ) : (
                <div className="modal-footer-preu">
                  {card.urlEncuesta && (
                    <a
                      href={card.urlEncuesta}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="modal-badge-encuesta"
                      onClick={(e) => e.stopPropagation()}
                    >
                      Encuesta
                    </a>
                  )}
                  <div className="modal-footer-info">
                    {card.fechaLimite && (
                      <p className="modal-plazo">
                        Plazo Máximo de Inscripción: {card.fechaLimite}
                      </p>
                    )}
                    {card.cuposDisponibles !== undefined && (
                      <p className="modal-cupos">
                        {card.cuposDisponibles}/{card.cuposTotales} Cupos
                        Disponibles
                      </p>
                    )}
                  </div>
                </div>
              )}
            </>
          ) : (
            <>
              <p>{card.description}</p>
              <div className="modal-section-title">Fecha:</div>
              <ul className="modal-list">
                <li>{card.date}</li>
              </ul>
              <div className="modal-section-title">
                Ubicación: <span>{card.lugar}</span>
                <br />
                Cupos:{" "}
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
                    <button
                      className={`modal-btn-main ${card.btnClass}`}
                      onClick={!isLoggedIn ? onLoginRequest : onClose}
                    >
                      Registrarse
                    </button>
                    {!isLoggedIn && (
                      <p className="modal-login-warning">
                        Ir a "INICIAR SESIÓN" en caso de tener sesión cerrada.
                      </p>
                    )}
                  </div>
                </>
              )}
            </>
          )}
        </div>
      </div>
    </div>
  );
}

export default function CardPREU({
  data,
  isLoggedIn = false,
  onLoginRequest = () => {},
}: {
  data: CardData & Partial<CardPreuData>;
  isLoggedIn?: boolean;
  onLoginRequest?: () => void;
}) {
  const [abierto, setAbierto] = useState(false);

  return (
    <>
      <div className="card" onClick={() => setAbierto(true)}>
        <span className="card_icon">
          <span
            style={{ color: data.iconColor }}
            className="card-header-icon material-symbols-outlined"
          >
            {data.icon}
          </span>
        </span>
        <span className="card_title">{data.title}</span>
        <p className="card_description">{data.description}</p>
      </div>

      {abierto && (
        <Modal
          card={data}
          isLoggedIn={isLoggedIn}
          onClose={() => setAbierto(false)}
          onLoginRequest={onLoginRequest}
        />
      )}
    </>
  );
}
