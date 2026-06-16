import { useEffect, useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar/Navbar";
import Inicio from "./components/pages/inicio/inicio";
import Asesoria from "./components/pages/Asesorias/Asesoria";
import { Base } from "./components/Base/Base";
import Footer from "./components/Footer/Footer";
import AccessibilityWidget from "./components/Accesibilidad/Accesibilidad";
import PopupEncuesta from "./components/popupencuesta/PopupEncuesta";

/**
 * @module App
 * @description Componente raíz de la aplicación Santiago Joven. Gestiona el enrutamiento,
 * la persistencia y sincronización del tema oscuro/claro, e integra los componentes
 * principales como la barra de navegación, footer, widget de accesibilidad y popup de encuestas.
 */

/**
 * Componente principal (App) de la aplicación.
 *
 * @component
 * @description
 * - Maneja el estado global del modo oscuro/claro con persistencia en localStorage
 * - Aplica la clase CSS "dark" o "light" al elemento raíz del documento
 * - Respeta las preferencias del sistema operativo si no hay tema guardado
 * - Proporciona las rutas de la aplicación usando React Router
 * - Integra componentes de UI como Navbar, Footer, Accesibilidad y PopupEncuesta
 *
 * @returns {JSX.Element} Árbol de componentes de la aplicación con enrutamiento y tema configurado
 *
 * @example
 * // En main.tsx
 * import App from './App'
 * ReactDOM.createRoot(document.getElementById('root')!).render(<App />)
 */
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
      {/* 
        Navbar: Barra de navegación con toggle del tema oscuro/claro
        Props:
        - modoOscuro: estado actual del modo oscuro
        - onToggleModo: callback para cambiar el tema
      */}
      <Navbar modoOscuro={modoOscuro} onToggleModo={() => setModoOscuro((value) => !value)} />
      
      {/* 
        PopupEncuesta: Widget de encuestas flotante
        Props:
        - hayEncuestas: true para mostrar, false para ocultar
        Nota: Cambiar a false si no hay encuestas activas
      */}
      <PopupEncuesta hayEncuestas={true} />
      
      {/* 
        Routes: Define las rutas de la aplicación
        Nota: Mantener la estructura tal como está. Cambiar solo el contenido
        para agregar nuevas rutas siguiendo el patrón Base + contenido
      */}
      <Routes>
        {/* Aqui tienen que poner exactamente lo mismo, pero cambiando el elemento por el componente que les corresponda  */}
        <Route path="/" element={<Base content={<Inicio />} />} /> {/* esto de aca no se borra */}
        <Route path="/asesoria" element={<Base content={<Asesoria />} />} />
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
