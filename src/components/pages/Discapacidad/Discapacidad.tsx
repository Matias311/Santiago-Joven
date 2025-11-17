// src/components/pages/Discapacidad/Discapacidad.tsx
import React from "react";
import "./Discapacidad.css";

const Discapacidad: React.FC = () => {
  const cards = [
    {
      title: "Banco de Prestamo de Ayuda Técnicas",
      img: "src/assets/Discapacidad/D.1.jpg",
    },
    {
      title: "Tecnologias de apoyo 3D",
      img: "src/assets/Discapacidad/D.2.jpg",
    },
    {
      title: "Linea Social Oficina de Discapacidad",
      img: "src/assets/Discapacidad/D.3.jpg",
    },
    {
      title: "Red local de Apoyos y Ciudados",
      img: "src/assets/Discapacidad/D.4.jpg",
    },
    {
      title: "Sala Multisensorial de la Oficina de Discapacidad",
      img: "src/assets/Discapacidad/D.5.jpg",
    },
  ];
  return (
    <div className="discapacidad-container">
      <h1>Discapacidad</h1>
      <div className="cards-grid">
        {cards.map((card, index) => (
          <div className="card" key={index}>
            <img src={card.img} alt={card.title} />
            <h3>{card.title}</h3>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Discapacidad;
