import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar/Navbar";
import PersonasMayores from "./components/pages/PersonasMayores/PersonasMayores";
import Inicio from "./components/pages/inicio/inicio";
import Agenda from "./components/pages/agenda/agenda";
import Talleres from "./components/pages/talleres/talleres";
import { Base } from "./components/Base/Base";
import Footer from "./components/Footer/Footer";

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        {/* Aqui tienen que poner exactamente lo mismo, pero cambiando el elemento por el componente que les corresponda  */}
        <Route path="/" element={<Base content={<Inicio />} />} />
        <Route path="/agenda" element={<Base content={<Agenda />} />} />
        <Route path="/talleres" element={<Base content={<Talleres />} />} />
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
