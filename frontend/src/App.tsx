import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar/Navbar";
import Inicio from "./components/pages/inicio/inicio";
import { Base } from "./components/Base/Base";
import Footer from "./components/Footer/Footer";
import AccessibilityWidget from "./components/Accesibilidad/Accesibilidad";
import PopupEncuesta from "./components/popupencuesta/PopupEncuesta";
import ConexionComunitaria from "./components/pages/ConexionComunitaria/ConexionComunitaria";

function App() {
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
      <AccessibilityWidget targetSelector="#site-content" />
      <Footer />
    </BrowserRouter>
  );
}

export default App;
