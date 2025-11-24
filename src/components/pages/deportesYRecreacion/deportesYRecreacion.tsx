import "./deportesYRecreacion.css";
import Deporte from "../../../assets/Deporte.png";

export default function DeportesRecreacion() {
  return (
    <main className="deportes-wrapper">
      <section className="deportes-header">
        <div className="deportes-title">
          <h2>
            Deportes y<br />
            Recreación
          </h2>
        </div>
        <div className="caja-imagen-deportes">
          <picture className="imagen-wrapper">
            <img
              src={Deporte}
              alt="Deportes y Recreación"
              className="imagen-principal"
            />
          </picture>
        </div>
      </section>
      <div className="deportes-content">
        <br></br>
        <h2>La Subdirección de Deportes y Recreación.</h2> a través de sus
        <p>
          seis (6) Programas: 1. Deporte Comunitario, 2. Desarrollo Deportivo
          Territorial, 3. Infraestructura Deportiva Comunitaria, 4. Santiago Te
          Mueve, 5. Deporte para todes y 6. Piscinas municipales, además de las
          líneas de intervención: Vinculación al territorio, torneos, eventos y
          competencias, deporte popular y fomento de la natación y seguridad en
          el agua; entrega una oferta gratuita de actividad física, deporte y
          recreación para las y los vecinos de Santiago, dirigido a los
          distintos grupos etarios, canalizada a través del uso de la
          infraestructura deportiva comunal y de los espacios públicos y áreas
          verdes existentes, logrando así democratizar el deporte, fomentando y
          fortaleciendo la vinculación con la comunidad, mediante la
          asociatividad y la cogestión con las organizaciones sociales. Este
          quehacer se enmarca en la dimensión estratégica “Derecho a la Ciudad”,
          del programa de gobierno local que busca específicamente a través de
          la promoción, fomento, y ejecución de actividades
          deportivo-recreativas, el uso y la recuperación de espacios públicos
          para la práctica deportiva, porque reconoce al DEPORTE como un pilar
          fundamental del buen vivir.
        </p>
        <h2>Servicios, productos y actividades.</h2>
        <ol>
          <li>
            <strong>Talleres deportivos presenciales y online.</strong>
            <p>
              Los talleres deportivos presenciales y online se realizan para los
              diversos grupos etarios (niños, niñas, jóvenes, adultos y personas
              mayores) utilizando parques, multicanchas, gimnasios, sedes
              sociales, salones parroquiales, centros comunitarios y piscinas al
              aire libre y temperada, que permitan desarrollar, mejorar y
              mantener en el tiempo las habilidades y cualidades motoras,
              ayudando a una mejor salud física, mental y social de las vecinas,
              vecinos y usuarios de Santiago.
            </p>
          </li>

          <li>
            <strong>Torneos, eventos y competencias.</strong>
            <p>
              Santiago Deporte realiza diversas convocatorias participativas a
              nivel comunal y barrial, con el objetivo de generar una instancia
              de recreación, motivación y acercamiento al deporte y a la vida
              sana, considerando criterios como la identidad y pertenencia
              barrial, y la vinculación con el territorio. Algunos de ellos son:
              Re-Corre Santiago, Jugando en Tu Barrio, Santiago Contigo en
              Verano, interbarrios, campeonatos, entre otros, los cuales
              alcanzan masiva asistencia de las vecinas y vecinos, además de
              permitir la vinculación directa con los establecimientos
              educacionales de nuestra comuna.
            </p>
          </li>

          <li>
            <strong>
              Prestación y convenios de uso de recintos deportivos administrados
              por la subdirección.
            </strong>
            <p>
              Ofrecer a las vecinas y vecinos de la comuna de Santiago, recintos
              deportivos y espacios en condiciones óptimas para la práctica de
              actividades físicas, deportivas y recreativas.
            </p>
          </li>

          <li>
            <strong>Beca Deportista Destacado – Destacada IMS.</strong>
            <p>
              Consiste en un aporte económico que el Municipio de Santiago
              entrega a través de la Subdirección de Deportes y Recreación, a
              personas naturales de la comuna que desarrollan prácticas
              deportivas a nivel de alta competencia, ya sea comunal, regional
              y/o nacional. Esta Beca privilegia la proyección deportiva de la
              alta competencia, de aquellos deportistas destacados que no
              disponen de los recursos necesarios para la concreción de sus
              méritos deportivos, preferentemente en niños, niñas y jóvenes de
              la comuna de Santiago.
            </p>
          </li>

          <li>
            <strong>Vinculación activa con el territorio.</strong>
            <p>
              La vinculación activa está destinada al mejoramiento de la gestión
              de las organizaciones comunitarias deportivas de la comuna de
              Santiago, con la finalidad de facilitarles el acceso a la
              institucionalidad deportiva, a través de asesorías en temas
              técnicas, administrativas y de capacitación. Asimismo, promover en
              la comuna la necesidad de formalizar grupos como organizaciones
              deportivas, de acuerdo a la legislación existente, fomentando la
              participación de las mismas en fondos concursables públicos y/o
              privados para su autogestión.
            </p>
          </li>

          <li>
            <strong>Piscinas municipales.</strong>
            <p>
              Tiene como finalidad poner a disposición de la comunidad de
              Santiago, la piscina municipal del Parque O´Higgins y la piscina
              municipal Parque Quinta Normal, en óptimas condiciones de higiene
              y seguridad, para la práctica de actividades deportivas,
              recreativas y de esparcimiento familiar, propio de la época
              estival (cursos de natación, talleres en tierra y de
              hidrogimnasia).
            </p>
          </li>
        </ol>
        <h2>Canales de Comunicación y difusión.</h2>
        <p>
          <strong>Dirección</strong>
          <br />
          Av. Rondizzoni Esq. Luis Cousiño, Interior del Parque O’Higgins
        </p>
        <p>
          <strong>Teléfonos</strong>
          <br />
          223867394 / 223867395 / 223867396
        </p>
        <p>
          <strong>Correo electrónico</strong>
          <br />
          deportes@munistgo.cl
        </p>
        <p>
          <strong>Instagram</strong>
          <br />
          @Stgodeporte
        </p>
      </div>
    </main>
  );
}
