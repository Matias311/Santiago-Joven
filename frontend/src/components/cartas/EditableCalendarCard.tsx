import { useState } from "react";
import CalendarCard, { type CalendarEvent } from "./CalendarCard";
import "./EditableCard.css";

interface EditableCalendarCardProps {
  eventProps: CalendarEvent;
  isAdmin: boolean;
  onSave: (updated: CalendarEvent) => void;
  onDelete: () => void;
}

export default function EditableCalendarCard({
  eventProps,
  isAdmin,
  onSave,
  onDelete,
}: EditableCalendarCardProps) {
  const [editing, setEditing] = useState(false);
  const [formData, setFormData] = useState<CalendarEvent>({ ...eventProps });

  const handleChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement
    >,
  ) => {
    const { name, value } = e.target;

    if (name === "categoria") {
      const categoriaMap: Record<
        string,
        "ferias" | "talleres" | "cursos" | "campañas"
      > = {
        Ferias: "ferias",
        Talleres: "talleres",
        Cursos: "cursos",
        Campañas: "campañas",
      };
      setFormData({
        ...formData,
        categoria: value as any,
        tagClass: categoriaMap[value],
      });
    } else {
      setFormData({
        ...formData,
        [name]: value,
      });
    }
  };

  const handleSave = () => {
    onSave(formData);
    setEditing(false);
  };

  return (
    <>
      <div className="editable_calendar_wrapper">
        {/* onClick vacío porque en inicio.tsx no hay modal de detalle */}
        <CalendarCard eventProps={eventProps} onClick={() => {}} />

        {isAdmin && (
          <button className="card_edit_btn" onClick={() => setEditing(true)}>
            Editar
          </button>
        )}
      </div>

      {editing && (
        <div className="modal_edit_overlay">
          <div className="modal_edit_content">
            <h2>Editar Actividad</h2>

            <div className="container">
              <p>Categoria</p>
              <select
                name="categoria"
                value={formData.categoria}
                onChange={handleChange}
                className="modal_calendar_select"
              >
                <option value="Ferias">Ferias</option>
                <option value="Talleres">Talleres</option>
                <option value="Cursos">Cursos</option>
                <option value="Campañas">Campañas</option>
              </select>
            </div>

            <div className="container">
              <p>Titulo de la Actividad</p>
              <textarea
                name="title"
                rows={2}
                value={formData.title}
                onChange={handleChange}
              />
            </div>

            <div className="container">
              <p>Fecha del Evento</p>
              <input
                type="date"
                name="date"
                value={formData.date}
                onChange={handleChange}
                className="modal_calendar_select"
                style={{ width: "100%", boxSizing: "border-box" }}
              />
            </div>

            <button id="save_btn" className="form_btn" onClick={handleSave}>
              Guardar
            </button>
            <button
              id="cancel_btn"
              className="form_btn"
              onClick={() => setEditing(false)}
            >
              Cancelar
            </button>
            <button id="delete_btn" className="form_btn" onClick={onDelete}>
              Eliminar
            </button>
          </div>
        </div>
      )}
    </>
  );
}
