import { useState, useEffect } from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import { Pagination, Scrollbar } from "swiper/modules";
import conexion from "../../../assets/ConexionComunitaria/conexion.png";
import calendario from "../../../assets/ConexionComunitaria/calendar.png";
import "swiper/css";
import "swiper/css/pagination";
import "swiper/css/scrollbar";
import "./ConexionComunitaria.css";
import Card, { Modal, type CardData } from "../../cartas/CardConexion.tsx";
import CalendarCard, {
  categoriaToTagClass,
  type CalendarEvent,
  type CategoriaCalendario,
} from "../../cartas/CalendarCard.tsx";

// Nombres de categoría (tal como están en la BD) que definen cada sección
const NOMBRE_CATEGORIA_ACTIVIDADES = "Campañas";
const NOMBRE_CATEGORIA_TALLERES = "Talleres";

const API_BASE = "http://localhost:8080/api/v1";

// Tipos que mapean la respuesta del backend
interface ActividadTallerAPI {
  id: string;
  titulo: string;
  descripcion: string;
  categoriaId: string;
  fechaHora: string;
  activo: boolean;
  cantidadMaximaParticipantes: number;
  imagen: string;
  ubicacionId: string;
  enlaceInscripcion: string;
  inscritos: number;
  estado: "CONFIRMADO" | "PENDIENTE" | "CANCELADO";
  usuarioCreadorId: string;
}

interface UbicacionAPI {
  id: string;
  nombre: string;
  direccion: string;
  ciudad: string;
  latitud: number;
  longitud: number;
}

// Icono según título y tipo de item
function resolveIcon(item: ActividadTallerAPI, esTaller: boolean): string {
  if (esTaller) {
    if (item.titulo.toLowerCase().includes("oratoria")) return "mic";
    if (
      item.titulo.toLowerCase().includes("creativo") ||
      item.titulo.toLowerCase().includes("music") ||
      item.titulo.toLowerCase().includes("teatro")
    )
      return "draw";
    return "lightbulb";
  }
  if (item.titulo.toLowerCase().includes("feria")) return "event_available";
  if (
    item.titulo.toLowerCase().includes("museo") ||
    item.titulo.toLowerCase().includes("cultur")
  )
    return "account_balance";
  return "conversation";
}

// Estado del botón según fecha, estado y cupos
function resolveBtnClass(item: ActividadTallerAPI): CardData["btnClass"] {
  if (item.estado === "CANCELADO") return "cancelado";
  if (new Date(item.fechaHora) < new Date()) return "cerrado";
  if (!item.activo) return "cerrado";
  if (item.cantidadMaximaParticipantes - item.inscritos <= 0) return "lleno";
  return "participar";
}

function btnLabel(btnClass: CardData["btnClass"]): string {
  const labels: Record<CardData["btnClass"], string> = {
    participar: "Participar",
    cerrado: "Cerrado",
    lleno: "Lleno",
    cancelado: "Cancelado",
  };
  return labels[btnClass];
}

// Formatea ISO a "30 de mayo - 15:00 PM"
function formatFecha(iso: string): string {
  const fecha = new Date(iso);
  const meses = [
    "enero",
    "febrero",
    "marzo",
    "abril",
    "mayo",
    "junio",
    "julio",
    "agosto",
    "septiembre",
    "octubre",
    "noviembre",
    "diciembre",
  ];
  const hora = fecha
    .toLocaleTimeString("es-CL", {
      hour: "2-digit",
      minute: "2-digit",
      hour12: true,
    })
    .toUpperCase();
  return `${fecha.getDate()} de ${meses[fecha.getMonth()]} - ${hora}`;
}

// Transforma un item de la API al formato que usa CardConexion
// Recibe dos mapas separados porque dirección y ciudad son campos distintos de Ubicacion
function apiToCardData(
  item: ActividadTallerAPI,
  esTaller: boolean,
  direccionMap: Record<string, string> = {},
  ciudadMap: Record<string, string> = {},
): CardData {
  const btnClass = resolveBtnClass(item);
  return {
    icon: resolveIcon(item, esTaller),
    iconColor: esTaller ? "#da9548" : "#79A45C",
    img: item.imagen,
    title: item.titulo,
    description: item.descripcion,
    date: formatFecha(item.fechaHora),
    lugar: direccionMap[item.ubicacionId] ?? "Ubicación no disponible",
    ciudad: ciudadMap[item.ubicacionId] ?? "",
    cupos: item.cantidadMaximaParticipantes,
    cupos_disponibles: item.cantidadMaximaParticipantes - item.inscritos,
    btnClass,
    btn: btnLabel(btnClass),
    enlaceInscripcion: item.enlaceInscripcion ?? "",
  };
}

