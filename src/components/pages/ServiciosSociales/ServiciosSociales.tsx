import "../ServiciosSociales/ServiciosSociales.css";
import imagen from "../../../../public/ServiciosSociales/IMAGENSS.png";

export default function ServiciosSociales() {
  return (
    <div className="servicios-sociales">
      <div className="ss-header">
        <aside className="ss-title">
          <h2>
            Servicios <br />
            Sociales
          </h2>
        </aside>

        <div className="ss-hero">
          <img src={imagen} alt="Servicios Sociales" />
        </div>
      </div>

      <article className="ss-content">
        <h2>Subdirección de Servicios Sociales</h2>

        <p>
          Tiene por misión ejecutar e implementar las políticas, planes y
          programas de la Municipalidad que tienen como objetivo dar protección
          social a las personas y grupos vulnerables de la comuna, facilitando
          su integración, participación y promoción social, a través de la
          articulación y coordinación con otros organismos municipales y las
          instituciones públicas afines.
        </p>

        <p>
          A través de la Atención Social Integral, colaboran en la resolución de
          las problemáticas de las personas y sus grupos familiares (quienes
          presenten carencias sociales manifiestas), y dan respuesta a
          necesidades urgentes (económicas, vivienda, trabajo, previsión, salud,
          educación y justicia, entre otras). Asimismo, orientan y apoyan a las
          personas que presentan una situación económica vulnerable en la
          postulación a diversos Subsidios (sociales y fiscales), entre los que
          mencionamos: subsidio al recién nacido, subsidio de agua potable,
          subsidio de aseo, pensión básica solidaria de invalidez, bono por
          hijo, entre otros. También entrega orientación para la postulación a
          la Beca Presidente de la República y la Beca Indígena.
        </p>

        <p>
          Cabe destacar que el Municipio cuenta con el beneficio de la Beca de
          Estudios Superiores IMS, la que se entrega a más de 200 estudiantes
          anualmente. Otro servicio que otorgan es la aplicación, ingreso de
          antecedentes, actualización e impresión de cartolas del Registro
          Social de Hogares (RSH), además de atención en los territorios a
          petición de organizaciones comunitarias.
        </p>
      </article>
    </div>
  );
}
