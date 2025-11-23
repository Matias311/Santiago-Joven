import "./CCCarol.css";

import lista1 from "../../../assets/listaC1.png";
import lista2 from "../../../assets/listaC2.png";
import lista3 from "../../../assets/listaC3.png";
import lista4 from "../../../assets/listaC4.png";
import lista5 from "../../../assets/listaC5.png";
import lista6 from "../../../assets/listaC6.png";

import CCCarolBanner from "../../../assets/CCCarolBanner.png";

export default function CCCarol() {
  return (
    <div className="cc-container">
      {/* HEADER SOBRE EL BANNER */}
      <header className="cccarol-header">
        <h1 className="banner-title">Centro Comunitario Carol Urzúa</h1>
        <p className="banner-description">
          Este antiguo y valorado espacio municipal tiene como misión principal,
          fomentar la participación y la vinculación de vecinos y vecinas, a
          través de la realización de actividades recreativas, formativas,
          culturales y deportivas. Este antiguo y valorado espacio municipal
          tiene como misión principal, fomentar la participación y la
          vinculación de vecinos y vecinas, a través de la realización de
          actividades recreativas, formativas, culturales y deportivas.
        </p>
      </header>

      {/* Banner */}
      <img className="cc-banner" src={CCCarolBanner} alt="Centro Carol Urzúa" />

      {/* LISTADO */}
      <div className="cc-list">
        {/* 1 */}
        <div className="cc-row">
          <img src={lista1} className="cc-icon left" />
          <div className="cc-text">Gimnasio techado</div>
          <img src={lista1} className="cc-icon right" />
        </div>

        {/* 2 */}
        <div className="cc-row">
          <img src={lista2} className="cc-icon left" />
          <div className="cc-text">Cancha de pasto sintético</div>
          <img src={lista2} className="cc-icon right" />
        </div>

        {/* 2 */}
        <div className="cc-row">
          <img src={lista4} className="cc-icon left" />
          <div className="cc-text">Camarines y baños</div>
          <img src={lista4} className="cc-icon right" />
        </div>

        {/* 3 */}
        <div className="cc-row">
          <img src={lista3} className="cc-icon left" />
          <div className="cc-text">Sala de clubes</div>
          <img src={lista3} className="cc-icon right" />
        </div>

        {/* 4 */}
        <div className="cc-row">
          <img src={lista6} className="cc-icon left" />
          <div className="cc-text">
            Sala de talleres <br /> (C. Comunitario Víctor Manuel)
          </div>
          <img src={lista6} className="cc-icon right" />
        </div>

        {/* 5 */}
        <div className="cc-row">
          <img src={lista5} className="cc-icon left" />
          <div className="cc-text">Cancha asfaltada</div>
          <img src={lista5} className="cc-icon right" />
        </div>

        {/* 6 */}
        <div className="cc-row">
          <img src={lista6} className="cc-icon left" />
          <div className="cc-text">Atención Convenio SERNAC</div>
          <img src={lista6} className="cc-icon right" />
        </div>
      </div>
    </div>
  );
}
