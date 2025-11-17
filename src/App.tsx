import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar/Navbar";
import Home from "./components/pages/Home/Home";
import PersonasMayores from "./components/pages/PersonasMayores/PersonasMayores";
import Discapacidad from "./components/pages/Discapacidad/Discapacidad";
import Juventud from "./components/pages/Juventud/Juventud";
import NinezAdolescencia from "./components/pages/Ninez-Adolescencia/NinezAdolescencia";
import { Base } from "./components/Base/Base";
import Footer from "./components/Footer/Footer";

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route
          path="/personas-mayores"
          element={<Base content={<PersonasMayores />} />}
        />
        <Route
          path="/discapacidad"
          element={<Base content={<Discapacidad />} />}
        />
        <Route path="/juventud" element={<Base content={<Juventud />} />} />
        <Route
          path="/ninezAdolescencia"
          element={<Base content={<NinezAdolescencia />} />}
        />
      </Routes>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
