import React, { useState } from "react";
import wave from "../../../assets/wave-effect.jpg";

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
    <div>
      <form
        style={{
          marginBottom: "100px",
          marginLeft: "50px",
          display: "flex",
          flexDirection: "column",
          gap: 15,
        }}
      >
        <h1 style={styles.title}>Encuesta</h1>
        <h3 style={styles.subtitle}>¿En qué consiste?</h3>

        <div id="Q1">
          <p style={styles.question}>
            Según tu experiencia, del 1 al 10 cuéntanos qué tanto te gusta la
            página
          </p>

          <div style={styles.options}>
            {Array.from({ length: 10 }, (_, i) => i + 1).map((num) => (
              <label key={num} style={styles.label}>
                {num}
                <input
                  id="Q1Answer"
                  type="radio"
                  name="respuesta1"
                  value={num}
                  checked={selectedQ1 === num}
                  onChange={(e) => handleRadioClickMuchosxd(e, setSelecteQ1)}
                />
              </label>
            ))}
          </div>
        </div>

        <div id="Q2">
          <p style={styles.question}>
            Según usted, ¿Qué tan intuitiva es la página?
          </p>
          <div style={styles.options}>
            {Array.from({ length: 10 }, (_, i) => i + 1).map((num) => (
              <label key={num} style={styles.label}>
                {num}
                <input
                  id="Q2Answer"
                  type="radio"
                  name="respuesta2"
                  value={num}
                  checked={selectedQ2 === num}
                  onChange={(e) => handleRadioClickMuchosxd(e, setSelectedQ2)}
                />
              </label>
            ))}
          </div>
        </div>

        <div id="Q3">
          <p style={styles.question}>
            ¿Le recomendarias la pagina a otra persona?
          </p>
          <div style={styles.options}>
            <label style={styles.label}>
              No
              <input
                type="radio"
                name="respuesta3"
                value="No"
                checked={selectedQ3 === "No"}
                onChange={handleRadioClick}
              />
            </label>
            <label style={styles.label}>
              Si
              <input
                type="radio"
                name="respuesta3"
                value="Si"
                checked={selectedQ3 === "Si"}
                onChange={handleRadioClick}
              />
            </label>
          </div>
        </div>

        <div id="Q4">
          <p style={styles.question}>
            ¿Que tanto la pagina le satisface sus consultas/dudas?
          </p>
          <div style={styles.options}>
            {Array.from({ length: 10 }, (_, i) => i + 1).map((num) => (
              <label key={num} style={styles.label}>
                {num}
                <input
                  id="Q4Answer"
                  type="radio"
                  name="respuesta4"
                  value={num}
                  checked={selectedQ4 === num}
                  onChange={(e) => handleRadioClickMuchosxd(e, setSelectedQ4)}
                />
              </label>
            ))}
          </div>
        </div>

        <div id="Q5">
          <p style={styles.question}>
            Envienos su comentario o ideas para mejorar la página
          </p>
          <label style={styles.label}>
            <textarea style={styles.textArea} />
          </label>
        </div>

        <button style={styles.buttonEnviar}>Enviar</button>
      </form>

      {/* imagen wave diseño xd */}
      <img
        src={wave}
        style={{ width: "100%", height: "100%", display: "block" }}
      ></img>
    </div>
  );
}

const styles = {
  options: {
    display: "flex" as const,
    gap: "1rem",
    justifyContent: "left" as const,
  },

  label: {
    padding: "0.2rem",
    display: "flex" as const,
    flexDirection: "column" as const,
    fontWeight: 600,
  },

  title: {
    display: "flex",
    color: "#6080BF",
    fontSize: "4rem",
    fontWeight: 600,
  },

  subtitle: {
    display: "flex",
    color: "#6080BF",
    fontSize: "2.5rem",
    fontWeight: 600,
  },

  question: {
    fontSize: "1.5rem",
    fontWeight: 600,
  },

  textArea: {
    border: 0,
    background: "#F3F3F3",
    height: 273,
    width: 633,
    resize: "none" as const,
    fontSize: "1.3rem",
  },

  buttonEnviar: {
    width: 206,
    height: 56,
    fontSize: "1.5rem",
    background: "#4C4C4C",
    color: "#F3F3F3",
    border: 0,
    borderRadius: 20,
  },
};
