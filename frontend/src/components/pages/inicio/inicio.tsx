import "./inicio.css";
import Card from "../../cartas/Card";
import EditableSlider from "../../sliders/EditableSlider";
import type { CartaItem } from "../../types/CartaItem";
import type { ConexionItem } from "../../types/ConexionItem";
import EditableCard from "../../cartas/EditableCard";
import EditableListItem from "../../cartas/EditableListItem";
import React, { useState } from "react";
import { type CalendarEvent } from "../../cartas/CalendarCard";
import EditableCalendarCard from "../../cartas/EditableCalendarCard";
import type { Survey } from "../../cartas/SurveyCard";
import EditableSurveyCard from "../../cartas/EditableSurveyCard";
/* LAS TEMPLATES DE LAS CARD PARA QUE AGREGUE LAS CARD NUEVAS */
import { templates } from "../../types/cardTemplate";

const asesorias: CartaItem[] = [
  {
    icono: "service_toolbox",
    iconoColor: "#78A75A",
    iconoTamaño: "42px",
    titulo: "Capacitación Adulto",
    descripcion:
      "Programas de alfabetización digital y capacitación tecnológica para adultos.",
  },
  {
    icono: "school",
    iconoColor: "#78A75A",
    iconoTamaño: "42px",
    titulo: "Asesoría a Jóvenes",
    descripcion:
      "¿Dudas con la TNE, el FUAS o el Servicio Militar? Te orientamos paso a paso.",
  },
];

const preuniversitario: CartaItem[] = [
  {
    icono: "book_ribbon",
    iconoColor: "#DA954B",
    iconoTamaño: "40px",
    titulo: "Competencia Lectora",
    descripcion: "Potencia tu comprensión y análisis de textos.",
  },
  {
    icono: "calculate",
    iconoColor: "#DA954B",
    iconoTamaño: "40px",
    titulo: "M1 (Matemáticas)",
    descripcion: "Refuerza tus habilidades numéricas para la PAES.",
  },
  {
    icono: "account_balance",
    iconoColor: "#DA954B",
    iconoTamaño: "40px",
    titulo: "Ciencias Sociales",
    descripcion: "Prepárate para la prueba de Historia y Cs. Sociales.",
  },
];

const cursos: CartaItem[] = [
  {
    icono: "smart_toy",
    iconoColor: "#789DE5",
    iconoFondo: "rgba(56,189,248,0.15)",
    botoncolor: "#0ea5e9",
    tituloColor: "#0ea5e9",
    sliderSombra: "0 16px 40px rgba(56,189,248,0.2)",
    titulo: "Introducción a la Inteligencia Artificial",
    descripcion:
      "Descubre los fundamentos de la IA, aprende sobre machine learning y cómo esta tecnología está cambiando el mundo.",
  },
  {
    icono: "content_copy",
    iconoColor: "#78A75A",
    iconoFondo: "rgba(32,197,141,0.15)",
    botoncolor: "#3f7d44",
    tituloColor: "#3f7d44",
    sliderSombra: "0 16px 40px rgba(32,197,141,0.2)",
    titulo: "Excel: Básico e Intermedio",
    descripcion:
      "Domina la planilla de cálculo más usada. Desde fórmulas básicas hasta tablas dinámicas.",
  },
];

const accion: CartaItem[] = [
  {
    titulo: "Proyectos de Impacto Social",
    descripcion:
      "Participa en campañas de reforestación, visitas a hogares de ancianos, colectas de alimentos y más. Conecta con otros jóvenes que, como tú, quieren dejar una huella positiva.",
    boton: "¡Quiero ser voluntario!",
    botoncolor: "",
    icono: "",
    iconoFondo: "",
    tituloColor: "inherit",
  },
];

