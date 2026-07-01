import { type ConexionItem } from "../types/ConexionItem";
import { useState, useEffect } from "react";

{
  /** Usamos el mismo estilo que el modal de cards */
}
import "./EditableCard.css";
import { iconos } from "../../assets/IconosCards/iconos";

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
  const [formData, setFormData] = useState<ConexionItem>(item);
  const [seccion, setSeccion] = useState<"actividades" | "talleres">(
    "actividades",
  );
  const [showIconPicker, setShowIconPicker] = useState(false);

  // Actualizar formData cuando cambia el item prop
  useEffect(() => {
    setFormData(item);
  }, [item]);

  const handleSave = () => {
    // Asegurarse de que todos los campos estén en formData
    const updatedItem: ConexionItem = {
      ...formData,
      // Asegurar que los números sean números
      cupos_disponibles: Number(formData.cupos_disponibles) || 0,
      cupos: Number(formData.cupos) || 0,
    };
    onSave(updatedItem, seccion);
    setEditing(false);
  };

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

            <p>Título</p>
            <textarea
              name="texto"
              value={formData.texto || ""}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  texto: e.target.value,
                })
              }
              placeholder="Título del elemento"
            />

            <p>URL de la página</p>
            <input
              type="text"
              name="url"
              value={(formData as { url?: string }).url || ""}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  url: e.target.value,
                })
              }
              placeholder="https://ejemplo.com"
            />

            <p>URL de la imagen</p>
            <input
              type="text"
              name="imagen"
              value={(formData as { imagen?: string }).imagen || ""}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  imagen: e.target.value,
                })
              }
              placeholder="https://ejemplo.com/imagen.jpg"
            />

            <p>Descripción de la actividad o evento</p>
            <textarea
              id="description"
              name="descripcion"
              value={formData.descripcion || ""}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  descripcion: e.target.value,
                })
              }
              placeholder="Describe la actividad o evento"
              rows={6}
            />

            <p>Fecha del evento</p>
            <input
              type="date"
              name="date"
              value={formData.date || ""}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  date: e.target.value,
                })
              }
              className="modal_calendar_select"
              style={{ width: "100%", boxSizing: "border-box" }}
            />

            <p>Dirección</p>
            <input
              type="text"
              name="lugar"
              value={formData.lugar || ""}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  lugar: e.target.value,
                })
              }
              placeholder="Dirección del evento"
            />

            <p>Ciudad</p>
            <input
              type="text"
              name="ciudad"
              value={formData.ciudad || ""}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  ciudad: e.target.value,
                })
              }
              placeholder="Ciudad"
            />

            <p>Cupos disponibles</p>
            <input
              type="number"
              name="cupos_disponibles"
              value={formData.cupos_disponibles ?? ""}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  cupos_disponibles:
                    e.target.value === "" ? 0 : Number(e.target.value),
                })
              }
              placeholder="Cupos disponibles"
              min="0"
            />

            <p>Cupos totales</p>
            <input
              type="number"
              name="cupos"
              value={formData.cupos ?? ""}
              onChange={(e) =>
                setFormData({
                  ...formData,
                  cupos: e.target.value === "" ? 0 : Number(e.target.value),
                })
              }
              placeholder="Cupos totales"
              min="0"
            />

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
                  {formData.icono || "help"}
                </span>
              </button>
            </div>

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

            <button className="form_btn" id="save_btn" onClick={handleSave}>
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
                    cupos_disponibles: Number(formData.cupos_disponibles) || 0,
                    cupos: Number(formData.cupos) || 0,
                  },
                  seccion,
                );
                setEditing(false);
              }}
            >
              Añadir elemento
            </button>

            <button
              className="form_btn"
              id="delete_btn"
              onClick={() => {
                onDelete();
                setEditing(false);
              }}
            >
              Eliminar
            </button>

            <button
              className="form_btn"
              id="cancel_btn"
              onClick={() => {
                setFormData(item); // Resetear formData al original
                setEditing(false);
              }}
            >
              Cancelar
            </button>
          </div>
        </div>
      )}
    </>
  );
}
