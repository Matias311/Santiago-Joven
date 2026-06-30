import { type ConexionItem } from "../types/ConexionItem";
import { useState } from "react";

{
  /** Usamos el mismo estilo que el modal de cards */
}
import "./EditableCard.css";
import { iconos } from "./EditableCard";

interface EditableListItemProps {
  item: ConexionItem;
  isAdmin: boolean;
  onSave: (updated: ConexionItem, seccion: "actividades" | "talleres") => void;
  onDelete: () => void;
  onAdd?: (newItem: ConexionItem, seccion: "actividades" | "talleres") => void;
}

export default function EditableListItem({
  item,
  isAdmin,
  onSave,
  onDelete,
  onAdd,
}: EditableListItemProps) {
  const [editing, setEditing] = useState(false);
  const [formData, setFormData] = useState(item);
  const [seccion, setSeccion] = useState<"actividades" | "talleres">(
    "actividades",
  );
  const [showIconPicker, setShowIconPicker] = useState(false);

  return (
    <>
      <li>
        <div className="li-contenido">
          <span className="material-symbols-outlined">{item.icono}</span>
          {item.texto}
        </div>

        {isAdmin && (
          <button onClick={() => setEditing(true)} className="card_edit_btn">
            Editar
          </button>
        )}
      </li>

      {editing && (
        <div className="modal_edit_overlay">
          <div className="modal_edit_content">
            <h2>Editar elemento</h2>

            <textarea
              value={formData.texto}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  texto: e.target.value,
                })
              }
            />
            {formData.icono != null && (
              <>
                <div className="form_picker">
                  <p>Elemento gráfico</p>
                  <button
                    type="button"
                    onClick={() => setShowIconPicker(true)}
                    className="icon_btn"
                  >
                    <span
                      className="material-symbols-outlined"
                      style={{
                        fontSize: "32px",
                      }}
                    >
                      {formData.icono}
                    </span>
                  </button>
                </div>

                {/** MODAL SELECTOR DE ICONO */}

                {showIconPicker && (
                  <div className="icon_picker">
                    <h3>Selecciona un icono</h3>

                    <div className="icon_grid">
                      {iconos.map((iconName) => (
                        <button
                          key={iconName}
                          className="icon_btn"
                          onClick={() => {
                            setFormData({
                              ...formData,
                              icono: iconName,
                            });

                            setShowIconPicker(false);
                          }}
                        >
                          <span
                            className="material-symbols-outlined"
                            style={{
                              fontSize: "32px",
                            }}
                          >
                            {iconName}
                          </span>
                        </button>
                      ))}
                    </div>
                  </div>
                )}
              </>
            )}

            <div className="form_section_picker">
              <p>Sección</p>

              <label className="radio_option">
                <input
                  type="radio"
                  name="seccion"
                  value="actividades"
                  checked={seccion === "actividades"}
                  onChange={() => setSeccion("actividades")}
                />
                Actividades
              </label>

              <label className="radio_option">
                <input
                  type="radio"
                  name="seccion"
                  value="talleres"
                  checked={seccion === "talleres"}
                  onChange={() => setSeccion("talleres")}
                />
                Talleres
              </label>
            </div>

            <button
              className="form_btn"
              id="save_btn"
              onClick={() => {
                onSave(formData, seccion);
                setEditing(false);
              }}
            >
              Guardar
            </button>

            <button
              className="form_btn"
              id="add_btn"
              onClick={() => {
                onAdd?.(
                  {
                    ...formData,
                    texto: "Insertar datos",
                  },
                  seccion,
                );

                setEditing(false);
              }}
            >
              Añadir elemento
            </button>

            <button className="form_btn" id="delete_btn" onClick={onDelete}>
              Eliminar
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
