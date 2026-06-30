import { useState } from "react";
import SurveyCard, { type Survey } from "./SurveyCard";
import "./EditableCard.css";

interface EditableSurveyCardProps {
  eventProps: Survey;
  isAdmin: boolean;
  onSave: (updated: Survey) => void;
  onDelete: () => void;
  onAdd?: (newSurvey: Survey) => void;
}

export default function EditableSurveyCard({
  eventProps,
  isAdmin,
  onSave,
  onDelete,
  onAdd,
}: EditableSurveyCardProps) {
  const [editing, setEditing] = useState(false);

  const [formData, setFormData] = useState<Survey>(eventProps);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>,
  ) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  return (
    <>
      <div className="container">
        <SurveyCard event={eventProps} />

        {isAdmin && (
          <button className="card_edit_btn" onClick={() => setEditing(true)}>
            Editar
          </button>
        )}
      </div>

      {editing && (
        <div className="modal_edit_overlay">
          <div className="modal_edit_content">
            <h2>Editar Encuesta</h2>

            <div className="container">
              <p>Titulo</p>
              <input
                name="title"
                value={formData.title}
                onChange={handleChange}
              />
            </div>

            <div className="container">
              <p>URL de la encuesta</p>
              <input name="url" value={formData.url} onChange={handleChange} />
            </div>

            <button
              className="form_btn"
              id="save_btn"
              onClick={() => {
                onSave(formData);
                setEditing(false);
              }}
            >
              Guardar Encuesta
            </button>

            <button
              className="form_btn"
              id="add_btn"
              onClick={() => {
                onAdd?.({
                  ...formData,
                  id: Date.now(),
                  title: `Nueva encuesta`,
                });

                setEditing(false);
              }}
            >
              Crear Encuesta
            </button>

            <button className="form_btn" id="delete_btn" onClick={onDelete}>
              Eliminar Encuesta
            </button>

            <button
              className="form_btn"
              id="cancel_btn"
              onClick={() => setEditing(false)}
            >
              Cancelar
            </button>
          </div>
        </div>
      )}
    </>
  );
}
