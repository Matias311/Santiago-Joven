import React from "react";
import "./NinezAdolescencia.css";

const NinezAdolescencia: React.FC = () => {
  const cards = [
    {
      title: "Desarrollo de Competencias Sociales",
      img: "src/assets/Ninez-adolecsencia/N.1.jpg",
    },
    {
      title: "Fortalecimientos de la Parentalidad",
      img: "src/assets/Ninez-adolecsencia/N.2.jpg",
    },
    {
      title: "Centro de Atencion a la Familia",
      img: "src/assets/Ninez-adolecsencia/N.3.jpg",
    },
    {
      title: "Programa Lazos ",
      img: "src/assets/Ninez-adolecsencia/N.4.jpg",
    },
    {
      title: "Oficinas Local de la Niñez (OLN)",
      img: "src/assets/Ninez-adolecsencia/N.5.jpg",
    },
    {
      title: "Prepara2",
      img: "src/assets/Ninez-adolecsencia/N.6.jpg",
    },
    {
      title: "Senda Previene en la Comunidad",
      img: "src/assets/Ninez-adolecsencia/N.7.jpg",
    },
    {
      title: "Polos de Cuidado Infantil",
      img: "src/assets/Ninez-adolecsencia/N.8.jpg",
    },
    {
      title: "Talleres de Infancia",
      img: "src/assets/Ninez-adolecsencia/N.9.jpg",
    },
  ];

  return (
    <div className="ninez-container">
      <h1>Niñez y Adolescencia</h1>

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

export default NinezAdolescencia;
