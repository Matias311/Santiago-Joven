import { useEffect, useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar/Navbar";
import Inicio from "./components/pages/inicio/inicio";
import Asesoria from "./components/pages/Asesorias/Asesoria";
import { Base } from "./components/Base/Base";
import Footer from "./components/Footer/Footer";
import AccessibilityWidget from "./components/Accesibilidad/Accesibilidad";
import PopupEncuesta from "./components/popupencuesta/PopupEncuesta";
import ConexionComunitaria from "./components/pages/ConexionComunitaria/ConexionComunitaria";

function App() {
  /**
   * Estado del modo oscuro/claro.
   * @type {[boolean, Function]}
   * @description
   * - `true`: modo oscuro activado
   * - `false`: modo claro activado
   * Se inicializa verificando:
   * 1. Tema guardado en localStorage
   * 2. Preferencia del sistema operativo (prefers-color-scheme)
   */
  const [modoOscuro, setModoOscuro] = useState(() => {
    const savedTheme = localStorage.getItem("theme");
    if (savedTheme === "dark") return true;
    if (savedTheme === "light") return false;
    return window.matchMedia?.("(prefers-color-scheme: dark)").matches ?? false;
  });

  /**
   * Efecto que sincroniza el estado del tema con el DOM y localStorage.
   *
   * @description
   * Cuando cambia `modoOscuro`:
   * - Añade/elimina la clase "dark" del elemento raíz (<html>)
   * - Añade/elimina la clase "light" del elemento raíz
   * - Persiste la selección en localStorage bajo la clave "theme"
   * Las variables CSS del tema se aplican automáticamente a través de las clases
   * definidas en reset.css
   *
   * @dependency modoOscuro
   */
  useEffect(() => {
    const root = document.documentElement;
    if (modoOscuro) {
      root.classList.add("dark");
      root.classList.remove("light");
      localStorage.setItem("theme", "dark");
    } else {
      root.classList.remove("dark");
      root.classList.add("light");
      localStorage.setItem("theme", "light");
    }
  }, [modoOscuro]);

  return (
    <BrowserRouter>
      <Navbar />
      <PopupEncuesta hayEncuestas={true} />
      {/* Cambiar a false para ocultar el popup si es que no hay encuestas */}
      <Routes>
        {/* Aqui tienen que poner exactamente lo mismo, pero cambiando el elemento por el componente que les corresponda  */}
        <Route path="/" element={<Base content={<Inicio />} />} />{" "}
        {/* esto de aca no se borra */}
        <Route
          path="/conexioncomunitaria"
          element={<Base content={<ConexionComunitaria />} />}
        />
      </Routes>

      {/* 
        AccessibilityWidget: Widget de accesibilidad flotante
        Props:
        - targetSelector: selector CSS del elemento contenedor principal
      */}
      <AccessibilityWidget targetSelector="#site-content" />

      {/* Footer: Pie de página */}
      <Footer />
    </BrowserRouter>
  );
}

export default App;
