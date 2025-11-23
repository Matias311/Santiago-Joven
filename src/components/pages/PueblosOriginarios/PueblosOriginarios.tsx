import indigenas from "../../../assets/pueblosOriginarios/indigenas.png";
import { Card } from "../../Card";
import "./PueblosOriginarios.css";

const informacionJSON = [
  {
    id: 1,
    img: indigenas,
    titulo: "Pueblos Indígenas",
  },
];
export default function PueblosOriginarios() {
  return (
    <>
      <h2>Pueblos Originarios</h2>
      <div className="tarjetas-contenedor">
        {informacionJSON.map((element) => (
          <Card key={element.id} item={element} />
        ))}
      </div>
    </>
  );
}
