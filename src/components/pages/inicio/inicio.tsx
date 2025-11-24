import municipalidadImg from "/inicio/municipalidad-centro.png";
import "./inicio.css";

export default function StgoJoven() {
  return (
    <>
      {/* Imagen superior */}
      <div className="imagen-top">
        <img
          src={municipalidadImg}
          alt="Centro de la Municipalidad de Santiago"
        />
      </div>

      {/* Contenido principal */}
      <div className="stgojoven">
        <h2 className="titulo">STGO Joven</h2>

        <div className="bloque">
          <h2>¿En qué consiste?</h2>
          <p>
            STGO Joven es un espacio abierto a las juventudes de nuestra comuna,
            que busca favorecer la participación e integración local de las
            juventudes, a través de la articulación de programas y servicios
            según sus intereses y necesidades.
          </p>
          <p>
            Se ofrecen diversos servicios como orientación social, facilitación
            de espacios, vinculación con el medio y talleres en áreas de
            arte/cultura, autocuidado/vida sana, tecnología/innovación,
            participación/integración social, empleabilidad/oficios,
            aprendizajes y deporte.
          </p>
          <p>
            Estos talleres se generan a partir de los intereses que los propios
            jóvenes señalan como prioritarios, promoviendo su participación
            activa en el desarrollo comunal.
          </p>
        </div>

        <div className="bloque">
          <h2>¿A quiénes va dirigido?</h2>
          <p>
            Jóvenes entre 14 y 29 años que vivan, estudien, trabajen o se
            organicen en la comuna de Santiago.
          </p>
        </div>

        <div className="bloque">
          <h2>¿En qué lugar se presta el servicio?</h2>
          <p>
            Casa de las Juventudes ubicada en Virginia Opazo 22, a pasos de la
            estación de metro República.
          </p>
        </div>

        <div className="bloque">
          <h2>¿Qué requisitos se deben cumplir?</h2>
          <ul>
            <li>Tener entre 14 y 29 años.</li>
            <li>
              Vivir, estudiar, trabajar u organizarse en la comuna de Santiago.
            </li>
          </ul>
        </div>

        <div className="bloque">
          <h2>¿Qué documentos son necesarios?</h2>
          <ul>
            <li>
              Documento que certifique residencia, estudio, trabajo u
              organización en la comuna (cuenta a tu nombre, certificado de
              residencia, certificado de estudio o laboral).
            </li>
          </ul>
        </div>

        <p className="etiqueta">Servicio Gratuito</p>
      </div>
    </>
  );
}
