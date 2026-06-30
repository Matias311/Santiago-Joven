import { useEffect, useState } from "react";
import "./inicio.css";
import Card from "../../cartas/Card";
import EditableSlider from "../../sliders/EditableSlider";
import type { CartaItem } from "../../types/CartaItem";
import type { ConexionItem } from "../../types/ConexionItem";
import EditableCard from "../../cartas/EditableCard";
import EditableListItem from "../../cartas/EditableListItem";
import React from "react";
import { type CalendarEvent } from "../../cartas/CalendarCard";
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

const encabezado: CartaItem[] = [
  {
    icono: "handshake",
    iconoColor: "#E3E3E3",
    iconoTamaño: "2.5rem",
    titulo: "Apoyo Joven",
  },
  {
    icono: "rocket",
    iconoColor: "#E3E3E3",
    iconoTamaño: "2.5rem",
    titulo: "Proyección",
  },
  {
    icono: "campaign",
    iconoColor: "#E3E3E3",
    iconoTamaño: "2.5rem",
    titulo: "Acción Joven",
  },
  {
    icono: "diversity_3",
    iconoColor: "#E3E3E3",
    iconoTamaño: "2.5rem",
    titulo: "Conexión",
  },
];

const contacto = {
  direccion:
    "Herrera 360, Comuna de Santiago. (Centro Comunitario Santiago en Compañía)",
  horario: "Lunes a jueves [09:00 - 18:00 hrs] - Viernes [09:00 a 17:00 hrs]",
  email: "stgojoven@munistgo.cl",
};

