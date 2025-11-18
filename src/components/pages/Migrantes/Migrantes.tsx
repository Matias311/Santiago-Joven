import vinculacion from "../../../assets/migrantes/vinculacion-medio.png";
import atencion from "../../../assets/migrantes/atencion-social.png";
import { Card } from "../ParticipacionCiudadana/Card";
import "./Migrantes.css";
const informacionJSON = [
  {
    id: 1,
    img: vinculacion,
    titulo: "Programa Vinculación con el Medio",
  },
  {
    id: 2,
    img: atencion,
    titulo: "Atención Social de personas migrantes y refugiadas",
  },
];
export default function Migrantes() {
  return (
    <>
      <h2>Migrantes</h2>
      <div className="tarjetas-contenedor">
        {informacionJSON.map((element) => (
          <Card key={element.id} item={element} />
        ))}
      </div>
    </>
  );
}