const programas: CartaItem[] = [
  {
    icono: "family_group",
    iconoColor: "#789DE5",
    iconoFondo: "rgba(56,189,248,0.15)",
    botoncolor: "#0ea5e9",
    tituloColor: "#0ea5e9",
    sliderSombra: "0 16px 40px rgba(56,189,248,0.2)",
    titulo: "Programa Lazos",
    descripcion:
      "El Programa Lazos es una iniciativa del Gobierno de Chile que busca prevenir conductas de riesgo en jóvenes.",
    boton: "¡Conócenos aquí!",
    clase: "programa-lazos",
  },
  {
    icono: "potted_plant",
    iconoColor: "#B15330",
    iconoFondo: "rgba(197, 120, 32, 0.15)",
    botoncolor: "#B15330",
    tituloColor: "#B15330",
    sliderSombra: "0 16px 40px rgba(197, 120, 32, 0.15)",
    titulo: "Programa Senda",
    descripcion:
      "El Programa Senda es una iniciativa del Gobierno de Chile que busca prevenir conductas de riesgo en jóvenes.",
    boton: "¡Conócenos aquí!",
    clase: "programa-senda",
  },
];

const salud: CartaItem[] = [
  {
    icono: "call",
    titulo: "Fono *4141",
    subtitulo: "Prevención del Suicidio",
    descripcion:
      "No estás solo, no estás sola. Línea gratuita, confidencial y disponible las 24 horas.",
  },
  {
    icono: "home_health",
    titulo: "SAMU 131",
    subtitulo: "Emergencias Vitales",
    descripcion:
      "Llama a la Ambulancia (SAMU) en caso de riesgo vital inmediato o emergencia grave.",
  },
  {
    icono: "record_voice_over",
    titulo: "Salud Responde",
    subtitulo: "Orientación 24/7",
    descripcion:
      "Llama al 600 360 77 77 para recibir orientación de profesionales de salud.",
  },
];

const actividades: ConexionItem[] = [
  {
    icono: "event_available",
    texto: "Ferias vocacionales y de emprendimiento.",
  },
  { icono: "account_balance", texto: "Viaje cultural al Museo." },
  { icono: "conversation", texto: "Reuniones y círculos de conversación." },
];

const talleres: ConexionItem[] = [
  { icono: "mic", texto: "Taller de liderazgo y oratoria." },
  { icono: "draw", texto: "Talleres creativos (Música, dibujo, teatro)." },
  { icono: "lightbulb", texto: "Taller de debate y pensamiento crítico." },
];

const contacto = {
  direccion:
    "Herrera 360, Comuna de Santiago. (Centro Comunitario Santiago en Compañía)",
  horario: "Lunes a jueves [09:00 - 18:00 hrs] - Viernes [09:00 a 17:00 hrs]",
  email: "stgojoven@munistgo.cl",
};

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

