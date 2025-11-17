import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar/Navbar";
import PersonasMayores from "./components/pages/PersonasMayores/PersonasMayores";
import ParticipacionCiudadana from "./components/pages/ParticipacionCiudadana/ParticipacionCiudadana";
import DesarrolloSocial from "./components/pages/DesarrolloSocial/DesarrolloSocial";
import Inicio from "./components/pages/inicio/inicio";
import Agenda from "./components/pages/agenda/agenda";
import Talleres from "./components/pages/talleres/talleres";
import { Base } from "./components/Base/Base";
import Footer from "./components/Footer/Footer";
import ServiciosSociales from "./components/pages/ServiciosSociales/ServiciosSociales";
import GestionYAdministracionComunitaria from "./components/pages/GestionYAdministracionComunitaria/GestionYAdministracionComunitaria";
import DireccionDeDesarrolloComunitario from "./components/pages/DirecciónDeDesarrolloComunitario/DireccionDeDesarrolloComunitario";

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
        <Route
          path="/servicios-sociales"
          element={<Base content={<ServiciosSociales />} />}
        />
        <Route
          path="/gestion-administracion-comunitaria"
          element={<Base content={<GestionYAdministracionComunitaria />} />}
        />
        <Route
          path="/direccion-desarrollo-comunitario"
          element={<Base content={<DireccionDeDesarrolloComunitario />} />}
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
