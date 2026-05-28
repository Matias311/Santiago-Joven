import './inicio.css';
import Card from '../../cartas/Card';
import Slider from '../../sliders/slider';
import type { Info } from '../../types/typeCard';

const info: Info = {
  apoyo: {
    asesorias: [
      { icono: "service_toolbox", iconoColor:"#78A75A", iconoTamaño:"42px", titulo: "Capacitación Adulto", descripcion: "Programas de alfabetización digital y capacitación tecnológica para adultos." },
      { icono: "school", iconoColor:"#78A75A", iconoTamaño:"42px", titulo: "Asesoría a Jóvenes", descripcion: "¿Dudas con la TNE, el FUAS o el Servicio Militar? Te orientamos paso a paso." }
    ],
    preuniversitario: [
      { icono: "book_ribbon", iconoColor:"#DA954B", iconoTamaño:"40px", titulo: "Competencia Lectora", descripcion: "Potencia tu comprensión y análisis de textos." },
      { icono: "calculate", iconoColor:"#DA954B", iconoTamaño:"40px", titulo: "M1 (Matemáticas)", descripcion: "Refuerza tus habilidades numéricas para la PAES." },
      { icono: "account_balance", iconoColor:"#DA954B", iconoTamaño:"40px", titulo: "Ciencias Sociales", descripcion: "Prepárate para la prueba de Historia y Cs. Sociales." }
    ]
  },
  cursos: [
    { icono: "smart_toy", iconoColor:"#789DE5", iconoFondo: "rgba(56,189,248,0.15)", botoncolor: "#0ea5e9", tituloColor: "#0ea5e9", sliderSombra: "0 16px 40px rgba(56,189,248,0.2)", titulo: "Introducción a la Inteligencia Artificial", descripcion: "Descubre los fundamentos de la IA, aprende sobre machine learning y cómo esta tecnología está cambiando el mundo." },
    { icono: "content_copy", iconoColor:"#78A75A", iconoFondo: "rgba(32,197,141,0.15)", botoncolor: "#3f7d44", tituloColor: "#3f7d44", sliderSombra: "0 16px 40px rgba(32,197,141,0.2)", titulo: "Excel: Básico e Intermedio", descripcion: "Domina la planilla de cálculo más usada. Desde fórmulas básicas hasta tablas dinámicas." }
  ],
  accion: [
    { titulo: "Proyectos de Impacto Social", descripcion: "Participa en campañas de reforestación, visitas a hogares de ancianos, colectas de alimentos y más. Conecta con otros jóvenes que, como tú, quieren dejar una huella positiva.", boton: "¡Quiero ser voluntario!" }
  ],
  programas: [
    { icono: "family_group", iconoColor:"#789DE5", iconoFondo: "rgba(56,189,248,0.15)", botoncolor: "#0ea5e9", tituloColor: "#0ea5e9", sliderSombra: "0 16px 40px rgba(56,189,248,0.2)", titulo: "Programa Lazos", descripcion: "El Programa Lazos es una iniciativa del Gobierno de Chile que busca prevenir conductas de riesgo en jóvenes.", boton: "¡Conócenos aquí!", clase: "programa-lazos" },
    { icono: "potted_plant", iconoColor:"#B15330", iconoFondo: "rgba(197, 120, 32, 0.15)", botoncolor: "#B15330", tituloColor: "#B15330", sliderSombra: "0 16px 40px rgba(197, 120, 32, 0.15)", titulo: "Programa Senda", descripcion: "El Programa Senda es una iniciativa del Gobierno de Chile que busca prevenir conductas de riesgo en jóvenes.", boton: "¡Conócenos aquí!", clase: "programa-senda" }
  ],
  salud: [
    { icono: "call", titulo: "Fono *4141", subtitulo: "Prevención del Suicidio", descripcion: "No estás solo, no estás sola. Línea gratuita, confidencial y disponible las 24 horas." },
    { icono: "home_health", titulo: "SAMU 131", subtitulo: "Emergencias Vitales", descripcion: "Llama a la Ambulancia (SAMU) en caso de riesgo vital inmediato o emergencia grave." },
    { icono: "record_voice_over", titulo: "Salud Responde", subtitulo: "Orientación 24/7", descripcion: "Llama al 600 360 77 77 para recibir orientación de profesionales de salud." }
  ],
  conexion: {
    actividades: [
      { icono: "event_available", texto: "Ferias vocacionales y de emprendimiento." },
      { icono: "account_balance", texto: "Viaje cultural al Museo." },
      { icono: "conversation", texto: "Reuniones y círculos de conversación." }
    ],
    talleres: [
      { icono: "mic", texto: "Taller de liderazgo y oratoria." },
      { icono: "draw", texto: "Talleres creativos (Música, dibujo, teatro)." },
      { icono: "lightbulb", texto: "Taller de debate y pensamiento crítico." }
    ]
  },
  contacto: {
    direccion: "Herrera 360, Comuna de Santiago. (Centro Comunitario Santiago en Compañía)",
    horario: "Lunes a jueves [09:00 - 18:00 hrs] - Viernes [09:00 a 17:00 hrs]",
    email: "stgojoven@munistgo.cl"
  }
};

