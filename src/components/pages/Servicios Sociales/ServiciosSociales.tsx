import wave from "../../../assets/wave-effect.jpg";
import InformesSociales from "../../../assets/ServiciosSocialesImg/InformesSociales.jpg";
import BecaEstudioMuni from "../../../assets/ServiciosSocialesImg/BecaEstudiosMuni.jpg";
import AtencionSocial from "../../../assets/ServiciosSocialesImg/AtencionSocial.jpg";
import ProgramaNocheDigna from "../../../assets/ServiciosSocialesImg/ProgramaNocheDigna.jpg";
import TrasladoEnseres from "../../../assets/ServiciosSocialesImg/TrasladoEnseres.jpg";
import EmergenciaSocial from "../../../assets/ServiciosSocialesImg/EmergenciaSocial.jpg";
import AsesoriaReparacionesEstadistica from "../../../assets/ServiciosSocialesImg/AsesoriaReparacionesEstadistica.jpg";
import FichaRegistro from "../../../assets/ServiciosSocialesImg/FichaRegistroSocial.jpg";
import ProgramaFamilias from "../../../assets/ServiciosSocialesImg/ProgramaFamilias.jpg";
import ProgramaHabitabilidad from "../../../assets/ServiciosSocialesImg/ProgramaHabitabilidad.jpg";
import ProgramaPersonasCalle from "../../../assets/ServiciosSocialesImg/ProgramapPersonasCalle.jpg";
import ProgramaApoyoIntegral from "../../../assets/ServiciosSocialesImg/ProgramaApoyoIntegral.jpg";
import ResidenciaPortugal from "../../../assets/ServiciosSocialesImg/ResidenciaPortugal.jpg";
import BonoxHijo from "../../../assets/ServiciosSocialesImg/BonoHijo.jpg";
import PensionGarantizada from "../../../assets/ServiciosSocialesImg/PensionGarantizada.jpg";
import AportePrevisional from "../../../assets/ServiciosSocialesImg/AportePrevisional.jpg";
import SubsidioFamiliar from "../../../assets/ServiciosSocialesImg/SubsidioFamiliar.jpg";
import SubsidioMaternal from "../../../assets/ServiciosSocialesImg/SubsidioMaternal.jpg";
import SubsidioAMadre from "../../../assets/ServiciosSocialesImg/SubsidioAMadre.jpg";
import SubsidioRecienNacido from "../../../assets/ServiciosSocialesImg/SubsidioRecienNacido.jpg";
import PensionBasicaInvalidez from "../../../assets/ServiciosSocialesImg/PensionBasicaInvalidez.jpg";
import SubsidioAseo from "../../../assets/ServiciosSocialesImg/SubsidioAseo.jpg";
import SubsidioAgua from "../../../assets/ServiciosSocialesImg/SubsidioAgua.jpg";

