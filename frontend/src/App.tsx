import { useEffect, useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar/Navbar";
import Inicio from "./components/pages/inicio/inicio";
import { Base } from "./components/Base/Base";
import Footer from "./components/Footer/Footer";
import AccessibilityWidget from "./components/Accesibilidad/Accesibilidad";
import PopupEncuesta from "./components/popupencuesta/PopupEncuesta";

function App() {
  const [modoOscuro, setModoOscuro] = useState(() => {
    const savedTheme = localStorage.getItem("theme");
    if (savedTheme === "dark") return true;
    if (savedTheme === "light") return false;
    return window.matchMedia?.("(prefers-color-scheme: dark)").matches ?? false;
  });

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
      <Navbar modoOscuro={modoOscuro} onToggleModo={() => setModoOscuro((value) => !value)} />
      <PopupEncuesta hayEncuestas={true} />{/* Cambiar a false para ocultar el popup si es que no hay encuestas */}
      <Routes>
        {/* Aqui tienen que poner exactamente lo mismo, pero cambiando el elemento por el componente que les corresponda  */}
        <Route path="/" element={<Base content={<Inicio />} />} /> {/* esto de aca no se borra */}
      </Routes>
      <AccessibilityWidget targetSelector="#site-content" />
      <Footer />
    </BrowserRouter>
  );
}

export default App;