export default function Inicio() {
  {
    /* Acá debería de ir el auth de si es admin o no, pero mientras pongo esto */
  }

  // Estado que guarda si el usuario es admin o no
  // false por defecto no es admin hasta verificarlo
  const [isAdmin, setIsAdmin] = useState(false);

  // Función que revisa en localStorage si el usuario tiene rol ADMIN
  // y actualiza el estado de React
  const updateAdmin = () => {
    setIsAdmin(tieneRol("ADMIN"));
  };

  useEffect(() => {
    // Se ejecuta una vez cuando el componente se monta
    // aquí se hace la primera verificación del rol
    updateAdmin();

    // Se crea un intervalo que se ejecuta cada 300ms
    // Esto permite detectar cambios en el login/logout
    const interval = setInterval(() => {
      updateAdmin();
    }, 300);

    // Cuando el componente se desmonta, se elimina el intervalo
    // para evitar consumo de memoria o procesos innecesarios
    return () => clearInterval(interval);
  }, []);

  {
    /* -- Convierte las listas en estados para que puedan ser editables -- */
  }

  {
    /* listas que usan el elemento Card normal */
  }
  const [asesoriasState, setAsesoriasState] = useState<CartaItem[]>([]);
  const [preuniversitarioState, setPreuniversitarioState] = useState<
    CartaItem[]
  >([]);
  const [saludState, setSaludState] = useState<CartaItem[]>([]);

  {
    /* listas que usan el elemento List */
  }
  const [actividadesState, setActividadesState] = useState<ConexionItem[]>([]);
  const [talleresState, setTalleresState] = useState<ConexionItem[]>([]);

  {
    /* listas que usan el elemento Slider */
  }
  const [accionState, setAccionState] = useState<CartaItem[]>([]);
  const [programasState, setProgramasState] = useState<CartaItem[]>([]);
  const [cursosState, setCursosState] = useState<CartaItem[]>([]);

  {
    /* listas que usan el elemento Calendar */
  }
  const [calendarioState, setCalendarioState] = useState<CalendarEvent[]>([]);
  const [filtro, setFiltro] = useState<string>("Todos");

  {
    /* listas que usan el elemento Survey */
  }
  const [encuestaState, setEncuestaState] = useState<Survey[]>([]);

  // IDs internos de la API para poder hacer PUT/DELETE (mapa titulo → id)
  const [idsAPI, setIdsAPI] = useState<{
    asesorias: Record<string, string>;
    cursos: Record<string, string>;
    acciones: Record<string, string>;
    programas: Record<string, string>;
    salud: Record<string, string>;
    actividades: Record<string, string>;
  }>({
    asesorias: {},
    cursos: {},
    acciones: {},
    programas: {},
    salud: {},
    actividades: {},
  });

  const [loading, setLoading] = useState(true);
  /* -------------------------------------------------------
     FETCH inicial: carga todos los datos desde la API
  ------------------------------------------------------- */
  useEffect(() => {
    Promise.allSettled([
      fetch(`${API_BASE}/asesorias`).then((r) => r.json()),
      fetch(`${API_BASE}/cursos-destacados`).then((r) => r.json()),
      fetch(`${API_BASE}/acciones-joven`).then((r) => r.json()),
      fetch(`${API_BASE}/programas`).then((r) => r.json()),
      fetch(`${API_BASE}/salud-mental`).then((r) => r.json()),
      fetch(`${API_BASE}/actividades-talleres`).then((r) => r.json()),
      fetch(`${API_BASE}/tu-contribucion-cuenta`).then((r) => r.json()),
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
      ] = results.map(ok);

      // Guardamos los IDs para poder hacer PUT/DELETE después
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
          String(i.titulo),
          String(i.id),
        ]),
      );
      setIdsAPI(nuevosIds);

      // Mapeo de datos de la API al formato CartaItem
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
          descripcion: String(item.eslogan || item.descripcion || ""),
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

      // Actividades: items sin "taller" en el título → lista simple
      setActividadesState(
        (actividadesData || [])
          .filter(
            (item: Record<string, unknown>) =>
              !/taller/i.test(String(item.titulo || "")),
          )
          .map((item: Record<string, unknown>) => ({
            icono: "sports_soccer",
            texto: String(item.titulo || ""),
          })),
      );

      // Talleres: items con "taller" en el título → lista simple
      setTalleresState(
        (actividadesData || [])
          .filter((item: Record<string, unknown>) =>
            /taller/i.test(String(item.titulo || "")),
          )
          .map((item: Record<string, unknown>) => ({
            icono: "work_outline",
            texto: String(item.titulo || ""),
          })),
      );

      // Calendario: todas las actividades mapeadas a CalendarEvent
      setCalendarioState(
        (actividadesData || []).map(
          (item: Record<string, unknown>, index: number) => ({
            id: index + 1,
            categoria: "Ferias" as CalendarEvent["categoria"],
            tagClass: "ferias" as CalendarEvent["tagClass"],
            title: String(item.titulo || ""),
            detail: "Ver más detalles",
            date: String(item.fechaHora || "").split("T")[0],
          }),
        ),
      );

      // Encuestas desde tu-contribucion-cuenta
      setEncuestaState(
        (encuestasData || []).map((item: Record<string, unknown>) => ({
          id: Number(item.id) || Date.now(),
          title: String(item.titulo || ""),
          desde: "",
          hasta: "",
          url: String(item.linkGoogleForms || ""),
        })),
      );

      setLoading(false);
    });
  }, []);

  const eventosFiltrados = calendarioState.filter(
    (evt) =>
      filtro === "Todos" ||
      evt.categoria.toLowerCase() === filtro.toLowerCase().replace(/s$/, "s"),
  );

  {
    /** Actualizar carta (local + API) */
  }
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

  {
    /** Eliminar carta (local + API) */
  }
  const deleteItem = <Carta,>(
    list: Carta[],
    setList: React.Dispatch<React.SetStateAction<Carta[]>>,
    index: number,
  ) => {
    const updatedList = list.filter((_, i) => i !== index);
    setList(updatedList);
  };

  {
    /** Añadir carta */
  }
  const addCard = <T,>(
    state: T[],
    setState: React.Dispatch<React.SetStateAction<T[]>>,
    template?: T,
  ) => {
    const base: any =
      state.length > 0
        ? { ...state[state.length - 1] }
        : template
          ? { ...template }
          : null;

    if (!base) return;

    const ignorar = [
      "icono",
      "iconoColor",
      "iconoFondo",
      "sliderSombra",
      "tituloColor",
      "botoncolor",
    ];

    Object.keys(base).forEach((key) => {
      if (
        !ignorar.includes(key) &&
        typeof base[key] === "string" &&
        base[key].trim() !== ""
      ) {
        base[key] = "Insertar datos";
      }
    });

    if ("id" in base) {
      base.id = Date.now();
    }

    if ("title" in base) {
      base.title = `Nueva carta ${state.length + 1}`;
    }

    if ("titulo" in base) {
      base.titulo = `Nueva carta ${state.length + 1}`;
    }

    if ("texto" in base) {
      base.texto = `Nuevo elemento ${state.length + 1}`;
    }

    setState([...state, base]);
  };

  /* -------------------------------------------------------
     Helpers de API para cada entidad
  ------------------------------------------------------- */
  const {
    saveAsesoria,
    deleteAsesoria,
    saveCurso,
    deleteCurso,
    saveAccion,
    deleteAccion,
    savePrograma,
    deletePrograma,
    saveSalud,
    deleteSalud,
    saveActividad,
    deleteActividad,
    saveCalendario,
    deleteCalendario,
  } = inicioApi({
    idsAPI,

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

    calendarioState,
    setCalendarioState,

    updateItem,
    deleteItem,
  });
  return (
    <>
      {/* encabezado de la pagina principal */}
      <header id="inicio" className="inicio-section">
        <div className="inicio-texto">
          <h1>Santiago Joven: Crece, participa y aprende</h1>
          <p>Tu espacio para encontrar orientación...</p>
          <p className="subtitulo-edad">Dirigido a jóvenes de 14 a 29 años.</p>
        </div>
        <div className="carta-seccion">
          {encabezado.map((carta) => (
            <Card
              key={carta.titulo}
              icono={carta.icono}
              iconoColor={carta.iconoColor}
              iconoTamaño={carta.iconoTamaño}
              titulo={carta.titulo}
            />
          ))}
        </div>
      </header>

      {/* contenido principal */}
      <main className="contenido-principal">
        {/* apoyo joven */}
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
            <div className="titulo-con-btnAdd">
              <h3>Asesoría</h3>
            </div>
            {isAdmin && (
              <button
                className="card_edit_btn"
                onClick={() =>
                  addCard(
                    asesoriasState,
                    setAsesoriasState,
                    templates.asesorias,
                  )
                }
              >
                Agregar
              </button>
            )}
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
                  onAdd={(newCard) =>
                    setAsesoriasState([...asesoriasState, { ...newCard }])
                  }
                />
              ))}
            </div>
          </div>
          <div className="grupo-cartas">
            <div className="titulo-con-btnAdd">
              <h3>Preuniversitario</h3>
            </div>
            {isAdmin && (
              <button
                className="card_edit_btn"
                onClick={() =>
                  addCard(
                    preuniversitarioState,
                    setPreuniversitarioState,
                    templates.preuniversitario,
                  )
                }
              >
                Agregar
              </button>
            )}
            <div className="contenedor-flex">
              {preuniversitarioState.map((carta, index) => (
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
                  onSave={(updated) =>
                    updateItem(
                      preuniversitarioState,
                      setPreuniversitarioState,
                      index,
                      updated,
                    )
                  }
                  onDelete={() =>
                    deleteItem(
                      preuniversitarioState,
                      setPreuniversitarioState,
                      index,
                    )
                  }
                  onAdd={() =>
                    addCard(
                      preuniversitarioState,
                      setPreuniversitarioState,
                      templates.preuniversitario,
                    )
                  }
                />
              ))}
            </div>
          </div>
        </section>

        {/* proyeccion joven */}
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
              onClick={() =>
                addCard(cursosState, setCursosState, templates.cursos)
              }
            >
              Agregar
            </button>
          )}
          <EditableSlider
            cartas={cursosState}
            isAdmin={isAdmin}
            onSave={(updated, index) => saveCurso(updated, index)}
            onDelete={(index) => deleteCurso(index)}
            onAdd={() => addCard(cursosState, setCursosState, templates.cursos)}
          />
        </section>

        {/* accion joven */}
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
                  onClick={() =>
                    addCard(accionState, setAccionState, templates.accion)
                  }
                >
                  Agregar
                </button>
              )}
            </div>
            <EditableSlider
              cartas={accionState}
              isAdmin={isAdmin}
              onSave={(updated, index) => saveAccion(updated, index)}
              onDelete={(index) => deleteAccion(index)}
              onAdd={() =>
                addCard(accionState, setAccionState, templates.accion)
              }
            />
          </section>
        </div>

        {/* nuestros programas */}
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
                onClick={() =>
                  addCard(
                    programasState,
                    setProgramasState,
                    templates.programas,
                  )
                }
              >
                Agregar
              </button>
            )}
          </div>
          <EditableSlider
            cartas={programasState}
            isAdmin={isAdmin}
            onSave={(updated, index) => savePrograma(updated, index)}
            onDelete={(index) => deletePrograma(index)}
            onAdd={() =>
              addCard(programasState, setProgramasState, templates.programas)
            }
          />
        </section>

        {/* salud mental */}
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
                  onClick={() =>
                    addCard(saludState, setSaludState, templates.salud)
                  }
                >
                  Agregar
                </button>
              )}
            </div>
            <div className="grupo-cartas">
              {saludState.map((carta, index) => (
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
                  onAdd={() =>
                    addCard(saludState, setSaludState, templates.salud)
                  }
                />
              ))}
            </div>
          </section>
        </div>

        {/* conexion comunitaria */}
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
              <div className="btn-con-btnAdd">
                <div className="titulo-con-btnAdd">
                  <h3>Actividades</h3>
                </div>
                {isAdmin && (
                  <button
                    className="card_edit_btn"
                    onClick={() =>
                      addCard(
                        actividadesState,
                        setActividadesState,
                        templates.actividades,
                      )
                    }
                  >
                    Agregar
                  </button>
                )}
              </div>
              <ul>
                {actividadesState.map((item, index) => (
                  <EditableListItem
                    key={`${item.texto}-${index}`}
                    item={item}
                    isAdmin={isAdmin}
                    onSave={(updated, seccion) => {
                      if (seccion === "actividades") {
                        saveActividad(updated, item.texto).then(() =>
                          updateItem(
                            actividadesState,
                            setActividadesState,
                            index,
                            updated,
                          ),
                        );
                      } else {
                        // Mover de actividades a talleres: elimina de actividades y agrega a talleres
                        deleteActividad(item.texto).then(() => {
                          setActividadesState(
                            actividadesState.filter((_, i) => i !== index),
                          );
                          setTalleresState((prev) => [...prev, updated]);
                        });
                      }
                    }}
                    onDelete={() => {
                      deleteActividad(item.texto).then(() =>
                        deleteItem(
                          actividadesState,
                          setActividadesState,
                          index,
                        ),
                      );
                    }}
                    onAdd={(newItem, seccion) => {
                      const state =
                        seccion === "actividades"
                          ? actividadesState
                          : talleresState;
                      const setState =
                        seccion === "actividades"
                          ? setActividadesState
                          : setTalleresState;
                      const template =
                        seccion === "actividades"
                          ? templates.actividades
                          : templates.talleres;
                      if (state.length === 0) {
                        addCard(state, setState, template);
                      } else {
                        setState([...state, { ...newItem }]);
                      }
                    }}
                  />
                ))}
              </ul>
            </div>
            <div className="lista-conexion">
              <div className="titulo-con-btnAdd">
                <h3>Talleres</h3>
              </div>
              {isAdmin && (
                <button
                  className="card_edit_btn"
                  onClick={() =>
                    addCard(talleresState, setTalleresState, templates.talleres)
                  }
                >
                  Agregar
                </button>
              )}
              <ul>
                {talleresState.map((item, index) => (
                  <EditableListItem
                    key={`${item.texto}-${index}`}
                    item={item}
                    isAdmin={isAdmin}
                    onSave={(updated) => {
                      saveActividad(updated, item.texto).then(() =>
                        updateItem(
                          talleresState,
                          setTalleresState,
                          index,
                          updated,
                        ),
                      );
                    }}
                    onDelete={() => {
                      deleteActividad(item.texto).then(() =>
                        deleteItem(talleresState, setTalleresState, index),
                      );
                    }}
                    onAdd={(newItem, seccion) => {
                      const state =
                        seccion === "actividades"
                          ? actividadesState
                          : talleresState;
                      const setState =
                        seccion === "actividades"
                          ? setActividadesState
                          : setTalleresState;
                      const template =
                        seccion === "actividades"
                          ? templates.actividades
                          : templates.talleres;
                      if (state.length === 0) {
                        addCard(state, setState, template);
                      } else {
                        setState([...state, { ...newItem }]);
                      }
                    }}
                  />
                ))}
              </ul>
            </div>
          </div>
        </section>

        {/* calendario */}
        <div className="fondo-gris">
          <section id="calendario" className="seccion-informativa">
            <div className="seccion-encabezado">
              <span
                className="material-symbols-outlined seccion-icono"
                style={{ color: "#78A75A", fontSize: "70px" }}
              >
                calendar_month
              </span>
              <h2>
                Calendario de Actividades{" "}
                {isAdmin && (
                  <button
                    className="card_edit_btn"
                    onClick={() =>
                      addCard(
                        calendarioState,
                        setCalendarioState,
                        templates.calendario,
                      )
                    }
                  >
                    Agregar
                  </button>
                )}
              </h2>
              <p>
                Revisa nuestros próximos eventos. ¡Filtra por categoría y no te
                pierdas nada!
              </p>
            </div>

            <div className="calendario-filtros-contenedor">
              {["Todos", "Ferias", "Talleres", "Cursos", "Campañas"].map(
                (cat) => (
                  <button
                    key={cat}
                    className={`filtro-eventos ${filtro === cat ? "activo" : ""}`}
                    onClick={() => setFiltro(cat)}
                  >
                    {cat}
                  </button>
                ),
              )}
            </div>

            {eventosFiltrados.length === 0 ? (
              <div className="sin-actividades">
                <p>No hay actividades programadas en esta categoría.</p>
              </div>
            ) : (
              <div className="contenedor-flex grilla-actividades">
                {eventosFiltrados.map((evento, index) => (
                  <EditableCalendarCard
                    key={evento.id || index}
                    eventProps={evento}
                    isAdmin={isAdmin}
                    onSave={(updated) => saveCalendario(updated, index)}
                    onDelete={() => deleteCalendario(index)}
                  />
                ))}
              </div>
            )}
          </section>
        </div>

        {/* contribución */}
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
                onClick={() =>
                  addCard(encuestaState, setEncuestaState, templates.encuesta)
                }
              >
                Agregar
              </button>
            )}
          </div>
          {encuestaState.length === 0 ? (
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
              {encuestaState.map((evento, index) => (
                <EditableSurveyCard
                  key={evento.id}
                  eventProps={evento}
                  isAdmin={isAdmin}
                  onSave={(updated) =>
                    updateItem(encuestaState, setEncuestaState, index, updated)
                  }
                  onDelete={() =>
                    deleteItem(encuestaState, setEncuestaState, index)
                  }
                  onAdd={(newSurvey) =>
                    encuestaState.length === 0
                      ? addCard(
                          encuestaState,
                          setEncuestaState,
                          templates.encuesta,
                        )
                      : setEncuestaState([
                          ...encuestaState,
                          { ...newSurvey, id: Date.now() },
                        ])
                  }
                />
              ))}
            </div>
          )}
        </section>

        {/* tu opinion cuenta */}
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

        {/* contactanos y ubicanos */}
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
