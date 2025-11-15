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
    <section className="main-section">
      <h1 className="title">Mujeres e Igualdad de Género</h1>

      <section className="section-service-list">
        {/* --------------------Fila 1-------------------- */}
        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Bienestar y Autocuidado"
              className="service-containerImg"
              src={BienestarAutocuidado}
            />
            <h3 className="subtitle">Bienestar y Autocuidado</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Estudios y capacitación de género"
              className="service-containerImg"
              src={EstudiosCapacitacionGenero}
            />
            <h3 className="subtitle">Estudios y Capacitación de Género</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Organizaciones de Mujeres"
              className="service-containerImg"
              src={OrganizacionesMujeres}
            />
            <h3 className="subtitle">Organizaciones de Mujeres</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        {/* --------------------Fila 2-------------------- */}
        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Atención Integral Social, Psicológico y Jurídico"
              className="service-containerImg"
              src={AtencionIntegral}
            />
            <h3 className="subtitle">
              Atención Integral (Social - Psicológico - Jurídico)
            </h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Brigada de Cuidados"
              className="service-containerImg"
              src={BrigadaCuidados}
            />
            <h3 className="subtitle">Brigada de Cuidados</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Talleres Programa Santiago x la No Violencia de Género"
              className="service-containerImg"
              src={ProgramaNoViolenciaGenero}
            />
            <h3 className="subtitle">
              Talleres Programa Santiago x la No Violencia de Género
            </h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        {/* --------------------Fila 3-------------------- */}
        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Escuela de Emprendedoras Santiago Mujeres"
              className="service-containerImg"
              src={EscuelaEmprendedoras}
            />
            <h3 className="subtitle">
              Escuela de Emprendedoras Santiago Mujeres
            </h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Programa Mujer, Derechos Sexuales y Reproductivos - Convenio SERNAMEG"
              className="service-containerImg"
              src={ProgramaMujerDerechoSexRep}
            />
            <h3 className="subtitle">
              Programa Mujer, Derechos Sexuales y Reproductivos - Convenio
              SERNAMEG
            </h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Programa 4 a 7 - Convenio SERNAMEG"
              className="service-containerImg"
              src={Programa4a7}
            />
            <h3 className="subtitle">Programa 4 a 7 - Convenio SERNAMEG</h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        {/* --------------------Fila 4-------------------- */}
        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Programa de Prevención en Violencia contra las Mujeres - Convenio SERNAMEG"
              className="service-containerImg"
              src={ProgramaPrevencionViolencia}
            />
            <h3 className="subtitle">
              Programa de Prevención en Violencia contra las Mujeres - Convenio
              SERNAMEG
            </h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>

        <a href="">
          <div className="service-container">
            <img
              alt="Imagen de Centro de la Mujer Santiago - Convenio SERNAMEG"
              className="service-containerImg"
              src={CentroMujerSantiago}
            />
            <h3 className="subtitle">
              Centro de la Mujer Santiago - Convenio SERNAMEG
            </h3>
            <p className="see-more">Ver más »</p>
          </div>
        </a>
      </section>

      {/* imagen wave xd */}
      <img src={wave} className="wave.img" />
    </section>
  );
}
