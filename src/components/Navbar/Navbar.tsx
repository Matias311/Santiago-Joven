import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import logo from "../../assets/logo.png";
import { faInstagram } from "@fortawesome/free-brands-svg-icons/faInstagram";
import { faYoutube } from "@fortawesome/free-brands-svg-icons/faYoutube";
import { faSquareFacebook } from "@fortawesome/free-brands-svg-icons/faSquareFacebook";
import { faCircleUser } from "@fortawesome/free-regular-svg-icons/faCircleUser";
import "../Navbar/Navbar.css";

export default function Navbar() {
  return (
    <header>
      <h1>
        <img src={logo} alt="Municipalidad de santiago" />
      </h1>
      <ul>
        <li>Inicio</li>
        {/* TODO: aqui se debe crear un componente de dropdown para agregar bien la funcionalidad */}
        <li>Servicios</li>
        <li>Agenda</li>
        <li>Talleres</li>
        {/* TODO: aqui se debe crear un componente de dropdown para agregar bien la funcionalidad */}
        <li>Conocenos</li>
      </ul>
      <div>
        <FontAwesomeIcon icon={faInstagram} />
        <FontAwesomeIcon icon={faYoutube} />
        <FontAwesomeIcon icon={faSquareFacebook} />
        <FontAwesomeIcon icon={faCircleUser} />
      </div>
    </header>
  );
}