const eventosCalendario: CalendarEvent[] = [
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

const encuesta: Survey[] = [
  {
    id: 101,
    title: "Encuesta Vacacional 2026",
    desde: "2026-12-10",
    hasta: "2026-11-15",
    url: "https://media1.tenor.com/m/RLthQ6DcOSQAAAAd/cat-dog-roblox-dance-el-gato-y-papu-perro-bailando.gif",
  },
  {
    id: 102,
    title: "Encuesta medio ambiente",
    desde: "2026-01-10",
    hasta: "2026-03-05",
    url: "https://media1.tenor.com/m/RLthQ6DcOSQAAAAd/cat-dog-roblox-dance-el-gato-y-papu-perro-bailando.gif",
  },
  {
    id: 103,
    title: "Encuesta opinion",
    desde: "2026-05-30",
    hasta: "2026-06-11",
    url: "https://media1.tenor.com/m/RLthQ6DcOSQAAAAd/cat-dog-roblox-dance-el-gato-y-papu-perro-bailando.gif",
  },
];

export default function Inicio() {
  {
    /* Acá debería de ir el auth de si es admin o no, pero mientras pongo esto */
  }
  const [isAdmin, setIsAdmin] = useState(true);

  {
    /* -- Convierte las listas en estados para que puedan ser editables -- */
  }

  {
    /* listas que usan el elemento Card normal */
  }
  const [asesoriasState, setAsesoriasState] = useState<CartaItem[]>(asesorias);
  const [preuniversitarioState, setPreuniversitarioState] =
    useState<CartaItem[]>(preuniversitario);
  const [saludState, setSaludState] = useState<CartaItem[]>(salud);

  {
    /* listas que usan el elemento List */
  }
  const [actividadesState, setActividadesState] =
    useState<ConexionItem[]>(actividades);
  const [talleresState, setTalleresState] = useState<ConexionItem[]>(talleres);

  {
    /* listas que usan el elemento Slider */
  }
  const [accionState, setAccionState] = useState<CartaItem[]>(accion);
  const [programasState, setProgramasState] = useState<CartaItem[]>(programas);
  const [cursosState, setCursosState] = useState<CartaItem[]>(cursos);

  {
    /* listas que usan el elemento Calendar */
  }
  const [calendarioState, setCalendarioState] =
    useState<CalendarEvent[]>(eventosCalendario);
  const [filtro, setFiltro] = useState<string>("Todos");

  {
    /* listas que usan el elemento Survey */
  }
  const [encuestaState, setEncuestaState] = useState<Survey[]>(encuesta);

  const eventosFiltrados = calendarioState.filter(
    (evt) =>
      filtro === "Todos" ||
      evt.categoria.toLowerCase() === filtro.toLowerCase().replace(/s$/, "s"),
  );

  {
    /** Actualizar carta */
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
    /** Eliminar carta */
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
        <label style={{ display: "flex", alignItems: "center", gap: "8px" }}>
          <input
            type="checkbox"
            checked={isAdmin}
            onChange={(e) => setIsAdmin(e.target.checked)}
          />
          Modo administrador
        </label>
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
            </div>
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
                  onSave={(updated) =>
                    updateItem(
                      asesoriasState,
                      setAsesoriasState,
                      index,
                      updated,
                    )
                  }
                  onDelete={() =>
                    deleteItem(asesoriasState, setAsesoriasState, index)
                  }
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
            </div>
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
            onSave={(updated, index) =>
              updateItem(cursosState, setCursosState, index, updated)
            }
            onDelete={(index) => deleteItem(cursosState, setCursosState, index)}
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
              onSave={(updated, index) =>
                updateItem(accionState, setAccionState, index, updated)
              }
              onDelete={(index) =>
                deleteItem(accionState, setAccionState, index)
              }
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
            onSave={(updated, index) =>
              updateItem(programasState, setProgramasState, index, updated)
            }
            onDelete={(index) =>
              deleteItem(programasState, setProgramasState, index)
            }
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
                  onSave={(updated) =>
                    updateItem(saludState, setSaludState, index, updated)
                  }
                  onDelete={() => deleteItem(saludState, setSaludState, index)}
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
              </div>
              <ul>
                {actividadesState.map((item, index) => (
                  <EditableListItem
                    key={`${item.texto}-${index}`}
                    item={item}
                    isAdmin={isAdmin}
                    onSave={(updated, seccion) => {
                      if (seccion === "actividades") {
                        updateItem(
                          actividadesState,
                          setActividadesState,
                          index,
                          updated,
                        );
                      } else {
                        setActividadesState(
                          actividadesState.filter((_, i) => i !== index),
                        );

                        setTalleresState((prev) => [...prev, updated]);
                      }
                    }}
                    onDelete={() =>
                      deleteItem(actividadesState, setActividadesState, index)
                    }
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
                {isAdmin && (
                  <button
                    className="card_edit_btn"
                    onClick={() =>
                      addCard(
                        talleresState,
                        setTalleresState,
                        templates.talleres,
                      )
                    }
                  >
                    Agregar
                  </button>
                )}
              </div>

              <ul>
                {talleresState.map((item, index) => (
                  <EditableListItem
                    key={`${item.texto}-${index}`}
                    item={item}
                    isAdmin={isAdmin}
                    onSave={(updated) =>
                      updateItem(
                        talleresState,
                        setTalleresState,
                        index,
                        updated,
                      )
                    }
                    onDelete={() =>
                      deleteItem(talleresState, setTalleresState, index)
                    }
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
                    onSave={(updated) => {
                      const newCalendarCard = [...calendarioState];
                      newCalendarCard[index] = updated;
                      setCalendarioState(newCalendarCard);
                    }}
                    onDelete={() => {
                      const newCalendarCard = calendarioState.filter(
                        (_, i) => i !== index,
                      );
                      setCalendarioState(newCalendarCard);
                    }}
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
                          {
                            ...newSurvey,
                            id: Date.now(),
                          },
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
