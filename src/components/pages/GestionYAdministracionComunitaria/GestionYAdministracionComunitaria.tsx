import '../ServiciosSociales/ServiciosSociales.css';
import imagen from '../../../assets/GestionYAdministracionComunitaria/IMAGENGYAC.png';

export default function GestionYAdministracionComunitaria() {
  return (
    <div className="gestion-y-administracion-comunitaria-container">
      <header className="ddc-header">
        <div className="side-title">Gestión y<br/>Administración<br/>Comunitaria</div>
        <div className="hero">
          <img src={imagen} alt="Edificio Gestión y Administración Comunitaria" className="hero-image" />
        </div>
      </header>

      <main className="ddc-content">
        <section className="intro-section">
          <h2>Subdirección de Gestión y Administración Comunitaria</h2>
          <div className="intro">
            <p>Esta Subdirección es la encargada de administrar los Centros Comunitarios Matta Sur, Carol Urzúa y Víctor Manuel, promoviendo actividades de interés para los vecinos, las vecinas y las organizaciones comunitarias de la comuna de Santiago.</p>

            <p>En los tres Centros Comunitarios están a disposición de la comunidad diversos talleres (manualidades, cocina, peluquería, deportivos y de formación artística), eventos culturales y recreativos, y espacios para el uso de las organizaciones para la promoción de la vida saludable.</p>

            <p>En lo administrativo, esta Subdirección presta asistencia técnica a la gestión de la Dirección de Desarrollo Comunitario. Coordina además, el desarrollo de acciones sociales conjuntas con otros servicios municipales, instituciones públicas o privadas en beneficio de la comunidad, como el dispositivo STGO en tu barrio.</p>
          </div>
        </section>
      </main>
    </div>
  );
}