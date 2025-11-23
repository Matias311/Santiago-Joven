import React from "react";
import "./NinezAdolescencia.css";
import { Card } from "../../Card";

const NinezAdolescencia: React.FC = () => {
  const cards = [
    {
      id: 1,
      img: "src/assets/Ninez-adolecsencia/N.1.jpg",
      titulo: "Desarrollo de Competencias Sociales",
    },
    {
      id: 2,
      img: "src/assets/Ninez-adolecsencia/N.2.jpg",
      titulo: "Fortalecimientos de la Parentalidad",
    },
    {
      id: 3,
      img: "src/assets/Ninez-adolecsencia/N.3.jpg",
      titulo: "Centro de Atencion a la Familia",
    },
    {
      id: 4,
      img: "src/assets/Ninez-adolecsencia/N.4.jpg",
      titulo: "Programa Lazos ",
    },
    {
      id: 5,
      img: "src/assets/Ninez-adolecsencia/N.5.jpg",
      titulo: "Oficinas Local de la Niñez (OLN)",
    },
    {
      id: 6,
      img: "src/assets/Ninez-adolecsencia/N.6.jpg",
      titulo: "Prepara2",
    },
    {
      id: 7,
      img: "src/assets/Ninez-adolecsencia/N.7.jpg",
      titulo: "Senda Previene en la Comunidad",
    },
    {
      id: 8,
      img: "src/assets/Ninez-adolecsencia/N.8.jpg",
      titulo: "Polos de Cuidado Infantil",
    },
    {
      id: 9,
      img: "src/assets/Ninez-adolecsencia/N.9.jpg",
      titulo: "Talleres de Infancia",
    },
  ];

  return (
    <>
      <h2>Niñez y Adolescencia</h2>

      <div className="tarjetas-contenedor">
        {cards.map((card) => (
          <Card item={card} />
        ))}
      </div>
    </>
  );
};

export default NinezAdolescencia;
