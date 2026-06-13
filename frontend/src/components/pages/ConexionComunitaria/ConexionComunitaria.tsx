// Importa useState para manejar estados en el componente
import { useState } from "react";

// Importa Swiper (carrusel de tarjetas)
import { Swiper, SwiperSlide } from "swiper/react";
import { Pagination, Scrollbar } from "swiper/modules";

// Imágenes usadas en actividades, talleres y UI
import conexion from "../../../assets/ConexionComunitaria/conexion.png";
import calendario from "../../../assets/ConexionComunitaria/calendar.png";

import feria from "../../../assets/ConexionComunitaria/feria.jpg";
import museo from "../../../assets/ConexionComunitaria/museo.jpg";
import reunion from "../../../assets/ConexionComunitaria/reunion.jpg";

import oratoria from "../../../assets/ConexionComunitaria/oratoria.png";
import creativo from "../../../assets/ConexionComunitaria/creativo.jpg";
import debate from "../../../assets/ConexionComunitaria/debate.jpg";

// Estilos obligatorios de Swiper
import "swiper/css";
import "swiper/css/pagination";
import "swiper/css/scrollbar";

// Estilos propios del componente
import "./ConexionComunitaria.css";

// Componentes reutilizables (cards)
import Card, { type CardData } from "../../cartas/CardConexion.tsx";
import CalendarCard, {
  type CalendarEvent,
} from "../../cartas/CalendarCard.tsx";

/* =========================================================
   INTERFAZ DEL MODAL
   Define qué datos recibe el popup al abrir una card
========================================================= */
interface ModalProps {
  card: CardData; // datos de la card seleccionada
  isLoggedIn: boolean; // estado de sesión
  onClose: () => void; // función para cerrar modal
  onLoginRequest: () => void; // función si intenta registrarse sin login
}

/* =========================================================
   DATA: ACTIVIDADES
   Lista de cards del carrusel de actividades
========================================================= */
const actividadesData: CardData[] = [
  {
    icon: "event_available",
    iconColor: "#79A45C",
    img: feria,
    title: "Ferias vocacionales y de emprendimiento.",
    description:
      "Explora nuevas oportunidades laborales y conecta con emprendedores locales para impulsar tu futuro profesional.",
    date: "30 de mayo - 15:00 PM",
    lugar: "Centro municipal",
    cupos: 40,
    cupos_disponibles: 15,
    btnClass: "participar",
    btn: "Participar",
  },
  {
    icon: "account_balance",
    iconColor: "#79A45C",
    img: museo,
    title: "Viaje cultural al Museo",
    description:
      "Sumérgete en la historia y el arte a través de un recorrido guiado por las obras más emblemáticas de nuestra región.",
    date: "5 de mayo - 12:00 PM",
    lugar: "Museo de Maipú",
    cupos: 50,
    cupos_disponibles: 50,
    btnClass: "cerrado",
    btn: "Cerrado",
  },
  {
    icon: "conversation",
    iconColor: "#79A45C",
    img: reunion,
    title: "Reuniones y círculos de conversación.",
    description:
      "Un espacio para compartir ideas, reflexiones y fortalecer el pensamiento crítico en comunidad.",
    date: "2 de mayo - 18:00 PM",
    lugar: "Centro municipal",
    cupos: 50,
    cupos_disponibles: 50,
    btnClass: "cerrado",
    btn: "Cerrado",
  },
];

/* =========================================================
   DATA: TALLERES
   Lista de cards del carrusel de talleres
========================================================= */
const talleresData: CardData[] = [
  {
    icon: "mic",
    iconColor: "#da9548",
    img: oratoria,
    title: "Taller de liderazgo y oratoria.",
    description:
      "Desarrolla habilidades de comunicación efectiva y aprende a guiar equipos de trabajo con confianza y empatía.",
    date: "30 de mayo - 15:00 PM",
    lugar: "Centro municipal",
    cupos: 20,
    cupos_disponibles: 8,
    btnClass: "lleno",
    btn: "Lleno",
  },
  {
    icon: "draw",
    iconColor: "#da9548",
    img: creativo,
    title: "Talleres creativos (Música, dibujo, teatro).",
    description:
      "Libera tu potencial artístico y aprende nuevas técnicas de expresión de la mano de expertos en cada disciplina.",
    date: "5 de mayo - 12:00 PM",
    lugar: "Teatro municipal",
    cupos: 25,
    cupos_disponibles: 0,
    btnClass: "cancelado",
    btn: "Cancelado",
  },
  {
    icon: "lightbulb",
    iconColor: "#da9548",
    img: debate,
    title: "Taller de debate y pensamiento crítico..",
    description:
      "Aprende a argumentar con base y lógica en dinámicas de discusión grupal sobre temas de actualidad.",
    date: "10 de mayo - 12:00 PM",
    lugar: "Teatro municipal",
    cupos: 25,
    cupos_disponibles: 0,
    btnClass: "cancelado",
    btn: "Cancelado",
  },
];

/* =========================================================
   DATA: CALENDARIO
   Lista de eventos del calendario
========================================================= */
const calendariosData: CalendarEvent[] = [
  {
    id: 1,
    categoria: "Ferias",
    tagClass: "ferias",
    title: "Feria Vocacional 2026",
    detail: "Ver más detalles",
    date: "2026-11-15",
  },
  {
    id: 2,
    categoria: "Talleres",
    tagClass: "talleres",
    title: "Taller Vocacional 2026",
    detail: "Ver más detalles",
    date: "2026-01-10",
  },
  {
    id: 3,
    categoria: "Campañas",
    tagClass: "campañas",
    title: "Campaña Vocacional 2026",
    detail: "Ver más detalles",
    date: "2026-05-30",
  },
];