export default function ServiciosSociales() {
  return (
    <section className="main-section">
      <h1 className="title">Servicios Sociales</h1>

      <section className="section-service-list">
        {/* --------------------Fila 1-------------------- */}
        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Informes Social - Asistencia"
              className="service-containerImg"
              src={InformesSociales}
            />
            <h3 className="subtitle">Informes Sociales - Asistencia</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Beca de Estudio I. Municipalidad de Santiago Postulacion y Asignacion"
              className="service-containerImg"
              src={BecaEstudioMuni}
            />
            <h3 className="subtitle">
              Beca de Estudio I. Municipalidad de Santiago - Postulación y
              Asignación
            </h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Atención Social Integral - Asistencia"
              className="service-containerImg"
              src={AtencionSocial}
            />
            <h3 className="subtitle">Atención Social Integral - Asistencia</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        {/* --------------------Fila 2-------------------- */}
        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Programa Noche Digna Centro de Acogida"
              className="service-containerImg"
              src={ProgramaNocheDigna}
            />
            <h3 className="subtitle">
              Programa Noche Digna - Centro de Acogida Municipal para Personas
              en Situación de Calle
            </h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Traslado de Enseres en Vehiculo Municipal"
              className="service-containerImg"
              src={TrasladoEnseres}
            />
            <h3 className="subtitle">
              Traslado de Enseres en Vehículo Municipal
            </h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Emergencia Social"
              className="service-containerImg"
              src={EmergenciaSocial}
            />
            <h3 className="subtitle">Emergencia Social</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        {/* --------------------Fila 3-------------------- */}
        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Asesoria en Reparacion Menores de Viviendas"
              className="service-containerImg"
              src={AsesoriaReparacionesEstadistica}
            />
            <h3 className="subtitle">
              Asesoría en Reparaciones Menores de Viviendas
            </h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Estadísticas Sociales - Informacion"
              className="service-containerImg"
              src={AsesoriaReparacionesEstadistica}
            />
            <h3 className="subtitle">Estadísticas Sociales - Información</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Ficha Registro Social de Hogares Situacion de Calle"
              className="service-containerImg"
              src={FichaRegistro}
            />
            <h3 className="subtitle">
              Ficha Registro Social de Hogares - Ingreso Personas en Situación
              de Calle
            </h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        {/* --------------------Fila 4-------------------- */}
        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Ficha Registro Social - Actualizacion"
              className="service-containerImg"
              src={FichaRegistro}
            />
            <h3 className="subtitle">
              Ficha Registro Social de Hogares - Actualización
            </h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Ficha Registro Social - Ingreso/Entrega de Cartola"
              className="service-containerImg"
              src={FichaRegistro}
            />
            <h3 className="subtitle">
              Ficha Registro Social de Hogares - Ingreso/Entrega de Cartola
            </h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Programa Familias"
              className="service-containerImg"
              src={ProgramaFamilias}
            />
            <h3 className="subtitle">Programa Familias</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        {/* --------------------Fila 5-------------------- */}
        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Programa de Habitabilidad"
              className="service-containerImg"
              src={ProgramaHabitabilidad}
            />
            <h3 className="subtitle">Programa de Habitabilidad</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Programa Personas situación de calle"
              className="service-containerImg"
              src={ProgramaPersonasCalle}
            />
            <h3 className="subtitle">
              Programa Personas en Situación de Calle (PSC)
            </h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Programa de Apoyo Integral al Adulto Mayor Vinculos"
              className="service-containerImg"
              src={ProgramaApoyoIntegral}
            />
            <h3 className="subtitle">
              Programa de Apoyo Integral al Adulto Mayor «Vínculos»
            </h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        {/* --------------------Fila 6-------------------- */}
        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Residencia Portugal - Programa Noche Digna"
              className="service-containerImg"
              src={ResidenciaPortugal}
            />
            <h3 className="subtitle">
              Residencia Portugal - Programa Noche Digna
            </h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Bono por Hijo"
              className="service-containerImg"
              src={BonoxHijo}
            />
            <h3 className="subtitle">Bono por Hijo</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Pensión Garantizada Universal"
              className="service-containerImg"
              src={PensionGarantizada}
            />
            <h3 className="subtitle">Pensión Garantizada Universal</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        {/* --------------------Fila 7-------------------- */}
        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Aporte Previsional Solidario de Invalidez"
              className="service-containerImg"
              src={AportePrevisional}
            />
            <h3 className="subtitle">
              Aporte Previsional Solidario de Invalidez
            </h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Subsidio Familiar"
              className="service-containerImg"
              src={SubsidioFamiliar}
            />
            <h3 className="subtitle">Subsidio Familiar</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Subsidio Maternal"
              className="service-containerImg"
              src={SubsidioMaternal}
            />
            <h3 className="subtitle">Subsidio Maternal</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        {/* --------------------Fila 8-------------------- */}
        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Subsidio a la Madre"
              className="service-containerImg"
              src={SubsidioAMadre}
            />
            <h3 className="subtitle">Subsidio a la Madre</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Subsidio al Recién Nacido"
              className="service-containerImg"
              src={SubsidioRecienNacido}
            />
            <h3 className="subtitle">Subsidio al Recién Nacido</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Pensión Básica Solidaria en Invalidez"
              className="service-containerImg"
              src={PensionBasicaInvalidez}
            />
            <h3 className="subtitle">Pensión Básica Solidaria de Invalidez</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        {/* --------------------Fila 9-------------------- */}
        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Subsidio de Aseo"
              className="service-containerImg"
              src={SubsidioAseo}
            />
            <h3 className="subtitle">Subsidio de Aseo</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Subsidio Agua Potable"
              className="service-containerImg"
              src={SubsidioAgua}
            />
            <h3 className="subtitle">Subsidio Agua Potable</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>
      </section>

      {/* imagen wave diseño xd */}
      <img src={wave} className="wave.img" />
    </section>
  );
}
