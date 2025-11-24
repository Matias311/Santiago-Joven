import "./ServiciosSociales.css";
import wave from "/wave-effect.jpg";
import InformesSociales from "/ServiciosSocialesImg/InformesSociales.jpg";
import BecaEstudioMuni from "/ServiciosSocialesImg/BecaEstudiosMuni.jpg";
import AtencionSocial from "/ServiciosSocialesImg/AtencionSocial.jpg";
import ProgramaNocheDigna from "/ServiciosSocialesImg/ProgramaNocheDigna.jpg";
import TrasladoEnseres from "/ServiciosSocialesImg/TrasladoEnseres.jpg";
import EmergenciaSocial from "/ServiciosSocialesImg/EmergenciaSocial.jpg";
import AsesoriaReparacionesEstadistica from "/ServiciosSocialesImg/AsesoriaReparacionesEstadistica.jpg";
import FichaRegistro from "/ServiciosSocialesImg/FichaRegistroSocial.jpg";
import ProgramaFamilias from "/ServiciosSocialesImg/ProgramaFamilias.jpg";
import ProgramaHabitabilidad from "/ServiciosSocialesImg/ProgramaHabitabilidad.jpg";
import ProgramaPersonasCalle from "/ServiciosSocialesImg/ProgramapPersonasCalle.jpg";
import ProgramaApoyoIntegral from "/ServiciosSocialesImg/ProgramaApoyoIntegral.jpg";
import ResidenciaPortugal from "/ServiciosSocialesImg/ResidenciaPortugal.jpg";
import BonoxHijo from "/ServiciosSocialesImg/BonoHijo.jpg";
import PensionGarantizada from "/ServiciosSocialesImg/PensionGarantizada.jpg";
import AportePrevisional from "/ServiciosSocialesImg/AportePrevisional.jpg";
import SubsidioFamiliar from "/ServiciosSocialesImg/SubsidioFamiliar.jpg";
import SubsidioMaternal from "/ServiciosSocialesImg/SubsidioMaternal.jpg";
import SubsidioAMadre from "/ServiciosSocialesImg/SubsidioAMadre.jpg";
import SubsidioRecienNacido from "/ServiciosSocialesImg/SubsidioRecienNacido.jpg";
import PensionBasicaInvalidez from "/ServiciosSocialesImg/PensionBasicaInvalidez.jpg";
import SubsidioAseo from "/ServiciosSocialesImg/SubsidioAseo.jpg";
import SubsidioAgua from "/ServiciosSocialesImg/SubsidioAgua.jpg";

export default function ServiciosSociales() {
  return (
    <div>
      <h2 className="titulo-pagina">Servicios Sociales</h2>
      <div className="grid-tarjetas">
        {[
          {
            id: 1,
            img: InformesSociales,
            titulo: "Informes Sociales - Asistencia",
          },
          {
            id: 2,
            img: BecaEstudioMuni,
            titulo:
              "Beca de Estudio I. Municipalidad de Santiago - Postulación y Asignación",
          },
          {
            id: 3,
            img: AtencionSocial,
            titulo: "Atención Social Integral - Asistencia",
          },
          {
            id: 4,
            img: ProgramaNocheDigna,
            titulo:
              "Programa Noche Digna - Centro de Acogida Municipal para Personas en Situación de Calle",
          },
          {
            id: 5,
            img: TrasladoEnseres,
            titulo: "Traslado de Enseres en Vehículo Municipal",
          },
          {
            id: 6,
            img: EmergenciaSocial,
            titulo: "Emergencia Social",
          },
          {
            id: 7,
            img: AsesoriaReparacionesEstadistica,
            titulo: "Asesoría en Reparaciones Menores de Viviendas",
          },
          {
            id: 8,
            img: AsesoriaReparacionesEstadistica,
            titulo: "Estadísticas Sociales - Información",
          },
          {
            id: 9,
            img: FichaRegistro,
            titulo:
              "Ficha Registro Social de Hogares - Ingreso Personas en Situación de Calle",
          },
          {
            id: 10,
            img: FichaRegistro,
            titulo: "Ficha Registro Social de Hogares - Actualización",
          },
          {
            id: 11,
            img: FichaRegistro,
            titulo:
              "Ficha Registro Social de Hogares - Ingreso/Entrega de Cartola",
          },
          {
            id: 12,
            img: ProgramaFamilias,
            titulo: "Programa Familias",
          },
          {
            id: 13,
            img: ProgramaHabitabilidad,
            titulo: "Programa de Habitabilidad",
          },
          {
            id: 14,
            img: ProgramaPersonasCalle,
            titulo: "Programa Personas en Situación de Calle (PSC)",
          },
          {
            id: 15,
            img: ProgramaApoyoIntegral,
            titulo: "Programa de Apoyo Integral al Adulto Mayor «Vínculos»",
          },
          {
            id: 16,
            img: ResidenciaPortugal,
            titulo: "Residencia Portugal - Programa Noche Digna",
          },
          {
            id: 17,
            img: BonoxHijo,
            titulo: "Bono por Hijo",
          },
          {
            id: 18,
            img: PensionGarantizada,
            titulo: "Pensión Garantizada Universal",
          },
          {
            id: 19,
            img: AportePrevisional,
            titulo: "Aporte Previsional Solidario de Invalidez",
          },
          {
            id: 20,
            img: SubsidioFamiliar,
            titulo: "Subsidio Familiar",
          },
          {
            id: 21,
            img: SubsidioMaternal,
            titulo: "Subsidio Maternal",
          },
          {
            id: 22,
            img: SubsidioAMadre,
            titulo: "Subsidio a la Madre",
          },
          {
            id: 23,
            img: SubsidioRecienNacido,
            titulo: "Subsidio al Recién Nacido",
          },
          {
            id: 24,
            img: PensionBasicaInvalidez,
            titulo: "Pensión Básica Solidaria de Invalidez",
          },
          {
            id: 25,
            img: SubsidioAseo,
            titulo: "Subsidio de Aseo",
          },
          {
            id: 26,
            img: SubsidioAgua,
            titulo: "Subsidio Agua Potable",
          },
        ].map((item) => (
          <div className="tarjeta" key={item.id}>
            <img src={item.img} alt={item.titulo} className="imagen-tarjeta" />
            <h3 className="titulo-tarjeta">{item.titulo}</h3>
            <p className="link-tarjeta">Ver más »</p>
          </div>
        ))}
      </div>
      <div className="espacio-footer"></div>
      {/* Imagen inferior olas */}
      <img src={wave} className="wave-img" />
    </div>
  );
}
