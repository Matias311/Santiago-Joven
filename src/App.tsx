import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar/Navbar";
import Home from "./components/pages/Home/Home";
import PersonasMayores from "./components/pages/PersonasMayores/PersonasMayores";
import { Base } from "./components/Base/Base";
import Footer from "./components/Footer/Footer";
import Encuesta from "./components/pages/Encuesta/Encuesta";
import DiversidadSexual from "./components/pages/DiversidadSexual/DiversidadSexual";
import MujeresIgualdadGenero from "./components/pages/MujeresIgualdadGenero/MujeresIgualdadGenero";
import ServiciosSociales from "./components/pages/Servicios Sociales/ServiciosSociales";

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        {/* Aqui tienen que poner exactamente lo mismo, pero cambiando el elemento por el componente que les corresponda  */}
        <Route path="/" element={<Home />} />
        {/* --------------------Partes Joshua xd (yo jiji)-------------------- */}
        <Route path="/encuesta" element={<Encuesta />} />
        <Route
          path="/diversidad-sexual"
          element={<Base content={<DiversidadSexual />} />}
        />
        <Route
          path="/mujeres-igualdad-genero"
          element={<MujeresIgualdadGenero />}
        />
        <Route path="/servicios-sociales" element={<ServiciosSociales />} />
        <Route
          path="/personas-mayores"
          element={<Base content={<PersonasMayores />} />}
        />
      </Routes>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
