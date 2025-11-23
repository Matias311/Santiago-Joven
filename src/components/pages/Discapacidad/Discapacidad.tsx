import React from "react";
import "./Discapacidad.css";
import { Card } from "../../Card";

const Discapacidad: React.FC = () => {
  const cards = [
    {
      id: 1,
      img: "src/assets/Discapacidad/D.1.jpg",
      titulo: "Banco de Prestamo de Ayuda Técnicas",
    },
    {
      id: 2,
      img: "src/assets/Discapacidad/D.2.jpg",
      titulo: "Tecnologias de apoyo 3D",
    },
    {
      id: 3,
      img: "src/assets/Discapacidad/D.3.jpg",
      titulo: "Linea Social Oficina de Discapacidad",
    },
    {
      id: 4,
      img: "src/assets/Discapacidad/D.4.jpg",
      titulo: "Red local de Apoyos y Ciudados",
    },
    {
      id: 5,
      img: "src/assets/Discapacidad/D.5.jpg",
      titulo: "Sala Multisensorial de la Oficina de Discapacidad",
    },
  ];
  return (
    <>
      <h2>Discapacidad</h2>
      <div className="tarjetas-contenedor">
        {cards.map((card) => (
          <Card item={card} />
        ))}
      </div>
    </>
  );
};

export default Discapacidad;
