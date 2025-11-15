import React from "react";
import "./ParticipacionCiudadana.css";
import comunitarias from "../../../assets/participacion-ciudadana/comunitarias.png";
import juridica from "../../../assets/participacion-ciudadana/juridica.png";
import antecedentes from "../../../assets/participacion-ciudadana/antecedentes.png";
import transporte from "../../../assets/participacion-ciudadana/transporte.png";
import ley from "../../../assets/participacion-ciudadana/ley.png";
import participativos from "../../../assets/participacion-ciudadana/participativos.png";
import concursables from "../../../assets/participacion-ciudadana/concursables.png";
import dirigencias from "../../../assets/participacion-ciudadana/dirigencias.png";

export default function ParticipacionCiudadana() {
  return (
    <div className="contenedor-participacion">
      <h1 className="titulo-pagina">Participación Ciudadana</h1>
      <div className="grid-tarjetas">
        {[
          { id: 1, img: comunitarias, titulo: "Modificadores de estatuto de Organizaciones comunitarias" },
          { id: 2, img: juridica, titulo: "Orientación y obtención de personalidad Jurídica" },
          { id: 3, img: antecedentes, titulo: "Revisión de Antecedentes Legales en Organizaciónes" },
          { id: 4, img: transporte, titulo: "Transporte a Organizaciónes" },
          { id: 5, img: ley, titulo: "Orientación Ley de Copropiedad 21.442" },
          { id: 6, img: participativos, titulo: "Presupuestos Participativos" },
          { id: 7, img: concursables, titulo: "Fondos Concursables" },
          { id: 8, img: dirigencias, titulo: "Escuela de Dirigencias Sociales" },
        ].map((item) => (
          <div className="tarjeta" key={item.id}>
            <img src={item.img} alt={item.titulo} className="imagen-tarjeta" />
            <h3 className="titulo-tarjeta">{item.titulo}</h3>
            <p className="link-tarjeta">Ver más</p>
          </div>
        ))}
      </div>
      <div className="espacio-footer"></div>
    </div>
  );
}
