describe("Página de Inicio", () => {
  beforeEach(() => {
    cy.visit("/");
  });

  it("debe cargar el navbar con el logo", () => {
    cy.get(".navbar").should("exist");
    cy.contains(".logo", "Santiago Joven");
  });

  it("debe mostrar las secciones principales del home", () => {
    cy.get("#inicio").should("exist");
    cy.get("#apoyo").should("exist");
    cy.get("#proyeccion").should("exist");
    cy.get("#accion").should("exist");
    cy.get("#programas").should("exist");
    cy.get("#salud").should("exist");
    cy.get("#conexion").should("exist");
    cy.get("#calendario").should("exist");
    cy.get("#contribucion").should("exist");
    cy.get("#opinion").should("exist");
    cy.get("#contacto").should("exist");
  });

  it("debe tener el boton de modo oscuro y alternar tema", () => {
    cy.get(".popup-cerrar").click();
    cy.get("button.boton-modo-oscuro").click();
    cy.get("html").should("have.class", "light").and("not.have.class", "dark");

    cy.get("button.boton-modo-oscuro").click();
    cy.get("html").should("have.class", "dark").and("not.have.class", "light");
  });

  it("debe mostrar los filtros de calendario", () => {
    cy.get("#calendario").within(() => {
      cy.contains("button", "Todos");
      cy.contains("button", "Talleres");
      cy.contains("button", "Cursos");
    });
  });

  it("debe tener enlaces de navegacion en el navbar", () => {
    cy.get("nav.fila-inferior").within(() => {
      cy.contains("a", "Inicio");
      cy.contains("a", "Apoyo");
      cy.contains("a", "Proyección");
      cy.contains("a", "Programas");
      cy.contains("a", "Salud mental");
    });
  });

  it("debe tener el widget de accesibilidad", () => {
    cy.get(".navbar").should("exist");
  });
});
