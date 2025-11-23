import "./Card.css";
type InfoItem = {
  id: number;
  img: string;
  titulo: string;
};
export const Card = ({ item }: { item: InfoItem }) => {
  return (
    <div>
      <div className="tarjeta" key={item.id}>
        <img src={item.img} alt={item.titulo} className="imagen-tarjeta" />
        <h3 className="titulo-tarjeta">{item.titulo}</h3>
        <p className="link-tarjeta">Ver más</p>
      </div>
    </div>
  );
};
