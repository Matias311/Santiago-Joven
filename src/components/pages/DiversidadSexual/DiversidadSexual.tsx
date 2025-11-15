import wave from "../../../assets/wave-effect.jpg";
import ApoyoLaboralT from "../../../assets/DiversidadSexualImg/ApoyoLaboralTrans.jpg";
import ProgramaSocialA from "../../../assets/DiversidadSexualImg/ProgramaSocialAcompanamiento.jpg";
import CentroAtencionF from "../../../assets/DiversidadSexualImg/CentroAtencionFamilia.jpg";

export default function DiversidadSexual() {
  return (
    <section className="main-section">
      <h1 className="title">Diversidad Sexual</h1>

      <section className="section-service-list">
        <a href="">
          <div className="service-container">
            <img className="service-containerImg" src={ApoyoLaboralT} />
            <h3 className="subtitle">Apoyo Laboral Trans</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img className="service-containerImg" src={ProgramaSocialA} />
            <h3 className="subtitle">Apoyo Laboral Trans</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img className="service-containerImg" src={CentroAtencionF} />
            <h3 className="subtitle">Apoyo Laboral Trans</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>
      </section>

      {/* imagen wave diseño xd */}
      <img src={wave} className="wave.img" />
    </section>
  );
}
