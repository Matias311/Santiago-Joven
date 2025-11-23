import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import logo from "../../assets/logo.png";
import { faInstagram } from "@fortawesome/free-brands-svg-icons/faInstagram";
import { faYoutube } from "@fortawesome/free-brands-svg-icons/faYoutube";
import { faSquareFacebook } from "@fortawesome/free-brands-svg-icons/faSquareFacebook";
import { faCircleUser } from "@fortawesome/free-regular-svg-icons/faCircleUser";
import "../Navbar/Navbar.css";
import { faCaretDown } from "@fortawesome/free-solid-svg-icons/faCaretDown";
import { Link } from "react-router-dom";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons/faMagnifyingGlass";
import Dropdown from "../Dropdown/Dropdown";
import { faDumbbell } from "@fortawesome/free-solid-svg-icons/faDumbbell";
import { faAccessibleIcon } from "@fortawesome/free-brands-svg-icons/faAccessibleIcon";
import { faChildReaching } from "@fortawesome/free-solid-svg-icons/faChildReaching";
import { faChild } from "@fortawesome/free-solid-svg-icons/faChild";
import { faEarthEurope } from "@fortawesome/free-solid-svg-icons/faEarthEurope";
import { faPeopleLine } from "@fortawesome/free-solid-svg-icons/faPeopleLine";
import { faPersonCane } from "@fortawesome/free-solid-svg-icons/faPersonCane";
import { faIndianRupeeSign } from "@fortawesome/free-solid-svg-icons/faIndianRupeeSign";
import { faBellConcierge } from "@fortawesome/free-solid-svg-icons/faBellConcierge";
import { faLocationDot } from "@fortawesome/free-solid-svg-icons/faLocationDot";

const serviciosSection = [
  {
    label: "Centros Comunitarios",
    to: "/centros-comunitarios",
    icon: <FontAwesomeIcon icon={faMagnifyingGlass} />,
  },
  {
    label: "Deportes y Recreación",
    to: "/deportes-recreacion",
    icon: <FontAwesomeIcon icon={faDumbbell} />,
  },
  {
    label: "Discapacidad",
    to: "/discapacidad",
    icon: <FontAwesomeIcon icon={faAccessibleIcon} />,
  },
  {
    label: "Niñez y Adolescencia",
    to: "/ninezAdolescencia",
    icon: <FontAwesomeIcon icon={faChildReaching} />,
  },
  {
    label: "Juventud",
    to: "/juventud",
    icon: <FontAwesomeIcon icon={faChild} />,
  },
  {
    label: "Migrantes",
    to: "/migrantes",
    icon: <FontAwesomeIcon icon={faEarthEurope} />,
  },
  {
    label: "Participación Ciudadana",
    to: "/participacion-ciudadana",
    icon: <FontAwesomeIcon icon={faPeopleLine} />,
  },
  {
    label: "personas mayores",
    to: "/personas-mayores",
    icon: <FontAwesomeIcon icon={faPersonCane} />,
  },
  {
    label: "Pueblos Originarios",
    to: "/pueblos-originarios",
    icon: <FontAwesomeIcon icon={faIndianRupeeSign} />,
  },
  {
    label: "Servicios Sociales",
    to: "/servicios-sociales",
    icon: <FontAwesomeIcon icon={faBellConcierge} />,
  },
];

const conocenosSection = [
  {
    label: "Dirección de Desarrollo Comunitario",
    to: "/direccion-desarrollo-comunitario",
    icon: <FontAwesomeIcon icon={faLocationDot} />,
  },
  {
    label: "Gestión y administración Comunitaria",
    to: "/gestion-administracion-comunitaria",
    icon: <FontAwesomeIcon icon={faLocationDot} />,
  },
  {
    label: "Servicios Sociales",
    to: "/servicios-sociales",
    icon: <FontAwesomeIcon icon={faLocationDot} />,
  },
  {
    label: "Participación Ciudadana",
    to: "/participacion-ciudadana",
    icon: <FontAwesomeIcon icon={faLocationDot} />,
  },
  {
    label: "Desarrollo Económico Local",
    to: "/desarrollo",
    icon: <FontAwesomeIcon icon={faLocationDot} />,
  },
  {
    label: "Desarrollo Social",
    to: "/desarrollo-social",
    icon: <FontAwesomeIcon icon={faLocationDot} />,
  },
  {
    label: "Deportes y Recreación",
    to: "/deportes-recreacion",
    icon: <FontAwesomeIcon icon={faLocationDot} />,
  },
];

export default function Navbar() {
  return (
    <header>
      <h1>
        <img src={logo} alt="Municipalidad de santiago" />
      </h1>
      <nav>
        <ul>
          <li>
            <Link to="/">Inicio</Link>
          </li>
          <li>
            <Dropdown
              label="Servicios"
              icon={<FontAwesomeIcon icon={faCaretDown} />}
              items={serviciosSection}
            />
          </li>
          <li>
            <Link to="/agenda">Agenda</Link>
          </li>
          <li>
            <Link to="/talleres">Talleres</Link>
          </li>
          <li>
            <Dropdown
              label="Conocenos"
              icon={<FontAwesomeIcon icon={faCaretDown} />}
              items={conocenosSection}
            />
          </li>
        </ul>
      </nav>
      <div>
        <FontAwesomeIcon className="icon" icon={faInstagram} />
        <FontAwesomeIcon className="icon" icon={faYoutube} />
        <FontAwesomeIcon className="icon" icon={faSquareFacebook} />
        <FontAwesomeIcon className="icon" icon={faCircleUser} />
      </div>
    </header>
  );
}
