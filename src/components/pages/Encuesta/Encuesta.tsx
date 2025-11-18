import React, { useState } from "react";
import "./Encuesta.css";
import wave from "../../../assets/wave-effect.jpg";

interface OptionGeneratorProps {
  quantity: number;
  answerName: string;
  selectedValue: number | null;
  onChangeHandler: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

function OptionGenerator({
  quantity,
  answerName,
  selectedValue,
  onChangeHandler,
}: OptionGeneratorProps) {
  return (
    <div className="base-options">
      {Array.from({ length: quantity }, (_, i) => i + 1).map((num) => (
        <label key={num} className="encuesta-label">
          {num}
          <input
            type="radio"
            name={answerName}
            value={num}
            checked={selectedValue === num}
            onChange={onChangeHandler}
          />
        </label>
      ))}
    </div>
  );
}

export default function Encuesta() {
  const [selectedQ1, setSelecteQ1] = useState<number | null>(null);
  const [selectedQ2, setSelectedQ2] = useState<number | null>(null);
  const [selectedQ3, setSelectedQ3] = useState("");
  const [selectedQ4, setSelectedQ4] = useState<number | null>(null);

  const handleRadioClickMuchosxd = (
    event: React.ChangeEvent<HTMLInputElement>,
    setSelected: React.Dispatch<React.SetStateAction<number | null>>
  ) => {
    const value = Number(event.target.value);
    setSelected(value);
  };

  const handleRadioClick = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedQ3(event.target.value);
  };

  return (
    <section>
      <form
        style={{
          marginBottom: "100px",
          marginLeft: "50px",
          display: "flex",
          flexDirection: "column",
          gap: 15,
        }}
      >
        {/* TÍTULO Y SUBTÍTULO */}
        <h2 className="title-encuesta">Encuesta</h2>
        <h3 className="subtitle-encuesta">¿En qué consiste?</h3>

        <div id="Q1">
          <p className="question">
            Según tu experiencia, del 1 al 10 cuéntanos qué tanto te gusta la
            página
          </p>

          <div className="base-options">
            <OptionGenerator
              quantity={10}
              answerName="respuesta1"
              selectedValue={selectedQ1}
              onChangeHandler={(e) => handleRadioClickMuchosxd(e, setSelecteQ1)}
            />
          </div>
        </div>

        <div id="Q2">
          <p className="question">
            Según usted, ¿Qué tan intuitiva es la página?
          </p>
          <div className="base-options">
            <OptionGenerator
              quantity={10}
              answerName="respuesta2"
              selectedValue={selectedQ2}
              onChangeHandler={(e) =>
                handleRadioClickMuchosxd(e, setSelectedQ2)
              }
            />
          </div>
        </div>

        <div id="Q3">
          <p className="question">
            ¿Le recomendarías la página a otra persona?
          </p>

          <div className="base-options">
            <label className="encuesta-label">
              No
              <input
                type="radio"
                name="respuesta3"
                value="No"
                checked={selectedQ3 === "No"}
                onChange={handleRadioClick}
              />
            </label>

            <label className="encuesta-label">
              Sí
              <input
                type="radio"
                name="respuesta3"
                value="Sí"
                checked={selectedQ3 === "Sí"}
                onChange={handleRadioClick}
              />
            </label>
          </div>
        </div>

        <div id="Q4">
          <p className="question">
            ¿Qué tanto la página le satisface sus consultas/dudas?
          </p>

          <div className="base-options">
            <OptionGenerator
              quantity={10}
              answerName="respuesta4"
              selectedValue={selectedQ4}
              onChangeHandler={(e) =>
                handleRadioClickMuchosxd(e, setSelectedQ4)
              }
            />
          </div>
        </div>

        <div id="Q5">
          <p className="question">
            Envíenos su comentario o ideas para mejorar la página
          </p>

          <label className="encuesta-label">
            <textarea className="encuesta-textarea" />
          </label>
        </div>

        {/* BOTÓN */}
        <button className="button-enviar">Enviar</button>
      </form>

      {/* Imagen inferior olas */}
      <img src={wave} className="wave-img" />
    </section>
  );
}
