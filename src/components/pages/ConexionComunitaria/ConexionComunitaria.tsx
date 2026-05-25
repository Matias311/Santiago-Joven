import { useState, useEffect } from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import { Pagination, Scrollbar } from "swiper/modules";
import conexion from "../../../assets/ConexionComunitaria/conexion.png";
import calendario from "../../../assets/ConexionComunitaria/calendar.png";

// Estilos de Swiper
import "swiper/css";
import "swiper/css/pagination";
import "swiper/css/scrollbar";

import "./ConexionComunitaria.css";

interface CardData {
  img: string;
  icon: string; // Para que acepte imagenes
  title: string;
  description: string;
  date: string;
  lugar: string;
  cupos_disponibles: number;
  cupos: number;
  btn: string;
  btnClass: "participar" | "cerrado" | "cancelado" | "lleno";
}

interface CalendarEvent {
  id: number;
  categoria: "Ferias" | "Talleres" | "Cursos" | "Campañas";
  tagClass: "ferias" | "talleres" | "cursos" | "campañas";
  title: string;
  lugar: string;
  date: string;
  btnStatus: "proximamente" | "expirado";
}

interface ModalProps {
  card: CardData;
  isLoggedIn: boolean;
  onClose: () => void;
  onLoginRequest: () => void;
}

function Modal({ card, isLoggedIn, onClose, onLoginRequest }: ModalProps) {
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
            Ubicación: <span>{card.lugar}</span>
            <br></br>Cupos disponibles para este evento:
            <span>
              {card.cupos_disponibles}/{card.cupos}
            </span>
          </div>{" "}
          {/* SI EL EVENTO ESTÁ CANCELADO/CUPOS LLENOS/CERRADO */}
          {iscancelado ? (
            <div className="modal-footer-action">
              <div className="event-status expirado">
                Ya no es posible registrarse.
              </div>
            </div>
          ) : (
            <>
              {/* FORMULARIO */}
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
        </div>
      </div>
    </div>
  );
}
function Card({ data, onClick }: { data: CardData; onClick: () => void }) {
  return (
    <div className="card" onClick={onClick}>
      <img src={data.img} className="card_img" alt={data.title} />
      <div className="card_body">
        <div className="card_header">
          <span className="card_icon">
            <img src={data.icon} alt="icono" className="card-header-icon" />
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

function CalendarCard({ event }: { event: CalendarEvent }) {
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

export default function ConexionComunitaria() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [selectedCard, setSelectedCard] = useState<CardData | null>(null);
  const [activeFilter, setActiveFilter] = useState("Todos");

  const [actividades, setActividades] = useState<CardData[]>([]);
  const [talleres, setTalleres] = useState<CardData[]>([]);
  const [eventosCalendario, setEventosCalendario] = useState<CalendarEvent[]>(
    [],
  );

  useEffect(() => {
    fetch("/api/actividades")
      .then((res) => res.json())
      .then((data) => setActividades(data))
      .catch((err) => console.error(err));

    fetch("/api/talleres")
      .then((res) => res.json())
      .then((data) => setTalleres(data))
      .catch((err) => console.error(err));

    fetch("/api/calendario")
      .then((res) => res.json())
      .then((data) => setEventosCalendario(data))
      .catch((err) => console.error(err));
  }, []);

  const handleCardClick = (card: CardData) => setSelectedCard(card);
  const handleClose = () => setSelectedCard(null);

  {
    /* ------ ACA DEBERÍA DE IR LA REDIRECCION AL LOGIN ------ */
  }
  const handleLoginRequest = () => {
    alert("Redireccionando a Iniciar Sesión...");
    setIsLoggedIn(true);
    setSelectedCard(null);
  };

  const eventosFiltrados = eventosCalendario.filter((evento) => {
    if (activeFilter === "Todos") return true;
    const filtroNormalizado = activeFilter.toLowerCase().replace(/s$/, "");
    return evento.categoria.toLowerCase().includes(filtroNormalizado);
  });

  const swiperBreakpoints = {
    0: { slidesPerView: 1, spaceBetween: 12 },
    768: { slidesPerView: 2.2, spaceBetween: 20 },
    1024: { slidesPerView: 3, spaceBetween: 24 },
  };

  return (
    <div className="conexion-comunitaria">
      <div className="welcome">
        <h1>Conexión Comunitaria</h1>
        <p>
          Participa en actividades recreativas, culturales y formativas.
          Conocegente nueva y desarrolla tus talentos
        </p>
        <div className="page-icon">
          <img src={conexion} className="diversity" alt="logo conexion" />
          <span>Conexión</span>
        </div>
      </div>
      <div className="content">
        {/*------- ACTIVIDADES -------*/}
        <div className="section">
          <h2>Actividades</h2>
          <Swiper
            modules={[Pagination, Scrollbar]}
            slidesPerView={1}
            spaceBetween={16}
            observer={true}
            observeParents={true}
            breakpoints={swiperBreakpoints}
            pagination={{ clickable: true, dynamicBullets: true }}
            scrollbar={{ draggable: true }}
            className="swiper-container-custom"
          >
            {actividades.map((item, index) => (
              <SwiperSlide key={index}>
                <Card data={item} onClick={() => handleCardClick(item)} />
              </SwiperSlide>
            ))}
          </Swiper>
        </div>
        {/*------- TALLERES -------*/}
        <div className="section">
          <h2>Talleres</h2>
          <Swiper
            modules={[Pagination, Scrollbar]}
            slidesPerView={1}
            spaceBetween={16}
            observer={true}
            observeParents={true}
            breakpoints={swiperBreakpoints}
            pagination={{ clickable: true, dynamicBullets: true }}
            scrollbar={{ draggable: true }}
            className="swiper-container-custom"
          >
            {talleres.map((item, index) => (
              <SwiperSlide key={index}>
                <Card data={item} onClick={() => handleCardClick(item)} />
              </SwiperSlide>
            ))}
          </Swiper>
        </div>
        {/*------- CALENDARIO -------*/}
        <div className="calendar-section">
          <div className="calendar-header">
            <span>
              <img
                src={calendario}
                className="calendar-icon"
                alt="calendario"
              />
            </span>
            <h2>Calendario de Actividades</h2>
            <p>
              Revisa nuestros próximos eventos. ¡Filtra por categoría y no te
              pierdas nada!
            </p>
          </div>
          <div className="calendar-filters">
            {["Todos", "Ferias", "Talleres", "Cursos", "Campañas"].map(
              (filter) => (
                <button
                  key={filter}
                  className={`filter-btn ${activeFilter === filter ? "active" : ""}`}
                  onClick={() => setActiveFilter(filter)}
                >
                  {filter}
                </button>
              ),
            )}
          </div>
          <div className="calendar-list">
            {eventosFiltrados.length > 0 ? (
              eventosFiltrados.map((evento) => (
                <CalendarCard key={evento.id} event={evento} />
              ))
            ) : (
              <p className="no-events-message">
                No hay eventos próximos en esta categoría.
              </p>
            )}
          </div>
        </div>
      </div>
      {selectedCard && (
        <Modal
          card={selectedCard}
          isLoggedIn={isLoggedIn}
          onClose={handleClose}
          onLoginRequest={handleLoginRequest}
        />
      )}
    </div>
  );
}
