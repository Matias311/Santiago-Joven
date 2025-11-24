import "./CCMatta.css";

import lista1 from "/lista1.png";
import lista2 from "/lista2.png";
import lista3 from "/lista3.png";
import lista4 from "/lista4.png";
import lista5 from "/lista5.png";
import lista6 from "/lista6.png";

import MattaImg from "/CentroComunitarioMattaBanner.png";

export default function CentroComunitarioMatta() {
  return (
    <>
      <div className="matta-wrapper">
        <h1 className="matta-title">Centro Comunitario Matta Sur</h1>

        <p className="matta-desc">
          El Centro Comunitario Matta es un nuevo recinto municipal que destaca
          por su arquitectura patrimonial recuperada y combinada con modernos y
          amplios espacios, que lo han hecho merecedor de diversos premios.
        </p>

        <img
          className="matta-main-img"
          src={MattaImg}
          alt="Centro Comunitario Matta"
        />

        <div className="matta-grid">
          <div className="matta-item">
            <img src={lista1} alt="" />
            <p>Auditorio con capacidad para 80 personas.</p>
          </div>

          <div className="matta-item">
            <img src={lista2} alt="" />
            <p>Sala de exposiciones.</p>
          </div>

          <div className="matta-item">
            <img src={lista3} alt="" />
            <p>2 salas multiuso (reuniones, talleres, capacitaciones).</p>
          </div>

          <div className="matta-item">
            <img src={lista4} alt="" />
            <p>Estudio de grabación, con sala de control.</p>
          </div>

          <div className="matta-item">
            <img src={lista5} alt="" />
            <p>
              Cocina y Peluquería habilitadas para la realización de talleres.
            </p>
          </div>

          <div className="matta-item">
            <img src={lista6} alt="" />
            <p>Explanada con plazoleta y bancas.</p>
          </div>
        </div>
      </div>
      <div className="servicios-wrapper">
        {/* ----------------------------- */}
        {/* ATENCIÓN SOCIAL */}
        {/* ----------------------------- */}
        <div className="servicio-block">
          <h2>Atención Social</h2>
          <p>
            Atención Social Integral, Información y gestión de Subsidios
            Fiscales, Solicitud y actualización de Registro Social de Hogares.
            <br />
            Para mayor información enviar correo a:
            <br />
            <b>atoloza@munistgo.cl – 224134725</b>
            <br />
            <span className="horario">
              Lunes a viernes · 09:00 a 14:00 hrs.
            </span>
          </p>
        </div>

        {/* ----------------------------- */}
        {/* OFICINA DE LA INFANCIA */}
        {/* ----------------------------- */}
        <div className="servicio-block">
          <h2>Oficina de la Infancia</h2>
          <p>
            Área Promoción de derechos: talleres, participación ciudadana NNNA.
            <br />
            Área atención social: primera acogida a familias.
            <br />
            Programa Polos Territoriales: espacio de cuidado para NNNA.
            <br />
            Programa Chile Crece Contigo: vinculación de familias a redes
            locales.
            <br />
            Para mayor información enviar correo a:
            <br />
            <b>oficinadelainfancia@munistgo.cl – 224134716</b>
            <br />
            <span className="horario">
              Lunes a viernes · 09:00 a 14:00 hrs.
            </span>
          </p>
        </div>

        {/* ----------------------------- */}
        {/* OFICINA DE MIGRANTES */}
        {/* ----------------------------- */}
        <div className="servicio-block">
          <h2>Oficina de Migrantes</h2>
          <p>
            Atención social y Orientación Migratoria.
            <br />
            Para mayor información enviar correo a:
            <br />
            <b>migrantes@munistgo.cl – 224134702</b>
            <br />
            <span className="horario">
              Lunes a viernes · 09:00 a 13:00 hrs.
            </span>
          </p>
        </div>

        {/* ----------------------------- */}
        {/* DESARROLLO ECONÓMICO LOCAL */}
        {/* ----------------------------- */}
        <div className="servicio-block">
          <h2>D. de Desarrollo Económico Local</h2>
          <p>
            Intermediación laboral, Emprendimiento y Capacitación.
            <br />
            Para mayor información enviar correo a:
            <br />
            <b>mpenar@munistgo.cl – 224134714</b>
            <br />
            <span className="horario">
              Lunes a viernes · 09:00 a 14:00 hrs.
            </span>
          </p>
        </div>

        {/* ----------------------------- */}
        {/* OFICINA DE LA MUJER */}
        {/* ----------------------------- */}
        <div className="servicio-block">
          <h2>Oficina de la Mujer</h2>
          <p>
            Escuela de Emprendedoras. Charlas sobre prevención de violencia de
            género y derechos laborales.
            <br />
            Talleres de Salud Mental, Cocina, Peluquería, Cosmética Natural,
            Manualidades.
            <br />
            Asesorías Legales (Derecho de familia).
            <br />
            Para mayor información enviar correo a:
            <br />
            <b>programamujer@munistgo.cl – 224134702</b>
            <br />
            <span className="horario">
              Lunes a viernes · 09:00 a 14:00 hrs.
            </span>
          </p>
        </div>

        {/* ----------------------------- */}
        {/* GESTIÓN COMUNITARIA */}
        {/* ----------------------------- */}
        <div className="servicio-block">
          <h2>Departamento de Gestión Comunitaria</h2>
          <p>
            Oficina de atención de los gestores territoriales de la Agrupación
            Vecinal N°10.
            <br />
            <b>Claudia Orrego</b> – corrego@munistgo.cl – 224134727
            <br />
            <b>Mathew Spencer</b> – mspencer@munistgo.cl
            <br />
            <b>Manuela Pacheco</b> – mpacheco@munistgo.cl
            <br />
            <span className="horario">
              Lunes a viernes · 09:00 a 17:30 hrs.
            </span>
          </p>
        </div>

        {/* ----------------------------- */}
        {/* DISCAPACIDAD */}
        {/* ----------------------------- */}
        <div className="servicio-block">
          <h2>Oficina de la Discapacidad</h2>
          <p>
            Sala de estimulación multisensorial para niños, niñas y adolescentes
            en situación de discapacidad.
            <br />
            Para mayor información enviar correo a:
            <br />
            <b>of.discapacidad@munistgo.cl – 224134708</b>
          </p>
        </div>

        {/* ----------------------------- */}
        {/* TALLERES DEPORTIVOS */}
        {/* ----------------------------- */}
        <div className="servicio-block">
          <h2>Talleres Deportivos</h2>
          <p>
            Talleres de Yoga y Entrenamiento Funcional.
            <br />
            Para inscripciones escribir a:
            <br />
            <b>deportes@munistgo.cl</b>
          </p>

          <p className="subtitulo">Yoga</p>
          <p>
            Martes y jueves: 09:00–09:45, 10:00–10:45, 11:00–11:45
            <br />
            Martes y viernes: 18:00–18:45, 19:00–19:45
          </p>

          <p className="subtitulo">Entrenamiento Funcional</p>
          <p>Martes y jueves: 18:00–18:45, 19:00–19:45</p>
        </div>

        {/* ----------------------------- */}
        {/* SALAS Y ESPACIOS */}
        {/* ----------------------------- */}
        <div className="servicio-block">
          <h2>Sala de Exposiciones, Auditorio y Salas de Reuniones</h2>
          <p>
            Espacios disponibles para organizaciones de la comunidad.
            <br />
            Para mayor información enviar correo a:
            <br />
            <b>centrocomunitariomattasur@munistgo.cl</b>
            <br />
            Lunes a viernes: 09:00–20:00 hrs.
            <br />
            Sábado: 09:00–16:00 hrs.
          </p>
        </div>

        {/* ----------------------------- */}
        {/* ESTUDIO DE GRABACIÓN */}
        {/* ----------------------------- */}
        <div className="servicio-block">
          <h2>Estudio de Grabación</h2>
          <p>
            Realización de proyectos fonográficos comunitarios con asesoría
            profesional.
            <br />
            Para mayor información escribir a:
            <br />
            <b>centrocomunitariomattasur@munistgo.cl</b>
            <br />
            Lunes a viernes: 09:00–20:00 hrs.
            <br />
            Sábado: 09:00–16:00 hrs.
          </p>
        </div>
      </div>
    </>
  );
}
