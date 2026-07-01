import { type CartaItem } from "../types/CartaItem";
import Card from "../../components/cartas/Card";
import { useState, useEffect } from "react";
import { iconos } from "../../assets/IconosCards/iconos";

import "./EditableCard.css";

interface EditableCardProps {
  cardProps: CartaItem;
  isAdmin: boolean;
  isEditing?: boolean;
  onEditingChange?: (editing: boolean) => void;
  onSave: (updated: CartaItem) => void;
  onDelete: () => void;
  onAdd?: (newCard: CartaItem) => void;
}

export default function EditableCard({
  cardProps,
  isAdmin,
  isEditing,
  onEditingChange,
  onSave,
  onDelete,
  onAdd,
}: EditableCardProps) {
  const [editing, setEditing] = useState(false);
  const [formData, setFormData] = useState<CartaItem>(cardProps);
  const [showIconPicker, setShowIconPicker] = useState(false);

  // Verifica si se esta editando para avisar al slider
  const openedModal = isEditing !== undefined ? isEditing : editing;

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSave = () => {
    onSave(formData);
  };

  useEffect(() => {
    if (openedModal) {
      document.body.style.overflow = "hidden";
    } else {
      document.body.style.overflow = "";
    }
    return () => {
      document.body.style.overflow = "";
    };
  }, [openedModal]);

  // Funciones para ver el estado local o avisar al slider
  const openModal = () => {
    if (onEditingChange) onEditingChange(true);
    else setEditing(true);
  };

  const closeModal = () => {
    if (onEditingChange) onEditingChange(false);
    else setEditing(false);
  };

  return (
    <>
      <div className="container">
        {/* Card original, con sus props */}
        <Card {...cardProps} />

        {/* Boton editar, solo para admin */}
        {isAdmin && (
          <button
            className="card_edit_btn"
            onClick={(e) => {
              e.stopPropagation();
              openModal();
            }}
          >
            Editar
          </button>
        )}
      </div>

      {/* MODAL EDITAR CARD */}
      {openedModal && (
        <div className="modal_edit_overlay">
          <div className="modal_edit_content">
            <h2>Editar Carta</h2>

            <p>Titulo de la carta</p>
            <textarea
              id="title"
              name="titulo"
              value={formData.titulo}
              onChange={handleChange}
              placeholder="Ingresa el título"
            ></textarea>

            {"tituloColor" in formData && formData.tituloColor != null && (
              <>
                <p>Color del título</p>
                <input
                  type="color"
                  value={formData.tituloColor}
                  onChange={(e) =>
                    setFormData({
                      ...formData,
                      tituloColor: e.target.value,
                    })
                  }
                />
              </>
            )}

            {formData.subtitulo != null && (
              <textarea
                id="subtitle"
                name="subtitulo"
                value={formData.subtitulo}
                onChange={handleChange}
                placeholder="Ingresa el subtítulo"
              ></textarea>
            )}

            <p>Descripción</p>
            <textarea
              id="description"
              name="descripcion"
              value={formData.descripcion}
              onChange={handleChange}
              placeholder="Describe la carta"
              rows={6}
            ></textarea>

            {/* URL de la página */}
            {"url" in formData && formData.url != null && (
              <>
                <p>URL de la página</p>
                <input
                  type="text"
                  name="url"
                  value={(formData as { url?: string }).url || ""}
                  onChange={handleChange}
                  placeholder="https://ejemplo.com"
                />
              </>
            )}

            {/* Imagen de la página */}
            {"imagen" in formData && formData.imagen != null && (
              <>
                <p>URL de la imagen</p>
                <input
                  type="text"
                  name="imagen"
                  value={(formData as { imagen?: string }).imagen || ""}
                  onChange={handleChange}
                  placeholder="https://ejemplo.com/imagen.jpg"
                />
              </>
            )}

            {/* Descripción de la página (meta description) */}
            {"descripcionPagina" in formData &&
              formData.descripcionPagina != null && (
                <>
                  <p>Descripción de la página (SEO)</p>
                  <textarea
                    id="description"
                    name="descripcion"
                    value={formData.descripcion}
                    onChange={handleChange}
                    placeholder="Describe la carta"
                    rows={6}
                  ></textarea>
                </>
              )}
            {/* Eslogan */}
            {"eslogan" in formData && formData.eslogan != null && (
              <>
                <p>Eslogan</p>
                <textarea
                  name="eslogan"
                  value={formData.eslogan as string}
                  onChange={handleChange}
                  placeholder="Ingresa el eslogan"
                  rows={2}
                />
              </>
            )}

            {/* Objetivo */}
            {"objetivo" in formData && formData.objetivo != null && (
              <>
                <p>Objetivo</p>
                <textarea
                  name="objetivo"
                  value={formData.objetivo as string}
                  onChange={handleChange}
                  placeholder="Ingresa el objetivo"
                  rows={3}
                />
              </>
            )}

            {/* Enlace de inscripción */}
            {"enlaceInscripcion" in formData &&
              formData.enlaceInscripcion != null && (
                <>
                  <p>Enlace de inscripción</p>
                  <input
                    type="url"
                    name="enlaceInscripcion"
                    value={formData.enlaceInscripcion as string}
                    onChange={handleChange}
                    placeholder="https://..."
                  />
                </>
              )}
            {/* Selector de icono y color */}
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
                        color: formData.iconoColor,
                        fontSize: "32px",
                      }}
                    >
                      {formData.icono}
                    </span>
                  </button>
                </div>

                {/* MODAL SELECTOR DE ICONO */}
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
                              color: formData.iconoColor,
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

                {/* SELECTOR COLOR ICONO */}
                <input
                  type="color"
                  value={formData.iconoColor}
                  onChange={(e) =>
                    setFormData({
                      ...formData,
                      iconoColor: e.target.value,
                    })
                  }
                />

                {"iconoFondo" in formData && formData.iconoFondo != null && (
                  <>
                    <p>Color de fondo del icono</p>
                    <input
                      type="color"
                      value={formData.iconoFondo}
                      onChange={(e) =>
                        setFormData({
                          ...formData,
                          iconoFondo: e.target.value,
                        })
                      }
                    />
                  </>
                )}
              </>
            )}

            {/* EDITAR BOTON DE REDIRECCIÓN */}
            {formData.boton && (
              <>
                <p>Titulo Boton redireccionador</p>
                <textarea
                  id="button"
                  name="boton"
                  value={formData.boton}
                  onChange={handleChange}
                  placeholder="Ejemplo: 'Inscríbete'"
                ></textarea>

                {formData.botoncolor != null && (
                  <>
                    <p>Color del botón</p>
                    <input
                      type="color"
                      value={formData.botoncolor}
                      onChange={(e) =>
                        setFormData({
                          ...formData,
                          botoncolor: e.target.value,
                        })
                      }
                    />
                  </>
                )}
              </>
            )}

            {/* Botones del formulario */}
            <button
              className="form_btn"
              id="save_btn"
              onClick={() => {
                handleSave();
                closeModal();
              }}
            >
              Guardar Carta
            </button>

            <button
              className="form_btn"
              id="add_btn"
              onClick={() => {
                onAdd?.(formData);
                closeModal();
              }}
            >
              Añadir Carta
            </button>

            <button
              className="form_btn"
              id="delete_btn"
              onClick={() => {
                onDelete();
                closeModal();
              }}
            >
              Eliminar Carta
            </button>

            <button className="form_btn" id="cancel_btn" onClick={closeModal}>
              Cancelar
            </button>
          </div>
        </div>
      )}
    </>
  );
}
