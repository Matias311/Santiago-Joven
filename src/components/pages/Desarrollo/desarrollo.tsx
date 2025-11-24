import "./desarrollo.css";
import Desarrollo from "../../../../public/Desarrollo.png";

export default function DesarrolloLocal() {
  return (
    <div className="des-page">
      <div className="banner">
        <h2 className="title-desarrollo-economico">
          Desarrollo Económico Local
        </h2>
        <div className="hero">
          <img src={Desarrollo} alt="Banner" className="hero-image" />
        </div>
      </div>

      <section className="content">
        <h2>Subdirección de Desarrollo Económico Local</h2>
        <p>
          La Subdirección de Desarrollo Económico Local (DIDEL) tiene por
          objetivo mejorar la calidad de vida de las vecinas y los vecinos de la
          comuna, a través de distintos programas e iniciativas gestionadas por
          las oficinas de Empleo, Capacitación, y Emprendimiento y Barrios
          Comerciales.
        </p>

        <h3>Oficina Municipal de Intermediación Laboral (OMIL)</h3>
        <p>
          Es la encargada de informar y orientar en materias de empleo, siendo
          un intermediario en la colocación laboral de vecinos y vecinas.
          Además, entrega herramientas y conocimientos prácticos para la
          búsqueda de trabajo, contribuyendo al reconocimiento de las
          competencias de las personas, mejorando así sus potencialidades a
          través de los siguientes servicios: Asesoría en la búsqueda de empleo,
          reclutamiento, selección de personal, OMIL Móvil, Bolsa de Empleo y
          ofertas de empleo.
        </p>

        <h3>Oficina de Capacitación</h3>
        <p>
          Entrega herramientas que fortalecen la capacidad de empleabilidad,
          creación, innovación y sostenibilidad en el tiempo, para quienes
          deseen emprender o están en la búsqueda de un nuevo empleo. Para ello,
          cuentan con la Escuela de Emprendimiento, que busca promover el
          desarrollo de los emprendimientos y negocios de la comuna de Santiago;
          y la Escuela de Empleo, que ayuda a descubrir y fortalecer las
          competencias, mejorando las posibilidades de empleabilidad.
        </p>

        <h3>Oficina de Emprendimiento y Barrios Comerciales</h3>
        <p>
          Orienta y ayuda a los emprendedores, emprendedoras y locatarios de la
          comuna mediante asesorías, programas de apoyo, formación y ferias de
          emprendimiento. A su vez, se enfoca en el fortalecimiento de Barrios
          Comerciales, potenciando la identidad patrimonial de cada sector de la
          comuna de Santiago, la asociatividad y dinamización.
        </p>
      </section>
    </div>
  );
}
