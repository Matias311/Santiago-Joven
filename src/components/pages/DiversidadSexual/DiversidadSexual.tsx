import wave from "../../../assets/wave-effect.jpg";
import ApoyoLaboralT from "../../../assets/DiversidadSexualImg/ApoyoLaboralTrans.jpg";
import ProgramaSocialA from "../../../assets/DiversidadSexualImg/ProgramaSocialAcompanamiento.jpg";
import CentroAtencionF from "../../../assets/DiversidadSexualImg/CentroAtencionFamilia.jpg";

export default function DiversidadSexual() {
  return (
    <div style={styles.pageContainer}>
      <h1 style={styles.title}>Diversidad Sexual</h1>
      <section
        style={{
          display: "grid",
          gridTemplateColumns: "repeat(3, 1fr)",
          placeItems: "center",
        }}
      >
        <a href="">
          <div style={styles.serviceContainer}>
            <img style={styles.serviceContainerImg} src={ApoyoLaboralT}></img>
            <h3 style={styles.serviceContainerTitle}>Apoyo Laboral Trans</h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img style={styles.serviceContainerImg} src={ProgramaSocialA}></img>
            <h3 style={styles.serviceContainerTitle}>Apoyo Laboral Trans</h3>
            <p style={styles.serviceContainerP}>Ver más »</p>
          </div>
        </a>

        <a href="">
          <div style={styles.serviceContainer}>
            <img style={styles.serviceContainerImg} src={CentroAtencionF}></img>
            <h3 style={styles.serviceContainerTitle}>Apoyo Laboral Trans</h3>
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
