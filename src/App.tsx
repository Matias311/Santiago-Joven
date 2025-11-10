import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar/Navbar";
import Home from "./components/pages/Home/Home";

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        {/* Aqui tienen que poner exactamente lo mismo, pero cambiando el elemento por el componente que les corresponda  */}
        <Route path="/" element={<Home />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
