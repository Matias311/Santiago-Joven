import { useState, useEffect } from "react";
import "./slider.css";
import Card from "../cartas/Card";
import type { CartaItem } from '../types/typeCard';

/**
 * Props del componente Slider.
 */
interface Props {
  /**
   * Array de tarjetas a mostrar en el slider.
   * Cada elemento es un objeto de tipo `CartaItem`.
   */
  cartas: CartaItem[];
}

/**
 * Slider automático de tarjetas reutilizable.
 *
 * Muestra una tarjeta a la vez y avanza automáticamente cada 5 segundos.
 * El usuario puede navegar manualmente con las flechas de anterior y siguiente.
 * Internamente usa el componente `Card` para renderizar cada tarjeta.
 *
 * @component
 * @example
 * // Uso en Inicio.tsx pasando un array de CartaItem
 * <Slider cartas={info.programas} />
 */
export default function Slider({ cartas }: Props) {
  const [indiceActual, setIndiceActual] = useState(0);

  const siguienteCarta = () => setIndiceActual((prev) => (prev === cartas.length - 1 ? 0 : prev + 1));
  const cartaAnterior = () => setIndiceActual((prev) => (prev === 0 ? cartas.length - 1 : prev - 1));

  /** Avanza automáticamente al siguiente slide cada 5 segundos. */
  useEffect(() => {
    const timer = setInterval(siguienteCarta, 5000);
    return () => clearInterval(timer);
  }, []);

  return (
    <div className="contenedor-slider">
      <button className="flecha-slider" onClick={cartaAnterior}>
        <span className="material-symbols-outlined">arrow_back_ios</span>
      </button>

      <div className="slider-activo">
        {/* Solo renderiza la carta en el índice actual */}
        {cartas.map((carta, index) => (
          index === indiceActual && (
            <Card
              key={carta.titulo}
              icono={carta.icono}
              iconoColor={carta.iconoColor}
              iconoTamaño={carta.iconoTamaño}
              iconoFondo={carta.iconoFondo}
              titulo={carta.titulo}
              tituloColor={carta.tituloColor}
              subtitulo={carta.subtitulo}
              sliderSombra={carta.sliderSombra}
              descripcion={carta.descripcion}
              boton={carta.boton}
              botoncolor={carta.botoncolor}
              clase={carta.clase}
            />
          )
        ))}
      </div>

      <button className="flecha-slider" onClick={siguienteCarta}>
        <span className="material-symbols-outlined">arrow_forward_ios</span>
      </button>
    </div>
  );
}