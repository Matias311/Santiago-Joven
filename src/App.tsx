import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar/Navbar";
import Home from "./components/pages/Home/Home";
import PersonasMayores from "./components/pages/PersonasMayores/PersonasMayores";
import ParticipacionCiudadana from "./components/pages/ParticipacionCiudadana/ParticipacionCiudadana";
import DesarrolloSocial from "./components/pages/DesarrolloSocial/DesarrolloSocial";
import { Base } from "./components/Base/Base";
import Footer from "./components/Footer/Footer";

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        {/* Aqui tienen que poner exactamente lo mismo, pero cambiando el elemento por el componente que les corresponda  */}
        <Route path="/" element={<Home />} />
        <Route
          path="/personas-mayores"
          element={<Base content={<PersonasMayores />} />}
        />
        <Route
          path="/participacion-ciudadana"
          element={<Base content={<ParticipacionCiudadana />} />}
        />
        <Route
          path="/desarrollo-social"
          element={<Base content={<DesarrolloSocial />} />}
        />
      </Routes>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