export default function Inicio() {
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
          <Card icono="handshake" iconoColor="#E3E3E3" iconoTamaño="2.5rem" titulo="Apoyo Joven" />
          <Card icono="rocket" iconoColor="#E3E3E3" iconoTamaño="2.5rem" titulo="Proyección" />
          <Card icono="campaign" iconoColor="#E3E3E3" iconoTamaño="2.5rem" titulo="Acción Joven" />
          <Card icono="diversity_3" iconoColor="#E3E3E3" iconoTamaño="2.5rem" titulo="Conexión" />
        </div>
      </header>

      {/* contenido principal */}
      <main className="contenido-principal">

        {/* apoyo joven */}
        <section id="apoyo" className="seccion-informativa">
          <div className="seccion-encabezado">
            <span className="material-symbols-outlined seccion-icono" style={{ color: "#789DE5", fontSize: "70px" }}>handshake</span>
            <h2>Apoyo Joven</h2>
            <p>Te acompañamos en tus trámites, estudios y beneficios. Encuentra aquí toda la asesoría que necesitas.</p>
          </div>
          <div className="grupo-cartas">
            <h3>Asesoría</h3>
            <div className="contenedor-flex">
              {info.apoyo.asesorias.map((carta) => (
                <Card key={carta.titulo} icono={carta.icono} iconoColor={carta.iconoColor} iconoTamaño={carta.iconoTamaño} titulo={carta.titulo} descripcion={carta.descripcion} />
              ))}
            </div>
          </div>
          <div className="grupo-cartas">
            <h3>Preuniversitario</h3>
            <div className="contenedor-flex">
              {info.apoyo.preuniversitario.map((carta) => (
                <Card key={carta.titulo} icono={carta.icono} iconoColor={carta.iconoColor} iconoTamaño={carta.iconoTamaño} titulo={carta.titulo} descripcion={carta.descripcion} />
              ))}
            </div>
          </div>
        </section>

        {/* proyeccion joven */}
        <section id="proyeccion" className="seccion-informativa">
          <div className="seccion-encabezado">
            <span className="material-symbols-outlined seccion-icono" style={{ color: "#50A116", fontSize: "70px" }}>rocket_launch</span>
            <h2>Proyección Joven</h2>
            <p>Impulsa tu futuro. Aquí encontrarás cursos y herramientas para tu crecimiento personal y profesional.</p>
          </div>
          <h3 style={{ textAlign: "center", fontSize: "1.4rem" }}>Cursos Destacados</h3>
          <Slider cartas={info.cursos} />
        </section>

        {/* accion joven */}
        <div className="fondo-gris">
          <section id="accion" className="accion-joven seccion-informativa">
            <div className="seccion-encabezado">
              <span className="material-symbols-outlined seccion-icono" style={{ fontSize: "70px" }}>campaign</span>
              <h2>Acción Joven</h2>
              <p>¡Tu energía puede cambiar las cosas! Súmate a nuestras iniciativas sociales y proyectos de voluntariado.</p>
            </div>
            {info.accion.map((carta) => (
              <Card key={carta.titulo} titulo={carta.titulo} descripcion={carta.descripcion} boton={carta.boton} />
            ))}
          </section>
        </div>

        {/* nuestros programas */}
        <section id="programas" className="seccion-informativa">
          <div className="seccion-encabezado">
            <span className="material-symbols-outlined seccion-icono" style={{ fontSize: "80px" }}>handshake</span>
            <h2>Nuestros Programas</h2>
            <p>Conoce los programas insertos en la Oficina de la Juventud para apoyarte.</p>
          </div>
          <Slider cartas={info.programas} />
        </section>

        {/* salud mental */}
        <div className="fondo-gris">
          <section id="salud" className="salud-mental-bienestar seccion-informativa">
            <div className="seccion-encabezado">
              <span className="material-symbols-outlined seccion-icono" style={{ color: "#789DE5", fontSize: "70px" }}>cardiology</span>
              <h2>Salud Mental y Bienestar</h2>
              <p>Tu bienestar emocional es prioridad. No estás solo/a. Aquí encontrarás canales de ayuda inmediata y profesional.</p>
            </div>
            <div className="grupo-cartas">
              {info.salud.map((carta) => (
                <Card key={carta.titulo} icono={carta.icono} iconoColor={carta.iconoColor} iconoTamaño={carta.iconoTamaño} titulo={carta.titulo} subtitulo={carta.subtitulo} descripcion={carta.descripcion} />
              ))}
            </div>
          </section>
        </div>

        {/* conexion comunitaria */}
        <section id="conexion" className="conexion-comunitaria seccion-informativa">
          <div className="seccion-encabezado">
            <span className="material-symbols-outlined seccion-icono" style={{ color: "#789DE5", fontSize: "70px" }}>groups</span>
            <h2>Conexión Comunitaria</h2>
            <p>Participa en actividades recreativas, culturales y formativas. Conoce gente nueva y desarrolla tus talentos.</p>
          </div>
          <div className="contenedor-flex">
            <div className="lista-conexion">
              <h3>Actividades</h3>
              <ul>
                {info.conexion.actividades.map((item) => (
                  <li key={item.texto}>
                    <span className="material-symbols-outlined">{item.icono}</span>
                    {item.texto}
                  </li>
                ))}
              </ul>
            </div>
            <div className="lista-conexion">
              <h3>Talleres</h3>
              <ul>
                {info.conexion.talleres.map((item) => (
                  <li key={item.texto}>
                    <span className="material-symbols-outlined">{item.icono}</span>
                    {item.texto}
                  </li>
                ))}
              </ul>
            </div>
          </div>
        </section>

        {/* calendario */}
        <div className="fondo-gris">
          <section id="calendario" className="calendario-eventos seccion-informativa">
            <div className="seccion-encabezado">
              <span className="material-symbols-outlined seccion-icono" style={{ color: "#78A75A", fontSize: "70px" }}>calendar_month</span>
              <h2>Calendario de Actividades</h2>
              <p>Revisa nuestros próximos eventos. ¡Filtra por categoría y no te pierdas nada!</p>
            </div>
            <div className="calendario-contenido">
              <button className="filtro-eventos">Todos</button>
              <button className="filtro-eventos">Ferias</button>
              <button className="filtro-eventos">Talleres</button>
              <button className="filtro-eventos">Cursos</button>
              <button className="filtro-eventos">Campañas</button>
            </div>
            <div className="sin-actividades">
              <span className="material-symbols-outlined seccion-icono" style={{ color: "#AFB0B1", fontSize: "160px" }}>schedule</span>
              <p>No hay actividades programadas en esta categoría.</p>
            </div>
          </section>
        </div>

        {/* contribución */}
        <section id="contribucion" className="tu-contribucion seccion-informativa">
          <div className="seccion-encabezado">
            <span className="material-symbols-outlined seccion-icono" style={{ color: "#D08370", fontSize: "70px" }}>calendar_month</span>
            <h2>Tu contribución cuenta</h2>
            <p>Ayúdanos a mejorar los programas comunales. Estas encuestas son anónimas y nos sirve para generar estadísticas y nuevas soluciones.</p>
          </div>
          <div className="sin-encuestas">
            <span className="material-symbols-outlined seccion-icono" style={{ color: "#AFB0B1", fontSize: "160px" }}>schedule</span>
            <p>no hay Encuestas recientes.</p>
          </div>
        </section>

        {/* tu opinion cuenta */}
        <div className="fondo-gris">
          <section id="opinion" className="tu-opinion-cuenta seccion-informativa">
            <div className="seccion-encabezado">
              <span className="material-symbols-outlined seccion-icono" style={{ color: "#D08370", fontSize: "70px" }}>content_paste</span>
              <h2>Tu opinión cuenta</h2>
              <p>Si tienes opiniones acerca de la pagina, hazlo saber para lograr mejorar la pagina!</p>
            </div>
            <div className="formulario-opinion">
              <label>Nombre</label>
              <input type="text" />
              <label>Mensaje</label>
              <textarea></textarea>
              <div className="formulario-footer">
                <button className="btn-enviar-opinion">Enviar Opinión</button>
                <span>Para enviar un comentario, debe estar con sesión iniciada</span>
              </div>
            </div>
          </section>
        </div>

        {/* contactanos y ubicanos */}
        <section id="contacto" className="contacto-ubicacion seccion-informativa">
          <div className="seccion-encabezado">
            <span className="material-symbols-outlined seccion-icono" style={{ color: "#0077FF", fontSize: "70px" }}>location_on</span>
            <h2>Contáctanos y Ubícanos</h2>
            <p>¿Tienes preguntas? Escríbenos. También puedes visitarnos en nuestra oficina.</p>
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
                <span className="material-symbols-outlined seccion-icono">location_on</span>
                <p>{info.contacto.direccion}</p>
              </div>
              <div className="ubicacion-item">
                <span className="material-symbols-outlined seccion-icono">schedule</span>
                <p>{info.contacto.horario}</p>
              </div>
              <div className="ubicacion-item">
                <span className="material-symbols-outlined seccion-icono">email</span>
                <p>{info.contacto.email}</p>
              </div>
            </div>
          </div>
        </section>

      </main>
    </>
  );
}