import { useState, useEffect } from "react";
import EditableCard from "../cartas/EditableCard";
import { type CartaItem } from "../types/CartaItem";
// ESTILO SLIDER
import "./slider.css";

interface SliderProps {
  cartas: CartaItem[];
  isAdmin: boolean;
  onSave: (updated: CartaItem, index: number) => void;
  onDelete: (index: number) => void;
  onAdd?: (newCard: CartaItem) => void;
}

export default function EditableSlider({
  cartas,
  isAdmin,
  onSave,
  onDelete,
  onAdd,
}: SliderProps) {
  const [indexActual, setindexActual] = useState(0);

  // Para ver si la carta activa se está editando
  const [isEditing, setIsEditing] = useState(false);

  const siguienteCarta = () => {
    if (isEditing) return;
    setindexActual((prev) => (prev === cartas.length - 1 ? 0 : prev + 1));
  };

  const cartaAnterior = () => {
    if (isEditing) return; // Bloquea las flechas manuales si edita
    setindexActual((prev) => (prev === 0 ? cartas.length - 1 : prev - 1));
  };

  /** Avanza automáticamente al siguiente slide cada 5 segundos. */
  useEffect(() => {
    if (cartas.length <= 1 || isEditing) return;

    const timer = setInterval(siguienteCarta, 5000);
    return () => clearInterval(timer);
  }, [cartas.length, indexActual, isEditing]);

  return (
    <div className="contenedor-slider">
      <button className="flecha-slider" onClick={cartaAnterior}>
        <span className="material-symbols-outlined">arrow_back_ios</span>
      </button>

      <div className="slider-activo">
        {cartas.map(
          (carta, index) =>
            index === indexActual && (
              <EditableCard
                key={carta.titulo + index}
                cardProps={carta}
                isAdmin={isAdmin}
                onSave={(updatedCard) => {
                  onSave(updatedCard, index);
                  setIsEditing(false);
                }}
                onDelete={() => {
                  onDelete(index);
                  setindexActual((prev) => (prev > 0 ? prev - 1 : 0));
                  setIsEditing(false);
                }}
                onAdd={onAdd}
                isEditing={isEditing}
                onEditingChange={setIsEditing}
              />
            ),
        )}
      </div>

      <button className="flecha-slider" onClick={siguienteCarta}>
        <span className="material-symbols-outlined">arrow_forward_ios</span>
      </button>
    </div>
  );
}
