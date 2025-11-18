import "./PersonasMayores.css";
import lavanderia from "../../../assets/personas-mayores/lavanderia-comunitaria.png";
import comunidadOrganizacion from "../../../assets/personas-mayores/comunidad-organizacion.png";
import atencionJuridica from "../../../assets/personas-mayores/atencion-juridica.png";
import centroDia from "../../../assets/personas-mayores/centro-dia.png";
import cuidados from "../../../assets/personas-mayores/cuidado-comunitario.png";
import talleres from "../../../assets/personas-mayores/talleres-formacion.png";
import { Card } from "../ParticipacionCiudadana/Card";

const informacionJSON = [
  {
    id: 1,
    img: lavanderia,
    titulo: "Lavandería comunitaria",
  },
  {
    id: 2,
    img: comunidadOrganizacion,
    titulo: "Comunidad y Organización",
  },
  {
    id: 3,
    img: atencionJuridica,
    titulo: "Atención Socio Jurídica",
  },
  {
    id: 4,
    img: centroDia,
    titulo: "Centro de Día",
  },
  {
    id: 5,
    img: cuidados,
    titulo: "Cuidados Domiciliarios",
  },
  {
    id: 6,
    img: talleres,
    titulo: "Talleres de Formación Continua",
  },
];
export default function PersonasMayores() {
  return (
    <>
      <h2>Personas Mayores</h2>
      <div className="tarjetas-contenedor">
        {informacionJSON.map((element) => (
          <Card key={element.id} item={element} />
        ))}
      </div>
    </>
  );
}
