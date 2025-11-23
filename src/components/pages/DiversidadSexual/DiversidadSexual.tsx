import "./DiversidadSexual.css";
import ApoyoLaboralT from "../../../assets/DiversidadSexualImg/ApoyoLaboralTrans.jpg";
import ProgramaSocialA from "../../../assets/DiversidadSexualImg/ProgramaSocialAcompanamiento.jpg";
import CentroAtencionF from "../../../assets/DiversidadSexualImg/CentroAtencionFamilia.jpg";

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
      <h2 className="titulo-pagina">Diversidad Sexual</h2>
      <div className="grid-tarjetas">
        {cardInfo.map((item) => (
          <div className="tarjeta" key={item.id}>
            <img src={item.img} alt={item.titulo} className="imagen-tarjeta" />
            <h3 className="titulo-tarjeta">{item.titulo}</h3>
            <p className="link-tarjeta">Ver más »</p>
          </div>
        ))}
      </div>
    </>
  );
}
