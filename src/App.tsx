import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar/Navbar";
import PersonasMayores from "./components/pages/PersonasMayores/PersonasMayores";
import DeportesRecreacion from "./components/pages/deportesYRecreacion/deportesYRecreacion";
import CentrosComunitarios from "./components/pages/centrosComunitarios/centrosComunitarios";
import CCMatta from "./components/pages/centrosComunitarios/CCMatta";
import CCCarol from "./components/pages/centrosComunitarios/CCCarol";
import DesarrolloLocal from "./components/pages/Desarrollo/desarrollo";
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
        <Route path="/matta" element={<Base content={<CCMatta />} />} />
        <Route path="/carol" element={<Base content={<CCCarol />} />} />
        <Route path="/" element={<Base content={<Inicio />} />} />
        <Route path="/agenda" element={<Base content={<Agenda />} />} />
        <Route path="/talleres" element={<Base content={<Talleres />} />} />
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