/* =========================================================
   COMPONENTE MODAL
   Popup que se abre al hacer click en una card
========================================================= */
function Modal({ card, isLoggedIn, onClose, onLoginRequest }: ModalProps) {
  // Determina si la card no permite registro
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
        {/* Imagen del evento */}
        <img src={card.img} className="modal-img" alt={card.title} />

        {/* Botón cerrar modal */}
        <button className="modal-close" onClick={onClose}>
          ✕
        </button>

        <div className="modal-body">
          {/* Título del evento */}
          <h3 className="modal-title">{card.title}</h3>

          {/* Descripción del evento */}
          <div className="modal-subtitle-info">
            <p>{card.description}</p>
          </div>

          {/* Fecha del evento */}
          <div className="modal-section-title">Fecha:</div>
          <ul className="modal-list">
            <li>{card.date}</li>
          </ul>

          {/* Ubicación y cupos */}
          <div className="modal-section-title">
            Ubicación: <span>{card.lugar}</span>
            <br></br>Cupos disponibles para este evento:
            <span>
              {card.cupos_disponibles}/{card.cupos}
            </span>
          </div>

          {/* Si el evento está cerrado/cancelado/lleno */}
          {iscancelado ? (
            <div className="modal-footer-action">
              <div className="event-status expirado">
                Ya no es posible registrarse.
              </div>
            </div>
          ) : (
            <>
              {/* Formulario de registro */}
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

              {/* Botón de registro */}
              <div className="modal-footer-action">
                <button
                  className={`modal-btn-main ${card.btnClass}`}
                  onClick={!isLoggedIn ? onLoginRequest : onClose}
                >
                  Registrarse
                </button>

                {/* Mensaje si no está logeado */}
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

/* =========================================================
   COMPONENTE PRINCIPAL
   Página Conexión Comunitaria
========================================================= */
export default function ConexionComunitaria() {
  // Estado de login simulado
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  // Card seleccionada para modal
  const [selectedCard, setSelectedCard] = useState<CardData | null>(null);

  // Filtro del calendario
  const [activeFilter, setActiveFilter] = useState("Todos");

  // Estados de datos (actividades, talleres, calendario)
  const [actividades] = useState<CardData[]>(actividadesData);
  const [talleres] = useState<CardData[]>(talleresData);
  const [eventosCalendario] = useState<CalendarEvent[]>(calendariosData);

  // Abre modal con card seleccionada
  const handleCardClick = (card: CardData) => setSelectedCard(card);

  // Cierra modal
  const handleClose = () => setSelectedCard(null);

  // Simulación de login
  const handleLoginRequest = () => {
    alert("Redireccionando a Iniciar Sesión...");
    setIsLoggedIn(true);
    setSelectedCard(null);
  };

  // Filtrado de eventos del calendario según categoría
  const eventosFiltrados = eventosCalendario.filter((evento) => {
    if (activeFilter === "Todos") return true;
    const filtroNormalizado = activeFilter.toLowerCase().replace(/s$/, "");
    return evento.categoria.toLowerCase().includes(filtroNormalizado);
  });

  // Configuración responsive del Swiper
  const swiperBreakpoints = {
    0: { slidesPerView: 1, spaceBetween: 12 },
    768: { slidesPerView: 2.2, spaceBetween: 20 },
    1024: { slidesPerView: 3, spaceBetween: 24 },
  };

  return (
    <>
      {/* HEADER PRINCIPAL */}
      <header className="welcome">
        <h1>Conexión Comunitaria</h1>
        <p>
          Participa en actividades recreativas, culturales y formativas. Conoce
          gente nueva y desarrolla tus talentos
        </p>
        <div className="page-icon">
          <img src={conexion} className="conexion" alt="logo conexion" />
          <span>Conexión</span>
        </div>
      </header>

      <main className="conexion-comunitaria">
        {/* SECCIÓN ACTIVIDADES */}
        <section className="content">
          {/* Carrusel de actividades */}
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
              style={
                {
                  "--swiper-scrollbar-drag-bg-color": "#a5eab0",
                } as React.CSSProperties
              }
            >
              {actividades.map((item, index) => (
                <SwiperSlide key={index}>
                  <Card data={item} onClick={() => handleCardClick(item)} />
                </SwiperSlide>
              ))}
            </Swiper>
          </div>

          {/* Carrusel de talleres */}
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
              style={
                {
                  "--swiper-scrollbar-drag-bg-color": "#da954b",
                } as React.CSSProperties
              }
            >
              {talleres.map((item, index) => (
                <SwiperSlide key={index}>
                  <Card data={item} onClick={() => handleCardClick(item)} />
                </SwiperSlide>
              ))}
            </Swiper>
          </div>

          {/* CALENDARIO */}
          <div className="calendar-section">
            {/* Header calendario */}
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

            {/* Filtros del calendario */}
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

            {/* Lista de eventos filtrados */}
            <div className="calendar-list">
              {eventosFiltrados.length > 0 ? (
                eventosFiltrados.map((evento) => (
                  <CalendarCard key={evento.id} eventProps={evento} />
                ))
              ) : (
                <p className="no-events-message">
                  No hay eventos próximos en esta categoría.
                </p>
              )}
            </div>
          </div>
        </section>

        {/* Render del modal cuando hay card seleccionada */}
        {selectedCard && (
          <Modal
            card={selectedCard}
            isLoggedIn={isLoggedIn}
            onClose={handleClose}
            onLoginRequest={handleLoginRequest}
          />
        )}
      </main>
    </>
  );
}
