import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import "./inicio.css";
import Card from "../../cartas/Card";
import EditableSlider from "../../sliders/EditableSlider";
import type { CartaItem } from "../../types/CartaItem";
import type { ConexionItem } from "../../types/ConexionItem";

interface DatosInicio {
  encabezado: CartaItem[];
  asesorias: CartaItem[];
  preuniversitario: CartaItem[];
  cursos: CartaItem[];
  accion: CartaItem[];
  programas: CartaItem[];
  salud: CartaItem[];
  actividades: ConexionItem[];
  talleres: ConexionItem[];
  contacto: { direccion: string; horario: string; email: string };
}

const ACCESOS_HERO = [
  { href: "#apoyo", icono: "handshake", titulo: "Apoyo Joven" },
  { href: "#proyeccion", icono: "rocket_launch", titulo: "Proyección" },
  { href: "#accion", icono: "campaign", titulo: "Acción Joven" },
  { href: "#conexion", icono: "groups", titulo: "Conexión" },
];

const DATOS_VACIOS: DatosInicio = {
  encabezado: [],
  asesorias: [],
  preuniversitario: [],
  cursos: [],
  accion: [],
  programas: [],
  salud: [],
  actividades: [],
  talleres: [],
  contacto: { direccion: "", horario: "", email: "" },
};

function SinDatos({ mensaje }: { mensaje: string }) {
  return (
    <div className="sin-actividades">
      <span
        className="material-symbols-outlined seccion-icono"
        style={{ color: "#AFB0B1", fontSize: "160px" }}
      >
        schedule
      </span>
      <p>{mensaje}</p>
    </div>
  );
}

import EditableCard from "../../cartas/EditableCard";
import EditableListItem from "../../cartas/EditableListItem";
import React from "react";

import { categoriaToTagClass } from "../../cartas/Calendarutils";
import {
  type CalendarEvent,
  type CategoriaCalendario,
} from "../../cartas/CalendarCard";
import EditableCalendarCard from "../../cartas/EditableCalendarCard";
import type { Survey } from "../../cartas/SurveyCard";
import EditableSurveyCard from "../../cartas/EditableSurveyCard";
/* LAS TEMPLATES DE LAS CARD PARA QUE AGREGUE LAS CARD NUEVAS */
import { templates } from "../../types/cardTemplate";

/* importa las funciones de admin */
import { inicioApi } from "./useInicioApi";

/* importa la verificacion de rol del user */
import { tieneRol } from "../../utils/sessionStorage";
const API_BASE =
  (import.meta.env.VITE_API_URL?.replace(/\/$/, "") ?? "") + "/api/v1";

