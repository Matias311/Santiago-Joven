import { useState, useEffect, useMemo } from "react";
import "./inicio.css";
import Card from "../../cartas/Card";
import Slider from "../../sliders/slider";
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

interface CategoriaItem {
  id: string | number;
  nombre: string;
}

interface ActividadCalendarioItem {
  id: string | number;
  titulo: string;
  descripcion?: string;
  categoriaId: string | number;
}

export default function Inicio() {
  const [datosInicio, setDatosInicio] = useState<DatosInicio | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [actividadesCalendario, setActividadesCalendario] = useState<
    ActividadCalendarioItem[]
  >([]);
  const [filtroActivo, setFiltroActivo] = useState("Todos");
  const [categorias, setCategorias] = useState<CategoriaItem[]>([]);

  useEffect(() => {
    const cargarDatos = async () => {
      setLoading(true);
      setError(null);

      const rawUrl = import.meta.env.VITE_API_URL?.replace(/\/$/, "") ?? "";

      // Validamos que exista la URL base antes de construir apiBase.
      if (!rawUrl) {
        console.error("VITE_API_URL no está definida.");
        setError("No se pudo determinar la URL del servidor.");
        setLoading(false);
        return;
      }

      const apiBase = `${rawUrl}/api/v1`;

      try {
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

        const results = await Promise.allSettled(
          endpoints.map((ep) =>
            fetch(`${apiBase}/${ep}`).then((r) => {
              if (!r.ok) {
                throw new Error(`Error ${r.status} al consultar ${ep}`);
              }
              return r.json();
            }),
          ),
        );

        const ok = (r: PromiseSettledResult<unknown>) =>
          r.status === "fulfilled"
            ? (r.value as Record<string, unknown>[])
            : [];

        results.forEach((r, i) => {
          if (r.status === "rejected") {
            console.error(`Fetch "${endpoints[i]}" falló:`, r.reason);
          }
        });

        const [
          asesoriasData,
          cursosData,
          accionData,
          programasData,
          saludData,
          actividadesData,
          ubicacionesData,
          categoriasData,
        ] = results.map(ok);

        setCategorias((categoriasData || []) as unknown as CategoriaItem[]);
        setActividadesCalendario(
          (actividadesData || []) as unknown as ActividadCalendarioItem[],
        );

        const actividades: ConexionItem[] = (actividadesData || [])
          .filter(
            (item: Record<string, unknown>) =>
              !/taller/i.test(String(item.titulo || "")),
          )
          .map((item: Record<string, unknown>) => ({
            icono: "sports_soccer",
            texto: String(item.titulo || ""),
          }));

        const talleres: ConexionItem[] = (actividadesData || [])
          .filter((item: Record<string, unknown>) =>
            /taller/i.test(String(item.titulo || "")),
          )
          .map((item: Record<string, unknown>) => ({
            icono: "work_outline",
            texto: String(item.titulo || ""),
          }));

        const datosParseados: DatosInicio = {
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
        };

        setDatosInicio(datosParseados);
      } catch (err) {
        console.error("Error al cargar los datos de inicio:", err);
        setError(
          "Ocurrió un error al cargar la información. Intenta nuevamente más tarde.",
        );
      } finally {
        setLoading(false);
      }
    };

    cargarDatos();
  }, []);

  // Memoizado para no recalcular el filtro en cada render, solo cuando
  // cambian el filtro activo o los datos de origen.
  const actividadesFiltradas = useMemo(() => {
    return filtroActivo === "Todos"
      ? actividadesCalendario
      : actividadesCalendario.filter((actividad) => {
          const categoria = categorias.find(
            (cat) => cat.id === actividad.categoriaId,
          );
          return categoria?.nombre
            ?.toLowerCase()
            .includes(filtroActivo.toLowerCase());
        });
  }, [filtroActivo, actividadesCalendario, categorias]);

  if (loading)
    return <div className="cargando">Cargando Santiago Joven...</div>;

  const datos: DatosInicio = datosInicio ?? {
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

  const {
    encabezado,
    asesorias,
    preuniversitario,
    cursos,
    accion,
    programas,
    salud,
    actividades,
    talleres,
    contacto,
  } = datos;

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
          <a href="#apoyo" className="hero-acceso">
            <Card
              icono="handshake"
              titulo="Apoyo Joven"
            />
          </a>
          <a href="#proyeccion" className="hero-acceso">
            <Card
              icono="rocket_launch"
              titulo="Proyección"
            />
          </a>
          <a href="#accion" className="hero-acceso">
            <Card
              icono="campaign"
              titulo="Acción Joven"
            />
          </a>
          <a href="#conexion" className="hero-acceso">
            <Card
              icono="groups"
              titulo="Conexión"
            />
          </a>
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
            <h3>Asesoría</h3>
            {asesorias.length === 0 ? (
              <div className="sin-actividades">
                <span
                  className="material-symbols-outlined seccion-icono"
                  style={{ color: "#AFB0B1", fontSize: "160px" }}
                >
                  schedule
                </span>
                <p>No hay asesorías disponibles por el momento.</p>
              </div>
            ) : (
              <div className="contenedor-flex">
                {asesorias.map((carta: CartaItem) => (
                  <Card
                    key={carta.titulo}
                    icono={carta.icono}
                    iconoColor={carta.iconoColor}
                    iconoTamaño={carta.iconoTamaño}
                    titulo={carta.titulo}
                    descripcion={carta.descripcion}
                  />
                ))}
              </div>
            )}
          </div>
          <div className="grupo-cartas">
            <h3>Preuniversitario</h3>
            {preuniversitario.length === 0 ? (
              <div className="sin-actividades">
                <span
                  className="material-symbols-outlined seccion-icono"
                  style={{ color: "#AFB0B1", fontSize: "160px" }}
                >
                  schedule
                </span>
                <p>
                  No hay cursos preuniversitarios disponibles por el momento.
                </p>
              </div>
            ) : (
              <div className="contenedor-flex">
                {preuniversitario.map((carta: CartaItem) => (
                  <Card
                    key={carta.titulo}
                    icono={carta.icono}
                    iconoColor={carta.iconoColor}
                    iconoTamaño={carta.iconoTamaño}
                    titulo={carta.titulo}
                    descripcion={carta.descripcion}
                  />
                ))}
              </div>
            )}
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
          {cursos.length === 0 ? (
            <div className="sin-actividades">
              <span
                className="material-symbols-outlined seccion-icono"
                style={{ color: "#AFB0B1", fontSize: "160px" }}
              >
                schedule
              </span>
              <p>No hay cursos destacados por el momento.</p>
            </div>
          ) : (
            <Slider cartas={cursos} />
          )}
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
            </div>
            {accion.length === 0 ? (
              <div className="sin-actividades">
                <span
                  className="material-symbols-outlined seccion-icono"
                  style={{ color: "#AFB0B1", fontSize: "160px" }}
                >
                  schedule
                </span>
                <p>No hay acciones joven disponibles por el momento.</p>
              </div>
            ) : (
              accion.map((carta: CartaItem) => (
                <Card
                  key={carta.titulo}
                  titulo={carta.titulo}
                  descripcion={carta.descripcion}
                  boton={carta.boton}
                />
              ))
            )}
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
          </div>
          {programas.length === 0 ? (
            <div className="sin-actividades">
              <span
                className="material-symbols-outlined seccion-icono"
                style={{ color: "#AFB0B1", fontSize: "160px" }}
              >
                schedule
              </span>
              <p>No hay programas disponibles por el momento.</p>
            </div>
          ) : (
            <Slider cartas={programas} />
          )}
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
            </div>
            <div className="grupo-cartas">
              {salud.length === 0 ? (
                <div className="sin-actividades">
                  <span
                    className="material-symbols-outlined seccion-icono"
                    style={{ color: "#AFB0B1", fontSize: "160px" }}
                  >
                    schedule
                  </span>
                  <p>
                    No hay recursos de salud mental disponibles por el momento.
                  </p>
                </div>
              ) : (
                salud.map((carta: CartaItem) => (
                  <Card
                    key={carta.titulo}
                    icono={carta.icono}
                    iconoColor={carta.iconoColor}
                    iconoTamaño={carta.iconoTamaño}
                    titulo={carta.titulo}
                    subtitulo={carta.subtitulo}
                    descripcion={carta.descripcion}
                  />
                ))
              )}
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
              <h3>Actividades</h3>
              {actividades.length === 0 ? (
                <div className="sin-actividades">
                  <span
                    className="material-symbols-outlined seccion-icono"
                    style={{ color: "#AFB0B1", fontSize: "160px" }}
                  >
                    schedule
                  </span>
                  <p>No hay actividades disponibles por el momento.</p>
                </div>
              ) : (
                <ul>
                  {actividades.map((item: ConexionItem) => (
                    <li key={item.texto}>
                      <span className="material-symbols-outlined">
                        {item.icono}
                      </span>
                      {item.texto}
                    </li>
                  ))}
                </ul>
              )}
            </div>
            <div className="lista-conexion">
              <h3>Talleres</h3>
              {talleres.length === 0 ? (
                <div className="sin-actividades">
                  <span
                    className="material-symbols-outlined seccion-icono"
                    style={{ color: "#AFB0B1", fontSize: "160px" }}
                  >
                    schedule
                  </span>
                  <p>No hay talleres disponibles por el momento.</p>
                </div>
              ) : (
                <ul>
                  {talleres.map((item: ConexionItem) => (
                    <li key={item.texto}>
                      <span className="material-symbols-outlined">
                        {item.icono}
                      </span>
                      {item.texto}
                    </li>
                  ))}
                </ul>
              )}
            </div>
          </div>
        </section>

        {/* calendario */}
        <div className="fondo-gris">
          <section
            id="calendario"
            className="calendario-eventos seccion-informativa"
          >
            <div className="seccion-encabezado">
              <span
                className="material-symbols-outlined seccion-icono"
                style={{ color: "#78A75A", fontSize: "70px" }}
              >
                calendar_month
              </span>
              <h2>Calendario de Actividades</h2>
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
            {actividadesFiltradas.length === 0 ? (
              <div className="sin-actividades">
                <span
                  className="material-symbols-outlined seccion-icono"
                  style={{ color: "#AFB0B1", fontSize: "160px" }}
                >
                  schedule
                </span>
                <p>No hay actividades programadas en esta categoría.</p>
              </div>
            ) : (
              <div className="contenedor-flex">
                {actividadesFiltradas.map(
                  (actividad: ActividadCalendarioItem) => (
                    <Card
                      key={actividad.id}
                      titulo={actividad.titulo}
                      descripcion={actividad.descripcion || ""}
                      icono="calendar_month"
                    />
                  ),
                )}
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
            <h2>Tu contribución cuenta</h2>
            <p>
              Ayúdanos a mejorar los programas comunales. Estas encuestas son
              anónimas y nos sirve para generar estadísticas y nuevas
              soluciones.
            </p>
          </div>
          <div className="sin-encuestas">
            <span
              className="material-symbols-outlined seccion-icono"
              style={{ color: "#AFB0B1", fontSize: "160px" }}
            >
              schedule
            </span>
            <p>no hay Encuestas recientes.</p>
          </div>
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