// Transforma un item al formato que usa CalendarCard
function apiToCalendarEvent(
  item: ActividadTallerAPI,
  index: number,
  categoriaMap: Record<string, string> = {},
): CalendarEvent {
  const nombreCategoria = categoriaMap[item.categoriaId] ?? "Talleres";
  const categoria = nombreCategoria as CategoriaCalendario;
  return {
    id: index + 1,
    categoria,
    tagClass: categoriaToTagClass[categoria] ?? "talleres",
    title: item.titulo,
    detail: "Ver más detalles",
    date: item.fechaHora.split("T")[0],
  };
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
  const [cardMap, setCardMap] = useState<Record<number, CardData>>({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    async function cargarDatos() {
      try {
        setLoading(true);
        setError(null);

        // Carga actividades, ubicaciones y categorías en paralelo
        const [resActividades, resUbicaciones, resCategorias] =
          await Promise.all([
            fetch(`${API_BASE}/actividades-talleres`),
            fetch(`${API_BASE}/ubicaciones`),
            fetch(`${API_BASE}/categorias`),
          ]);

        if (!resActividades.ok)
          throw new Error(
            `Error ${resActividades.status}: ${resActividades.statusText}`,
          );
        if (!resUbicaciones.ok)
          throw new Error(
            `Error ${resUbicaciones.status}: ${resUbicaciones.statusText}`,
          );
        if (!resCategorias.ok)
          throw new Error(
            `Error ${resCategorias.status}: ${resCategorias.statusText}`,
          );

        const data: ActividadTallerAPI[] = await resActividades.json();
        const ubicaciones: UbicacionAPI[] = await resUbicaciones.json();
        const categorias: { id: string; nombre: string }[] =
          await resCategorias.json();

        // Mapas de ID → valor para lookup rápido (uno por cada campo de Ubicacion)
        const direccionMap: Record<string, string> = Object.fromEntries(
          ubicaciones.map((u) => [u.id, u.direccion]),
        );
        const ciudadMap: Record<string, string> = Object.fromEntries(
          ubicaciones.map((u) => [u.id, u.ciudad]),
        );
        const categoriaMap: Record<string, string> = Object.fromEntries(
          categorias.map((c) => [c.id, c.nombre]),
        );

        // Separación por nombre real de la categoría (no por estado ni mitad)
        const itemsActividades = data.filter(
          (d) => categoriaMap[d.categoriaId] === NOMBRE_CATEGORIA_ACTIVIDADES,
        );
        const itemsTalleres = data.filter(
          (d) => categoriaMap[d.categoriaId] === NOMBRE_CATEGORIA_TALLERES,
        );

        setActividades(
          itemsActividades.map((d) =>
            apiToCardData(d, false, direccionMap, ciudadMap),
          ),
        );
        setTalleres(
          itemsTalleres.map((d) =>
            apiToCardData(d, true, direccionMap, ciudadMap),
          ),
        );

        // Calendario: todos los items + mapa para abrir el modal correcto
        const eventos = data.map((item, index) =>
          apiToCalendarEvent(item, index, categoriaMap),
        );
        const mapa: Record<number, CardData> = {};
        data.forEach((item, index) => {
          const esTaller =
            categoriaMap[item.categoriaId] === NOMBRE_CATEGORIA_TALLERES;
          mapa[index + 1] = apiToCardData(
            item,
            esTaller,
            direccionMap,
            ciudadMap,
          );
        });
        setEventosCalendario(eventos);
        setCardMap(mapa);
      } catch (err) {
        setError(
          err instanceof Error
            ? err.message
            : "Error desconocido al cargar datos.",
        );
      } finally {
        setLoading(false);
      }
    }

    cargarDatos();
  }, []);

  const handleCardClick = (card: CardData) => setSelectedCard(card);
  const handleClose = () => setSelectedCard(null);

  // Filtra eventos del calendario según categoría seleccionada
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

  if (loading) {
    return (
      <>
        <header className="welcome">
          <h1>Conexión Comunitaria</h1>
        </header>
        <main className="conexion-comunitaria">
          <div className="loading-state">Cargando actividades...</div>
        </main>
      </>
    );
  }

  if (error) {
    return (
      <>
        <header className="welcome">
          <h1>Conexión Comunitaria</h1>
        </header>
        <main className="conexion-comunitaria">
          <div className="error-state">
            <p>No se pudieron cargar los datos.</p>
            <p className="error-detail">{error}</p>
          </div>
        </main>
      </>
    );
  }

  return (
    <>
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
        <section className="content">
          <div className="section">
            <h2>Actividades</h2>
            {actividades.length === 0 ? (
              <div className="sin-encuestas">
                <span
                  className="material-symbols-outlined seccion-icono"
                  style={{ color: "#AFB0B1", fontSize: "160px" }}
                >
                  schedule
                </span>
                <p>No hay encuestas recientes.</p>
              </div>
            ) : (
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
            )}
          </div>

          <div className="section">
            <h2>Talleres</h2>
            {talleres.length === 0 ? (
              <div className="sin-encuestas">
                <span
                  className="material-symbols-outlined seccion-icono"
                  style={{ color: "#AFB0B1", fontSize: "160px" }}
                >
                  schedule
                </span>
                <p>No hay encuestas recientes.</p>
              </div>
            ) : (
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
            )}
          </div>

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
                  <CalendarCard
                    key={evento.id}
                    eventProps={evento}
                    onClick={() => handleCardClick(cardMap[evento.id])}
                  />
                ))
              ) : (
                <p className="no-events-message">
                  No hay eventos próximos en esta categoría.
                </p>
              )}
            </div>
          </div>
        </section>

        {selectedCard && (
          <Modal
            card={selectedCard}
            isLoggedIn={isLoggedIn}
            onClose={handleClose}
          />
        )}
      </main>
    </>
  );
}