export default function Inicio() {
  const navigate = useNavigate();

  // Estado que guarda si el usuario es admin o no
  const [isAdmin, setIsAdmin] = useState(false);

  // Función que revisa en localStorage si el usuario tiene rol ADMIN
  const updateAdmin = () => {
    setIsAdmin(tieneRol("ADMIN"));
  };

  useEffect(() => {
    updateAdmin();

    const interval = setInterval(() => {
      updateAdmin();
    }, 300);

    return () => clearInterval(interval);
  }, []);

  // listas que usan el elemento Card normal
  const [asesoriasState, setAsesoriasState] = useState<CartaItem[]>([]);
  const [saludState, setSaludState] = useState<CartaItem[]>([]);

  // listas que usan el elemento List
  const [actividadesState, setActividadesState] = useState<ConexionItem[]>([]);
  const [talleresState, setTalleresState] = useState<ConexionItem[]>([]);

  // listas que usan el elemento Slider
  const [accionState, setAccionState] = useState<CartaItem[]>([]);
  const [programasState, setProgramasState] = useState<CartaItem[]>([]);
  const [cursosState, setCursosState] = useState<CartaItem[]>([]);

  // listas que usan el elemento Calendar
  const [calendarioState, setCalendarioState] = useState<CalendarEvent[]>([]);

  // listas que usan el elemento Survey
  const [encuestaState, setEncuestaState] = useState<Survey[]>([]);

  const [idsAPI, setIdsAPI] = useState<{
    asesorias: Record<string, string>;
    cursos: Record<string, string>;
    acciones: Record<string, string>;
    programas: Record<string, string>;
    salud: Record<string, string>;
    actividades: Record<string, string>;
    encuestas: Record<string, string>;
  }>({
    asesorias: {},
    cursos: {},
    acciones: {},
    programas: {},
    salud: {},
    actividades: {},
    encuestas: {},
  });

  const [categoriasPorTipo, setCategoriasPorTipo] = useState<
    Record<string, string>
  >({});

  const updateIdsAPI = (
    seccion: keyof typeof idsAPI,
    titulo: string,
    id: string,
  ) => {
    setIdsAPI((prev) => ({
      ...prev,
      [seccion]: {
        ...prev[seccion],
        [titulo]: id,
      },
    }));
  };

  const [loading, setLoading] = useState(true);
  const [datosInicio, setDatosInicio] = useState<DatosInicio>(DATOS_VACIOS);
  const [filtroActivo, setFiltroActivo] = useState("Todos");
  const [categoriasPorNombre, setCategoriasPorNombre] = useState<
    Record<string, string>
  >({});

  // Fetch inicial de datos
  useEffect(() => {
    let cancelled = false;

    const rawUrl = import.meta.env.VITE_API_URL?.replace(/\/$/, "");
    if (!rawUrl) return;

    const apiBase = `${rawUrl}/api/v1`;
    const endpoints = [
      "asesorias",
      "cursos-destacados",
      "acciones-joven",
      "programas",
      "salud-mental",
      "actividades-talleres",
      "ubicaciones",
      "categorias",
    ];

    Promise.allSettled(
      endpoints.map((ep) =>
        fetch(`${apiBase}/${ep}`).then((r) => {
          if (!r.ok) throw new Error(`Error ${r.status} al consultar ${ep}`);
          return r.json();
        }),
      ),
    )
      .then((results) => {
        if (cancelled) return;

        results.forEach((r, i) => {
          if (r.status === "rejected") {
            console.error(`Fetch "${endpoints[i]}" falló:`, r.reason);
          }
        });

        const ok = (r: PromiseSettledResult<unknown>) =>
          r.status === "fulfilled"
            ? (r.value as Record<string, unknown>[])
            : [];

        const [
          asesoriasData,
          cursosData,
          accionData,
          programasData,
          saludData,
          actividadesData,
          ubicacionesData,
        ] = results.map(ok);

        const actividades: ConexionItem[] = (actividadesData || [])
          .filter((item) => !/taller/i.test(String(item.titulo || "")))
          .map((item) => ({
            icono: "sports_soccer",
            texto: String(item.titulo || ""),
          }));

        const talleres: ConexionItem[] = (actividadesData || [])
          .filter((item) => /taller/i.test(String(item.titulo || "")))
          .map((item) => ({
            icono: "work_outline",
            texto: String(item.titulo || ""),
          }));

        setDatosInicio({
          encabezado: [],
          asesorias: (asesoriasData || []).map(
            (item: Record<string, unknown>) => ({
              titulo: String(item.titulo || ""),
              descripcion: String(item.definicion || item.objetivos || ""),
              icono: "support",
            }),
          ),
          preuniversitario: [],
          cursos: (cursosData || []).map((item: Record<string, unknown>) => ({
            titulo: String(item.titulo || ""),
            descripcion: String(item.eslogan || item.descripcion || ""),
            icono: "school",
          })),
          accion: (accionData || []).map((item: Record<string, unknown>) => ({
            titulo: String(item.titulo || ""),
            descripcion: String(item.descripcion || ""),
            boton: "Ver más",
          })),
          programas: (programasData || []).map(
            (item: Record<string, unknown>) => ({
              titulo: String(item.titulo || ""),
              descripcion: String(item.descripcion || ""),
              icono: "groups",
            }),
          ),
          salud: (saludData || []).map((item: Record<string, unknown>) => ({
            titulo: String(item.titulo || ""),
            descripcion: String(item.descripcion || ""),
            icono: String(item.icono || "health_and_safety"),
          })),
          actividades,
          talleres,
          contacto: {
            direccion: String(
              (ubicacionesData?.[0] as Record<string, unknown>)?.direccion ??
                "",
            ),
            horario: "",
            email: "",
          },
        });
      })
      .catch((err) => {
        console.error("Error al cargar los datos de inicio:", err);
      });

    return () => {
      cancelled = true;
    };
  }, []);

  // Fetch para datos editables
  useEffect(() => {
    Promise.allSettled([
      fetch(`${API_BASE}/asesorias`).then((r) => r.json()),
      fetch(`${API_BASE}/cursos-destacados`).then((r) => r.json()),
      fetch(`${API_BASE}/acciones-joven`).then((r) => r.json()),
      fetch(`${API_BASE}/programas`).then((r) => r.json()),
      fetch(`${API_BASE}/salud-mental`).then((r) => r.json()),
      fetch(`${API_BASE}/actividades-talleres`).then((r) => r.json()),
      fetch(`${API_BASE}/tu-contribucion-cuenta`).then((r) => r.json()),
      fetch(`${API_BASE}/categorias`).then((r) => r.json()),
    ]).then((results) => {
      const ok = (r: PromiseSettledResult<unknown>) =>
        r.status === "fulfilled" ? (r.value as Record<string, unknown>[]) : [];

      results.forEach((r, i) => {
        if (r.status === "rejected") {
          console.error(`Fetch #${i} falló:`, r.reason);
        }
      });

      const [
        asesoriasData,
        cursosData,
        accionData,
        programasData,
        saludData,
        actividadesData,
        encuestasData,
        categoriasData,
      ] = results.map(ok);

      const nuevosIds = { ...idsAPI };

      nuevosIds.asesorias = Object.fromEntries(
        (asesoriasData || []).map((i: Record<string, unknown>) => [
          String(i.titulo),
          String(i.id),
        ]),
      );
      nuevosIds.cursos = Object.fromEntries(
        (cursosData || []).map((i: Record<string, unknown>) => [
          String(i.titulo),
          String(i.id),
        ]),
      );
      nuevosIds.acciones = Object.fromEntries(
        (accionData || []).map((i: Record<string, unknown>) => [
          String(i.titulo),
          String(i.id),
        ]),
      );
      nuevosIds.programas = Object.fromEntries(
        (programasData || []).map((i: Record<string, unknown>) => [
          String(i.titulo),
          String(i.id),
        ]),
      );
      nuevosIds.salud = Object.fromEntries(
        (saludData || []).map((i: Record<string, unknown>) => [
          String(i.titulo),
          String(i.id),
        ]),
      );
      nuevosIds.actividades = Object.fromEntries(
        (actividadesData || []).map((i: Record<string, unknown>) => [
          String(i.titulo || i.texto || ""),
          String(i.id),
        ]),
      );
      nuevosIds.encuestas = Object.fromEntries(
        (encuestasData || []).map((i: Record<string, unknown>) => [
          String(i.titulo || ""),
          String(i.id),
        ]),
      );
      setIdsAPI(nuevosIds);

      setAsesoriasState(
        (asesoriasData || []).map((item: Record<string, unknown>) => ({
          icono: "service_toolbox",
          iconoColor: "#78A75A",
          iconoTamaño: "42px",
          titulo: String(item.titulo || ""),
          descripcion: String(item.definicion || item.objetivos || ""),
        })),
      );

      setCursosState(
        (cursosData || []).map((item: Record<string, unknown>) => ({
          icono: "smart_toy",
          iconoColor: "#789DE5",
          iconoFondo: "rgba(56,189,248,0.15)",
          botoncolor: "#0ea5e9",
          tituloColor: "#0ea5e9",
          sliderSombra: "0 16px 40px rgba(56,189,248,0.2)",
          titulo: String(item.titulo || ""),
          descripcion: String(item.descripcion || ""),
          eslogan: String(item.eslogan || ""),
          objetivo: String(item.objetivo || ""),
          enlaceInscripcion: String(item.enlaceInscripcion || ""),
        })),
      );

      setAccionState(
        (accionData || []).map((item: Record<string, unknown>) => ({
          titulo: String(item.titulo || ""),
          descripcion: String(item.descripcion || ""),
          boton: "¡Quiero ser voluntario!",
          botoncolor: "",
          icono: "",
          iconoFondo: "",
          tituloColor: "inherit",
        })),
      );

      setProgramasState(
        (programasData || []).map((item: Record<string, unknown>) => ({
          icono: "family_group",
          iconoColor: "#789DE5",
          iconoFondo: "rgba(56,189,248,0.15)",
          botoncolor: "#0ea5e9",
          tituloColor: "#0ea5e9",
          sliderSombra: "0 16px 40px rgba(56,189,248,0.2)",
          titulo: String(item.titulo || ""),
          descripcion: String(item.descripcion || ""),
          boton: "¡Conócenos aquí!",
        })),
      );

      setSaludState(
        (saludData || []).map((item: Record<string, unknown>) => ({
          icono: String(item.icono || "health_and_safety"),
          titulo: String(item.titulo || ""),
          descripcion: String(item.descripcion || ""),
        })),
      );

      setActividadesState(
        (actividadesData || [])
          .filter(
            (item: Record<string, unknown>) =>
              !/taller/i.test(String(item.titulo || "")),
          )
          .map((item: Record<string, unknown>) => ({
            icono: "sports_soccer",
            texto: String(item.titulo || ""),
            descripcion: String(item.descripcion || ""),
            imagen: String(item.imagen || ""),
            date: String(item.fechaHora || "").split("T")[0],
            lugar: String(item.ubicacionId || ""),
            cupos: Number(item.cantidadMaximaParticipantes || 0),
            cupos_disponibles:
              Number(item.cantidadMaximaParticipantes || 0) -
              Number(item.inscritos || 0),
          })),
      );

      setTalleresState(
        (actividadesData || [])
          .filter((item: Record<string, unknown>) =>
            /taller/i.test(String(item.titulo || "")),
          )
          .map((item: Record<string, unknown>) => ({
            icono: "work_outline",
            texto: String(item.titulo || ""),
            descripcion: String(item.descripcion || ""),
            imagen: String(item.imagen || ""),
            date: String(item.fechaHora || "").split("T")[0],
            lugar: String(item.ubicacionId || ""),
            cupos: Number(item.cantidadMaximaParticipantes || 0),
            cupos_disponibles:
              Number(item.cantidadMaximaParticipantes || 0) -
              Number(item.inscritos || 0),
          })),
      );

      const categoriaMap: Record<string, string> = Object.fromEntries(
        (categoriasData || []).map((c: Record<string, unknown>) => [
          String(c.id),
          String(c.nombre),
        ]),
      );

      const nuevasCategoriasPorTipo: Record<string, string> = {};
      (categoriasData || []).forEach((c: Record<string, unknown>) => {
        const tipo = String(c.tipo || "");
        if (tipo && !nuevasCategoriasPorTipo[tipo]) {
          nuevasCategoriasPorTipo[tipo] = String(c.id);
        }
      });
      setCategoriasPorTipo(nuevasCategoriasPorTipo);

      const mapNombre: Record<string, string> = {};
      (categoriasData || []).forEach((c: Record<string, unknown>) => {
        const nombre = String(c.nombre || "");
        if (nombre) mapNombre[nombre] = String(c.id);
      });
      setCategoriasPorNombre(mapNombre);
      setCalendarioState(
        (actividadesData || []).map(
          (item: Record<string, unknown>, index: number) => {
            const nombreCategoria =
              categoriaMap[String(item.categoriaId)] ?? "Talleres";
            const categoria = nombreCategoria as CategoriaCalendario;
            return {
              id: index + 1,
              categoria,
              tagClass: categoriaToTagClass[categoria] ?? "talleres",
              title: String(item.titulo || ""),
              detail: "Ver más detalles",
              date: String(item.fechaHora || "").split("T")[0],
            };
          },
        ),
      );

      setEncuestaState(
        (encuestasData || []).map(
          (item: Record<string, unknown>, index: number) => ({
            id: index + 1,
            title: String(item.titulo || ""),
            descripcion: String(item.descripcion || ""),
            desde: "",
            hasta: "",
            url: String(item.linkGoogleForms || ""),
          }),
        ),
      );

      setLoading(false);
    });
    // Se ejecuta solo al montar: idsAPI se omite a propósito, ya que
    // incluirlo dispararía este fetch de nuevo cada vez que se crea/edita
    // un ítem (porque idsAPI cambia en cada updateIdsAPI).
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const eventosFiltrados = calendarioState.filter(
    (evt) =>
      filtroActivo === "Todos" ||
      evt.categoria.toLowerCase() ===
        filtroActivo.toLowerCase().replace(/s$/, "s"),
  );

  const updateItem = <Carta,>(
    list: Carta[],
    setList: React.Dispatch<React.SetStateAction<Carta[]>>,
    index: number,
    updated: Carta,
  ) => {
    const updatedList = [...list];
    updatedList[index] = updated;
    setList(updatedList);
  };

  const deleteItem = <Carta,>(
    list: Carta[],
    setList: React.Dispatch<React.SetStateAction<Carta[]>>,
    index: number,
  ) => {
    const updatedList = list.filter((_, i) => i !== index);
    setList(updatedList);
  };

  const getPreparedTemplate = <T extends object>(
    template: T,
    currentLength: number,
  ): T => {
    const base = { ...template };
    // `interface` no satisface un índice de string como Record<string, unknown>
    // (a diferencia de `type`), aunque tenga las mismas propiedades. Por eso
    // se castea vía `unknown`: solo se usa para mutar dinámicamente acá
    // adentro; la firma pública de la función sigue siendo segura (T -> T).
    const mutable = base as unknown as Record<string, unknown>;

    const ignorar = [
      "icono",
      "iconoColor",
      "iconoFondo",
      "sliderSombra",
      "tituloColor",
      "botoncolor",
    ];

    (Object.keys(base) as Array<keyof T>).forEach((key) => {
      const value = mutable[key as string];
      if (
        !ignorar.includes(key as string) &&
        typeof value === "string" &&
        value.trim() !== ""
      ) {
        mutable[key as string] = "Insertar datos";
      }
    });

    if ("id" in base) mutable.id = Date.now();
    if ("title" in base) mutable.title = `Nueva carta ${currentLength + 1}`;
    if ("titulo" in base) mutable.titulo = `Nueva carta ${currentLength + 1}`;
    if ("texto" in base) mutable.texto = `Nuevo elemento ${currentLength + 1}`;
    if ("date" in base) mutable.date = new Date().toISOString().split("T")[0];

    return base;
  };

  const {
    createAsesoria,
    saveAsesoria,
    deleteAsesoria,
    createCurso,
    saveCurso,
    deleteCurso,
    createAccion,
    saveAccion,
    deleteAccion,
    createPrograma,
    savePrograma,
    deletePrograma,
    createSalud,
    saveSalud,
    deleteSalud,
    createActividad,
    saveActividad,
    deleteActividad,
    createCalendario,
    saveCalendario,
    deleteCalendario,
    createEncuesta,
    saveEncuesta,
    deleteEncuesta,
  } = inicioApi({
    idsAPI,
    updateIdsAPI,
    categoriasPorTipo,
    asesoriasState,
    setAsesoriasState,
    cursosState,
    setCursosState,
    accionState,
    setAccionState,
    programasState,
    setProgramasState,
    saludState,
    setSaludState,
    actividadesState,
    setActividadesState,
    talleresState,
    setTalleresState,
    calendarioState,
    setCalendarioState,
    encuestaState,
    setEncuestaState,
    updateItem,
    deleteItem,
    categoriasPorNombre,
  });

  const { contacto } = datosInicio;

  return (
    <>
      {loading && (
        <div className="cargando-banner" role="status">
          Cargando contenido…
        </div>
      )}
      <header id="inicio" className="inicio-section">
        <div className="inicio-texto">
          <h1>Santiago Joven: Crece, participa y aprende</h1>
          <p>Tu espacio para encontrar orientación...</p>
          <p className="subtitulo-edad">Dirigido a jóvenes de 14 a 29 años.</p>
        </div>
        <div className="carta-seccion">
          {ACCESOS_HERO.map((acceso) => (
            <a key={acceso.href} href={acceso.href} className="hero-acceso">
              <Card icono={acceso.icono} titulo={acceso.titulo} />
            </a>
          ))}
        </div>
      </header>

      <main className="contenido-principal">
        <section id="apoyo" className="seccion-informativa">
          <div className="seccion-encabezado">
            <span
              className="material-symbols-outlined seccion-icono"
              style={{ color: "#789DE5", fontSize: "70px" }}
            >
              handshake
            </span>
            <h2>Apoyo Joven</h2>
            <p>
              Te acompañamos en tus trámites, estudios y beneficios. Encuentra
              aquí toda la asesoría que necesitas.
            </p>
          </div>
          <div className="grupo-cartas">
            <h3>Asesoría</h3>
            {isAdmin && (
              <button
                className="card_edit_btn"
                onClick={() => {
                  const preparado = getPreparedTemplate(
                    templates.asesorias,
                    asesoriasState.length,
                  );
                  createAsesoria(preparado);
                }}
              >
                Agregar
              </button>
            )}
            {asesoriasState.length === 0 ? (
              <SinDatos mensaje="No hay asesorías disponibles por el momento." />
            ) : (
              <div className="contenedor-flex">
                {asesoriasState.map((carta, index) => (
                  <EditableCard
                    key={index}
                    cardProps={{
                      icono: carta.icono,
                      iconoColor: carta.iconoColor,
                      iconoTamaño: carta.iconoTamaño,
                      titulo: carta.titulo,
                      descripcion: carta.descripcion,
                    }}
                    isAdmin={isAdmin}
                    onSave={(updated) => saveAsesoria(updated, index)}
                    onDelete={() => deleteAsesoria(index)}
                    onAdd={(newCard) => createAsesoria(newCard)}
                  />
                ))}
              </div>
            )}
          </div>
          <div className="grupo-cartas">
            <h3>Preuniversitario</h3>
            {isAdmin && (
              <button
                className="card_edit_btn"
                onClick={() => {
                  const preparado = getPreparedTemplate(
                    templates.encuesta,
                    encuestaState.length,
                  );
                  createEncuesta({
                    ...preparado,
                    title: `Preuniversitario ${encuestaState.length + 1}`,
                  });
                }}
              >
                Agregar
              </button>
            )}
            {encuestaState.filter((e) => e.title.includes("Preuniversitario"))
              .length === 0 ? (
              <SinDatos mensaje="No hay cursos preuniversitarios disponibles por el momento." />
            ) : (
              <div className="contenedor-flex">
                {encuestaState
                  .filter((e) => e.title.includes("Preuniversitario"))
                  .map((encuesta, index) => (
                    <EditableSurveyCard
                      key={encuesta.id}
                      eventProps={encuesta}
                      isAdmin={isAdmin}
                      onSave={(updated) => saveEncuesta(updated, index)}
                      onDelete={() => deleteEncuesta(index)}
                      onAdd={(newSurvey) => createEncuesta(newSurvey)}
                    />
                  ))}
              </div>
            )}
          </div>
        </section>

        <section id="proyeccion" className="seccion-informativa">
          <div className="seccion-encabezado">
            <span
              className="material-symbols-outlined seccion-icono"
              style={{ color: "#50A116", fontSize: "70px" }}
            >
              rocket_launch
            </span>
            <h2>Proyección Joven</h2>
            <p>
              Impulsa tu futuro. Aquí encontrarás cursos y herramientas para tu
              crecimiento personal y profesional.
            </p>
          </div>
          <h3 style={{ textAlign: "center", fontSize: "1.4rem" }}>
            Cursos Destacados
          </h3>
          {isAdmin && (
            <button
              className="card_edit_btn"
              onClick={() => {
                const preparado = getPreparedTemplate(
                  templates.cursos,
                  cursosState.length,
                );
                createCurso(preparado);
              }}
            >
              Agregar
            </button>
          )}
          {cursosState.length === 0 ? (
            <SinDatos mensaje="No hay cursos destacados por el momento." />
          ) : (
            <EditableSlider
              cartas={cursosState}
              isAdmin={isAdmin}
              onSave={(updated, index) => saveCurso(updated, index)}
              onDelete={(index) => deleteCurso(index)}
              onAdd={() => {
                const preparado = getPreparedTemplate(
                  templates.cursos,
                  cursosState.length,
                );
                createCurso(preparado);
              }}
            />
          )}
        </section>

        <div className="fondo-gris">
          <section id="accion" className="accion-joven seccion-informativa">
            <div className="seccion-encabezado">
              <span
                className="material-symbols-outlined seccion-icono"
                style={{ fontSize: "70px" }}
              >
                campaign
              </span>
              <h2>Acción Joven</h2>
              <p>
                ¡Tu energía puede cambiar las cosas! Súmate a nuestras
                iniciativas sociales y proyectos de voluntariado.
              </p>
              {isAdmin && (
                <button
                  className="card_edit_btn"
                  onClick={() => {
                    const preparado = getPreparedTemplate(
                      templates.accion,
                      accionState.length,
                    );
                    createAccion(preparado);
                  }}
                >
                  Agregar
                </button>
              )}
            </div>
            {accionState.length === 0 ? (
              <SinDatos mensaje="No hay acciones joven disponibles por el momento." />
            ) : (
              <EditableSlider
                cartas={accionState}
                isAdmin={isAdmin}
                onSave={(updated, index) => saveAccion(updated, index)}
                onDelete={(index) => deleteAccion(index)}
                onAdd={() => {
                  const preparado = getPreparedTemplate(
                    templates.accion,
                    accionState.length,
                  );
                  createAccion(preparado);
                }}
              />
            )}
          </section>
        </div>

        <section id="programas" className="seccion-informativa">
          <div className="seccion-encabezado">
            <span
              className="material-symbols-outlined seccion-icono"
              style={{ fontSize: "80px" }}
            >
              handshake
            </span>
            <h2>Nuestros Programas</h2>
            <p>
              Conoce los programas insertos en la Oficina de la Juventud para
              apoyarte.
            </p>
            {isAdmin && (
              <button
                className="card_edit_btn"
                onClick={() => {
                  const preparado = getPreparedTemplate(
                    templates.programas,
                    programasState.length,
                  );
                  createPrograma(preparado);
                }}
              >
                Agregar
              </button>
            )}
          </div>
          {programasState.length === 0 ? (
            <SinDatos mensaje="No hay programas disponibles por el momento." />
          ) : (
            <EditableSlider
              cartas={programasState}
              isAdmin={isAdmin}
              onSave={(updated, index) => savePrograma(updated, index)}
              onDelete={(index) => deletePrograma(index)}
              onAdd={() => {
                const preparado = getPreparedTemplate(
                  templates.programas,
                  programasState.length,
                );
                createPrograma(preparado);
              }}
            />
          )}
        </section>

        <div className="fondo-gris">
          <section
            id="salud"
            className="salud-mental-bienestar seccion-informativa"
          >
            <div className="seccion-encabezado">
              <span
                className="material-symbols-outlined seccion-icono"
                style={{ color: "#789DE5", fontSize: "70px" }}
              >
                cardiology
              </span>
              <h2>Salud Mental y Bienestar</h2>
              <p>
                Tu bienestar emocional es prioridad. No estás solo/a. Aquí
                encontrarás canales de ayuda inmediata y profesional.
              </p>
              {isAdmin && (
                <button
                  className="card_edit_btn"
                  onClick={() => {
                    const preparado = getPreparedTemplate(
                      templates.salud,
                      saludState.length,
                    );
                    createSalud(preparado);
                  }}
                >
                  Agregar
                </button>
              )}
            </div>
            <div className="grupo-cartas">
              {saludState.length === 0 ? (
                <SinDatos mensaje="No hay recursos de salud mental disponibles por el momento." />
              ) : (
                saludState.map((carta, index) => (
                  <EditableCard
                    key={index}
                    cardProps={{
                      icono: carta.icono,
                      iconoColor: carta.iconoColor,
                      iconoTamaño: carta.iconoTamaño,
                      titulo: carta.titulo,
                      subtitulo: carta.subtitulo,
                      descripcion: carta.descripcion,
                    }}
                    isAdmin={isAdmin}
                    onSave={(updated) => saveSalud(updated, index)}
                    onDelete={() => deleteSalud(index)}
                    onAdd={() => {
                      const preparado = getPreparedTemplate(
                        templates.salud,
                        saludState.length,
                      );
                      createSalud(preparado);
                    }}
                  />
                ))
              )}
            </div>
          </section>
        </div>

        <section
          id="conexion"
          className="conexion-comunitaria seccion-informativa"
        >
          <div className="seccion-encabezado">
            <span
              className="material-symbols-outlined seccion-icono"
              style={{ color: "#789DE5", fontSize: "70px" }}
            >
              groups
            </span>
            <h2>Conexión Comunitaria</h2>
            <p>
              Participa en actividades recreativas, culturales y formativas.
              Conoce gente nueva y desarrolla tus talentos.
            </p>
          </div>
          <div className="contenedor-flex">
            <div className="lista-conexion">
              <div className="titulo-con-btnAdd">
                <h3>Actividades</h3>
                {isAdmin && (
                  <button
                    className="card_edit_btn"
                    onClick={() => {
                      const preparado = getPreparedTemplate(
                        templates.actividades,
                        actividadesState.length,
                      );
                      createActividad(preparado);
                    }}
                  >
                    Agregar
                  </button>
                )}
              </div>
              {actividadesState.length === 0 ? (
                <SinDatos mensaje="No hay actividades disponibles por el momento." />
              ) : (
                <ul>
                  {actividadesState.map((item, index) => (
                    <EditableListItem
                      key={`${item.texto}-${index}`}
                      item={item}
                      isAdmin={isAdmin}
                      onSave={(updated) => {
                        const tituloOriginal = item.texto;
                        saveActividad(updated, tituloOriginal, index, false); // false para actividades
                      }}
                      onDelete={() => {
                        deleteActividad(item.texto).then(() => {
                          deleteItem(
                            actividadesState,
                            setActividadesState,
                            index,
                          );
                        });
                      }}
                      onAdd={(newItem) => {
                        createActividad(newItem, "actividades");
                      }}
                    />
                  ))}
                </ul>
              )}
            </div>
            <div className="lista-conexion">
              <div className="titulo-con-btnAdd">
                <h3>Talleres</h3>
                {isAdmin && (
                  <button
                    className="card_edit_btn"
                    onClick={() => {
                      const preparado = getPreparedTemplate(
                        templates.talleres,
                        talleresState.length,
                      );
                      createActividad(preparado, "talleres");
                    }}
                  >
                    Agregar
                  </button>
                )}
              </div>
              {talleresState.length === 0 ? (
                <SinDatos mensaje="No hay talleres disponibles por el momento." />
              ) : (
                <ul>
                  {talleresState.map((item, index) => (
                    <EditableListItem
                      key={`${item.texto}-${index}`}
                      item={item}
                      isAdmin={isAdmin}
                      onSave={(updated) => {
                        const tituloOriginal = item.texto;
                        saveActividad(updated, tituloOriginal, index, false); // false para actividades
                      }}
                      onDelete={() => {
                        deleteActividad(item.texto).then(() => {
                          deleteItem(talleresState, setTalleresState, index);
                        });
                      }}
                      onAdd={(newItem) => {
                        createActividad(newItem, "talleres");
                      }}
                    />
                  ))}
                </ul>
              )}
            </div>
          </div>
        </section>

        <div className="fondo-gris">
          <section id="calendario" className="seccion-informativa">
            <div className="seccion-encabezado">
              <span
                className="material-symbols-outlined seccion-icono"
                style={{ color: "#78A75A", fontSize: "70px" }}
              >
                calendar_month
              </span>
              <h2>Calendario de Actividades</h2>
              {isAdmin && (
                <button
                  className="card_edit_btn"
                  onClick={() => {
                    const preparado = getPreparedTemplate(
                      templates.calendario,
                      calendarioState.length,
                    );
                    createCalendario(preparado);
                  }}
                >
                  Agregar
                </button>
              )}
              <p>
                Revisa nuestros próximos eventos. ¡Filtra por categoría y no te
                pierdas nada!
              </p>
            </div>
            <div className="calendario-contenido">
              {["Todos", "Ferias", "Talleres", "Cursos", "Campañas"].map(
                (filtro) => (
                  <button
                    key={filtro}
                    className={`filtro-eventos ${filtroActivo === filtro ? "activo" : ""}`}
                    onClick={() => setFiltroActivo(filtro)}
                  >
                    {filtro}
                  </button>
                ),
              )}
            </div>
            {eventosFiltrados.length === 0 ? (
              <SinDatos mensaje="No hay actividades programadas en esta categoría." />
            ) : (
              <div className="contenedor-flex grilla-actividades">
                {eventosFiltrados.map((evento, index) => (
                  <EditableCalendarCard
                    key={evento.id || index}
                    eventProps={evento}
                    isAdmin={isAdmin}
                    onSave={(updated) => saveCalendario(updated, index)}
                    onDelete={() => deleteCalendario(index)}
                    onDetailClick={() => navigate("/conexioncomunitaria")}
                  />
                ))}
              </div>
            )}
          </section>
        </div>

        <section
          id="contribucion"
          className="tu-contribucion seccion-informativa"
        >
          <div className="seccion-encabezado">
            <span
              className="material-symbols-outlined seccion-icono"
              style={{ color: "#D08370", fontSize: "70px" }}
            >
              calendar_month
            </span>
            <div
              style={{
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                gap: "8px",
              }}
            >
              <h2>Tu contribución cuenta</h2>
            </div>
            <p>
              Ayúdanos a mejorar los programas comunales. Estas encuestas son
              anónimas y nos sirve para generar estadísticas y nuevas
              soluciones.
            </p>
            {isAdmin && (
              <button
                className="card_edit_btn"
                onClick={() => {
                  const preparado = getPreparedTemplate(
                    templates.encuesta,
                    encuestaState.length,
                  );
                  createEncuesta(preparado);
                }}
              >
                Agregar
              </button>
            )}
          </div>
          {encuestaState.filter((e) => !e.title.includes("Preuniversitario"))
            .length === 0 ? (
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
            <div className="contenedor-flex grilla-actividades">
              {encuestaState
                .filter((e) => !e.title.includes("Preuniversitario"))
                .map((evento) => (
                  <EditableSurveyCard
                    key={evento.id}
                    eventProps={evento}
                    isAdmin={isAdmin}
                    onSave={(updated) =>
                      saveEncuesta(updated, encuestaState.indexOf(evento))
                    }
                    onDelete={() =>
                      deleteEncuesta(encuestaState.indexOf(evento))
                    }
                    onAdd={(newSurvey) => createEncuesta(newSurvey)}
                  />
                ))}
            </div>
          )}
        </section>

        <div className="fondo-gris">
          <section
            id="opinion"
            className="tu-opinion-cuenta seccion-informativa"
          >
            <div className="seccion-encabezado">
              <span
                className="material-symbols-outlined seccion-icono"
                style={{ color: "#D08370", fontSize: "70px" }}
              >
                content_paste
              </span>
              <h2>Tu opinión cuenta</h2>
              <p>
                Si tienes opiniones acerca de la pagina, hazlo saber para lograr
                mejorar la pagina!
              </p>
            </div>
            <div className="formulario-opinion">
              <label>Nombre</label>
              <input type="text" />
              <label>Mensaje</label>
              <textarea></textarea>
              <div className="formulario-footer">
                <button className="btn-enviar-opinion">Enviar Opinión</button>
                <span>
                  Para enviar un comentario, debe estar con sesión iniciada
                </span>
              </div>
            </div>
          </section>
        </div>

        <section
          id="contacto"
          className="contacto-ubicacion seccion-informativa"
        >
          <div className="seccion-encabezado">
            <span
              className="material-symbols-outlined seccion-icono"
              style={{ color: "#0077FF", fontSize: "70px" }}
            >
              location_on
            </span>
            <h2>Contáctanos y Ubícanos</h2>
            <p>
              ¿Tienes preguntas? Escríbenos. También puedes visitarnos en
              nuestra oficina.
            </p>
          </div>
          <div className="contacto-layout">
            <div className="formulario-contacto">
              <h3>Envíanos un mensaje</h3>
              <label>Nombre</label>
              <input type="text" />
              <label>Correo Electrónico</label>
              <input type="email" />
              <label>Mensaje</label>
              <textarea></textarea>
              <button className="btn-enviar-contacto">Enviar Mensaje</button>
            </div>
            <div className="info-ubicacion">
              <h3>Nuestra Ubicación</h3>
              <div className="ubicacion-item">
                <span className="material-symbols-outlined seccion-icono">
                  location_on
                </span>
                <p>{contacto.direccion}</p>
              </div>
              <div className="ubicacion-item">
                <span className="material-symbols-outlined seccion-icono">
                  schedule
                </span>
                <p>{contacto.horario}</p>
              </div>
              <div className="ubicacion-item">
                <span className="material-symbols-outlined seccion-icono">
                  email
                </span>
                <p>{contacto.email}</p>
              </div>
            </div>
          </div>
        </section>
      </main>
    </>
  );
}
