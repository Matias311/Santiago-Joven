import "./ParticipacionCiudadana.css";
import comunitarias from "/participacion-ciudadana/comunitarias.png";
import juridica from "/participacion-ciudadana/juridica.png";
import antecedentes from "/participacion-ciudadana/antecedentes.png";
import transporte from "/participacion-ciudadana/transporte.png";
import ley from "/participacion-ciudadana/ley.png";
import participativos from "/participacion-ciudadana/participativos.png";
import concursables from "/participacion-ciudadana/concursables.png";
import dirigencias from "/participacion-ciudadana/dirigencias.png";
import { Card } from "../../Card";

const informacionJSON = [
  {
    id: 1,
    img: comunitarias,
    titulo: "Modificadores de estatuto de Organizaciones comunitarias",
  },
  {
    id: 2,
    img: juridica,
    titulo: "Orientación y obtención de personalidad Jurídica",
  },
  {
    id: 3,
    img: antecedentes,
    titulo: "Revisión de Antecedentes Legales en Organizaciónes",
  },
  { id: 4, img: transporte, titulo: "Transporte a Organizaciónes" },
  { id: 5, img: ley, titulo: "Orientación Ley de Copropiedad 21.442" },
  { id: 6, img: participativos, titulo: "Presupuestos Participativos" },
  { id: 7, img: concursables, titulo: "Fondos Concursables" },
  {
    id: 8,
    img: dirigencias,
    titulo: "Escuela de Dirigencias Sociales",
  },
];

export default function ParticipacionCiudadana() {
  return (
    <div className="contenedor-participacion">
      <h1 className="titulo-pagina">Participación Ciudadana</h1>
      <div className="grid-tarjetas">
        {informacionJSON.map((item) => (
          <Card key={item.id} item={item} />
        ))}
      </div>
      <div className="espacio-footer"></div>
    </div>
  );
}
