import "./MujeresIgualdadGenero.css";
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
import { Card } from "../../Card";

const infoCard = [
  {
    id: 1,
    img: BienestarAutocuidado,
    titulo: "Bienestar y Autocuidado",
  },
  {
    id: 2,
    img: EstudiosCapacitacionGenero,
    titulo: "Estudios y Capacitación de Género",
  },
  {
    id: 3,
    img: OrganizacionesMujeres,
    titulo: "Organizaciones de Mujeres",
  },
  {
    id: 4,
    img: AtencionIntegral,
    titulo: "Atención Integral (Social - Psicológico - Jurídico)",
  },
  {
    id: 5,
    img: BrigadaCuidados,
    titulo: "Brigada de Cuidados",
  },
  {
    id: 6,
    img: ProgramaNoViolenciaGenero,
    titulo: "Talleres Programa Santiago x la No Violencia de Género",
  },
  {
    id: 7,
    img: EscuelaEmprendedoras,
    titulo: "Escuela de Emprendedoras Santiago Mujeres",
  },
  {
    id: 8,
    img: ProgramaMujerDerechoSexRep,
    titulo:
      "Programa Mujer, Derechos Sexuales y Reproductivos - Convenio SERNAMEG",
  },
  {
    id: 9,
    img: Programa4a7,
    titulo: "Programa 4 a 7 - Convenio SERNAMEG",
  },
  {
    id: 10,
    img: ProgramaPrevencionViolencia,
    titulo:
      "Programa de Prevención en Violencia contra las Mujeres - Convenio SERNAMEG",
  },
  {
    id: 11,
    img: CentroMujerSantiago,
    titulo: "Centro de la Mujer Santiago - Convenio SERNAMEG",
  },
];

export default function MujeresIgualdadGenero() {
  return (
    <>
      <h2 className="titulo-pagina">Mujeres e Igualdad de Género</h2>
      <div className="tarjetas-contenedor">
        {infoCard.map((item) => (
          <Card item={item} />
        ))}
      </div>
    </>
  );
}
