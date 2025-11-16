import '../DirecciónDeDesarrolloComunitario/DireccionDeDesarrolloComunitario.css';
import imagen from '../../../assets/DireccionDeDesarrolloComunitario/IMAGENDDDC.png';
import logoFooter from '../../../assets/logo_footer.png';

export default function DireccionDeDesarrolloComunitario() {
  return (
    <div className="direccion-desarrollo-comunitario-container">
      <header className="ddc-header">
        <div className="side-title">Dirección de<br/>Desarrollo<br/>Comunitario</div>
        <div className="hero">
          <img src={imagen} alt="Edificio Dirección de Desarrollo Comunitario" className="hero-image" />
        </div>
      </header>

      <main className="ddc-content">
        {/* Intro / Subdirección visible en la imagen */}
        <section className="Cuadro2">
          <h2>Subdirección de Gestión y Administración Comunitaria</h2>
          <div className="intro">
            <p>Esta Subdirección es la encargada de administrar los Centros Comunitarios Matta Sur, Carol Urzúa y Víctor Manuel, promoviendo actividades de interés para los vecinos, las vecinas y las organizaciones comunitarias de la comuna de Santiago.</p>
            <p>En los tres Centros Comunitarios están a disposición de la comunidad diversos talleres (manualidades, cocina, peluquería, deportivos y de formación artística), eventos culturales y recreativos, y espacios para el uso de las organizaciones para la promoción de la vida saludable.</p>
            <p>En lo administrativo, esta Subdirección presta asistencia técnica a la gestión de la Dirección de Desarrollo Comunitario. Coordina además, el desarrollo de acciones sociales con otros servicios municipales, instituciones públicas o privadas en beneficio de la comunidad.</p>
          </div>

          <ul>
            <li><strong>Bernardita Bakovic Iturriaga</strong></li>
            <li><a href="mailto:bbakovic@munistgo.cl">bbakovic@munistgo.cl</a></li>
            <li>22 489 7478 | Santo Domingo 789, piso 3</li>
          </ul>
        </section>

        <hr className="section-sep" />

        {/* CUADRO 1: Dirección Principal */}
        <section className="Cuadro1">
          <h2>Dirección</h2>
          <ul>
            <li><strong>Eleonora Espinoza Hernández</strong></li>
            <li>Directora</li>
            <li><a href="mailto:eespinoza@munistgo.cl">eespinoza@munistgo.cl</a></li>
            <li>22 489 7471 / Santo Domingo 789, piso 3</li>
          </ul>
        </section>

        <hr className="section-sep" />

        {/* CUADRO 3 */}
        <section className="Cuadro3">
          <h2>Subdirección de Deportes y Recreación</h2>
          <ul>
            <li><strong>Angie Farías Videla</strong></li>
            <li><a href="mailto:afarias@munistgo.cl">afarias@munistgo.cl</a></li>
            <li>22 386 7394 – 22 386 7395 – 22 386 7396 – 22 386 7397 / Rondizzoni 1859, Interior Parque O’Higgins.</li>
          </ul>

          <h4>Departamento de Administración de Recintos Deportivos</h4>
          <ul>
            <li>Wladimir Pérez Rojas</li>
            <li><a href="mailto:wperez@munistgo.cl">wperez@munistgo.cl</a></li>
            <li>22 386 7394 – 22 386 7395 – 22 386 7396 – 22 386 7397 / Rondizzoni 1859, Interior Parque O’Higgins.</li>
          </ul>
        </section>

        <hr className="section-sep" />

        {/* CUADRO 4 */}
        <section className="Cuadro4">
          <h2>Subdirección de Desarrollo Económico Local (DIDEL)</h2>
          <ul>
            <li><strong>Leslie Briones Rojo</strong></li>
            <li><a href="mailto:lbriones@munistgo.cl">lbriones@munistgo.cl</a></li>
            <li>224897208 / Santo Domingo 789, piso 4</li>
          </ul>
        </section>

        <hr className="section-sep" />

        {/* Resto de secciones (mantengo el contenido original) */}
        <section className="Cuadro5">
          <h2>Subdirección de Participación Ciudadana</h2>
          <ul>
            <li><strong>Eduardo Marante Díaz</strong></li>
            <li><a href="mailto:emarante@munistgo.cl">emarante@munistgo.cl</a></li>
            <li>22 489 7490 / Santo Domingo 789, piso 3</li>
          </ul>
        </section>

        <hr className="section-sep" />

        <section className="Cuadro6">
          <h2>Subdirección de Servicios Sociales</h2>
          <ul>
            <li><strong>Marjorie Vásquez Acuña</strong></li>
            <li><a href="mailto:mvasquez@munistgo.cl">mvasquez@munistgo.cl</a></li>
            <li>22 489 7593 / Santo Domingo 789, piso 2</li>
          </ul>
        </section>

        <hr className="section-sep" />

        <section className="Cuadro7">
          <h2>Subdirección de Desarrollo Social</h2>
          <ul>
            <li><strong>Nora Muñoz Alfaro</strong></li>
            <li><a href="mailto:nmunoza@munistgo.cl">nmunoza@munistgo.cl</a></li>
            <li>22 489 7296 / Santo Domingo 789, piso 4</li>
          </ul>
        </section>

        <hr className="section-sep" />

        <section className="Cuadro8">
          <h2>Subdirección de Igualdad de Género, Diversidad Sexual e Inclusión</h2>
          <ul>
            <li><strong>María Jesús Garrido San Martin</strong></li>
            <li><a href="mailto:mgarrido@munistgo.cl">mgarrido@munistgo.cl</a></li>
            <li>22 489 7565 / Santo Domingo 789, piso 2</li>
          </ul>
        </section>
      </main>
    </div>
  );
}