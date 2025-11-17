import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar/Navbar";
import Home from "./components/pages/Home/Home";
import PersonasMayores from "./components/pages/PersonasMayores/PersonasMayores";
import DeportesRecreacion from "./components/pages/deportesYRecreacion/deportesYRecreacion";
import CentrosComunitarios from "./components/pages/centrosComunitarios/centrosComunitarios";
import CCMatta from "./components/pages/centrosComunitarios/CCMatta";
import CCCarol from "./components/pages/centrosComunitarios/CCCarol";
import DesarrolloLocal from "./components/pages/Desarrollo/desarrollo";
import { Base } from "./components/Base/Base";
import Footer from "./components/Footer/Footer";

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        {/* Aqui tienen que poner exactamente lo mismo, pero cambiando el elemento por el componente que les corresponda  */}
        <Route path="/" element={<Home />} />
        <Route path="/matta" element={<Base content={<CCMatta />} />} />
        <Route path="/carol" element={<Base content={<CCCarol />} />} />
        <Route
          path="/desarrollo"
          element={<Base content={<DesarrolloLocal />} />}
        />
        <Route
          path="/centros-comunitarios"
          element={<Base content={<CentrosComunitarios />} />}
        />
        <Route
          path="/deportes-recreacion"
          element={<Base content={<DeportesRecreacion />} />}
        />
        <Route
          path="/pages/personasMayores"
          element={<Base content={<PersonasMayores />} />}
        />
      </Routes>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
