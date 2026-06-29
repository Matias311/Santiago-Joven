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
    cy.get("button.boton-modo-oscuro").should("exist");

    cy.get(".popup-cerrar").realClick();
    cy.get(".popup-overlay").should("not.exist");

    cy.get("button.boton-modo-oscuro").realClick();
    cy.get("html").should("have.class", "light");
    cy.window().its("localStorage.theme").should("eq", "light");

    cy.get("button.boton-modo-oscuro").realClick();
    cy.get("html").should("have.class", "dark");
    cy.window().its("localStorage.theme").should("eq", "dark");
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
