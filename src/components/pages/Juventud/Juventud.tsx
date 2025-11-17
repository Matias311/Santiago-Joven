import React from "react";
import "./Juventud.css";

const Juventud: React.FC = () => {
  const cards = [
    {
      title: "Participacion Juvenil",
      img: "src/assets/Juventud/J.1.jpg",
    },
    {
      title: "Orientacion Social",
      img: "src/assets/Juventud/J.2.jpg",
    },
    {
      title: "Trabajo Joven",
      img: "src/assets/Juventud/J.3.jpg",
    },
    {
      title: "STGO Joven",
      img: "src/assets/Juventud/J.4.jpg",
    },
  ];
  return (
    <div className="Juventud-container">
      <h1>Juventud</h1>
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

export default Juventud;
