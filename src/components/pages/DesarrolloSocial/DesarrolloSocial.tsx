import "./DesarrolloSocial.css";
import imagenPrincipal from "../../../../public/desarrollo-social/hero_desarrollo_social.png";

export default function DesarrolloSocial() {
  return (
    <div className="contenedor-desarrollo">
      <div className="caja-flexible">
        <div className="tarjeta-titulo">
          <h2 className="titulo-principal">
            <span>Desarrollo</span>
            <span>Social</span>
          </h2>
        </div>

        <div className="caja-imagen">
          <div className="imagen-wrapper">
            <img
              src={imagenPrincipal}
              alt="Desarrollo Social"
              className="imagen-principal"
            />
          </div>
        </div>
      </div>

      <div className="seccion-texto">
        <h2 className="subtitulo">Subdirección de Desarrollo Social</h2>

        <p className="parrafo">
          La Subdirección de Desarrollo Social tiene como objetivo promover,
          fomentar y potenciar el desarrollo de acciones que mejoren la calidad
          de vida de los vecinos de la comuna de Santiago. Para eso, pone a
          disposición oficinas y programas como:
        </p>

        <p className="departamento">Departamento de Personas Mayores.</p>

        <ul className="lista">
          <li>Centro de día.</li>
          <li>Oficina de Atención Sociogerontológica.</li>
          <li>Formación Continua (talleres).</li>
          <li>Vinculación con el medio.</li>
          <li>Programa Cuidados domiciliarios.</li>
        </ul>

        <p className="departamento">
          Departamento de Infancia, Adolescencia y Familia
        </p>

        <ul className="lista">
          <li>
            Oficina de la Infancia: Polos de Cuidados, Chile Crece Contigo y
            Línea de Promoción de Derechos.
          </li>
          <li>Oficina Local de la Niñez (OLN).</li>
          <li>Centro de Atención a la Familia (CAF).</li>
          <li>Santiago Joven.</li>
          <li>Senda Previene.</li>
          <li>Programa Lazos.</li>
        </ul>
      </div>

      <div className="espacio-footer" />
    </div>
  );
}
