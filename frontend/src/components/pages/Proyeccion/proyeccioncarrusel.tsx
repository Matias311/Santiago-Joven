// =============================================================
// ARCHIVO: proyeccioncarrusel.tsx
// PROPOSITO: Carrusel generico que muestra un elemento a la vez,
//            navegable con swipe (touch) y dots de paginacion.
//            No tiene flechas.
//
// USO:
//   <ProyeccionCarrusel tema="blue" total={3} indice={idx} onChange={setIdx}>
//     <ProyeccionCard1 ... />
//   </ProyeccionCarrusel>
//
//   El componente padre maneja el indice activo (useState) y pasa
//   los hijos ya construidos. El carrusel solo se encarga del
//   contenedor visual, el swipe y los dots.
//
// PROPS:
//   children  — ReactNode: las cartas ya instanciadas
//   total     — numero total de slides (para los dots)
//   indice    — indice activo controlado desde el padre
//   onChange  — callback (nuevoIndice: number) => void
//   tema      — "blue" | "orange" define el color de los dots activos
//
// SWIPE:
//   Se detecta con onTouchStart / onTouchEnd en el contenedor.
//   Un desplazamiento horizontal mayor a UMBRAL_SWIPE (50px)
//   avanza o retrocede. No interfiere con scroll vertical.
//
// PARA CONECTAR CON LA API:
//   El carrusel no sabe nada de los datos; solo recibe children.
//   Cuando la API este lista, el padre genera las cartas y las pasa.
// =============================================================

import { useRef } from "react";
import "./proyeccioncarrusel.css";

const UMBRAL_SWIPE = 50; // px minimos para considerar un swipe

type ProyeccionCarruselProps = {
  children: React.ReactNode[];
  total: number;
  indice: number;
  onChange: (nuevoIndice: number) => void;
  tema: "blue" | "orange";
};

export default function ProyeccionCarrusel({
  children,
  total,
  indice,
  onChange,
  tema,
}: ProyeccionCarruselProps) {
  // Almacena la posicion X al inicio del toque para calcular el delta
  const touchStartX = useRef<number | null>(null);

  const handleTouchStart = (e: React.TouchEvent) => {
    touchStartX.current = e.touches[0].clientX;
  };

  const handleTouchEnd = (e: React.TouchEvent) => {
    if (touchStartX.current === null) return;
    const delta = e.changedTouches[0].clientX - touchStartX.current;
    touchStartX.current = null;

    if (Math.abs(delta) < UMBRAL_SWIPE) return;

    if (delta < 0 && indice < total - 1) {
      // Swipe izquierda: avanzar
      onChange(indice + 1);
    } else if (delta > 0 && indice > 0) {
      // Swipe derecha: retroceder
      onChange(indice - 1);
    }
  };

  return (
    <div className="pcarr-wrapper">
      {/* Ventana del carrusel — solo muestra el slide activo */}
      <div
        className="pcarr-viewport"
        onTouchStart={handleTouchStart}
        onTouchEnd={handleTouchEnd}
        aria-live="polite"
      >
        {children.map((child, i) => (
          <div
            key={i}
            className={`pcarr-slide ${i === indice ? "pcarr-slide--active" : ""}`}
            aria-hidden={i !== indice}
          >
            {child}
          </div>
        ))}
      </div>

      {/* Dots de paginacion */}
      {total > 1 && (
        <div
          className="pcarr-dots"
          role="tablist"
          aria-label="Navegacion de cartas"
        >
          {Array.from({ length: total }).map((_, i) => (
            <button
              key={i}
              role="tab"
              aria-selected={i === indice}
              aria-label={`Carta ${i + 1} de ${total}`}
              className={`pcarr-dot pcarr-dot--${tema} ${i === indice ? "pcarr-dot--active" : ""}`}
              onClick={() => onChange(i)}
            />
          ))}
        </div>
      )}
    </div>
  );
}
