import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar/Navbar";
import Home from "./components/pages/Home/Home";
import PersonasMayores from "./components/pages/PersonasMayores/PersonasMayores";
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
      </Routes>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
