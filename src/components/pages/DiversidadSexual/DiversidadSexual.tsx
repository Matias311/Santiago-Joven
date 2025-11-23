import "./DiversidadSexual.css";
import ApoyoLaboralT from "../../../assets/DiversidadSexualImg/ApoyoLaboralTrans.jpg";
import ProgramaSocialA from "../../../assets/DiversidadSexualImg/ProgramaSocialAcompanamiento.jpg";
import CentroAtencionF from "../../../assets/DiversidadSexualImg/CentroAtencionFamilia.jpg";
import { Card } from "../../Card";

const cardInfo = [
  {
    id: 1,
    img: ApoyoLaboralT,
    titulo: "Apoyo Laboral Trans",
  },
  {
    id: 2,
    img: ProgramaSocialA,
    titulo: "Programa social de acompañamiento",
  },
  {
    id: 3,
    img: CentroAtencionF,
    titulo:
      "Programa de atención a personas LGTBIQ+ víctimas de violencia en contexto de pareja",
  },
];

export default function DiversidadSexual() {
  return (
    <>
      <h2>Diversidad Sexual</h2>
      <div className="tarjetas-contenedor">
        {cardInfo.map((item) => (
          <Card item={item} />
        ))}
      </div>
    </>
  );
}
