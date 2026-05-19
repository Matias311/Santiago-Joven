import React from "react";
import "./Juventud.css";
import { Card } from "../../Card";

const Juventud: React.FC = () => {
  const cards = [
    {
      id: 1,
      img: "/Juventud/J.1.jpg",
      titulo: "Participacion Juvenil",
    },
    {
      id: 2,
      img: "/Juventud/J.2.jpg",
      titulo: "Orientacion Social",
    },
    {
      id: 3,
      img: "/Juventud/J.3.jpg",
      titulo: "Trabajo Joven",
    },
    {
      id: 4,
      img: "/Juventud/J.4.jpg",
      titulo: "STGO Joven",
    },
  ];
  return (
    <>
      <h2>Juventud</h2>
      <div className="tarjetas-contenedor">
        {cards.map((card) => (
          <Card item={card} />
        ))}
      </div>
    </>
  );
};

export default Juventud;
