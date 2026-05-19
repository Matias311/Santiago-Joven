import { faRegistered } from "@fortawesome/free-regular-svg-icons/faRegistered";
import logo from "/logo_footer.png";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "../Footer/Footer.css";
import { Link } from "react-router-dom";
export default function Footer() {
  return (
    <footer>
      <div className="contenido">
        <img src={logo} alt="logo de la municipalidad de santiago" />
        <div>
          <h2>
            Direccion de desarrollo comunitario <br /> ilustre municipalidad de
            Santiago
          </h2>
          <p>Direccion: Santiago Herrera 360</p>
          <p>
            <FontAwesomeIcon icon={faRegistered} />
            Todos los derechos reservados
          </p>
          <p>
            Participa en la <Link to="/encuesta">encuesta</Link> para mejorar la
            pagina.
          </p>
        </div>
      </div>
    </footer>
  );
}
