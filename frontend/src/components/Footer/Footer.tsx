import logo from "/logo_footer.png";
import "../Footer/Footer.css";
export default function Footer() {
  return (
    <footer>
      <div className="contenido">
        <img src={logo} alt="logo de la municipalidad de santiago" />
      </div>
    </footer>
  );
}
