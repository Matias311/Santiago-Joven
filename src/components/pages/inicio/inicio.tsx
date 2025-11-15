// src/StgoJoven.tsx
import municipalidadImg from "../../../assets/inicio/municipalidad-centro.png";
import "./inicio.css";
export default function StgoJoven() {
  return (
    <>
      <div className="imagen-centro">
        <img src={municipalidadImg} alt="imagen-centro municipalidad" />
      </div>

      <main>
        <section className="stgojoven">
          <h1>STGO Joven</h1>

          <h2>¿En qué consiste?</h2>
          <p>
            STGO Joven es un espacio abierto a las juventudes de nuestra comuna,
            que busca favorecer la participación e integración local de las
            juventudes, a través de la articulación de programas y servicios
            según sus intereses y necesidades. Se ofrecen diversos servicios,
            como la orientación social, facilitación de espacios, vinculación
            con el medio y una oferta programática de talleres en áreas de
            arte/cultura, autocuidado/vida sana, tecnología/innovación,
            participación/integración social, empleabilidad/oficios,
            aprendizajes y deporte. Estos talleres se generan a partir de los
            intereses y preferencias que los propios jóvenes señalan como
            prioritarios. Impulsamos mediante diversas campañas, que las
            juventudes sean escuchadas y tengan una participación activa en el
            desarrollo comunal.
          </p>
          <p>
            Estos talleres se enmarcan en el interés de ofrecer respuestas a las
            problemáticas que las juventudes definen como prioritarias,
            impulsando instancias donde las juventudes, desde sus saberes
            socio-comunitarios, se vinculen en una participación activa en sus
            comunidades.
          </p>

          <h2>¿A quiénes va dirigido?</h2>
          <p>
            Jóvenes que tengan entre 14 y 29 años, que vivan, estudien, trabajen
            o se organicen en la comuna de Santiago.
          </p>

          <h2>¿En qué lugar se presta el servicio?</h2>
          <p>
            Casa de las Juventudes ubicada en Virginia Opazo 22, a pasos de la
            estación de metro República.
          </p>

          <h2>¿Qué requisitos se deben cumplir?</h2>
          <ul>
            <li>Tener entre 14 y 29 años.</li>
            <li>
              Vivir, estudiar, trabajar u organizarse en la comuna de Santiago.
            </li>
          </ul>

          <h2>¿Qué documentos son necesarios?</h2>
          <ul>
            <li>
              Presentar documento que certifique que vive, estudie, trabaje o se
              organice en la comuna de Santiago (cuentas a su nombre,
              certificado de residencia o certificado de estudio o laboral).
            </li>
          </ul>

          <p>
            <strong>Servicio Gratuito</strong>
          </p>
        </section>
      </main>
    </>
  );
}
