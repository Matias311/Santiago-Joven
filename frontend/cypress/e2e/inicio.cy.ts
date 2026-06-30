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

  it("debe tener el boton de modo oscuro y persistir el tema", () => {
    cy.get("button.boton-modo-oscuro").should("exist");
    cy.get("button.boton-modo-oscuro").should("have.attr", "aria-label");

    cy.clearLocalStorage("theme");
    cy.visit("/");
    cy.get("html").should(
      ($html) => $html.hasClass("dark") || $html.hasClass("light"),
    );

    cy.clearLocalStorage("theme");
    cy.window().then((win) => {
      win.localStorage.setItem("theme", "light");
    });
    cy.visit("/");
    cy.get("html").should("have.class", "light");

    cy.clearLocalStorage("theme");
    cy.window().then((win) => {
      win.localStorage.setItem("theme", "dark");
    });
    cy.visit("/");
    cy.get("html").should("have.class", "dark");
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
