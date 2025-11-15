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
    <div style={styles.pageContainer}>
      <h1 style={styles.title}>Mujeres e Igualdad de Género</h1>
      <section
        style={{
          display: "grid",
          gridTemplateColumns: "repeat(3, 1fr)",
          rowGap: 50,
          placeItems: "center",
        }}
      >
        {/* --------------------Fila 1-------------------- */}
        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Informes Social - Asistencia"
              style={styles.serviceContainerImg}
              src={InformesSociales}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Informes Sociales - Asistencia
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Beca de Estudio I. Municipalidad de Santiago Postulacion y Asignacion"
              style={styles.serviceContainerImg}
              src={BecaEstudioMuni}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Beca de Estudio I. Municipalidad de Santiago - Postulacion y
              Asignación
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Atencion Social Integral - Asistencia"
              style={styles.serviceContainerImg}
              src={AtencionSocial}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Atención Social Integral - Asistencia
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        {/* --------------------Fila 2-------------------- */}
        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Programa Noche Digna Centro de Acogida"
              style={styles.serviceContainerImg}
              src={ProgramaNocheDigna}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Programa Noche Digna - Centro de Acogida Municipal para Personas
              en Situación de Calle
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Traslado de Enseres en Vehiculo Municipal"
              style={styles.serviceContainerImg}
              src={TrasladoEnseres}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Traslado de Enseres en Vehículo Municipal
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Emergencia Social"
              style={styles.serviceContainerImg}
              src={EmergenciaSocial}
            ></img>
            <h3 style={styles.serviceContainerTitle}>Emergencia Social</h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        {/* --------------------Fila 3-------------------- */}
        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Asesoria en Reparacion Menores de Viviendas"
              style={styles.serviceContainerImg}
              src={AsesoriaReparacionesEstadistica}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Asesoría en Reparaciones Menores de Viviendas
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Estadísticas Sociales - Informacion"
              style={styles.serviceContainerImg}
              src={AsesoriaReparacionesEstadistica}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Estadísticas Sociales - Información
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Ficha de Registro Social de Social de Hogares Situacion de Calle"
              style={styles.serviceContainerImg}
              src={FichaRegistro}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Ficha Registro Social de Hogares - Ingreso Personas en Situación
              de Calle
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        {/* --------------------Fila 4-------------------- */}
        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Ficha de Registro Social de Social de Hogares Actualizacion"
              style={styles.serviceContainerImg}
              src={FichaRegistro}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Ficha Registro Social de Hogares - Actualización
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Ficha de Registro Social de Social de Hogares Ingreso/Entrega de Cartola"
              style={styles.serviceContainerImg}
              src={FichaRegistro}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Ficha Registro Social de Hogares - Ingreso/Entrega de Cartola
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Programa Familias"
              style={styles.serviceContainerImg}
              src={ProgramaFamilias}
            ></img>
            <h3 style={styles.serviceContainerTitle}>Programa Familias</h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        {/* --------------------Fila 5-------------------- */}

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Programa de Habitabilidad"
              style={styles.serviceContainerImg}
              src={ProgramaHabitabilidad}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Programa de Habitabilidad
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Programa Personas situación de calle"
              style={styles.serviceContainerImg}
              src={ProgramaPersonasCalle}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Programa Personas en Situación de Calle (PSC)
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Programa de Apoyo Integral al Adulto Mayor <<Vinculos>>"
              style={styles.serviceContainerImg}
              src={ProgramaApoyoIntegral}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Programa de Apoyo Integral al Adulto Mayor «Vinculos»
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        {/* --------------------Fila 6-------------------- */}

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Residencia Portugal - Programa Noche Digna"
              style={styles.serviceContainerImg}
              src={ResidenciaPortugal}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Residencia Portugal - Programa Noche Digna
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Bono por Hijo"
              style={styles.serviceContainerImg}
              src={BonoxHijo}
            ></img>
            <h3 style={styles.serviceContainerTitle}>Bono por Hijo</h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Pensión Garantizada Universal"
              style={styles.serviceContainerImg}
              src={PensionGarantizada}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Pensión Garantizada Universal
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        {/* --------------------Fila 7-------------------- */}

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Aporte Previsional Solidario de Invalidez"
              style={styles.serviceContainerImg}
              src={AportePrevisional}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Aporte Previsional Solidario de Invalidez
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Subsidio Familiar"
              style={styles.serviceContainerImg}
              src={SubsidioFamiliar}
            ></img>
            <h3 style={styles.serviceContainerTitle}>Subsidio Familiar</h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Subsidio Maternal"
              style={styles.serviceContainerImg}
              src={SubsidioMaternal}
            ></img>
            <h3 style={styles.serviceContainerTitle}>Subsidio Maternal</h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        {/* --------------------Fila 8-------------------- */}

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Subsidio a la Madre"
              style={styles.serviceContainerImg}
              src={SubsidioAMadre}
            ></img>
            <h3 style={styles.serviceContainerTitle}>Subsidio a la Madre</h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Subsidio al Recién Nacido"
              style={styles.serviceContainerImg}
              src={SubsidioRecienNacido}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Subsidio al Recién Nacido
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Pensión Básica Solidaria en Invalidez"
              style={styles.serviceContainerImg}
              src={PensionBasicaInvalidez}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Pensión Básica Solidaria de Invalidez
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        {/* --------------------Fila 9-------------------- */}

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Subsidio de Aseo"
              style={styles.serviceContainerImg}
              src={SubsidioAseo}
            ></img>
            <h3 style={styles.serviceContainerTitle}>Subsidio de Aseo</h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Subsidio Agua Potable"
              style={styles.serviceContainerImg}
              src={SubsidioAgua}
            ></img>
            <h3 style={styles.serviceContainerTitle}>Subsidio Agua Potable</h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>
      </section>
      {/* imagen wave diseño xd */}
      <img src={wave} style={{ width: "100%", display: "block" }}></img>
    </div>
  );
}

const styles = {
  pageContainer: {
    display: "flex",
    flexDirection: "column" as const,
    gap: 100,
  },

  title: {
    color: "#6080BF",
    fontSize: "4rem",
    fontWeight: 600,
    textAlign: "center" as const,
  },

  serviceContainerTitle: {
    color: "#6080BF",
    fontSize: "1.5rem",
    fontWeight: 500,
  },

  serviceContainerP: {
    color: "#6080BF",
    fontSize: "1.3rem",
    fontWeight: 300,
  },

  serviceContainer: {
    display: "flex",
    flexDirection: "column" as const,
    gap: "5%",
    overflow: "hidden",
    textAlign: "center" as const,
    width: 530,
    height: 407,
    borderStyle: "solid",
    borderWidth: 1,
    borderColor: "#C2C2C2",
    borderRadius: 60,
  },

  serviceContainerImg: {
    width: "100%",
    height: 245,
  },
};
