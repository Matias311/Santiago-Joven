import wave from "../../../assets/wave-effect.jpg";
import AtencionIntegral from "../../../assets/MujeresIgualdadGenero/AtencionIntegral.jpg";
import BienestarAutocuidado from "../../../assets/MujeresIgualdadGenero/BienestarAutocuidado.jpg";
import BrigadaCuidados from "../../../assets/MujeresIgualdadGenero/BrigadaCuidados.jpg";
import EscuelaEmprendedoras from "../../../assets/MujeresIgualdadGenero/EscuelaEmprendedoras.jpg";
import EstudiosCapacitacionGenero from "../../../assets/MujeresIgualdadGenero/EstudiosCapacitacionGenero.jpg";
import OrganizacionesMujeres from "../../../assets/MujeresIgualdadGenero/OrganizacionesMujeres.jpg";
import Programa4a7 from "../../../assets/MujeresIgualdadGenero/Programa4a7.jpg";
import ProgramaMujerDerechoSexRep from "../../../assets/MujeresIgualdadGenero/ProgramaMujerDerechoSexRep.jpg";
import ProgramaNoViolenciaGenero from "../../../assets/MujeresIgualdadGenero/ProgramaNoViolenciaGenero.jpg";
import ProgramaPrevencionViolencia from "../../../assets/MujeresIgualdadGenero/ProgramaPrevencionViolencia.jpg";
import CentroMujerSantiago from "../../../assets/MujeresIgualdadGenero/CentroMujerSantiago.jpg";

export default function MujeresIgualdadGenero() {
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
              alt="Imagen de Bienestar y Autocuidado"
              style={styles.serviceContainerImg}
              src={BienestarAutocuidado}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Bienestar y Autocuidado
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Estudios y capacitacion de genero"
              style={styles.serviceContainerImg}
              src={EstudiosCapacitacionGenero}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Estudios y capacitacion de género
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Organizaciones de Mujeres"
              style={styles.serviceContainerImg}
              src={OrganizacionesMujeres}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Organizaciones de Mujeres
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        {/* --------------------Fila 2-------------------- */}
        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Atencion Integral Social, Psicologico y Juridico"
              style={styles.serviceContainerImg}
              src={AtencionIntegral}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Atención Integral (Social - Psicológico - Jurídico)
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Brigada de Cuidados"
              style={styles.serviceContainerImg}
              src={BrigadaCuidados}
            ></img>
            <h3 style={styles.serviceContainerTitle}>Brigada de Cuidados</h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Talleres Programa Santiago x la No Violencia de Genero"
              style={styles.serviceContainerImg}
              src={ProgramaNoViolenciaGenero}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Talleres Programa Santiago x la No Violencia de Género
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        {/* --------------------Fila 3-------------------- */}
        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Escuela de Emprendedoras Santiago Mujeres"
              style={styles.serviceContainerImg}
              src={EscuelaEmprendedoras}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Escuela de Emprendedoras Santiago Mujeres
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Programa Mujer, Derechos Sexuales y Reproductivos - Convenio SERNAMEG"
              style={styles.serviceContainerImg}
              src={ProgramaMujerDerechoSexRep}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Programa Mujer, Derechos Sexuales y Reproductivos - Convenio
              SERNAMEG
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Programa 4 a 7 - Convenio SERNAMEG"
              style={styles.serviceContainerImg}
              src={Programa4a7}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Programa 4 a 7 - Convenio SERNAMEG
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        {/* Fila 4 */}
        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Programa de Prevencion en Violencia contra las Mujeres - Convenio SERNAMEG"
              style={styles.serviceContainerImg}
              src={ProgramaPrevencionViolencia}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Programa de Prevención en Violencia contra las Mujeres - Convenio
              SERNAMEG
            </h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img
              alt="Imagen de Centro de la Mujer Santiago - Convenio"
              style={styles.serviceContainerImg}
              src={CentroMujerSantiago}
            ></img>
            <h3 style={styles.serviceContainerTitle}>
              Centro de la Mujer Santiago - Convenio SERNAMEG
            </h3>
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
